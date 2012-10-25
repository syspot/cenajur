package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.TipoProcesso;

@ViewScoped
@ManagedBean(name = "tipoProcessoFaces")
public class TipoProcessoFaces extends CrudFaces<TipoProcesso> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
