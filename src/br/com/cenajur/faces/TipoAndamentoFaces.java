package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.TipoAndamentoProcesso;

@ViewScoped
@ManagedBean(name = "tipoAndamentoFaces")
public class TipoAndamentoFaces extends CrudFaces<TipoAndamentoProcesso> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
