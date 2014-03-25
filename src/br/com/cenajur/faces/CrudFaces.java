package br.com.cenajur.faces;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import br.com.topsys.database.hibernate.TSActiveRecordIf;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

public abstract class CrudFaces <T extends TSActiveRecordIf<T>> extends TSMainFaces{

	private T crudModel;
	
	private T crudPesquisaModel;
	
	private List<T> grid;
	
	private Integer tabIndex;
	
	private boolean flagAlterar;
	
	private Integer statusPesquisa;
	
	@SuppressWarnings("unchecked")
	private Class<T> modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	@Override
	protected void clearFields() {
		this.limpar();
		this.limparPesquisa();
		this.tabIndex = 0;
	}
	
	public String limpar(){
		this.flagAlterar = false;
		this.tabIndex = 1;
		try {
			this.crudModel = modelClass.newInstance();
		} catch (Exception e) {
			throw new TSSystemException(e);
		}
		return null;
	}
	
	public String limparPesquisa(){
		this.grid = Collections.emptyList();
		this.tabIndex = 0;
		try {
			this.crudPesquisaModel = modelClass.newInstance();
		} catch (Exception e) {
			throw new TSSystemException(e);
		}
		
		return null;
	}
	
	protected boolean validaCampos(){
		return false;
	}
	
	protected void prePersist(){
	}
	
	protected void preInsert(){
	}
	
	protected void preUpdate(){
	}
	
	protected void preFind(){
	}
	
	protected void posDetail(){
	}
	
	protected void posPersist() throws TSApplicationException{
	}
	
	protected void posInsert(){
	}
	
	protected void posUpdate(){
	}
	
	protected void posFind(){
	}
	
	protected void tratarException(){
	}

	
	@Override
	protected String insert() throws TSApplicationException {
		
		this.setClearFields(Boolean.FALSE);
		
		this.setDefaultMessage(Boolean.FALSE);
		
		if(validaCampos()){
			return null;
		}
		
		this.prePersist();
		
		this.preInsert();
		
		try{
			this.crudModel.save();
		} catch(Exception e){
			tratarException();
			throw new TSApplicationException(e.getMessage());
		}
		
		this.posInsert();
		
		this.posPersist();
		
		this.setDefaultMessage(Boolean.TRUE);
		
		this.setFlagAlterar(Boolean.TRUE);
		
		this.limpar();
		
		this.tabIndex = 0;
		
		return null;
		
	}
	
	
	@Override
	protected String update() throws TSApplicationException {
		
		this.setClearFields(Boolean.FALSE);
		
		this.setDefaultMessage(Boolean.FALSE);

		if(validaCampos()){
			return null;
		}
		
		this.prePersist();
		
		this.preUpdate();
		
		try{
			this.crudModel.update();
		} catch(Exception e){
			tratarException();
			throw new TSApplicationException(e.getMessage());
		}
		
		this.posUpdate();
		
		this.posPersist();
		
		this.setDefaultMessage(Boolean.TRUE);
		
		this.limpar();
		
		this.tabIndex = 0;
		
		return null;
		
	}
	
	@Override
	protected String delete() throws TSApplicationException {
		
		this.crudModel.delete();
		
		this.limpar();
		
		this.grid = this.crudPesquisaModel.findByModel();
		
		this.tabIndex = 0;
		
		return null;
		
	}
	
	protected String detail() {

		this.crudModel = this.crudModel.getById();
		
		this.tabIndex = 1;
		
		this.flagAlterar = true;
		
		this.posDetail();
		
		return null;
		
	}
	
	@Override
	protected String find() {
		
		preFind();
		
		this.grid = this.crudPesquisaModel.findByModel();
		
		posFind();
		
		TSFacesUtil.gerarResultadoLista(this.grid);
		
		this.tabIndex = 0;
		
		return null;
		
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

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public boolean isFlagAlterar() {
		return flagAlterar;
	}

	public void setFlagAlterar(boolean flagAlterar) {
		this.flagAlterar = flagAlterar;
	}

	public Integer getStatusPesquisa() {
		return statusPesquisa;
	}

	public void setStatusPesquisa(Integer statusPesquisa) {
		this.statusPesquisa = statusPesquisa;
	}
	
}
