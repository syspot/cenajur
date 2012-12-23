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
@Table(name = "situacoes_processos_partes_contrarias")
public class SituacaoProcessoParteContraria extends TSActiveRecordAb<SituacaoProcessoParteContraria>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1138576109721130512L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="situacoes_processos_partes_contrarias_id")
	@SequenceGenerator(name="situacoes_processos_partes_contrarias_id", sequenceName="situacoes_processsos_partes_contrarias_id_seq")
	private Long id;
	
	private String descricao;
	
	private String css;
	
	public SituacaoProcessoParteContraria() {
	}
	
	public SituacaoProcessoParteContraria(Long id) {
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
		SituacaoProcessoParteContraria other = (SituacaoProcessoParteContraria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
