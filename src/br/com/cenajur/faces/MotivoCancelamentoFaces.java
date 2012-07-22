package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.MotivoCancelamento;

@SessionScoped
@ManagedBean(name = "motivoCancelamentoFaces")
public class MotivoCancelamentoFaces extends CrudFaces<MotivoCancelamento> {

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
