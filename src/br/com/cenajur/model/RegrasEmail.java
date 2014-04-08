
package br.com.cenajur.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "regras_email")
public class RegrasEmail extends TSActiveRecordAb<RegrasEmail>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5316982979712072845L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "regras_email_id")
	@SequenceGenerator(name="regras_email_id", sequenceName="regras_email_id_seq")
	private Long id;
	
	private String descricao;

	@Column(name = "dias_envio")
	private Integer diasEnvio;
	
	@OneToMany(mappedBy = "regrasEmail")
	private List<ConfiguracoesEmail> configuracoesEmails;
	
	public RegrasEmail() {
	}
	
	public RegrasEmail(Long id) {
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
	
	public Integer getDiasEnvio() {
		return diasEnvio;
	}

	public void setDiasEnvio(Integer diasEnvio) {
		this.diasEnvio = diasEnvio;
	}

	public List<ConfiguracoesEmail> getConfiguracoesEmails() {
		return configuracoesEmails;
	}

	public void setConfiguracoesEmails(List<ConfiguracoesEmail> configuracoesEmails) {
		this.configuracoesEmails = configuracoesEmails;
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
		RegrasEmail other = (RegrasEmail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
