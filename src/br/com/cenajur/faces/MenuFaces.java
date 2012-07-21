package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Menu;

@SessionScoped
@ManagedBean(name = "menuFaces")
public class MenuFaces extends CrudFaces<Menu> {

	@PostConstruct
	protected void init() {
		this.clearFields();
	}
		
	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("nome");
		return super.limparPesquisa();
	}
	
}
