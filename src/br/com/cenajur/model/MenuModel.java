package br.com.cenajur.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "menus")
public final class MenuModel extends TSActiveRecordAb<MenuModel> {

	@Id
	@GeneratedValue
    private Long id;
	
	@Column(name = "nome", nullable = false)
    private String nome;
	
	@Column(name = "flag_ativo", nullable = false)
    private Boolean flagAtivo;
	
	@Column(name = "ordem", nullable = false)
    private Integer ordem;
	
	@Column(name = "flag_expandido", nullable = false)
    private Boolean flagExpandido;
	
	@OneToMany(mappedBy = "menuModel")
    private List<PermissaoModel> permissoes;

	
	public MenuModel() {
	
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Boolean getFlagExpandido() {
		return flagExpandido;
	}

	public void setFlagExpandido(Boolean flagExpandido) {
		this.flagExpandido = flagExpandido;
	}

	public List<PermissaoModel> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<PermissaoModel> permissoes) {
		this.permissoes = permissoes;
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
		MenuModel other = (MenuModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

    
}
