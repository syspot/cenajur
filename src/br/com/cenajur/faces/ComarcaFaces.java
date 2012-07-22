package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Comarca;

@SessionScoped
@ManagedBean(name = "comarcaFaces")
public class ComarcaFaces extends CrudFaces<Comarca> {

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
