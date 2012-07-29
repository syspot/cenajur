package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.ParteContraria;
import br.com.cenajur.model.TipoDocumento;

@SessionScoped
@ManagedBean(name = "parteContrariaFaces")
public class ParteContrariaFaces extends CrudFaces<ParteContraria> {

	private List<SelectItem> tiposDocumentos;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombos();
	}
	
	private void initCombos(){
		this.tiposDocumentos = this.initCombo(new TipoDocumento().findAll("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new ParteContraria());
		getCrudModel().setTipoDocumento(new TipoDocumento());
		setFlagAlterar(Boolean.FALSE);
		return "sucessso";
	}
	
	@Override
	public String limparPesquisa(){
		setCrudPesquisaModel(new ParteContraria());
		getCrudPesquisaModel().setTipoDocumento(new TipoDocumento());
		this.setFieldOrdem("descricao");
		setGrid(new ArrayList<ParteContraria>());
		return "sucesso";
	}

	public List<SelectItem> getTiposDocumentos() {
		return tiposDocumentos;
	}

	public void setTiposDocumentos(List<SelectItem> tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

}
