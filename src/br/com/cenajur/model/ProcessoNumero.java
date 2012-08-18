package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "processos_numeros")
public class ProcessoNumero extends TSActiveRecordAb<ProcessoNumero>{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="processos_numeros_id")
	@SequenceGenerator(name="processos_numeros_id", sequenceName="processos_numeros_id_seq")
	private Long id;
	
	@ManyToOne
	private Processo processo;
	
	private String numero;

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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		ProcessoNumero other = (ProcessoNumero) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return numero;
	}
	
	@Override
	public List<ProcessoNumero> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from ProcessoNumero pn where 1 = 1 ");
		
		if(!TSUtil.isEmpty(processo) && !TSUtil.isEmpty(processo.getId())){
			query.append("and pn.processo.id = ? ");
		}
		
		if(!TSUtil.isEmpty(numero)){
			query.append(CenajurUtil.getParamSemAcento("pn.numero"));
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(processo) && !TSUtil.isEmpty(processo.getId())){
			params.add(processo.getId());
		}
		
		if(!TSUtil.isEmpty(numero)){
			params.add(CenajurUtil.tratarString(numero));
		}
		
		return super.find(query.toString(), null, params.toArray());
	}	
}
