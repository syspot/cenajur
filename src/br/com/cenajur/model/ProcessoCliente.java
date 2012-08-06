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

import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "processos_clientes")
public class ProcessoCliente extends TSActiveRecordAb<ProcessoCliente>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	private Processo processo;
	
	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "situacao_processo_cliente_id")
	private SituacaoProcessoCliente situacaoProcessoCliente;
	
	@Column(name = "data_arquivamento")
	private Date dataArquivamento;

	public ProcessoCliente() {
	}
	
	public ProcessoCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public SituacaoProcessoCliente getSituacaoProcessoCliente() {
		return situacaoProcessoCliente;
	}

	public void setSituacaoProcessoCliente(SituacaoProcessoCliente situacaoProcessoCliente) {
		this.situacaoProcessoCliente = situacaoProcessoCliente;
	}

	public Date getDataArquivamento() {
		return dataArquivamento;
	}

	public void setDataArquivamento(Date dataArquivamento) {
		this.dataArquivamento = dataArquivamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		ProcessoCliente other = (ProcessoCliente) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		return true;
	}
	
	public boolean isProcessoClienteArquivado(){
		return Constantes.SITUACAO_PROCESSO_CLIENTE_ARQUIVADO.equals(this.situacaoProcessoCliente.getId());
	}

}
