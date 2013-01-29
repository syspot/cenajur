package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.DocumentoPesquisa;
import br.com.cenajur.model.TipoCategoria;

@ViewScoped
@ManagedBean(name = "documentoPesquisaFaces")
public class DocumentoPesquisaFaces extends PesquisaFaces<DocumentoPesquisa>{

	private List<SelectItem> tiposCategorias;
	
	@PostConstruct
	protected void init() {
		this.limpar();
		getModel().setTipoCategoria(new TipoCategoria());
		this.tiposCategorias = super.initCombo(new TipoCategoria().findAll("descricao"), "id", "descricao");
	}

	public List<SelectItem> getTiposCategorias() {
		return tiposCategorias;
	}

	public void setTiposCategorias(List<SelectItem> tiposCategorias) {
		this.tiposCategorias = tiposCategorias;
	}

}