package br.com.cenajur.faces;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.cenajur.model.ContadorModel;
import br.com.cenajur.model.TipoInformacao;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;

public abstract class RelatorioContadorFaces extends TSMainFaces{

	private List<SelectItem> tiposInformacoes;
	
	private TipoInformacao tipoInformacao;
	private Date dataInicio;
	private Date dataFim;
	
	private List<ContadorModel> grid;
	
	public RelatorioContadorFaces() {
		this.tipoInformacao = new TipoInformacao();
		this.tiposInformacoes = super.initCombo(new TipoInformacao().findAll("descricao"), "id", "descricao");
		this.dataInicio = new Date();
		this.dataFim = new Date();
	}
	
	public boolean validaCampos(){
		
		boolean valida = true;
		
		if(TSUtil.isEmpty(dataInicio)){
			valida = false;
			super.addErrorMessage("Data Inicial: campo obrigatório");
		}
		
		if(TSUtil.isEmpty(dataFim)){
			valida = false;
			super.addErrorMessage("Data Final: campo obrigatório");
		}
		
		return valida;
	}
	
	public abstract void executarPesquisa();
	
	public String atualizar(){
		
		if(validaCampos()){

			this.executarPesquisa();
		
		}
		
		return null;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<SelectItem> getTiposInformacoes() {
		return tiposInformacoes;
	}

	public void setTiposInformacoes(List<SelectItem> tiposInformacoes) {
		this.tiposInformacoes = tiposInformacoes;
	}

	public TipoInformacao getTipoInformacao() {
		return tipoInformacao;
	}

	public void setTipoInformacao(TipoInformacao tipoInformacao) {
		this.tipoInformacao = tipoInformacao;
	}

	public List<ContadorModel> getGrid() {
		return grid;
	}

	public void setGrid(List<ContadorModel> grid) {
		this.grid = grid;
	}

}
