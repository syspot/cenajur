package br.com.cenajur.faces;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Cliente;
import br.com.cenajur.relat.JasperUtil;
import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.web.faces.TSMainFaces;

@ViewScoped
@ManagedBean(name = "declaracaoSituacaoEconomicaFaces")
public class DeclaracaoSituacaoEconomicaFaces extends TSMainFaces{

	private Cliente clienteSelecionado;
	
	@PostConstruct
	public void init(){
		this.clienteSelecionado = new Cliente();
	}
	
	public String gerarRelatorio() {

        try {

            Map<String, Object> parametros = CenajurUtil.getHashMapReport();

            parametros.put("P_CLIENTE_ID", clienteSelecionado.getId());

            new JasperUtil().gerarRelatorio("declaracaoSituacaoEconomica.jasper", "declaracao_situacao_economica".toString(), parametros);

        } catch (Exception ex) {

            CenajurUtil.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        }

        return "sucesso";

    }
	
	public String addCliente(){
		return null;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	
}
