
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
@Table(name = "mensagem_comprovante_pagamento")
public class MensagemComprovantePagamento extends TSActiveRecordAb<MensagemComprovantePagamento>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 993288574640522835L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "mensagem_comprovante_pagamento_id")
	@SequenceGenerator(name="mensagem_comprovante_pagamento_id", sequenceName="mensagem_comprovante_pagamento_id_seq")
	private Long id;
	
	private String descricao;
	
	public MensagemComprovantePagamento() {
	}
	
	public MensagemComprovantePagamento(Long id) {
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
		MensagemComprovantePagamento other = (MensagemComprovantePagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
