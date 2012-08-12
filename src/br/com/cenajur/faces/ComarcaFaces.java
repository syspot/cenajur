package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Comarca;

@ViewScoped
@ManagedBean(name = "comarcaFaces")
public class ComarcaFaces extends CrudFaces<Comarca> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
