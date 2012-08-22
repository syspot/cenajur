package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "mensagens_destinatarios")
public class MensagemDestinatario extends TSActiveRecordAb<MensagemDestinatario>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="mensagens_destinatarios_id")
	@SequenceGenerator(name="mensagens_destinatarios_id", sequenceName="mensagens_destinatarios_id_seq")
	private Long id;
	
	@ManyToOne
	private Mensagem mensagem;
	
	@ManyToOne
	private Colaborador destinatario;
	
	@Column(name = "flag_lida")
	private Boolean flagLida;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public Colaborador getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Colaborador destinatario) {
		this.destinatario = destinatario;
	}

	public Boolean getFlagLida() {
		return flagLida;
	}

	public void setFlagLida(Boolean flagLida) {
		this.flagLida = flagLida;
	}
	
	public String getCss(){
		return flagLida ? "" : "mensagemNaoLida";
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
		MensagemDestinatario other = (MensagemDestinatario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.destinatario.getNome();
	}
	
	public List<MensagemDestinatario> pesquisarPorColaborador(Colaborador destinatario) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from MensagemDestinatario md where md.destinatario.id = ? ");
		
		List<Object> param = new ArrayList<Object>();
		
		param.add(destinatario.getId());
		
		return super.find(query.toString(), "md.mensagem.data desc", param.toArray());
	}
	
	public MensagemDestinatario obterPorMensagemColaborador(Mensagem mensagem, Colaborador destinatario) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from MensagemDestinatario md where md.mensagem.id = ? and md.destinatario.id = ?");
		
		List<Object> param = new ArrayList<Object>();
		
		param.add(mensagem.getId());
		param.add(destinatario.getId());
		
		return super.get(query.toString(), param.toArray());
	}
	
}
