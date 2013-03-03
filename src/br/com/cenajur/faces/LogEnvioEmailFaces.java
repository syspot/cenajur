package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.LogEnvioEmail;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@ManagedBean(name = "logEnvioEmailFaces")
public class LogEnvioEmailFaces extends CrudFaces<LogEnvioEmail> {

	@PostConstruct
	protected void init() {
		
		this.clearFields();
		
		AutenticacaoFaces autenticacaoFaces = (AutenticacaoFaces) TSFacesUtil.getManagedBean("autenticacaoFaces");
		
		if(!TSUtil.isEmpty(autenticacaoFaces) && !TSUtil.isEmpty(autenticacaoFaces.getData())){
			this.getCrudPesquisaModel().setData(autenticacaoFaces.getData());
			setGrid(getCrudPesquisaModel().findByModel());
		}
		
	}
	
	@Override
	protected String insert() throws TSApplicationException {
		return null;
	}
	
	@Override
	protected String update() throws TSApplicationException {
		return null;
	}
	
	@Override
	protected String delete() throws TSApplicationException {
		return null;
	}
	
}
