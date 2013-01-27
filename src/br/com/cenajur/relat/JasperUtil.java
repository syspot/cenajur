package br.com.cenajur.relat;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import br.com.cenajur.util.ConnectionFactory;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

public class JasperUtil {

	public void gerarRelatorio(String jasper, String nomeRelatorio, Map<String, Object> parametros) throws SQLException, ClassNotFoundException, JRException,
			IOException {

		Connection con = ConnectionFactory.getConnection();

		InputStream is = TSFacesUtil.getServletContext().getResourceAsStream("/WEB-INF/relatorios/" + jasper);

		if (!TSUtil.isEmpty(is)) {

			JasperPrint impressao = JasperFillManager.fillReport(is, parametros, con);

			if (!TSUtil.isEmpty(impressao)) {

				ExternalContext econtext = TSFacesUtil.getFacesContext().getExternalContext();

				HttpServletResponse response = (HttpServletResponse) econtext.getResponse();

				response.setContentType("APPLICATION/PDF");

				response.setHeader("Content-Disposition", "attachment; filename=" + nomeRelatorio + ".pdf");

				try {

					JasperExportManager.exportReportToPdfStream(impressao, response.getOutputStream());

					TSFacesUtil.getFacesContext().responseComplete();

				} catch (Exception e) {

					TSFacesUtil.addErrorMessage(e.getMessage());

					e.printStackTrace();

				} finally {

					ConnectionFactory.closeConnection(con);

				}

			}

		} else {

			TSFacesUtil.addErrorMessage("Não foi possível encontrar o arquivo.");
		}

	}

	public void gerarRelatorioHtml(String jasper, String nomeRelatorio, Map<String, Object> parametros) throws SQLException, ClassNotFoundException,
			JRException, IOException {

		Connection con = ConnectionFactory.getConnection();

		InputStream is = TSFacesUtil.getServletContext().getResourceAsStream("/WEB-INF/relatorios/" + jasper);

		if (!TSUtil.isEmpty(is)) {

			JasperPrint impressao = JasperFillManager.fillReport(is, parametros, con);
			
			if (!TSUtil.isEmpty(impressao)) {

				ExternalContext econtext = TSFacesUtil.getFacesContext().getExternalContext();
				
				HttpServletRequest request = (HttpServletRequest) econtext.getRequest();
				HttpServletResponse response = (HttpServletResponse) econtext.getResponse();
				
				
				JRHtmlExporter htmlExporter = new JRHtmlExporter();

				response.setContentType("text/html");

				response.setCharacterEncoding("ISO-8859-1");

				request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, impressao);
				
				htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, impressao);
				htmlExporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
				htmlExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "ISO-8859-1");
				htmlExporter.exportReport();
				
			}

		} else {

			TSFacesUtil.addErrorMessage("Não foi possível encontrar o arquivo.");
		}
		
	}

}
