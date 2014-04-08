
package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.model.*;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "log_envio_emails")
public class LogEnvioEmail extends TSActiveRecordAb<LogEnvioEmail>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7509769000747437373L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "log_envio_emails_id")
	@SequenceGenerator(name="log_envio_emails_id", sequenceName="log_envio_emails_id_seq")
	private Long id;
	
	private Date data;
	
	private String assunto;
	
	private String texto;
	
	@ManyToOne
	private Cliente cliente;
	
	@Column(name = "email_cliente")
	private String emailCliente;
	
	public LogEnvioEmail() {
		super();
	}

	public LogEnvioEmail(String assunto, String texto, Cliente cliente, String emailCliente) {
		super();
		this.data = new Date();
		this.assunto = assunto;
		this.texto = texto;
		this.cliente = cliente;
		this.emailCliente = emailCliente;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
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
		LogEnvioEmail other = (LogEnvioEmail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<LogEnvioEmail> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from LogEnvioEmail lee where 1 = 1 ");
		
		if(!TSUtil.isEmpty(data)){
			query.append(" and date(lee.data) = date(?) ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(data)){
			params.add(TSParseUtil.dateToString(data, TSDateUtil.DD_MM_YYYY));
		}
		
		return super.find(query.toString(), "data", params.toArray());
	}
}
