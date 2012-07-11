package br.com.cenajur.model;

import java.util.Date;

public class CompromissoModel {

	private Long id;
	
	private ProcessoModel processoModel;
	
	private Date dataCompromisso;
	
	private SituacaoCompromissoModel situacaoCompromissoModel;
	
	private String local;
	
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

	public Date getDataCompromisso() {
		return dataCompromisso;
	}

	public void setDataCompromisso(Date dataCompromisso) {
		this.dataCompromisso = dataCompromisso;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
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

	public SituacaoCompromissoModel getSituacaoCompromissoModel() {
		return situacaoCompromissoModel;
	}

	public void setSituacaoCompromissoModel(SituacaoCompromissoModel situacaoCompromissoModel) {
		this.situacaoCompromissoModel = situacaoCompromissoModel;
	}
	
	
}
