package br.com.cenajur.faces;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Comarca;

@SessionScoped
@ManagedBean(name = "comarcaFaces")
public class ComarcaFaces extends CrudFaces<Comarca> {

	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
	@Override
	public String limpar(){
		this.crudModel = new Comarca();
		return super.limpar();
	}
	
	@Override
	public String limparPesquisa(){
		this.crudPesquisaModel = new Comarca();
		this.grid = Collections.emptyList();
		this.fieldOrdem = "descricao";
		return super.limparPesquisa();
	}
	
}
