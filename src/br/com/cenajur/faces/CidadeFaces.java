package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Estado;

@SessionScoped
@ManagedBean(name = "cidadeFaces")
public class CidadeFaces extends CrudFaces<Cidade> {

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
		setCrudModel(new Cidade());
		getCrudModel().setEstado(new Estado());
		setFlagAlterar(Boolean.FALSE);
		return SUCESSO;
	}

	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("descricao");
		setCrudPesquisaModel(new Cidade());
		getCrudPesquisaModel().setEstado(new Estado());
		setGrid(new ArrayList<Cidade>());
		return "sucesso";
	}

	public List<SelectItem> getEstados() {
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}
	
}
