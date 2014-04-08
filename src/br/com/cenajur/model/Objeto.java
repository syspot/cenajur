package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "objetos")
public class Objeto extends TSActiveRecordAb<Objeto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7158408979898638220L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="objetos_id")
	@SequenceGenerator(name="objetos_id", sequenceName="objetos_id_seq")
	private Long id;
	
	private String descricao;
	
	@OneToMany(mappedBy = "objeto")
	private List<Processo> processos;
	
	public Objeto() {
	}
	
	public Objeto(Long id) {
		this.id = id;
	}
	
	public Objeto(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
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

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
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
		Objeto other = (Objeto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<Objeto> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Objeto o where 1 = 1 ");
		
		if(!TSUtil.isEmpty(descricao)){
			query.append("and ").append(CenajurUtil.semAcento("o.descricao")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		return super.find(query.toString(), "descricao", params.toArray());
	}
	
	public List<Objeto> pesquisaPorProcessosColetivos() {
		return super.find(" select distinct new Objeto(o.id, o.descricao) from Objeto o inner join o.processos p where p.tipoProcesso.id = ? ", "o.descricao", Constantes.TIPO_PROCESSO_COLETIVO);
	}
	
}
