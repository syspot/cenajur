package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Date;
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
import br.com.cenajur.util.Constantes;
import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "documentos_gerais")
public class DocumentoGeral extends TSActiveRecordAb<DocumentoGeral> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8923505402953823315L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="documentos_gerais_id")
	@SequenceGenerator(name="documentos_gerais_id", sequenceName="documentos_gerais_id_seq")
	private Long id;
	
	private String arquivo;
	
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name = "categoria_documento_id")
	private CategoriaDocumento categoriaDocumento;
	
	private String descricao;
	
	@Column(name = "descricao_busca")
	private String descricaoBusca;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getDescricaoBusca() {
		return descricaoBusca;
	}

	public void setDescricaoBusca(String descricaoBusca) {
		this.descricaoBusca = descricaoBusca;
	}

	public String getDescricaoResumo() {
		return CenajurUtil.obterResumoGrid(descricao, 70);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getCaminhoUploadCompleto(){
		return Constantes.PASTA_UPLOAD_ARQUIVO + Constantes.PASTA_GERAL + arquivo;
	}
	
	public String getCaminhoDownloadCompleto(){
		return Constantes.PASTA_DOWNLOAD_ARQUIVO + Constantes.PASTA_GERAL + arquivo;
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
		DocumentoGeral other = (DocumentoGeral) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<DocumentoGeral> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();

		query.append(" from DocumentoGeral dg where 1 = 1 ");

		if (!TSUtil.isEmpty(this.descricao)) {
			query.append(CenajurUtil.getParamSemAcento("dg.descricao"));
		}
		
		if (!TSUtil.isEmpty(this.categoriaDocumento) && !TSUtil.isEmpty(this.categoriaDocumento.getId())) {
			query.append(" and dg.categoriaDocumento.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(this.descricao)) {
			params.add(CenajurUtil.tratarString(this.descricao));
		}
		
		if (!TSUtil.isEmpty(this.categoriaDocumento) && !TSUtil.isEmpty(this.categoriaDocumento.getId())) {
			params.add(this.categoriaDocumento.getId());
		}
		
		return super.find(query.toString(), "dg.descricao", params.toArray());
	}
	
}
