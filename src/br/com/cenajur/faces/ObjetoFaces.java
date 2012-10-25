package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Objeto;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@ManagedBean(name = "objetoFaces")
public class ObjetoFaces extends CrudFaces<Objeto> {

	@PostConstruct
	protected void init() {
		this.clearFields();
		
		AutenticacaoFaces autenticacaoFaces = (AutenticacaoFaces) TSFacesUtil.getManagedBean("autenticacaoFaces");
		
		if(!TSUtil.isEmpty(autenticacaoFaces) && !TSUtil.isEmpty(autenticacaoFaces.getObjetoSelecionado())){
			this.setCrudModel(autenticacaoFaces.getObjetoSelecionado());
			this.detailEvent();
		}
		
	}
	
}
