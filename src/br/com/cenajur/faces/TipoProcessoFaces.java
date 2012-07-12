package br.com.cenajur.faces;

import java.util.Collections;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.model.TipoProcessoModel;

@RequestScoped
@ManagedBean(name = "tipoProcessoFaces")
public class TipoProcessoFaces extends CrudFaces<TipoProcessoModel> {

	public TipoProcessoFaces() {
		
		this.crudModel = new TipoProcessoModel();
		
		this.crudPesquisaModel = new TipoProcessoModel();
		
		this.grid = Collections.emptyList();
		
	}
	
}
