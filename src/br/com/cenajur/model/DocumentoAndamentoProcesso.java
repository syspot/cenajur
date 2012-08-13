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
import javax.persistence.Transient;

import org.primefaces.model.UploadedFile;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "documentos_andamentos_processos")
public class DocumentoAndamentoProcesso extends TSActiveRecordAb<DocumentoAndamentoProcesso>{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="documentos_andamentos_processos_id")
	@SequenceGenerator(name="documentos_andamentos_processos_id", sequenceName="documentos_andamentos_processos_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "andamento_processo_id")
	private AndamentoProcesso andamentoProcesso;
	
	private String arquivo;
	
	private String descricao;
	
	@Column(name = "descricao_busca")
	private String descricaoBusca;
	
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

	public AndamentoProcesso getAndamentoProcesso() {
		return andamentoProcesso;
	}

	public void setAndamentoProcesso(AndamentoProcesso andamentoProcesso) {
		this.andamentoProcesso = andamentoProcesso;
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

	public UploadedFile getDocumento() {
		return documento;
	}

	public void setDocumento(UploadedFile documento) {
		this.documento = documento;
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
		DocumentoAndamentoProcesso other = (DocumentoAndamentoProcesso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String getCaminhoUploadCompleto(){
		return Constantes.PASTA_UPLOAD_ARQUIVO + CenajurUtil.getAnoMes(andamentoProcesso.getDataCadastro()) + Constantes.PASTA_ANDAMENTO_PROCESSO + arquivo;
	}
	
	public String getCaminhoDownloadCompleto(){
		return Constantes.PASTA_DOWNLOAD_ARQUIVO + CenajurUtil.getAnoMesWeb(andamentoProcesso.getDataCadastro()) + Constantes.PASTA_ANDAMENTO_PROCESSO + arquivo;
	}
	
	@Override
	public List<DocumentoAndamentoProcesso> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from DocumentoAndamentoProcesso dap where 1 = 1 ");
		
		if(!TSUtil.isEmpty(andamentoProcesso) && !TSUtil.isEmpty(andamentoProcesso.getId())){
			query.append("and dap.andamentoProcesso.id = ? ");
		}
		
		if(!TSUtil.isEmpty(categoriaDocumento) && !TSUtil.isEmpty(categoriaDocumento.getId())){
			query.append("and dap.categoriaDocumento.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(andamentoProcesso) && !TSUtil.isEmpty(andamentoProcesso.getId())){
			params.add(andamentoProcesso.getId());
		}
		
		if(!TSUtil.isEmpty(categoriaDocumento) && !TSUtil.isEmpty(categoriaDocumento.getId())){
			params.add(categoriaDocumento.getId());
		}
		
		return super.find(query.toString(), null, params.toArray());
	}
	
}
