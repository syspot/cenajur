package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.TipoAndamentoProcesso;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.topsys.util.TSUtil;

@SessionScoped
@ManagedBean(name = "andamentoProcessoFaces")
public class AndamentoProcessoFaces extends CrudFaces<AndamentoProcesso> {

	private List<SelectItem> tiposAndamentosProcessos;
	
	private Processo processoSelecionado;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.tiposAndamentosProcessos = this.initCombo(new TipoAndamentoProcesso().findAll("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new AndamentoProcesso());
		getCrudModel().setTipoAndamentoProcesso(new TipoAndamentoProcesso());
		setFlagAlterar(Boolean.FALSE);
		return "sucesso";
	}
	
	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("descricao");
		setCrudPesquisaModel(new AndamentoProcesso());
		getCrudPesquisaModel().setProcesso(new Processo());
		getCrudPesquisaModel().setTipoAndamentoProcesso(new TipoAndamentoProcesso());
		setGrid(new ArrayList<AndamentoProcesso>());
		return "sucesso";
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(TSUtil.isEmpty(getCrudModel().getProcesso().getId())){
			erro = true;
			CenajurUtil.addErrorMessage("Processo: Campo obrigatório");
		}
		
		return erro;
	}
	
	@Override
	protected void prePersist() {
		getCrudModel().setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		getCrudModel().setDataAtualizacao(new Date());
	}
	
	public String addProcesso(){
		getCrudModel().setProcesso(this.processoSelecionado);
		CenajurUtil.addInfoMessage("Processo adicionado com sucesso");
		return "sucesso";
	}

	public List<SelectItem> getTiposAndamentosProcessos() {
		return tiposAndamentosProcessos;
	}

	public void setTiposAndamentosProcessos(List<SelectItem> tiposAndamentosProcessos) {
		this.tiposAndamentosProcessos = tiposAndamentosProcessos;
	}

	public Processo getProcessoSelecionado() {
		return processoSelecionado;
	}

	public void setProcessoSelecionado(Processo processoSelecionado) {
		this.processoSelecionado = processoSelecionado;
	}
	
}
