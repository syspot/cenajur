package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "andamentos_processos")
public class AndamentoProcesso extends TSActiveRecordAb<AndamentoProcesso>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	private Processo processo;
	
	private String descricao;
	
	@Column(name = "data_andamento")
	private Date dataAndamento;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@ManyToOne
	@JoinColumn(name = "tipo_andamento_processo_id")
	private TipoAndamentoProcesso tipoAndamentoProcesso;
	
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "andamentoProcesso", cascade = CascadeType.ALL)
	private List<DocumentoAndamentoProcesso> documentos;

	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_atualizacao_id")
	private Colaborador colaboradorAtualizacao;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
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

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Date getDataAndamento() {
		return dataAndamento;
	}
	
	public void setDataAndamento(Date dataAndamento) {
		this.dataAndamento = dataAndamento;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public TipoAndamentoProcesso getTipoAndamentoProcesso() {
		return tipoAndamentoProcesso;
	}

	public void setTipoAndamentoProcesso(TipoAndamentoProcesso tipoAndamentoProcesso) {
		this.tipoAndamentoProcesso = tipoAndamentoProcesso;
	}
	
	public List<DocumentoAndamentoProcesso> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoAndamentoProcesso> documentos) {
		this.documentos = documentos;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Colaborador getColaboradorAtualizacao() {
		return colaboradorAtualizacao;
	}

	public void setColaboradorAtualizacao(Colaborador colaboradorAtualizacao) {
		this.colaboradorAtualizacao = colaboradorAtualizacao;
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
		AndamentoProcesso other = (AndamentoProcesso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<AndamentoProcesso> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from AndamentoProcesso a where 1 = 1 ");
		
		if(!TSUtil.isEmpty(processo) && !TSUtil.isEmpty(processo.getNumeroProcesso())){
			query.append("and ").append(CenajurUtil.semAcento("a.processo.numeroProcesso")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		if(!TSUtil.isEmpty(dataAndamento)){
			query.append("and day(a.dataAndamento) = ? and month(a.dataAndamento) = ? and year(a.dataAndamento) = ? ");
		}
		
		if(!TSUtil.isEmpty(tipoAndamentoProcesso) && !TSUtil.isEmpty(tipoAndamentoProcesso.getId())){
			query.append("and a.tipoAndamentoProcesso.id = ? ");
		}
		
		if(!TSUtil.isEmpty(descricao)){
			query.append("and ").append(CenajurUtil.semAcento("a.descricao")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(processo) && !TSUtil.isEmpty(processo.getNumeroProcesso())){
			params.add(CenajurUtil.tratarString(processo.getNumeroProcesso()));
		}
		
		if(!TSUtil.isEmpty(dataAndamento)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataAndamento);
			params.add(calendar.get(Calendar.DAY_OF_MONTH));
			params.add(calendar.get(Calendar.MONTH) + 1);
			params.add(calendar.get(Calendar.YEAR));
		}
		
		if(!TSUtil.isEmpty(tipoAndamentoProcesso) && !TSUtil.isEmpty(tipoAndamentoProcesso.getId())){
			params.add(tipoAndamentoProcesso.getId());
		}
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		return super.find(query.toString(), params.toArray());
	}
}
