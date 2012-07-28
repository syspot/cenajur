package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Estado;
import br.com.cenajur.model.Lotacao;

@SessionScoped
@ManagedBean(name = "lotacaoFaces")
public class LotacaoFaces extends CrudFaces<Lotacao> {

	private List<SelectItem> estados;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.estados = super.initCombo(new Estado().findAll("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Lotacao());
		getCrudModel().setCidade(new Cidade());
		setFlagAlterar(Boolean.FALSE);
		return SUCESSO;
	}

	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("descricao");
		setCrudPesquisaModel(new Lotacao());
		getCrudPesquisaModel().setCidade(new Cidade());
		setGrid(new ArrayList<Lotacao>());
		return "sucesso";
	}

	public List<SelectItem> getEstados() {
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}
	
}
