package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "processos_clientes")
public class ProcessoCliente extends TSActiveRecordAb<ProcessoCliente>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1986348420028543167L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="processos_clientes_id")
	@SequenceGenerator(name="processos_clientes_id", sequenceName="processos_clientes_id_seq")
	private Long id;
	
	@ManyToOne
	private Processo processo;
	
	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "situacao_processo_cliente_id")
	private SituacaoProcessoCliente situacaoProcessoCliente;
	
	@Transient
	private SituacaoProcessoCliente situacaoProcessoClienteTemp;
	
	@Column(name = "data_arquivamento")
	private Date dataArquivamento;

	public ProcessoCliente() {
	}
	
	public ProcessoCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public ProcessoCliente(Processo processo) {
		this.processo = processo;
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

	public SituacaoProcessoCliente getSituacaoProcessoClienteTemp() {
		return situacaoProcessoClienteTemp;
	}

	public void setSituacaoProcessoClienteTemp(SituacaoProcessoCliente situacaoProcessoClienteTemp) {
		this.situacaoProcessoClienteTemp = situacaoProcessoClienteTemp;
	}

	public boolean isProcessoClienteArquivado(){
		return Constantes.SITUACAO_PROCESSO_CLIENTE_ARQUIVADO.equals(this.situacaoProcessoClienteTemp.getId());
	}
	
	@Override
	public String toString() {
		return cliente.getNome();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
	
	@Override
	public List<ProcessoCliente> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from ProcessoCliente pc inner join pc.cliente c where 1 = 1 ");
		
		if(!TSUtil.isEmpty(processo) && !TSUtil.isEmpty(processo.getId())){
			query.append(" and pc.processo.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(processo) && !TSUtil.isEmpty(processo.getId())){
			params.add(getProcesso().getId());
		}
		
		return super.find(query.toString(), "c.matricula", params.toArray());
		
	}
	
}
