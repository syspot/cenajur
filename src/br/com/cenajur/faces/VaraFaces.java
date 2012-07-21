package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Vara;

@SessionScoped
@ManagedBean(name = "varaFaces")
public class VaraFaces extends CrudFaces<Vara> {

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
