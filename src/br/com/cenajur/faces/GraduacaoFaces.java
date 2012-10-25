package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Graduacao;

@ViewScoped
@ManagedBean(name = "graduacaoFaces")
public class GraduacaoFaces extends CrudFaces<Graduacao> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
