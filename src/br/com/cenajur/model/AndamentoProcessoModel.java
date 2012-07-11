package br.com.cenajur.model;

import java.util.Date;

public class AndamentoProcessoModel {

	private Long id;
	
	private ProcessoModel processoModel;
	
	private String descricao;
	
	private Date dataAndamento;

	private TipoAndamentoProcessoModel tipoAndamentoProcessoModel;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public ProcessoModel getProcessoModel() {
		return processoModel;
	}
	
	public void setProcessoModel(ProcessoModel processoModel) {
		this.processoModel = processoModel;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Date getDataAndamento() {
		return dataAndamento;
	}
	
	public void setDataAndamento(Date dataAndamento) {
		this.dataAndamento = dataAndamento;
	}
	
	public TipoAndamentoProcessoModel getTipoAndamentoProcessoModel() {
		return tipoAndamentoProcessoModel;
	}

	public void setTipoAndamentoProcessoModel(TipoAndamentoProcessoModel tipoAndamentoProcessoModel) {
		this.tipoAndamentoProcessoModel = tipoAndamentoProcessoModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AndamentoProcessoModel other = (AndamentoProcessoModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
