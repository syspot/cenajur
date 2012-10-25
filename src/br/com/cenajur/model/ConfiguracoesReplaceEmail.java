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
@Table(name = "configuracoes_replace_email")
public class ConfiguracoesReplaceEmail extends TSActiveRecordAb<ConfiguracoesReplaceEmail>{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4829539698064981213L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="configuracoes_replace_email_id")
	@SequenceGenerator(name="configuracoes_replace_email_id", sequenceName="configuracoes_replace_email_id_seq")
	private Long id;
	
	private String descricao;
	
	private String codigo;
	
	public ConfiguracoesReplaceEmail() { 
	}
	
	public ConfiguracoesReplaceEmail(Long id) {
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
		ConfiguracoesReplaceEmail other = (ConfiguracoesReplaceEmail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
}
