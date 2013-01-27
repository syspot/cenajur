package br.com.cenajur.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import br.com.cenajur.util.ConnectionFactory;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

/**
 * Servlet implementation class GerarRelatorioClientesInadimplentes
 */
@WebServlet("/GerarRelatorioClientesInadimplentes")
public class GerarRelatorioClientesInadimplentes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GerarRelatorioClientesInadimplentes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map parametros = new HashMap();
		parametros.put(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
		try {
			gerarRelatorioHtml("relatClientesInadimplentes.jasper", "relatorio_clientes_inadimplentes".toString(), parametros, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private void gerarRelatorioHtml(String jasper, String nomeRelatorio, Map parametros, HttpServletRequest request,
			HttpServletResponse response) throws SQLException, ClassNotFoundException, JRException, IOException {

		Connection con = ConnectionFactory.getConnection();

		InputStream is = getServletContext().getResourceAsStream("/WEB-INF/relatorios/" + jasper);

		if (!TSUtil.isEmpty(is)) {

			JasperPrint impressao = JasperFillManager.fillReport(is, parametros, con);

			if (!TSUtil.isEmpty(impressao)) {

				JRHtmlExporter htmlExporter = new JRHtmlExporter();

				response.setContentType("text/html");

				response.setCharacterEncoding("ISO-8859-1");

				request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, impressao);

				htmlExporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
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
