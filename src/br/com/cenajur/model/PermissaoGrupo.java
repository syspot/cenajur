package br.com.cenajur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "permissoes_grupos")
public class PermissaoGrupo extends TSActiveRecordAb<PermissaoGrupo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6384093207908500049L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="permissoes_grupos_id")
	@SequenceGenerator(name="permissoes_grupos_id", sequenceName="permissoes_grupos_id_seq")
	private Long id;

	@ManyToOne
	private Grupo grupo;

	@ManyToOne
	private Permissao permissao;

	@Column(name = "flag_inserir")
	private Boolean flagInserir;

	@Column(name = "flag_alterar")
	private Boolean flagAlterar;

	@Column(name = "flag_excluir")
	private Boolean flagExcluir;

	public PermissaoGrupo() {
		super();
	}

	public PermissaoGrupo(Long id, Boolean flagInserir, Boolean flagAlterar, Boolean flagExcluir) {
		super();
		this.id = id;
		this.flagInserir = flagInserir;
		this.flagAlterar = flagAlterar;
		this.flagExcluir = flagExcluir;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public Boolean getFlagInserir() {
		return flagInserir;
	}

	public void setFlagInserir(Boolean flagInserir) {
		this.flagInserir = flagInserir;
	}

	public Boolean getFlagAlterar() {
		return flagAlterar;
	}

	public void setFlagAlterar(Boolean flagAlterar) {
		this.flagAlterar = flagAlterar;
	}

	public Boolean getFlagExcluir() {
		return flagExcluir;
	}

	public void setFlagExcluir(Boolean flagExcluir) {
		this.flagExcluir = flagExcluir;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + ((permissao == null) ? 0 : permissao.hashCode());
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
		PermissaoGrupo other = (PermissaoGrupo) obj;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (permissao == null) {
			if (other.permissao != null)
				return false;
		} else if (!permissao.equals(other.permissao))
			return false;
		return true;
	}
	
	public PermissaoGrupo obterPorGrupoPermissao(){
		return super.get(" select new PermissaoGrupo(pg.id, pg.flagInserir, pg.flagAlterar, pg.flagExcluir) from PermissaoGrupo pg where pg.grupo.id = ? and pg.permissao.id = ? ", getGrupo().getId(), getPermissao().getId());
	}

}
