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
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "documentos_processos")
public class DocumentoProcesso extends TSActiveRecordAb<DocumentoProcesso> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1399719504843067740L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="documentos_processos_id")
	@SequenceGenerator(name="documentos_processos_id", sequenceName="documentos_processos_id_seq")
	private Long id;
	
	@ManyToOne
	private Processo processo;
	
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

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
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
		return Constantes.PASTA_UPLOAD_ARQUIVO + Constantes.PASTA_PROCESSO + arquivo;
	}
	
	public String getCaminhoDownloadCompleto(){
		return Constantes.PASTA_DOWNLOAD_ARQUIVO + Constantes.PASTA_PROCESSO + arquivo;
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
		DocumentoProcesso other = (DocumentoProcesso) obj;
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
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		return true;
	}

	@Override
	public List<DocumentoProcesso> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from DocumentoProcesso dp where 1 = 1 ");
		
		if(!TSUtil.isEmpty(processo) && !TSUtil.isEmpty(processo.getId())){
			query.append("and dp.processo.id = ? ");
		}
		
		if(!TSUtil.isEmpty(categoriaDocumento) && !TSUtil.isEmpty(categoriaDocumento.getId())){
			query.append("and dp.categoriaDocumento.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(processo) && !TSUtil.isEmpty(processo.getId())){
			params.add(processo.getId());
		}
		
		if(!TSUtil.isEmpty(categoriaDocumento) && !TSUtil.isEmpty(categoriaDocumento.getId())){
			params.add(categoriaDocumento.getId());
		}
		
		return super.find(query.toString(), null, params.toArray());
	}
	
	@Override
	public DocumentoProcesso getByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();
		
		query.append(" from DocumentoProcesso dp where 1 = 1 ");
		
		query.append("and dp.processo.id = ? ");
		
		query.append("and dp.categoriaDocumento.id = ? ");

		query.append("and dp.arquivo = ? ");
	
		query.append("and dp.descricao = ? ");
	
		List<Object> params = new ArrayList<Object>();
		
		params.add(processo.getId());
		
		params.add(categoriaDocumento.getId());
		
		params.add(arquivo);
		
		params.add(descricao);
		
		return super.get(query.toString(), params.toArray());
	}
	
}
