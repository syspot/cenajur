package br.com.cenajur.model;

import java.util.Date;

public class VisitaClienteModel {

	private Long id;
	
	private Date dataVisita;
	
	private SituacaoVisitaModel situacaoVisitaModel;
	
	private Cliente clienteModel;
	
	private Colaborador advogado;
	
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataVisita() {
		return dataVisita;
	}

	public void setDataVisita(Date dataVisita) {
		this.dataVisita = dataVisita;
	}

	public SituacaoVisitaModel getSituacaoVisitaModel() {
		return situacaoVisitaModel;
	}

	public void setSituacaoVisitaModel(SituacaoVisitaModel situacaoVisitaModel) {
		this.situacaoVisitaModel = situacaoVisitaModel;
	}

	public Cliente getClienteModel() {
		return clienteModel;
	}

	public void setClienteModel(Cliente clienteModel) {
		this.clienteModel = clienteModel;
	}

	public Colaborador getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Colaborador advogado) {
		this.advogado = advogado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		VisitaClienteModel other = (VisitaClienteModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
