package br.com.cenajur.model;

import java.util.ArrayList;
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

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "configuracoes_email")
public class ConfiguracoesEmail extends TSActiveRecordAb<ConfiguracoesEmail>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6645577463981314041L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="configuracoes_email_id")
	@SequenceGenerator(name="configuracoes_email_id", sequenceName="configuracoes_email_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "regras_email_id")
	private RegrasEmail regrasEmail;
	
	private String assunto;
	
	@Column(name = "corpo_email")
	private String corpoEmail;
	
	@Column(name = "flag_imediato")
	private Boolean flagImediato;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public RegrasEmail getRegrasEmail() {
		return regrasEmail;
	}

	public void setRegrasEmail(RegrasEmail regrasEmail) {
		this.regrasEmail = regrasEmail;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getCorpoEmail() {
		return corpoEmail;
	}

	public void setCorpoEmail(String corpoEmail) {
		this.corpoEmail = corpoEmail;
	}

	public Boolean getFlagImediato() {
		return flagImediato;
	}

	public void setFlagImediato(Boolean flagImediato) {
		this.flagImediato = flagImediato;
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
		ConfiguracoesEmail other = (ConfiguracoesEmail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<ConfiguracoesEmail> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from ConfiguracoesEmail ce where 1 = 1 ");
		
		if(!TSUtil.isEmpty(assunto)){
			query.append("and ").append(CenajurUtil.semAcento("ce.assunto")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		if(!TSUtil.isEmpty(regrasEmail) && !TSUtil.isEmpty(regrasEmail.getId())){
			query.append("and ce.regrasEmail.id = ? ");
		}
		
		if(!TSUtil.isEmpty(flagImediato)){
			query.append("and ce.flagImediato = ? ");
		}
		
		
		List<Object> params = new ArrayList<Object>();
		
		
		if(!TSUtil.isEmpty(assunto)){
			params.add(CenajurUtil.tratarString(assunto));
		}
		
		if(!TSUtil.isEmpty(regrasEmail) && !TSUtil.isEmpty(regrasEmail.getId())){
			params.add(regrasEmail.getId());
		}
		
		if(!TSUtil.isEmpty(flagImediato)){
			params.add(flagImediato);
		}
		
		return super.find(query.toString(), "assunto", params.toArray());
	}
	
}
