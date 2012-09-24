package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "processos")
public class Processo extends TSActiveRecordAb<Processo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4629137037446817513L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="processos_id")
	@SequenceGenerator(name="processos_id", sequenceName="processos_id_seq")
	private Long id;
	
	@Column(name = "data_ajuizamento")
	private Date dataAjuizamento;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	
	@Transient
	private ProcessoNumero processoNumeroPrincipal;
	
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
	@org.hibernate.annotations.OrderBy(clause = "flag_principal asc, numero")
	private List<ProcessoNumero> processosNumeros;
	
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProcessoCliente> processosClientes;
	
	@ManyToOne
	private Objeto objeto;
	
	private Integer lote;
	
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProcessoParteContraria> processosPartesContrarias;
	
	@ManyToOne
	@JoinColumn(name = "tipo_processo_id")
	private TipoProcesso tipoProcesso;
	
	@ManyToOne
	private Vara vara;
	
	@ManyToOne
	private Comarca comarca;
	
	@ManyToOne
	private Colaborador advogado;
	
	@Column(name = "data_abertura")
	private Date dataAbertura;
	
	@ManyToOne
	@JoinColumn(name = "tipo_parte_id")
	private TipoParte tipoParte;
	
	@ManyToOne
	@JoinColumn(name = "situacao_processo_id")
	private SituacaoProcesso situacaoProcesso;
	
	@Column(name = "data_arquivamento")
	private Date dataArquivamento;
	
	@ManyToOne
	private Processo processo;
	
	private String observacao;
	
	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_atualizacao_id")
	private Colaborador colaboradorAtualizacao;
	
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<DocumentoProcesso> documentos;
	
	@ManyToOne
	private Turno turno;
	
	@Transient
	private List<AndamentoProcesso> andamentos;
	
	@Transient
	private List<Audiencia> audiencias;
	
	@Transient
	private List<ProcessoNumero> processosNumerosTemp;
	
	@Transient
	private Long situacaoProcessoId;
	
	@Transient
	private String ano;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public List<ProcessoNumero> getProcessosNumeros() {
		return processosNumeros;
	}

	public void setProcessosNumeros(List<ProcessoNumero> processosNumeros) {
		this.processosNumeros = processosNumeros;
	}

	public List<ProcessoCliente> getProcessosClientes() {
		return processosClientes;
	}

	public void setProcessosClientes(List<ProcessoCliente> processosClientes) {
		this.processosClientes = processosClientes;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public Integer getLote() {
		return TSUtil.tratarInteger(lote);
	}

	public void setLote(Integer lote) {
		this.lote = lote;
	}

	public List<ProcessoParteContraria> getProcessosPartesContrarias() {
		return processosPartesContrarias;
	}

	public void setProcessosPartesContrarias(List<ProcessoParteContraria> processosPartesContrarias) {
		this.processosPartesContrarias = processosPartesContrarias;
	}

	public TipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(TipoProcesso tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public Vara getVara() {
		return vara;
	}

	public void setVara(Vara vara) {
		this.vara = vara;
	}

	public Comarca getComarca() {
		return comarca;
	}

	public void setComarca(Comarca comarca) {
		this.comarca = comarca;
	}

	public Colaborador getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Colaborador advogado) {
		this.advogado = advogado;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public TipoParte getTipoParte() {
		return tipoParte;
	}

	public void setTipoParte(TipoParte tipoParte) {
		this.tipoParte = tipoParte;
	}

	public SituacaoProcesso getSituacaoProcesso() {
		return situacaoProcesso;
	}

	public void setSituacaoProcesso(SituacaoProcesso situacaoProcesso) {
		this.situacaoProcesso = situacaoProcesso;
	}

	public Date getDataArquivamento() {
		return dataArquivamento;
	}

	public void setDataArquivamento(Date dataArquivamento) {
		this.dataArquivamento = dataArquivamento;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public Date getDataAjuizamento() {
		return dataAjuizamento;
	}

	public void setDataAjuizamento(Date dataAjuizamento) {
		this.dataAjuizamento = dataAjuizamento;
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

	public List<DocumentoProcesso> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoProcesso> documentos) {
		this.documentos = documentos;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public ProcessoNumero getProcessoNumeroPrincipal() {
		return processoNumeroPrincipal;
	}

	public void setProcessoNumeroPrincipal(ProcessoNumero processoNumeroPrincipal) {
		this.processoNumeroPrincipal = processoNumeroPrincipal;
	}

	public List<AndamentoProcesso> getAndamentos() {
		return andamentos;
	}

	public void setAndamentos(List<AndamentoProcesso> andamentos) {
		this.andamentos = andamentos;
	}

	public List<Audiencia> getAudiencias() {
		return audiencias;
	}

	public void setAudiencias(List<Audiencia> audiencias) {
		this.audiencias = audiencias;
	}
	
	public List<ProcessoNumero> getProcessosNumerosTemp() {
		return processosNumerosTemp;
	}

	public void setProcessosNumerosTemp(List<ProcessoNumero> processosNumerosTemp) {
		this.processosNumerosTemp = processosNumerosTemp;
	}

	public Long getSituacaoProcessoId() {
		return situacaoProcessoId;
	}

	public void setSituacaoProcessoId(Long situacaoProcessoId) {
		this.situacaoProcessoId = situacaoProcessoId;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public boolean isProcessoUnico(){
		return (TSUtil.isEmpty(getProcessosNumeros()) || getProcessosNumeros().size() < 2);
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
		Processo other = (Processo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<Processo> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" select distinct p from Processo p left outer join fetch p.processosNumeros pn where 1 = 1 ");
		
		if(!TSUtil.isEmpty(processoNumeroPrincipal) && !TSUtil.isEmpty(processoNumeroPrincipal.getNumero())){
			query.append(CenajurUtil.formataNumeroProcessoBusca("pn.numero"));
		}
		
		if(!TSUtil.isEmpty(dataAbertura)){
			query.append(" and date(p.dataAbertura) = date(?) ");
		} else if(!TSUtil.isEmpty(this.ano) && !TSUtil.isEmpty(CenajurUtil.converterStringInteiro(this.ano))){
			query.append(" and year(p.dataAbertura) = ? ");
		}
		
		if(!TSUtil.isEmpty(dataAjuizamento)){
			query.append(" and date(p.dataAjuizamento) = date(?) ");
		}
		
		if(!TSUtil.isEmpty(tipoProcesso) && !TSUtil.isEmpty(tipoProcesso.getId())){
			query.append(" and p.tipoProcesso.id = ? ");
		}
		
		if(!TSUtil.isEmpty(tipoParte) && !TSUtil.isEmpty(tipoParte.getId())){
			query.append(" and p.tipoParte.id = ? ");
		}
		
		if(!TSUtil.isEmpty(advogado) && !TSUtil.isEmpty(advogado.getId())){
			query.append(" and p.advogado.id = ? ");
		}
		
		if(!TSUtil.isEmpty(objeto) && !TSUtil.isEmpty(objeto.getId())){
			query.append(" and p.objeto.id = ? ");
		}
		
		if(!TSUtil.isEmpty(comarca) && !TSUtil.isEmpty(comarca.getId())){
			query.append(" and p.comarca.id = ? ");
		}
		
		if(!TSUtil.isEmpty(vara) && !TSUtil.isEmpty(vara.getId())){
			query.append(" and p.vara.id = ? ");
		}
		
		if(!TSUtil.isEmpty(situacaoProcesso) && !TSUtil.isEmpty(situacaoProcesso.getId())){
			query.append(" and p.situacaoProcesso.id = ? ");
		} else if(!TSUtil.isEmpty(getSituacaoProcessoId())){
			query.append(" and p.situacaoProcesso.id = ? ");
		}
		
		if(!TSUtil.isEmpty(turno) && !TSUtil.isEmpty(turno.getId())){
			query.append(" and p.turno.id = ? ");
		}
		
		if(!TSUtil.isEmpty(dataArquivamento)){
			query.append(" and date(p.dataArquivamento) = date(?) ");
		}
		
		if(!TSUtil.isEmpty(getLote())){
			query.append(" and p.lote = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(processoNumeroPrincipal) && !TSUtil.isEmpty(processoNumeroPrincipal.getNumero())){
			params.add(CenajurUtil.tratarString(processoNumeroPrincipal.getNumero()));
		}
		
		if(!TSUtil.isEmpty(dataAbertura)){
			params.add(dataAbertura);
		} else if(!TSUtil.isEmpty(this.ano) && !TSUtil.isEmpty(CenajurUtil.converterStringInteiro(this.ano))){
			params.add(CenajurUtil.converterStringInteiro(this.ano));
		}
		
		if(!TSUtil.isEmpty(dataAjuizamento)){
			params.add(dataAjuizamento);
		}
		
		if(!TSUtil.isEmpty(tipoProcesso) && !TSUtil.isEmpty(tipoProcesso.getId())){
			params.add(tipoProcesso.getId());
		}
		
		if(!TSUtil.isEmpty(tipoParte) && !TSUtil.isEmpty(tipoParte.getId())){
			params.add(tipoParte.getId());
		}
		
		if(!TSUtil.isEmpty(advogado) && !TSUtil.isEmpty(advogado.getId())){
			params.add(advogado.getId());
		}
		
		if(!TSUtil.isEmpty(objeto) && !TSUtil.isEmpty(objeto.getId())){
			params.add(objeto.getId());
		}
		
		if(!TSUtil.isEmpty(comarca) && !TSUtil.isEmpty(comarca.getId())){
			params.add(comarca.getId());
		}
		
		if(!TSUtil.isEmpty(vara) && !TSUtil.isEmpty(vara.getId())){
			params.add(vara.getId());
		}
		
		if(!TSUtil.isEmpty(situacaoProcesso) && !TSUtil.isEmpty(situacaoProcesso.getId())){
			params.add(situacaoProcesso.getId());
		} else if(!TSUtil.isEmpty(getSituacaoProcessoId())){
			params.add(getSituacaoProcessoId());
		}
		
		if(!TSUtil.isEmpty(turno) && !TSUtil.isEmpty(turno.getId())){
			params.add(turno.getId());
		}
		
		if(!TSUtil.isEmpty(dataArquivamento)){
			params.add(dataArquivamento);
		}
		
		if(!TSUtil.isEmpty(getLote())){
			params.add(getLote());
		}
		
		return super.find(query.toString(), "p.situacaoProcesso, p.tipoProcesso", params.toArray());
	}
	
	public boolean isProcessoArquivado(){
		return Constantes.SITUACAO_PROCESSO_ARQUIVADO.equals(situacaoProcesso.getId());
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public List findBySQL(Class classe, String[] atributos, String sql, Object... param) {

		this.openTransaction();

		Query queryObject=null;
		Session session = null;

		List coll = null;

		try {
		
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			SQLQuery sqlQuery = session.createSQLQuery(sql);

		if (atributos != null){
			
			for (String attr : atributos){
				sqlQuery = sqlQuery.addScalar(attr);
			}
		}

		queryObject = sqlQuery.setResultTransformer(Transformers.aliasToBean(classe));

		if (param != null) {
			
			int i = 0;
		
			for (Object o : param) {
				
				queryObject.setParameter(i, o);
				i++;
			}
		}

		coll = queryObject.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return coll;
	}
	
	public Integer obterTotalAtivo(){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where p.situacao_processo_id = 1")).getQtd();
	}

	public Integer obterTotalSuspenso(){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where p.situacao_processo_id = 2")).getQtd();
	}

	public Integer obterTotalArquivado(){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where p.situacao_processo_id = 3")).getQtd();
	}
	
	public Integer obterTotalAtivoPorAno(String ano){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where to_char(p.data_abertura, 'YYYY') = coalesce(?, to_char(p.data_abertura, 'YYYY'))  and p.situacao_processo_id = 1", ano)).getQtd();
	}
	
	public Integer obterTotalAtivoPorAnoObjeto(String ano, Objeto objeto){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where to_char(p.data_abertura, 'YYYY') = coalesce(?, to_char(p.data_abertura, 'YYYY'))  and p.objeto_id = ? and p.situacao_processo_id = 1", ano, objeto.getId())).getQtd();
	}
	
	public Integer obterTotalAtivoPorAnoAdvogado(String ano, Colaborador advogado){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where to_char(p.data_abertura, 'YYYY') = coalesce(?, to_char(p.data_abertura, 'YYYY'))  and p.advogado_id = ? and p.situacao_processo_id = 1", ano, advogado.getId())).getQtd();
	}
	
	public Integer obterTotalAtivoPorObjeto(Objeto objeto){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where p.objeto_id = ? and p.situacao_processo_id = 1", objeto.getId())).getQtd();
	}
	
	public Integer obterTotalAtivoPorAdvogado(Colaborador advogado){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where p.advogado_id = ? and p.situacao_processo_id = 1", advogado.getId())).getQtd();
	}
	
	public Integer obterTotalSuspensoPorObjeto(Objeto objeto){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where p.objeto_id = ? and p.situacao_processo_id = 2", objeto.getId())).getQtd();
	}
	
	public Integer obterTotalSuspensoPorAdvogado(Colaborador advogado){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where p.advogado_id = ? and p.situacao_processo_id = 2", advogado.getId())).getQtd();
	}
	
	public Integer obterTotalArquivadoPorObjeto(Objeto objeto){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where p.objeto_id = ? and p.situacao_processo_id = 3", objeto.getId())).getQtd();
	}
	
	public Integer obterTotalArquivadoPorAdvogado(Colaborador advogado){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from processos p where p.advogado_id = ? and p.situacao_processo_id = 3", advogado.getId())).getQtd();
	}

}
