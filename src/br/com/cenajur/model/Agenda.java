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

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
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
	
	@OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AgendaColaborador> agendasColaboradores;
	
	@Column(name = "flag_geral")
	private Boolean flagGeral;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_solicitante_id")
	private Colaborador colaboradorSolicitante;

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
	
	public boolean isTipoAudiencia(){
		return Constantes.TIPO_AGENDA_AUDIENCIA.equals(getTipoAgenda().getId());
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

	public List<Agenda> pesquisarPorDataColaborador(Date dataInicial, Date dataFinal, Colaborador colaborador) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" select distinct a from Agenda a left outer join fetch a.agendasColaboradores ac where 1 = 1 ");
		
		query.append(" and ( (day(a.dataInicial) = ? and month(a.dataInicial) = ? and year(a.dataInicial) = ?) ");
		query.append(" or (day(a.dataFinal) = ? and month(a.dataFinal) = ? and year(a.dataFinal) = ? ) )");
		
		if(!colaborador.getFlagPermissaoAgenda()){
			
			query.append(" and (ac.colaborador.id = ? or a.flagGeral = true ) ");
			
		}
		
		List<Object> params = new ArrayList<Object>();
		
		params.addAll(CenajurUtil.obterParamsDataAtual(dataInicial));
		params.addAll(CenajurUtil.obterParamsDataAtual(dataFinal));
		
		if(!colaborador.getFlagPermissaoAgenda()){
			
			params.add(colaborador.getId());
			
		}
		
		return super.find(query.toString(), "a.dataInicial", params.toArray());
	}

}
