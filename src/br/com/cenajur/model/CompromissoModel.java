package br.com.cenajur.model;

import java.util.Date;

import br.com.topsys.util.TSUtil;

public class CompromissoModel {

	private Long id;
	
	private Processo processoModel;
	
	private Date dataCompromisso;
	
	private SituacaoCompromissoModel situacaoCompromissoModel;
	
	private String local;
	
	private Colaborador advogado;
	
	private String descricao;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Processo getProcessoModel() {
		return processoModel;
	}

	public void setProcessoModel(Processo processoModel) {
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

	public SituacaoCompromissoModel getSituacaoCompromissoModel() {
		return situacaoCompromissoModel;
	}

	public void setSituacaoCompromissoModel(SituacaoCompromissoModel situacaoCompromissoModel) {
		this.situacaoCompromissoModel = situacaoCompromissoModel;
	}
	
	
}
