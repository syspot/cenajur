package br.com.cenajur.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "menus")
public final class Menu extends TSActiveRecordAb<Menu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7457297598157001838L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="menus_id")
	@SequenceGenerator(name="menus_id", sequenceName="menus_id_seq")
    private Long id;
	
    private String nome;
    
    @Column(name = "flag_ativo")
    private Boolean flagAtivo;
    
    private Integer ordem;
    
    @Column(name = "flag_expandido")
    private Boolean flagExpandido;
    
	@OneToMany(mappedBy = "menu")
	@org.hibernate.annotations.OrderBy(clause = "ordem asc")
    private List<Permissao> permissoes;

	public Menu() {
	}
	
	public Menu(Boolean flagAtivo){
		this.flagAtivo = flagAtivo;
	}
	
	public Long getId() {
		return TSUtil.tratarLong(id);
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

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
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
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
