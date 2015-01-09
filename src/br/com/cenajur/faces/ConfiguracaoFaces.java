package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Configuracao;

@ViewScoped
@ManagedBean(name = "configuracaoFaces")
public class ConfiguracaoFaces extends CrudFaces<Configuracao> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
	}
	
}
