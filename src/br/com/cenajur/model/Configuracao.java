package br.com.cenajur.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "configuracoes")
public class Configuracao extends TSActiveRecordAb<Configuracao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6340701732388061789L;

	@Id
	private Long id;

	private String descricao;

	private String valor;

	public Configuracao() {
		super();
	}

	public Configuracao(Long id) {
		super();
		this.id = id;
	}

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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
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
		Configuracao other = (Configuracao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
