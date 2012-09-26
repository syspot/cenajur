package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "motivos_cancelamentos")
public class MotivoCancelamento extends TSActiveRecordAb<MotivoCancelamento>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5688189706221316751L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "motivos_cancelamentos_id")
	@SequenceGenerator(name="motivos_cancelamentos_id", sequenceName="motivos_cancelamentos_id_seq")
	private Long id;
	
	private String descricao;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
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
		MotivoCancelamento other = (MotivoCancelamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<MotivoCancelamento> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from MotivoCancelamento mc where 1 = 1 ");
		
		if(!TSUtil.isEmpty(descricao)){
			query.append("and ").append(CenajurUtil.semAcento("mc.descricao")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		return super.find(query.toString(), "descricao", params.toArray());
	}
	
}
