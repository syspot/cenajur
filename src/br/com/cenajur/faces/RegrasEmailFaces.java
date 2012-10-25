package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.RegrasEmail;
import br.com.topsys.exception.TSApplicationException;

@ViewScoped
@ManagedBean(name = "regrasEmailFaces")
public class RegrasEmailFaces extends CrudFaces<RegrasEmail> {

	private RegrasEmail regrasEmail;
	
	@PostConstruct
	protected void init() {
		regrasEmail = new RegrasEmail();
		this.clearFields();
		setGrid(regrasEmail.findByModel("descricao"));
	}
	
	@Override
	protected void posPersist() throws TSApplicationException {
		setGrid(regrasEmail.findByModel("descricao"));
	}
	
}
