package br.com.cenajur.model;

import java.util.Date;

public class AudienciaModel {

	private Long id;
	
	private ProcessoModel processoModel;
	
	private Date dataAudiencia;
	
	private SituacaoAudienciaModel situacaoAudienciaModel;
	
	private VaraModel varaModel;
	
	private ColaboradorModel advogado;
	
	private String descricao;

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

	public Date getDataAudiencia() {
		return dataAudiencia;
	}

	public void setDataAudiencia(Date dataAudiencia) {
		this.dataAudiencia = dataAudiencia;
	}

	public SituacaoAudienciaModel getSituacaoAudienciaModel() {
		return situacaoAudienciaModel;
	}

	public void setSituacaoAudienciaModel(
			SituacaoAudienciaModel situacaoAudienciaModel) {
		this.situacaoAudienciaModel = situacaoAudienciaModel;
	}

	public VaraModel getVaraModel() {
		return varaModel;
	}

	public void setVaraModel(VaraModel varaModel) {
		this.varaModel = varaModel;
	}

	public ColaboradorModel getAdvogado() {
		return advogado;
	}

	public void setAdvogado(ColaboradorModel advogado) {
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
		AudienciaModel other = (AudienciaModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
