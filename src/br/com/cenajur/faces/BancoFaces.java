package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Banco;

@ViewScoped
@ManagedBean(name = "bancoFaces")
public class BancoFaces extends CrudFaces<Banco> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
