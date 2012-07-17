package br.com.cenajur.faces;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.TipoProcesso;

@SessionScoped
@ManagedBean(name = "tipoProcessoFaces")
public class TipoProcessoFaces extends CrudFaces<TipoProcesso> {

	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
	@Override
	public String limpar(){
		this.crudModel = new TipoProcesso();
		return super.limpar();
	}
	
	@Override
	public String limparPesquisa(){
		this.crudPesquisaModel = new TipoProcesso();
		this.grid = Collections.emptyList();
		this.fieldOrdem = "descricao";
		return super.limparPesquisa();
	}
	
}
