package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "documentos")
public class DocumentoPesquisa extends TSActiveRecordAb<DocumentoPesquisa> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6849280530069013442L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentos_id")
	@SequenceGenerator(name = "documentos_id", sequenceName = "documentos_id_seq")
	private Long id;

	@Column(name = "descricao_busca")
	private String descricaoBusca;

	@Column(name = "url_arquivo")
	private String urlArquivo;

	@Column(name = "categoria_id")
	private Long categoriaId;

	@Column(name = "categoria_descricao")
	private String categoriaDescricao;

	@Column(name = "titulo")
	private String titulo;

	@ManyToOne
	@JoinColumn(name = "tipo_categoria_id")
	private TipoCategoria tipoCategoria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricaoBusca() {
		return descricaoBusca;
	}

	public void setDescricaoBusca(String descricaoBusca) {
		this.descricaoBusca = descricaoBusca;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getCategoriaDescricao() {
		return categoriaDescricao;
	}

	public void setCategoriaDescricao(String categoriaDescricao) {
		this.categoriaDescricao = categoriaDescricao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
		DocumentoPesquisa other = (DocumentoPesquisa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public List<DocumentoPesquisa> findByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" select d.* from documentos d where 1 = 1 ");

		if (!TSUtil.isEmpty(tipoCategoria) && !TSUtil.isEmpty(tipoCategoria.getId())) {
			query.append(" and tipo_categoria_id = ? ");
		}

		if (!TSUtil.isEmpty(descricaoBusca)) {
			query.append("and d.descricao_busca @@ to_tsquery(?) ");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(tipoCategoria) && !TSUtil.isEmpty(tipoCategoria.getId())) {
			params.add(tipoCategoria.getId());
		}

		if (!TSUtil.isEmpty(descricaoBusca)) {
			params.add(CenajurUtil.getTsVectorBusca(descricaoBusca));
		}

		query.append(" order by d.tipo_categoria_id ");

		return super.findBySQL(query.toString(), params.toArray());
	}

}
