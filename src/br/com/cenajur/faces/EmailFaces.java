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
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(!getCrudModel().getEmail().contains("gmail.com")){
			erro = true;
			super.addErrorMessage("É necessário utilizar um endereço gmail");
		}
		
		return erro;
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Email(Constantes.EMAIL_ID).getById());
		setTabIndex(1);
		return null;
	}
	
}
