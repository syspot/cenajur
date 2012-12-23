package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Estado;

@ViewScoped
@ManagedBean(name = "estadoFaces")
public class EstadoFaces extends CrudFaces<Estado> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
