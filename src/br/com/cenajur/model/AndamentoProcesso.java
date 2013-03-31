package br.com.cenajur.model;

import java.util.ArrayList;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "andamentos_processos")
public class AndamentoProcesso extends TSActiveRecordAb<AndamentoProcesso>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -653350560849142124L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="andamentos_processos_id")
	@SequenceGenerator(name="andamentos_processos_id", sequenceName="andamentos_processos_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "processo_numero_id")
	private ProcessoNumero processoNumero;
	
	private String descricao;
	
	@Column(name = "data_andamento")
	private Date dataAndamento;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@ManyToOne
	@JoinColumn(name = "tipo_andamento_processo_id")
	private TipoAndamentoProcesso tipoAndamentoProcesso;
	
	@OneToMany(mappedBy = "andamentoProcesso", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DocumentoAndamentoProcesso> documentos;

	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_atualizacao_id")
	private Colaborador colaboradorAtualizacao;
	
	@Transient
	private Date dataInicial;
	
	@Transient
	private Date dataFinal;
	
	public AndamentoProcesso() {
	}
	
	public AndamentoProcesso(Long id, String descricao, String descricaoTipoAndamento, Date dataAndamento) {
		this.id = id;
		this.descricao = descricao;
		this.tipoAndamentoProcesso = new TipoAndamentoProcesso();
		this.tipoAndamentoProcesso.setDescricao(descricaoTipoAndamento);
		this.dataAndamento = dataAndamento;
	}
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public ProcessoNumero getProcessoNumero() {
		return processoNumero;
	}

	public void setProcessoNumero(ProcessoNumero processoNumero) {
		this.processoNumero = processoNumero;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getResumoDescricao() {
		return CenajurUtil.obterResumoGrid(descricao, 50);
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

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
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
	
	public List<AndamentoProcesso> findByProcesso(Processo processo){
		return super.find("from AndamentoProcesso a where a.processoNumero.processo.id = ?", "a.dataAndamento" ,processo.getId());
	}
	
	@Override
	public List<AndamentoProcesso> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" select new AndamentoProcesso(a.id, a.descricao, a.tipoAndamentoProcesso.descricao, a.dataAndamento) from AndamentoProcesso a  where 1 = 1 ");
		
		if(!TSUtil.isEmpty(processoNumero) && !TSUtil.isEmpty(processoNumero.getNumero())){
			query.append(CenajurUtil.getParamSemAcento("a.processoNumero.numero"));
		}
		
		if(!TSUtil.isEmpty(dataInicial) && !TSUtil.isEmpty(dataFinal)){
			query.append("and date(a.dataAndamento) between date(?) and date(?) ");
		}
		
		if(!TSUtil.isEmpty(tipoAndamentoProcesso) && !TSUtil.isEmpty(tipoAndamentoProcesso.getId())){
			query.append("and a.tipoAndamentoProcesso.id = ? ");
		}
		
		if(!TSUtil.isEmpty(descricao)){
			query.append("and ").append(CenajurUtil.semAcento("a.descricao")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(processoNumero) && !TSUtil.isEmpty(processoNumero.getNumero())){
			params.add(CenajurUtil.tratarString(processoNumero.getNumero()));
		}
		
		if(!TSUtil.isEmpty(dataInicial) && !TSUtil.isEmpty(dataFinal)){
			params.add(dataInicial);
			params.add(dataFinal);
		}
		
		if(!TSUtil.isEmpty(tipoAndamentoProcesso) && !TSUtil.isEmpty(tipoAndamentoProcesso.getId())){
			params.add(tipoAndamentoProcesso.getId());
		}
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		return super.find(query.toString(), "a.dataAndamento", params.toArray());
	}
	
	public List<AndamentoProcesso> pesquisarAndamentoRecente(){
		return super.find("select ap from AndamentoProcesso ap where date(ap.dataAndamento) = date(current_date() - 1) ", null);
	}
}
