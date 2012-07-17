package br.com.cenajur.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "grupos")
public class Grupo extends TSActiveRecordAb<Grupo>  {

	
	@Id
	@GeneratedValue
	private Long id;
	
	private String descricao;
	
	@OneToMany(mappedBy = "grupo")
	private List<Colaborador> colaboradores;
	
	@OneToMany(mappedBy = "grupo")
	private List<PermissaoGrupo> permissoesGrupos;
	
	public Long getId() {
		return id;
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
		return true;
	}
	
	
}
