package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "grupos")
public class Grupo extends TSActiveRecordAb<Grupo>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 825014696209847193L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="grupos_id")
	@SequenceGenerator(name="grupos_id", sequenceName="grupos_id_seq")
	private Long id;
	
	private String descricao;
	
	@OneToMany(mappedBy = "grupo")
	private List<Colaborador> colaboradores;
	
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PermissaoGrupo> permissoesGrupos;
	
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

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public List<PermissaoGrupo> getPermissoesGrupos() {
		return permissoesGrupos;
	}

	public void setPermissoesGrupos(List<PermissaoGrupo> permissoesGrupos) {
		this.permissoesGrupos = permissoesGrupos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((permissoesGrupos == null) ? 0 : permissoesGrupos.hashCode());
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
		Grupo other = (Grupo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (permissoesGrupos == null) {
			if (other.permissoesGrupos != null)
				return false;
		} else if (!permissoesGrupos.equals(other.permissoesGrupos))
			return false;
		return true;
	}

	@Override
	public List<Grupo> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Grupo g where 1 = 1 ");
		
		if(!TSUtil.isEmpty(descricao)){
			query.append("and ").append(CenajurUtil.semAcento("g.descricao")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		return super.find(query.toString(), "descricao", params.toArray());
	}
	
}
