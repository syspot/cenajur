package br.com.cenajur.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.exception.TSApplicationException;

@Entity
@Table(name = "contador_emails")
public class ContadorEmail extends TSActiveRecordAb<ContadorEmail>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1560879297650206658L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="contador_emails_id")
	@SequenceGenerator(name="contador_emails_id", sequenceName="contador_emails_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tipo_informacao_id")
	private TipoInformacao tipoInformacao;
	
	private Date data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoInformacao getTipoInformacao() {
		return tipoInformacao;
	}

	public void setTipoInformacao(TipoInformacao tipoInformacao) {
		this.tipoInformacao = tipoInformacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
		ContadorEmail other = (ContadorEmail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public void gravar(TipoInformacao tipoInformacao){
		
		this.data = new Date();
		this.tipoInformacao = tipoInformacao;
		
		try {
			super.save();
		} catch (TSApplicationException e) {
			e.printStackTrace();
		}
	
	}
		
}
