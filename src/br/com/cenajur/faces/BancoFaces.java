package br.com.cenajur.faces;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Banco;

@SessionScoped
@ManagedBean(name = "bancoFaces")
public class BancoFaces extends CrudFaces<Banco> {

	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
	@Override
	public String limpar(){
		this.crudModel = new Banco();
		return super.limpar();
	}
	
	@Override
	public String limparPesquisa(){
		this.crudPesquisaModel = new Banco();
		this.grid = Collections.emptyList();
		this.fieldOrdem = "descricao";
		return super.limparPesquisa();
	}
	
}
