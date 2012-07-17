package br.com.cenajur.model;

import java.util.Date;

public class Audiencia {

	private Long id;
	
	private Processo processo;
	
	private Date dataAudiencia;
	
	private SituacaoAudiencia situacaoAudiencia;
	
	private Vara vara;
	
	private Colaborador advogado;
	
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Date getDataAudiencia() {
		return dataAudiencia;
	}

	public void setDataAudiencia(Date dataAudiencia) {
		this.dataAudiencia = dataAudiencia;
	}

	public SituacaoAudiencia getSituacaoAudiencia() {
		return situacaoAudiencia;
	}

	public void setSituacaoAudiencia(SituacaoAudiencia situacaoAudiencia) {
		this.situacaoAudiencia = situacaoAudiencia;
	}

	public Vara getVara() {
		return vara;
	}

	public void setVara(Vara vara) {
		this.vara = vara;
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
		Audiencia other = (Audiencia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
