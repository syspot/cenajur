package br.com.cenajur.faces;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.relat.JasperUtil;
import br.com.cenajur.util.CenajurUtil;

@ViewScoped
@ManagedBean(name = "relatorioCancelamentoPorMotivoFaces")
public class RelatorioCancelamentoPorMotivoFaces {

	private Long ano;
	
	public String gerarRelatorio() {

        try {

            Map<String, Object> parametros = CenajurUtil.getHashMapReport();

            parametros.put("ano", ano);

            new JasperUtil().gerarRelatorio("relatCancelamento.jasper", "relatorio_cancelamento".toString(), parametros);

        } catch (Exception ex) {

            CenajurUtil.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        }

        return "sucesso";

    }

	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

}
