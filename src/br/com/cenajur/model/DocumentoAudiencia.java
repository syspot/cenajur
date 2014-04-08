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
@Table(name = "documentos_audiencias")
public class DocumentoAudiencia extends TSActiveRecordAb<DocumentoAudiencia> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4945332863075887407L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="documentos_audiencias_id")
	@SequenceGenerator(name="documentos_audiencias_id", sequenceName="documentos_audiencias_id_seq")
	private Long id;
	
	@ManyToOne
	private Audiencia audiencia;
	
	private String arquivo;
	
	private String descricao;
	
	@Column(name = "descricao_busca")
	private String descricaoBusca;
	
	@ManyToOne
	@JoinColumn(name = "categoria_documento_id")
	private CategoriaDocumento categoriaDocumento;
	
	@Column(name = "flag_permissao_cliente")
	private Boolean flagPermissaoCliente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Audiencia getAudiencia() {
		return audiencia;
	}

	public void setAudiencia(Audiencia audiencia) {
		this.audiencia = audiencia;
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

	public Boolean getFlagPermissaoCliente() {
		return flagPermissaoCliente;
	}

	public void setFlagPermissaoCliente(Boolean flagPermissaoCliente) {
		this.flagPermissaoCliente = flagPermissaoCliente;
	}
	
	public String getCaminhoUploadCompleto(){
		return Constantes.PASTA_UPLOAD_ARQUIVO + Constantes.PASTA_AUDIENCIA + arquivo;
	}
	
	public String getCaminhoDownloadCompleto(){
		return Constantes.PASTA_DOWNLOAD_ARQUIVO + Constantes.PASTA_AUDIENCIA + arquivo;
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
		DocumentoAudiencia other = (DocumentoAudiencia) obj;
		if (arquivo == null) {
			if (other.arquivo != null)
				return false;
		} else if (!arquivo.equals(other.arquivo))
			return false;
		if (audiencia == null) {
			if (other.audiencia != null)
				return false;
		} else if (!audiencia.equals(other.audiencia))
			return false;
		if (categoriaDocumento == null) {
			if (other.categoriaDocumento != null)
				return false;
		} else if (!categoriaDocumento.equals(other.categoriaDocumento))
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
	public List<DocumentoAudiencia> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from DocumentoAudiencia da where 1 = 1 ");
		
		if(!TSUtil.isEmpty(audiencia) && !TSUtil.isEmpty(audiencia.getId())){
			query.append("and da.audiencia.id = ? ");
		}
		
		if(!TSUtil.isEmpty(categoriaDocumento) && !TSUtil.isEmpty(categoriaDocumento.getId())){
			query.append("and da.categoriaDocumento.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(audiencia) && !TSUtil.isEmpty(audiencia.getId())){
			params.add(audiencia.getId());
		}
		
		if(!TSUtil.isEmpty(categoriaDocumento) && !TSUtil.isEmpty(categoriaDocumento.getId())){
			params.add(categoriaDocumento.getId());
		}
		
		return super.find(query.toString(), null, params.toArray());
	}
	
	@Override
	public DocumentoAudiencia getByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();
		
		query.append(" from DocumentoAudiencia da where 1 = 1 ");
		
		query.append("and da.audiencia.id = ? ");
		
		query.append("and da.categoriaDocumento.id = ? ");

		query.append("and da.arquivo = ? ");
	
		query.append("and da.descricao = ? ");
	
		List<Object> params = new ArrayList<Object>();
		
		params.add(audiencia.getId());
		
		params.add(categoriaDocumento.getId());
		
		params.add(arquivo);
		
		params.add(descricao);
		
		return super.get(query.toString(), params.toArray());
	}
	
}
