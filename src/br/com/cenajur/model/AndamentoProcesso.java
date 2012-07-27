package br.com.cenajur.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "andamentos_processos")
public class AndamentoProcesso extends TSActiveRecordAb<AndamentoProcesso>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	private Processo processo;
	
	private String descricao;
	
	@Column(name = "data_andamento")
	private Date dataAndamento;

	@ManyToOne
	@JoinColumn(name = "tipo_andamento_processo_id")
	private TipoAndamentoProcesso tipoAndamentoProcesso;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
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
	
	public TipoAndamentoProcesso getTipoAndamentoProcesso() {
		return tipoAndamentoProcesso;
	}

	public void setTipoAndamentoProcesso(TipoAndamentoProcesso tipoAndamentoProcesso) {
		this.tipoAndamentoProcesso = tipoAndamentoProcesso;
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
		AndamentoProcesso other = (AndamentoProcesso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
