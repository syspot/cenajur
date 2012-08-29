package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.cenajur.model.Agenda;
import br.com.cenajur.model.AgendaColaborador;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.AudienciaAdvogado;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.SituacaoProcesso;
import br.com.cenajur.model.TipoAgenda;
import br.com.cenajur.model.Vara;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@ManagedBean(name = "agendaFaces")
public class AgendaFaces {
	
	private ScheduleModel eventModel;
	private ScheduleEvent event;

	private List<SelectItem> tiposAgendas;
	private List<SelectItem> varas;
	private List<SelectItem> situacoesAudiencias;
	
	private Agenda agenda;
	
	private ProcessoAudienciaUtil processoAudienciaUtil;
	
	private Colaborador colaboradorSelecionado;
	private ProcessoNumero processoNumeroSelecionado;
	private AgendaColaborador agendaColaboradorSelecionado;
	
	private Colaborador colaboradorConectado;
	
	
	@PostConstruct
	protected void init() {
		this.colaboradorConectado = ColaboradorUtil.obterColaboradorConectado();
		this.eventModel = new DefaultScheduleModel();
		this.event = new DefaultScheduleEvent();
		this.initCombo();
		this.limpar();
		this.iniciarSchedule();
	}
	
	public String limpar(){
		this.agenda = new Agenda();
		this.agenda.setTipoAgenda(new TipoAgenda());
		this.agenda.setAgendasColaboradores(new ArrayList<AgendaColaborador>());
		this.agendaColaboradorSelecionado = new AgendaColaborador();
		return null;
	}
	
	public void iniciarSchedule(){
		
		List<Agenda> agendas = new Agenda().pesquisarPorDataColaborador(CenajurUtil.getMesAtual(), CenajurUtil.getMesProximo(), this.colaboradorConectado);
		
		for(Agenda agenda : agendas){
		
			eventModel.addEvent(new DefaultScheduleEvent(agenda.getTipoAgenda().getDescricao(), agenda.getDataInicial(), agenda.getDataFinal(), agenda));
			
		}
		
	}
	
	private void initCombo(){
		this.tiposAgendas = TSFacesUtil.initCombo(new TipoAgenda().findAll("descricao"), "id", "descricao");
		this.situacoesAudiencias = TSFacesUtil.initCombo(new SituacaoProcesso().findAll("descricao"), "id", "descricao");
		this.varas = TSFacesUtil.initCombo(new Vara().findAll("descricao"), "id", "descricao");
	}
	
	public String onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		
		this.agenda = (Agenda) selectEvent.getScheduleEvent().getData();
		
		if(!this.isUsuarioMaster()){
			this.agendaColaboradorSelecionado = new AgendaColaborador().obterPorAgendaColaborador(this.agenda, this.colaboradorConectado);
		}
		
