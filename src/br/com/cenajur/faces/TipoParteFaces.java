package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.TipoParte;

@ViewScoped
@ManagedBean(name = "tipoParteFaces")
public class TipoParteFaces extends CrudFaces<TipoParte> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
