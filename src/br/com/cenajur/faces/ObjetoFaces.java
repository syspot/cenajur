package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Objeto;

@ViewScoped
@ManagedBean(name = "objetoFaces")
public class ObjetoFaces extends CrudFaces<Objeto> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
