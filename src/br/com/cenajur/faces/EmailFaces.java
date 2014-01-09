package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Email;
import br.com.cenajur.util.Constantes;

@ViewScoped
@ManagedBean(name = "emailFaces")
public class EmailFaces extends CrudFaces<Email> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Email(Constantes.EMAIL_ID).getById());
		setFlagAlterar(Boolean.FALSE);
		setTabIndex(1);
		return null;
	}
	
}
