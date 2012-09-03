
package br.com.cenajur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "regras_bloqueios")
public class RegrasBloqueio extends TSActiveRecordAb<RegrasBloqueio>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5316982979712072845L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "regras_bloqueios_id")
	@SequenceGenerator(name="regras_bloqueios_id", sequenceName="regras_bloqueios_id_seq")
	private Long id;
	
	private String descricao;

	@Column(name = "dias_bloqueio")
	private Integer diasBloqueio;
	
	public RegrasBloqueio() {
	}
	
	public RegrasBloqueio(Long id) {
		this.id = id;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getDiasBloqueio() {
		return TSUtil.tratarInteger(diasBloqueio);
	}

	public void setDiasBloqueio(Integer diasBloqueio) {
		this.diasBloqueio = diasBloqueio;
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
		RegrasBloqueio other = (RegrasBloqueio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
