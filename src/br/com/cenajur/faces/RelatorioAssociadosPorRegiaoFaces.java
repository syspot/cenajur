package br.com.cenajur.faces;

import java.util.Date;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.relat.JasperUtil;
import br.com.cenajur.util.CenajurUtil;

@ViewScoped
@ManagedBean(name = "relatorioAssociadosPorRegiaoFaces")
public class RelatorioAssociadosPorRegiaoFaces{

	private Date dataInicial;
	private Date dataFinal;
	
	public String gerarRelatorio() {

        try {

            StringBuilder nomeRelatorio = new StringBuilder("relatorioAssociadosPorCidade");

            Map<String, Object> parametros = CenajurUtil.getHashMapReport();

            parametros.put("DATA_INICIAL", dataInicial);
            parametros.put("DATA_FINAL", dataFinal);

            new JasperUtil().gerarRelatorio("relatAssociadosPorRegiao.jasper", nomeRelatorio.toString(), parametros);

        } catch (Exception ex) {

            CenajurUtil.addErrorMessage("N�o foi poss�vel gerar relat�rio.");

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
