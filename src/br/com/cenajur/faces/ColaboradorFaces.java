package br.com.cenajur.faces;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Colaborador;

@SessionScoped
@ManagedBean(name = "colaboradorFaces")
public class ColaboradorFaces extends CrudFaces<Colaborador> {

	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
	@Override
	public String limpar(){
		this.crudModel = new Colaborador();
		return super.limpar();
	}
	
	@Override
	public String limparPesquisa(){
		this.crudPesquisaModel = new Colaborador();
		this.grid = Collections.emptyList();
		this.fieldOrdem = "nome";
		return super.limparPesquisa();
	}
	
}