        return null;
    }  
      
    public String onDateSelect(DateSelectEvent selectEvent) {  
        this.limpar();
        agenda.setDataInicial(selectEvent.getDate());
        agenda.setDataFinal(selectEvent.getDate());
        return null;
    }
    
    public String onEventMove(ScheduleEntryMoveEvent event) {
    	
    	this.agenda = (Agenda) event.getScheduleEvent().getData();
    	
    	if(!this.isUsuarioMaster()){
			this.agendaColaboradorSelecionado = new AgendaColaborador().obterPorAgendaColaborador(this.agenda, this.colaboradorConectado);
		}
    	
        return null;
    }
      
    public String onEventResize(ScheduleEntryResizeEvent event) {

    	this.agenda = (Agenda) event.getScheduleEvent().getData();
    	
    	if(!this.isUsuarioMaster()){
			this.agendaColaboradorSelecionado = new AgendaColaborador().obterPorAgendaColaborador(this.agenda, this.colaboradorConectado);
		}
    	
        return null;
    }
	
	public String salvar() throws TSApplicationException{
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(!this.agenda.getFlagGeral() && TSUtil.isEmpty(this.agenda.getAgendasColaboradores())){
			context.addCallbackParam("sucesso", false);
			CenajurUtil.addErrorMessage("Colaborador: Campo obrigatório");
			return null;
		}
		
		if(this.agenda.isTipoAudiencia()){
			
			if(TSUtil.isEmpty(this.agenda.getProcessoNumero())){
				context.addCallbackParam("sucesso", false);
				CenajurUtil.addErrorMessage("Processo: Campo obrigatório");
				return null;
			}
			
			if(TSUtil.isEmpty(this.agenda.getAgendasColaboradores())){
				context.addCallbackParam("sucesso", false);
				CenajurUtil.addErrorMessage("Colaborador: Campo obrigatório para Audiência");
				return null;	
			}
			
			this.agenda.setFlagGeral(Boolean.FALSE);
			
		}
		
		this.agenda.setColaboradorSolicitante(this.colaboradorConectado);
		
		context.addCallbackParam("sucesso", true);
		
		if(TSUtil.isEmpty(this.agenda.getId())){
			this.agenda.save();
		} else {
			this.agenda.update();
		}
		
		CenajurUtil.addInfoMessage("Operação realizada com sucesso");
		return null;
	}
	
	public String addProcessoNumero(){
		this.agenda.setProcessoNumero(this.processoNumeroSelecionado);
		CenajurUtil.addInfoMessage("Processo adicionado com sucesso");
		return null;
	}
	
	public String addAgendaColaborador(){
		
		AgendaColaborador agendaColaborador = new AgendaColaborador();
		agendaColaborador.setAgenda(this.agenda);
		agendaColaborador.setColaborador(this.colaboradorSelecionado);
		agendaColaborador.setFlagConcluido(Boolean.FALSE);
		
		if(this.agenda.getAgendasColaboradores().contains(agendaColaborador)){
		
			CenajurUtil.addErrorMessage("Esse colaborador já foi adicionado");
			
		} else{
			
			this.agenda.getAgendasColaboradores().add(agendaColaborador);
			CenajurUtil.addInfoMessage("Colaborador adicionado com sucesso");
			
		}
		
		return null;
	}
	
	public String salvarAgendaColaborador() throws TSApplicationException{
		
		RequestContext context = RequestContext.getCurrentInstance();

		if(this.agendaColaboradorSelecionado.getFlagConcluido() && this.agendaColaboradorSelecionado.getAgenda().isTipoAudiencia()){
			
			context.addCallbackParam("criarAudiencia", true);
			
			Audiencia audiencia = new Audiencia().obterPorAgenda(this.agendaColaboradorSelecionado.getAgenda());
			
			this.processoAudienciaUtil = new ProcessoAudienciaUtil(this.agendaColaboradorSelecionado.getAgenda().getProcessoNumero().getProcesso());
			this.processoAudienciaUtil.getAudiencia().setProcessoNumero(this.agendaColaboradorSelecionado.getAgenda().getProcessoNumero());
			
			if(TSUtil.isEmpty(audiencia)){
				
				AudienciaAdvogado audienciaAdvogado;
				this.agendaColaboradorSelecionado.getAgenda().setAgendasColaboradores(
						this.agendaColaboradorSelecionado.perquisarPorAgenda(this.agendaColaboradorSelecionado.getAgenda()));
				
				for(AgendaColaborador agendaColaborador : this.agendaColaboradorSelecionado.getAgenda().getAgendasColaboradores()){
					
					audienciaAdvogado = new AudienciaAdvogado();
					audienciaAdvogado.setAdvogado(agendaColaborador.getColaborador());
					audienciaAdvogado.setAudiencia(this.processoAudienciaUtil.getAudiencia());
					
					this.processoAudienciaUtil.getAudiencia().getAudienciasAdvogados().add(audienciaAdvogado);
					
				}
				
				this.processoAudienciaUtil.getAudiencia().setAgenda(this.agendaColaboradorSelecionado.getAgenda());
				this.processoAudienciaUtil.getAudiencia().setDataAudiencia(this.agendaColaboradorSelecionado.getAgenda().getDataInicial());
				
			} else{
				
				this.processoAudienciaUtil.setAudiencia(audiencia);
				
			}
			
		} else{
			
			context.addCallbackParam("criarAudiencia", false);
			this.agendaColaboradorSelecionado.update();
			CenajurUtil.addInfoMessage("operação realizada com sucesso");
			
		}
		
		return null;
	}
	
	public String salvarAudiencia() throws TSApplicationException{
		
		if(TSUtil.isEmpty(this.processoAudienciaUtil.getAudiencia().getId())){
			
			this.processoAudienciaUtil.cadastrarAudiencia();
			
		} else{
			
			this.processoAudienciaUtil.alterarAudiencia();
			
		}
				
		this.agendaColaboradorSelecionado.update();
		
		return null;
	}
	
	public String limparAgendasColaboradores(){
		this.agenda.getAgendasColaboradores().clear();
		return null;
	}
	
	public String removerAgendaColaborador(){
		this.agenda.getAgendasColaboradores().remove(this.agendaColaboradorSelecionado);
		return null;
	}
	
	public boolean isUsuarioMaster(){
		return this.colaboradorConectado.getFlagPermissaoAgenda();
	}
	
	public boolean isColaboradorSolicitante(){
		return TSUtil.isEmpty(this.agenda.getId()) ? true : this.colaboradorConectado.equals(this.agenda.getColaboradorSolicitante());
	}

	public List<SelectItem> getTiposAgendas() {
		return tiposAgendas;
	}

	public void setTiposAgendas(List<SelectItem> tiposAgendas) {
		this.tiposAgendas = tiposAgendas;
	}

	public List<SelectItem> getVaras() {
		return varas;
	}

	public void setVaras(List<SelectItem> varas) {
		this.varas = varas;
	}

	public List<SelectItem> getSituacoesAudiencias() {
		return situacoesAudiencias;
	}

	public void setSituacoesAudiencias(List<SelectItem> situacoesAudiencias) {
		this.situacoesAudiencias = situacoesAudiencias;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Colaborador getColaboradorSelecionado() {
		return colaboradorSelecionado;
	}

	public void setColaboradorSelecionado(Colaborador colaboradorSelecionado) {
		this.colaboradorSelecionado = colaboradorSelecionado;
	}

	public ProcessoAudienciaUtil getProcessoAudienciaUtil() {
		return processoAudienciaUtil;
	}

	public void setProcessoAudienciaUtil(ProcessoAudienciaUtil processoAudienciaUtil) {
		this.processoAudienciaUtil = processoAudienciaUtil;
	}

	public ProcessoNumero getProcessoNumeroSelecionado() {
		return processoNumeroSelecionado;
	}

	public void setProcessoNumeroSelecionado(ProcessoNumero processoNumeroSelecionado) {
		this.processoNumeroSelecionado = processoNumeroSelecionado;
	}

	public AgendaColaborador getAgendaColaboradorSelecionado() {
		return agendaColaboradorSelecionado;
	}

	public void setAgendaColaboradorSelecionado(AgendaColaborador agendaColaboradorSelecionado) {
		this.agendaColaboradorSelecionado = agendaColaboradorSelecionado;
	}
	
}
