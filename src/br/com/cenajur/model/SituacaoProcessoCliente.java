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
@Table(name = "situacoes_processos_clientes")
public class SituacaoProcessoCliente extends TSActiveRecordAb<SituacaoProcessoCliente>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1729070438517163081L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="situacoes_processos_clientes_id")
	@SequenceGenerator(name="situacoes_processos_clientes_id", sequenceName="situacoes_processos_clientes_id_seq")
	private Long id;
	
	private String descricao;
	
	private String css;
	


	public SituacaoProcessoCliente() {
		
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((css == null) ? 0 : css.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
		SituacaoProcessoCliente other = (SituacaoProcessoCliente) obj;
		if (css == null) {
			if (other.css != null)
				return false;
		} else if (!css.equals(other.css))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	public SituacaoProcessoCliente(Long id) {
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

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	

}
