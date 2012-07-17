package br.com.cenajur.faces;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Objeto;

@SessionScoped
@ManagedBean(name = "objetoFaces")
public class ObjetoFaces extends CrudFaces<Objeto> {

	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
	@Override
	public String limpar(){
		this.crudModel = new Objeto();
		return super.limpar();
	}
	
	@Override
	public String limparPesquisa(){
		this.crudPesquisaModel = new Objeto();
		this.grid = Collections.emptyList();
		this.fieldOrdem = "descricao";
		return super.limparPesquisa();
	}
	
}
