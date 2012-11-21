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

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "agendas")
public class Agenda extends TSActiveRecordAb<Agenda>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7867621300772491361L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="agendas_id")
	@SequenceGenerator(name="agendas_id", sequenceName="agendas_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tipo_agenda_id")
	private TipoAgenda tipoAgenda;
	
	@Column(name = "data_inicial")
	private Date dataInicial;
	
	@Column(name = "data_final")
	private Date dataFinal;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "processo_numero_id")
	private ProcessoNumero processoNumero;
	
	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "tipo_visita_id")
	private TipoVisita tipoVisita;
	
	@Column(name = "telefone_cliente")
	private String telefoneCliente;
	
	@OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AgendaColaborador> agendasColaboradores;
	
	@Column(name = "flag_geral")
	private Boolean flagGeral;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_solicitante_id")
	private Colaborador colaboradorSolicitante;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_atualizacao_id")
	private Colaborador colaboradorAtualizacao;
	
	@Transient
	private Colaborador colaboradorBusca;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public TipoAgenda getTipoAgenda() {
		return tipoAgenda;
	}

	public void setTipoAgenda(TipoAgenda tipoAgenda) {
		this.tipoAgenda = tipoAgenda;
	}

	public Date getDataInicial() {
		return dataInicial;
	}
	
	public String getDataInicialFormatada() {
		return TSParseUtil.dateToString(dataInicial, TSDateUtil.DD_MM_YYYY_HH_MM);
	}
	
	public String getTitleAba() {
		return TSParseUtil.dateToString(dataInicial, TSDateUtil.DD_MM_YYYY_HH_MM) + " -- | --  " + CenajurUtil.obterResumoGrid(getDescricao(), 100);
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ProcessoNumero getProcessoNumero() {
		return processoNumero;
	}

	public void setProcessoNumero(ProcessoNumero processoNumero) {
		this.processoNumero = processoNumero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoVisita getTipoVisita() {
		return tipoVisita;
	}

	public void setTipoVisita(TipoVisita tipoVisita) {
		this.tipoVisita = tipoVisita;
	}

	public String getTelefoneCliente() {
		return telefoneCliente;
	}

	public void setTelefoneCliente(String telefoneCliente) {
		this.telefoneCliente = telefoneCliente;
	}

	public List<AgendaColaborador> getAgendasColaboradores() {
		return agendasColaboradores;
	}

	public void setAgendasColaboradores(List<AgendaColaborador> agendasColaboradores) {
		this.agendasColaboradores = agendasColaboradores;
	}

	public Boolean getFlagGeral() {
		return TSUtil.isEmpty(flagGeral) ? false : flagGeral;
	}

	public void setFlagGeral(Boolean flagGeral) {
		this.flagGeral = flagGeral;
	}

	public Colaborador getColaboradorSolicitante() {
		return colaboradorSolicitante;
	}

	public void setColaboradorSolicitante(Colaborador colaboradorSolicitante) {
		this.colaboradorSolicitante = colaboradorSolicitante;
	}
	
	public Colaborador getColaboradorAtualizacao() {
		return colaboradorAtualizacao;
	}

	public void setColaboradorAtualizacao(Colaborador colaboradorAtualizacao) {
		this.colaboradorAtualizacao = colaboradorAtualizacao;
	}

	public Colaborador getColaboradorBusca() {
		return colaboradorBusca;
	}

	public void setColaboradorBusca(Colaborador colaboradorBusca) {
		this.colaboradorBusca = colaboradorBusca;
	}

	public boolean isTipoAudiencia(){
		return Constantes.TIPO_AGENDA_AUDIENCIA.equals(getTipoAgenda().getId());
	}
	
	public boolean isTipoVisitaDoCliente(){
		return Constantes.TIPO_AGENDA_VISITA_DO_CLIENTE.equals(getTipoAgenda().getId());
	}
	
	public Date getDataFinalMinima() {
		return TSUtil.isEmpty(dataInicial) ? dataInicial : CenajurUtil.getDataMaisMeiaHora(dataInicial);
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
		Agenda other = (Agenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<Agenda> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Agenda a where 1 = 1 ");
		
		if(!TSUtil.isEmpty(descricao)){
			query.append(CenajurUtil.getParamSemAcento("a.descricao"));
		}
		
		if(!TSUtil.isEmpty(tipoAgenda) && !TSUtil.isEmpty(tipoAgenda.getId())){
			query.append("and a.tipoAgenda.id = ? ");
		}
		
		if(!TSUtil.isEmpty(dataInicial)){
			query.append("and date(a.dataInicial) = date(?) ");
		}
		
		if(!TSUtil.isEmpty(dataFinal)){
			query.append("and date(a.dataFinal) = date(?) ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		if(!TSUtil.isEmpty(tipoAgenda) && !TSUtil.isEmpty(tipoAgenda.getId())){
			params.add(tipoAgenda.getId());
		}
		
		if(!TSUtil.isEmpty(dataInicial)){
			params.add(dataInicial);
		}
		
		if(!TSUtil.isEmpty(dataFinal)){
			params.add(dataFinal);
		}
		
		return super.find(query.toString(), "dataInicial", params.toArray());
	}

	public List<Agenda> pesquisarAgendas(Date dataInicial, Date dataFinal) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" select distinct a from Agenda a left outer join fetch a.agendasColaboradores ac where 1 = 1 ");
		
		query.append(" and a.dataInicial between ? and ? ");
		
		if(!TSUtil.isEmpty(colaboradorBusca) && !TSUtil.isEmpty(colaboradorBusca.getId())){
			
			query.append(" and (ac.colaborador.id = ? or a.flagGeral = true ) ");
			
		}
		
		if(!TSUtil.isEmpty(tipoAgenda) && !TSUtil.isEmpty(tipoAgenda.getId())){
			
			query.append(" and a.tipoAgenda.id = ? ");
			
		}
		
		List<Object> params = new ArrayList<Object>();
		
		params.add(dataInicial);
		params.add(dataFinal);
		
		if(!TSUtil.isEmpty(colaboradorBusca) && !TSUtil.isEmpty(colaboradorBusca.getId())){
			
			params.add(colaboradorBusca.getId());
			
		}
		
		if(!TSUtil.isEmpty(tipoAgenda) && !TSUtil.isEmpty(tipoAgenda.getId())){
			
			params.add(tipoAgenda.getId());
			
		}

		return super.find(query.toString(), "a.dataInicial", params.toArray());
	}
	
	public List<Agenda> pesquisarVisitasPorCliente(Cliente cliente) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" select distinct a from Agenda a left outer join fetch a.agendasColaboradores ac where 1 = 1 ");
		
		if(!TSUtil.isEmpty(cliente) && !TSUtil.isEmpty(cliente.getId())){
			
			query.append(" and a.cliente.id = ? ");
			
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(cliente) && !TSUtil.isEmpty(cliente.getId())){
			
			params.add(cliente.getId());
			
		}
		
		return super.find(query.toString(), "a.dataInicial desc", params.toArray());
	}
	
	public List<Agenda> pesquisarVisitasProximas(int qtdDias){
		return super.find("select a from Agenda a where a.dataInicial between ? and ? ", null, new Date(), CenajurUtil.getDataMaisDias(qtdDias));
	}

}
