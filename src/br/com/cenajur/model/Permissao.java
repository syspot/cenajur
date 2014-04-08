package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "permissoes")
public class Permissao extends TSActiveRecordAb<Permissao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 814393364038640001L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="permissoes_id")
	@SequenceGenerator(name="permissoes_id", sequenceName="permissoes_id_seq")
	private Long id;
	
	private String descricao;
	
	private String faces;
	
	private String url;
	
	@Column(name = "tab_ativa")
	private Integer tabAtiva;
	
	@ManyToOne
	private Menu menu;
	
	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	
	@OneToMany(mappedBy="permissao")
	private List<PermissaoGrupo> permissoesGrupos;

	public Permissao() {
	}
	
	public Permissao(Long id) {
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

	public String getFaces() {
		return faces;
	}

	public void setFaces(String faces) {
		this.faces = faces;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getTabAtiva() {
		return tabAtiva;
	}

	public void setTabAtiva(Integer tabAtiva) {
		this.tabAtiva = tabAtiva;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
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
		Permissao other = (Permissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<Permissao> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Permissao p where 1 = 1 ");
		
		if(!TSUtil.isEmpty(descricao)){
			query.append("and lower(p.descricao) like ? ");
		}
		
		if(!TSUtil.isEmpty(menu) && !TSUtil.isEmpty(menu.getId())){
			query.append("and p.menu.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		if(!TSUtil.isEmpty(menu) && !TSUtil.isEmpty(menu.getId())){
			params.add(menu.getId());
		}
		
		return super.find(query.toString(), "descricao", params.toArray());
	}
}
