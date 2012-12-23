package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name="categorias_documentos")
public class CategoriaDocumento extends TSActiveRecordAb<CategoriaDocumento>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7343681663575154988L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorias_documentos_id")
	@SequenceGenerator(name="categorias_documentos_id", sequenceName="categorias_documentos_id_seq")
	private Long id;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "tipo_categoria_id")
	private TipoCategoria tipoCategoria;
	
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

	public TipoCategoria getTipoCategoria() {
		return tipoCategoria;
	}

	public void setTipoCategoria(TipoCategoria tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
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
		CategoriaDocumento other = (CategoriaDocumento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<CategoriaDocumento> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from CategoriaDocumento cd where 1 = 1 ");
		
		if(!TSUtil.isEmpty(descricao)){
			query.append("and ").append(CenajurUtil.semAcento("cd.descricao")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		if(!TSUtil.isEmpty(tipoCategoria) && !TSUtil.isEmpty(tipoCategoria.getId())){
			query.append("and cd.tipoCategoria.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		if(!TSUtil.isEmpty(tipoCategoria) && !TSUtil.isEmpty(tipoCategoria.getId())){
			params.add(tipoCategoria.getId());
		}
		
		return super.find(query.toString(), "descricao", params.toArray());
	}
}
