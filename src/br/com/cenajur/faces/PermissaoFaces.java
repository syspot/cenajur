package br.com.cenajur.faces;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Menu;

@SessionScoped
@ManagedBean(name = "permissaoFaces")
public class PermissaoFaces extends CrudFaces<Menu> {

	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
	@Override
	public String limpar(){
		this.crudModel = new Menu();
		return super.limpar();
	}
	
	@Override
	public String limparPesquisa(){
		this.crudPesquisaModel = new Menu();
		this.grid = Collections.emptyList();
		this.fieldOrdem = "nome";
		return super.limparPesquisa();
	}
	
}
