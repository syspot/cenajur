package br.com.cenajur.faces;

import java.util.List;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

public class CrudFaces <T extends TSActiveRecordAb<T>> extends TSMainFaces{

	protected T crudModel;
	
	protected T crudPesquisaModel;
	
	protected List<T> grid;
	
	
	public CrudFaces() {
		
	}
	
	protected boolean validaCampos(){
		return false;
	}
	
	@Override
	protected String insert() throws TSApplicationException {
		
		this.setClearFields(Boolean.FALSE);
		
		this.setDefaultMessage(Boolean.FALSE);
		
		if(validaCampos()){
			return null;
		}
		
		this.crudModel.save();
		
		return SUCESSO;
		
	}
	
	
	@Override
	protected String update() throws TSApplicationException {
		
		this.setClearFields(Boolean.FALSE);
		
		this.setDefaultMessage(Boolean.FALSE);

		if(validaCampos()){
			return null;
		}
		
		this.crudModel.update();
		
		return SUCESSO;
		
	}
	
	@Override
	protected String delete() throws TSApplicationException {
		
		this.crudModel.delete();
		
		return SUCESSO;
		
	}
	
	@Override
	protected String find() {
		
		this.grid = this.crudPesquisaModel.findByModel();
		
		TSFacesUtil.gerarResultadoLista(this.grid);
		
		return SUCESSO;
		
	}

	public T getCrudModel() {
		return crudModel;
	}

	public void setCrudModel(T crudModel) {
		this.crudModel = crudModel;
	}

	public T getCrudPesquisaModel() {
		return crudPesquisaModel;
	}

	public void setCrudPesquisaModel(T crudPesquisaModel) {
		this.crudPesquisaModel = crudPesquisaModel;
	}

	public List<T> getGrid() {
		return grid;
	}

	public void setGrid(List<T> grid) {
		this.grid = grid;
	}
	
}
