package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.DocumentoPesquisa;

@ViewScoped
@ManagedBean(name = "documentoPesquisaFaces")
public class DocumentoPesquisaFaces extends PesquisaFaces<DocumentoPesquisa>{

	@PostConstruct
	protected void init() {
		this.limpar();
	}
	
}