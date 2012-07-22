package br.com.cenajur.faces;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Graduacao;

@SessionScoped
@ManagedBean(name = "graduacaoFaces")
public class GraduacaoFaces extends CrudFaces<Graduacao> {

	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
		
	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("descricao");
		return super.limparPesquisa();
	}
	
}
