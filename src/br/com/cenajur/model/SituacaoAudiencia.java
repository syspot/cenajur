package br.com.cenajur.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "situacoes_audiencias")
public class SituacaoAudiencia extends TSActiveRecordAb<SituacaoAudiencia>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6223875716194054587L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="situacoes_audiencias_id")
	@SequenceGenerator(name="situacoes_audiencias_id", sequenceName="situacoes_audiencias_id_seq")
	private Long id;
	
	private String descricao;
	
	private String css;

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

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
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
		SituacaoAudiencia other = (SituacaoAudiencia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
