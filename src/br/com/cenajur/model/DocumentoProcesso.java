package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.primefaces.model.UploadedFile;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "documentos_processos")
public class DocumentoProcesso extends TSActiveRecordAb<DocumentoProcesso>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	private Processo processo;
	
	private String arquivo;
	
	@ManyToOne
	@JoinColumn(name = "categoria_documento_id")
	private CategoriaDocumento categoriaDocumento;
	
	@Transient
	private UploadedFile documento;

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

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}
	
	public UploadedFile getDocumento() {
		return documento;
	}

	public void setDocumento(UploadedFile documento) {
		this.documento = documento;
	}

	public String getCaminhoUploadCompleto(){
		return Constantes.PASTA_UPLOAD_ARQUIVO + CenajurUtil.getAnoMes(processo.getDataCadastro()) + Constantes.PASTA_PROCESSO + arquivo;
	}
	
	public String getCaminhoDownloadCompleto(){
		return Constantes.PASTA_DOWNLOAD_ARQUIVO + CenajurUtil.getAnoMesWeb(processo.getDataCadastro()) + Constantes.PASTA_PROCESSO + arquivo;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		
		return super.find(query.toString(), params.toArray());
	}
	
}
