package br.com.cenajur.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "agendas_colaboradores")
public class AgendaColaborador extends TSActiveRecordAb<AgendaColaborador>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4541430641342646947L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Agenda agenda;
	
	@ManyToOne
	private Colaborador colaborador;
	
	private String descricao;
	
	@Column(name = "texto_resposta")
	private String textoResposta;
	
	@Column(name = "flag_concluido")
	private Boolean flagConcluido;
	
	public AgendaColaborador() {
	}
	
	public AgendaColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public AgendaColaborador(Long id, Long agendaId, Long colaboradorId, String colaboradorNome, String colaboradorApelido, String descricao, String textoResposta, Boolean flagConcluido) {
		super();
		this.id = id;
		this.agenda = new Agenda();
		this.agenda.setId(agendaId);
		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);
		this.colaborador.setApelido(colaboradorApelido);
		this.descricao = descricao;
		this.textoResposta = textoResposta;
		this.flagConcluido = flagConcluido;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTextoResposta() {
		return textoResposta;
	}

	public void setTextoResposta(String textoResposta) {
		this.textoResposta = textoResposta;
	}

	public Boolean getFlagConcluido() {
		return TSUtil.isEmpty(flagConcluido) ? false : flagConcluido;
	}

	public void setFlagConcluido(Boolean flagConcluido) {
		this.flagConcluido = flagConcluido;
	}
	
	public String getStatus(){
		return getFlagConcluido() ? "Concluído" : "Aguardando";
	}
	
	public String getCss(){
		return getFlagConcluido() ? "situacaoAtiva" : "situacaoSuspensa";
	}
	
	@Override
	public String toString() {
		return this.colaborador.getApelido();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agenda == null) ? 0 : agenda.hashCode());
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
		AgendaColaborador other = (AgendaColaborador) obj;
		if (agenda == null) {
			if (other.agenda != null)
				return false;
		} else if (!agenda.equals(other.agenda))
			return false;
		if (colaborador == null) {
			if (other.colaborador != null)
				return false;
		} else if (!colaborador.equals(other.colaborador))
			return false;
		return true;
	}

	public AgendaColaborador obterPorAgendaColaborador(Agenda agenda, Colaborador colaborador){
		return super.get(" from AgendaColaborador ac where ac.agenda.id = ? and ac.colaborador.id = ? ", agenda.getId(), colaborador.getId());
	}
	
	public List<AgendaColaborador> perquisarPorAgenda(Agenda agenda){
		return super.find(" from AgendaColaborador ac where ac.agenda.id = ? ", null, agenda.getId());
	}

	public List<AgendaColaborador> perquisarPorAgenda2(Agenda agenda){
		return super.find(" select new AgendaColaborador(ac.id, ac.agenda.id, ac.colaborador.id, ac.colaborador.nome, ac.colaborador.apelido, ac.descricao, ac.textoResposta, ac.flagConcluido) from AgendaColaborador ac where ac.agenda.id = ? ", null, agenda.getId());
	}
	
	public List<AgendaColaborador> perquisarNaoFechadas(Colaborador colaborador, int dias, TipoAgenda tipoAgenda){
		return super.find(" from AgendaColaborador ac where ac.colaborador.id = ? and ac.flagConcluido = false and ac.agenda.tipoAgenda.id = ? and ac.agenda.dataFinal < CURRENT_DATE - ? ", null, colaborador.getId(), tipoAgenda.getId(), dias);
	}
	
}
