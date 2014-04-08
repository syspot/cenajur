package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "mensagens")
public class Mensagem extends TSActiveRecordAb<Mensagem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2885062774835360183L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="mensagens_id")
	@SequenceGenerator(name="mensagens_id", sequenceName="mensagens_id_seq")
	private Long id;
	
	@ManyToOne
	private Colaborador remetente;
	
	private String texto;
	
	private Date data;
	
	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	
	@OneToMany(mappedBy = "mensagem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<MensagemDestinatario> mensagensDestinatarios;
	
	@ManyToOne
	private Mensagem mensagem;
	
	@Transient
	private boolean flagSelecionado;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public Colaborador getRemetente() {
		return remetente;
	}

	public void setRemetente(Colaborador remetente) {
		this.remetente = remetente;
	}

	public String getTexto() {
		return texto;
	}
	
	public String getTextoResumo() {
		return CenajurUtil.obterResumoGrid(texto, 23);
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public List<MensagemDestinatario> getMensagensDestinatarios() {
		return mensagensDestinatarios;
	}

	public void setMensagensDestinatarios(List<MensagemDestinatario> mensagensDestinatarios) {
		this.mensagensDestinatarios = mensagensDestinatarios;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isFlagSelecionado() {
		return flagSelecionado;
	}

	public void setFlagSelecionado(boolean flagSelecionado) {
		this.flagSelecionado = flagSelecionado;
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
		Mensagem other = (Mensagem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public List<Mensagem> pesquisarPorColaborador(Colaborador remetente) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" select distinct m from Mensagem m inner join fetch m.mensagensDestinatarios md where m.remetente.id = ? and m.flagAtivo = true");
		
		List<Object> param = new ArrayList<Object>();
		
		param.add(remetente.getId());
		
		return super.find(query.toString(), "m.data desc", param.toArray());
	}
		
}
