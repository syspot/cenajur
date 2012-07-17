package br.com.cenajur.faces;

import java.util.List;

import br.com.topsys.database.hibernate.TSActiveRecordIf;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

public class CrudFaces <T extends TSActiveRecordIf<T>> extends TSMainFaces{

	protected T crudModel;
	
	protected T crudPesquisaModel;
	
	protected List<T> grid;
	
	protected String fieldOrdem;
	
	private Integer tabIndex;
	
	private boolean flagAlterar;
	
	@Override
	protected void clearFields() {
		this.limpar();
		this.limparPesquisa();
		this.tabIndex = 0;
		this.flagAlterar = false;
	}
	
	public String limpar(){
		this.flagAlterar = false;
		return SUCESSO;
	}
	
	public String limparPesquisa(){
		return SUCESSO;
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
		
		this.limpar();
		
		this.setDefaultMessage(Boolean.TRUE);
		
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
		
		this.limpar();
		
		this.setDefaultMessage(Boolean.TRUE);
		
		return SUCESSO;
		
	}
	
	@Override
	protected String delete() throws TSApplicationException {
		
		this.crudModel.delete();
		
		this.limpar();
		
		this.grid = this.crudPesquisaModel.findByModel();
		
		this.tabIndex = 1;
		
		return SUCESSO;
		
	}
	
	@Override
	protected String detail() {

		this.crudModel = this.crudModel.getByModel();
		
		this.tabIndex = 0;
		
		this.flagAlterar = true;
		
		return SUCESSO;
		
	}
	
	@Override
	protected String find() {
		
		this.grid = this.crudPesquisaModel.findByModel(fieldOrdem);
		
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
	
}
