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

import br.com.cenajur.util.Constantes;
import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "documentos_colaboradores")
public class DocumentoColaborador extends TSActiveRecordAb<DocumentoColaborador> {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2067775426703495427L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="documentos_colaboradores_id")
	@SequenceGenerator(name="documentos_colaboradores_id", sequenceName="documentos_colaboradores_id_seq")
	private Long id;
	
	@ManyToOne
	private Colaborador colaborador;
	
	private String arquivo;
	
	private String descricao;
	
	@Column(name = "descricao_busca")
	private String descricaoBusca;
	
	@ManyToOne
	@JoinColumn(name = "categoria_documento_id")
	private CategoriaDocumento categoriaDocumento;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricaoBusca() {
		return descricaoBusca;
	}

	public void setDescricaoBusca(String descricaoBusca) {
		this.descricaoBusca = descricaoBusca;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}
	
	public String getCaminhoUploadCompleto(){
		return Constantes.PASTA_UPLOAD_ARQUIVO + Constantes.PASTA_COLABORADOR + arquivo;
	}
	
	public String getCaminhoDownloadCompleto(){
		return Constantes.PASTA_DOWNLOAD_ARQUIVO + Constantes.PASTA_COLABORADOR + arquivo;
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
		DocumentoColaborador other = (DocumentoColaborador) obj;
		if (arquivo == null) {
			if (other.arquivo != null)
				return false;
		} else if (!arquivo.equals(other.arquivo))
			return false;
		if (categoriaDocumento == null) {
			if (other.categoriaDocumento != null)
				return false;
		} else if (!categoriaDocumento.equals(other.categoriaDocumento))
			return false;
		if (colaborador == null) {
			if (other.colaborador != null)
				return false;
		} else if (!colaborador.equals(other.colaborador))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public List<DocumentoColaborador> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from DocumentoColaborador dc where 1 = 1 ");
		
		if(!TSUtil.isEmpty(colaborador) && !TSUtil.isEmpty(colaborador.getId())){
			query.append("and dc.colaborador.id = ? ");
		}
		
		if(!TSUtil.isEmpty(categoriaDocumento) && !TSUtil.isEmpty(categoriaDocumento.getId())){
			query.append("and dc.categoriaDocumento.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(colaborador) && !TSUtil.isEmpty(colaborador.getId())){
			params.add(colaborador.getId());
		}
		
		if(!TSUtil.isEmpty(categoriaDocumento) && !TSUtil.isEmpty(categoriaDocumento.getId())){
			params.add(categoriaDocumento.getId());
		}
		
		return super.find(query.toString(), null, params.toArray());
	}
	
	@Override
	public DocumentoColaborador getByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();
		
		query.append(" from DocumentoCliente dc where 1 = 1 ");
		
		query.append("and dc.cliente.id = ? ");
		
		query.append("and dc.categoriaDocumento.id = ? ");

		query.append("and dc.arquivo = ? ");
	
		query.append("and dc.descricao = ? ");
	
		List<Object> params = new ArrayList<Object>();
		
		params.add(colaborador.getId());
		
		params.add(categoriaDocumento.getId());
		
		params.add(arquivo);
		
		params.add(descricao);
		
		return super.get(query.toString(), params.toArray());
	}
	
}
