package br.com.cenajur.model;

import java.util.Date;

public class TarefaModel {

	private Long id;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private SituacaoTarefaModel situacaoTarefaModel;
	
	private ColaboradorModel colaboradorResponsavel;
	
	private ColaboradorModel colaboradorSupervisor;
	
	private String descricao;
	
	private String resultado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public SituacaoTarefaModel getSituacaoTarefaModel() {
		return situacaoTarefaModel;
	}

	public void setSituacaoTarefaModel(SituacaoTarefaModel situacaoTarefaModel) {
		this.situacaoTarefaModel = situacaoTarefaModel;
	}

	public ColaboradorModel getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(ColaboradorModel colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public ColaboradorModel getColaboradorSupervisor() {
		return colaboradorSupervisor;
	}

	public void setColaboradorSupervisor(ColaboradorModel colaboradorSupervisor) {
		this.colaboradorSupervisor = colaboradorSupervisor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
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
		TarefaModel other = (TarefaModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
