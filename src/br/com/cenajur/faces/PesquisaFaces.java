package br.com.cenajur.faces;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;

import br.com.topsys.database.hibernate.TSActiveRecordIf;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.web.util.TSFacesUtil;

public abstract class PesquisaFaces <T extends TSActiveRecordIf<T>>{

	private T model;
	
	private List<T> grid;
	
	private String fieldOrdem;
	
	public String getFieldOrdem() {
		return fieldOrdem;
	}

	public void setFieldOrdem(String fieldOrdem) {
		this.fieldOrdem = fieldOrdem;
	}

	private Class<T> modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	public String limpar(){
		try {
			this.model = modelClass.newInstance();
		} catch (Exception e) {
			throw new TSSystemException(e);
		}
		return "sucesso";
	}
	
	public String find() {
		
		this.grid = this.model.findByModel(getFieldOrdem());
		
		TSFacesUtil.gerarResultadoLista(this.grid);
		
		return "sucesso";
		
	}
	
	protected final List<SelectItem> initCombo(Collection coll,String nomeValue,String nomeLabel) {
		
		List<SelectItem> list = new ArrayList<SelectItem>();
		
		for(Object o:coll){
			
			try {
			
				list.add(new SelectItem(BeanUtils.getProperty(o,nomeValue),BeanUtils.getProperty(o,nomeLabel)));
			
			} catch (Exception e) {
				
				e.printStackTrace();
				
				throw new TSSystemException(e);
			} 
		}
		return list;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public List<T> getGrid() {
		return grid;
	}

	public void setGrid(List<T> grid) {
		this.grid = grid;
	}

}
