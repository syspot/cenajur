package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Vara;

@ViewScoped
@ManagedBean(name = "varaFaces")
public class VaraFaces extends CrudFaces<Vara> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
