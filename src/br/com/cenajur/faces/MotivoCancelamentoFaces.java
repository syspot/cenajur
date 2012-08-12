package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.MotivoCancelamento;

@ViewScoped
@ManagedBean(name = "motivoCancelamentoFaces")
public class MotivoCancelamentoFaces extends CrudFaces<MotivoCancelamento> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
