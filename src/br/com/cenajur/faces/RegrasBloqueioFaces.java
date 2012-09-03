package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.RegrasBloqueio;
import br.com.topsys.exception.TSApplicationException;

@ViewScoped
@ManagedBean(name = "regrasBloqueioFaces")
public class RegrasBloqueioFaces extends CrudFaces<RegrasBloqueio> {

	private RegrasBloqueio regrasBloqueio;
	
	@PostConstruct
	protected void init() {
		regrasBloqueio = new RegrasBloqueio();
		this.clearFields();
		setGrid(regrasBloqueio.findByModel("descricao"));
	}
	
	@Override
	protected void posPersist() throws TSApplicationException {
		setGrid(regrasBloqueio.findByModel("descricao"));
	}
	
}
