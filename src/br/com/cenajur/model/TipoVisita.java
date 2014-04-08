package br.com.cenajur.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name="tipos_visitas")
public class TipoVisita extends TSActiveRecordAb<TipoVisita>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3414883765084689901L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tipos_visitas_id")
	@SequenceGenerator(name="tipos_visitas_id", sequenceName="tipos_visitas_id_seq")
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
		TipoVisita other = (TipoVisita) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
