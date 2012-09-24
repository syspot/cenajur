package br.com.cenajur.faces;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.cenajur.relat.JasperUtil;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.util.TSUtil;

public abstract class RelatorioModeloFaces{

	private String nomeRelatorio;
	private String nomeRelatorioImpressao;
	private Date dataInicial;
	private Date dataFinal;
	
	private boolean validaCampos(){
		
		boolean erro = false;
		
		if(!TSUtil.isEmpty(dataInicial) && TSUtil.isEmpty(dataFinal)){
			erro = false;
			CenajurUtil.addErrorMessage("Necessário selecionar a data final");
		}
		
		if(TSUtil.isEmpty(dataInicial) && !TSUtil.isEmpty(dataFinal)){
			erro = false;
			CenajurUtil.addErrorMessage("Necessário selecionar a data inicial");
		}
		
		return erro;
	}
	
	public String gerarRelatorio() {

		if(validaCampos()){
			return null;
		}
		
        try {

            Map<String, Object> parametros = new HashMap<String, Object>();

            parametros.put("SUBREPORT_DIR", Constantes.PASTA_RELATORIOS);
            parametros.put("DATA_INICIAL", dataInicial);
            parametros.put("DATA_FINAL", dataFinal);

            new JasperUtil().gerarRelatorio(nomeRelatorio, nomeRelatorioImpressao.toString(), parametros);

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

	public String getNomeRelatorio() {
		return nomeRelatorio;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public String getNomeRelatorioImpressao() {
		return nomeRelatorioImpressao;
	}

	public void setNomeRelatorioImpressao(String nomeRelatorioImpressao) {
		this.nomeRelatorioImpressao = nomeRelatorioImpressao;
	}
	
}
