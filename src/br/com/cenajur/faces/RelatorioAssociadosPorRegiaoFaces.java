package br.com.cenajur.faces;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.relat.JasperUtil;
import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@ManagedBean(name = "relatorioAssociadosPorRegiaoFaces")
public class RelatorioAssociadosPorRegiaoFaces{

	private Date dataInicial;
	private Date dataFinal;
	
	public String gerarRelatorio() {

        try {

            StringBuilder nomeRelatorio = new StringBuilder("relatorioProcesso_2012");

            Map<String, Object> parametros = new HashMap<String, Object>();

            parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF/relatorios/"));
            parametros.put("DATA_INICIAL", dataInicial);
            parametros.put("DATA_FINAL", dataFinal);

            new JasperUtil().gerarRelatorio("relatAssociadosPorRegiao.jasper", nomeRelatorio.toString(), parametros);

        } catch (Exception ex) {

            CenajurUtil.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        }

        return "sucesso";

    }

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	
	
}
