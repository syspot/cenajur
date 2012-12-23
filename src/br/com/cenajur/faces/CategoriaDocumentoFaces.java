package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.TipoCategoria;

@ViewScoped
@ManagedBean(name = "categoriaDocumentoFaces")
public class CategoriaDocumentoFaces extends CrudFaces<CategoriaDocumento> {

	private List<SelectItem> tiposCategorias;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
		
	}
	
	private void initCombo(){
		this.tiposCategorias = super.initCombo(new TipoCategoria().findAll("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new CategoriaDocumento());
		getCrudModel().setTipoCategoria(new TipoCategoria());
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new CategoriaDocumento());
		getCrudPesquisaModel().setTipoCategoria(new TipoCategoria());
		setGrid(new ArrayList<CategoriaDocumento>());
		return null;
	}

	public List<SelectItem> getTiposCategorias() {
		return tiposCategorias;
	}

	public void setTiposCategorias(List<SelectItem> tiposCategorias) {
		this.tiposCategorias = tiposCategorias;
	}
	
}
