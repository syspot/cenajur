
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
@Table(name = "email")
public class Email extends TSActiveRecordAb<Email>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4154522374751545623L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "email_id")
	@SequenceGenerator(name="email_id", sequenceName="email_id_seq")
	private Long id;
	
	private String email;
	
	private String senha;

	private String smtp;
	
	private Long porta;
	
	public Email() {
	}
	
	public Email(Long id) {
		this.id = id;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public Long getPorta() {
		return porta;
	}

	public void setPorta(Long porta) {
		this.porta = porta;
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
		Email other = (Email) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
