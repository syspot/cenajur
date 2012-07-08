package br.com.cenajur.faces;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.model.TipoProcessoModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

@RequestScoped
@ManagedBean(name = "tipoProcessoFaces")
public class TipoProcessoFaces extends TSMainFaces {

	private TipoProcessoModel tipoProcessoModel;
	private List<TipoProcessoModel> listTipoProcessoModel;
	
	public TipoProcessoFaces() {
		this.tipoProcessoModel = new TipoProcessoModel();  
		this.listTipoProcessoModel = Collections.emptyList();
	}
	
	@Override
	protected void clearFields() {
		this.tipoProcessoModel = new TipoProcessoModel();  
	}
	
	@Override
	protected String insert() throws TSApplicationException {
		
		this.tipoProcessoModel.save();
		
		this.listTipoProcessoModel = this.tipoProcessoModel.findAll();
		
		return null;
	}
	
	@Override
	protected String find() {
		
		this.listTipoProcessoModel = this.tipoProcessoModel.findByModel();
		
		return null;
	}

	public TipoProcessoModel getTipoProcessoModel() {
		return tipoProcessoModel;
	}

	public void setTipoProcessoModel(TipoProcessoModel tipoProcessoModel) {
		this.tipoProcessoModel = tipoProcessoModel;
	}

	public List<TipoProcessoModel> getListTipoProcessoModel() {
		return listTipoProcessoModel;
	}

	public void setListTipoProcessoModel(
			List<TipoProcessoModel> listTipoProcessoModel) {
		this.listTipoProcessoModel = listTipoProcessoModel;
	}
	
}
