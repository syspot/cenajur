package br.com.cenajur.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "processos_partes_contrarias")
public class ProcessoParteContraria extends TSActiveRecordAb<ProcessoParteContraria>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3310580116691932208L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="processos_partes_contrarias_id")
	@SequenceGenerator(name="processos_partes_contrarias_id", sequenceName="processos_partes_contrarias_id_seq")
	private Long id;
	
	@ManyToOne
	private Processo processo;
	
	@ManyToOne
	@JoinColumn(name = "parte_contraria_id")
	private ParteContraria parteContraria;
	
	@ManyToOne
	@JoinColumn(name = "situacao_processo_parte_contraria_id")
	private SituacaoProcessoParteContraria situacaoProcessoParteContraria;
	
	@Transient
	private SituacaoProcessoParteContraria situacaoProcessoParteContrariaTemp;
	
	@Column(name = "data_arquivamento")
	private Date dataArquivamento;

	public ProcessoParteContraria() {
	}
	
	public ProcessoParteContraria(ParteContraria parteContraria) {
		this.parteContraria = parteContraria;
	}
	
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

	public ParteContraria getParteContraria() {
		return parteContraria;
	}

	public void setParteContraria(ParteContraria parteContraria) {
		this.parteContraria = parteContraria;
	}

	public SituacaoProcessoParteContraria getSituacaoProcessoParteContraria() {
		return situacaoProcessoParteContraria;
	}

	public void setSituacaoProcessoParteContraria(SituacaoProcessoParteContraria situacaoProcessoParteContraria) {
		this.situacaoProcessoParteContraria = situacaoProcessoParteContraria;
	}

	public Date getDataArquivamento() {
		return dataArquivamento;
	}

	public void setDataArquivamento(Date dataArquivamento) {
		this.dataArquivamento = dataArquivamento;
	}

	public SituacaoProcessoParteContraria getSituacaoProcessoParteContrariaTemp() {
		return situacaoProcessoParteContrariaTemp;
	}

	public void setSituacaoProcessoParteContrariaTemp(SituacaoProcessoParteContraria situacaoProcessoParteContrariaTemp) {
		this.situacaoProcessoParteContrariaTemp = situacaoProcessoParteContrariaTemp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parteContraria == null) ? 0 : parteContraria.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
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
		ProcessoParteContraria other = (ProcessoParteContraria) obj;
		if (parteContraria == null) {
			if (other.parteContraria != null)
				return false;
		} else if (!parteContraria.equals(other.parteContraria))
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		return true;
	}
	

	public boolean isProcessoParteContrariaArquivado(){
		return Constantes.SITUACAO_PROCESSO_PARTE_CONTRARIA_ARQUIVADO.equals(this.situacaoProcessoParteContrariaTemp.getId());
	}

}
