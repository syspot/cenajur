package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.TipoColaborador;

@ViewScoped
@ManagedBean(name = "tipoColaboradorFaces")
public class TipoColaboradorFaces extends CrudFaces<TipoColaborador> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
