package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.cenajur.model.Agenda;
import br.com.cenajur.model.AgendaColaborador;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.AudienciaAdvogado;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.SituacaoAudiencia;
import br.com.cenajur.model.TipoAgenda;
import br.com.cenajur.model.TipoVisita;
import br.com.cenajur.model.Vara;
import br.com.cenajur.relat.JasperUtil;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.EmailLayoutUtil;
import br.com.cenajur.util.SMSLayoutUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name = "agendaFaces")
public class AgendaFaces extends TSMainFaces {

	private ScheduleModel lazyEventModel;
	private ScheduleModel eventModel;
	private ScheduleEvent event;

	private List<SelectItem> tiposAgendas;
	private List<SelectItem> varas;
	private List<SelectItem> situacoesAudiencias;
	private List<SelectItem> colaboradores;
	private List<SelectItem> tiposVisitas;

	private Agenda agenda;
	private Agenda agendaBusca;

	private ProcessoAudienciaUtil processoAudienciaUtil;

	private Cliente clienteSelecionado;
	private Colaborador colaboradorSelecionado;
	private ProcessoNumero processoNumeroSelecionado;
	private AgendaColaborador agendaColaboradorSelecionado;

	private Colaborador colaboradorConectado;

	private boolean buscaIndividual;

	private boolean flagClienteCiente;

	@PostConstruct
	protected void init() {
		this.colaboradorConectado = ColaboradorUtil.obterColaboradorConectado();
		this.eventModel = new DefaultScheduleModel();
		this.event = new DefaultScheduleEvent();
		this.agendaBusca = new Agenda();
		this.agendaBusca.setColaboradorBusca(new Colaborador());
		this.agendaBusca.setTipoAgenda(new TipoAgenda());
		this.initCombo();
		this.limpar();
		this.atualizarSchedule();
	}

	public String limpar() {
		this.agenda = new Agenda();
		this.agenda.setTipoAgenda(new TipoAgenda());
		this.agenda.setAgendasColaboradores(new ArrayList<AgendaColaborador>());
		this.agenda.setTipoVisita(new TipoVisita());
		this.agenda.setLocal("Cenajur");
		this.agendaColaboradorSelecionado = new AgendaColaborador();
		return null;
	}

	public String atualizarSchedule() {

		this.lazyEventModel = new LazyScheduleModel() {

			public void loadEvents(Date start, Date end) {

				clear();

				List<Agenda> agendas = agendaBusca.pesquisarAgendas(start, end);

				AgendaColaborador agendaColaboradorAux = new AgendaColaborador();

				for (Agenda agenda : agendas) {

					DefaultScheduleEvent dse = new DefaultScheduleEvent(agenda.getTipoAgenda().getDescricao() + ": " + CenajurUtil.obterDescricaoAgenda(agenda)
							+ (TSUtil.isEmpty(agenda.getLocal()) ? "" : " - Local: " + agenda.getLocal()), agenda.getDataInicial(), agenda.getDataFinal(),
							agenda);

					agendaColaboradorAux.setAgenda(agenda);
					agendaColaboradorAux.setColaborador(colaboradorConectado);

					dse.setStyleClass(CenajurUtil.obterCssAgenda(agenda, agendaColaboradorAux));

					addEvent(dse);

				}

			}
		};

		return null;

	}

	private void initCombo() {
		this.tiposAgendas = TSFacesUtil.initCombo(new TipoAgenda().findAll("descricao"), "id", "descricao");
		this.situacoesAudiencias = TSFacesUtil.initCombo(new SituacaoAudiencia().findAll("descricao"), "id", "descricao");
		this.varas = TSFacesUtil.initCombo(new Vara().findAll("descricao"), "id", "descricao");
		this.colaboradores = TSFacesUtil.initCombo(new Colaborador(true).findByModel("apelido"), "id", "apelido");
		this.tiposVisitas = TSFacesUtil.initCombo(new TipoVisita().findAll("descricao"), "id", "descricao");
	}

	public String onEventSelect(ScheduleEntrySelectEvent selectEvent) {

		this.agenda = (Agenda) selectEvent.getScheduleEvent().getData();

//		if (this.agenda.isTipoAudiencia()) {
//
//			Audiencia audiencia = new Audiencia().obterPorAgenda(this.agenda);
//
//			if (!TSUtil.isEmpty(audiencia)) {
//
//				this.idVaraAudiencia = audiencia.getVara().getId();
//
//			}
//
//		}

		if (this.isUsuarioMaster()) {

			if (!this.isColaboradorSolicitante()) {

				this.agendaColaboradorSelecionado = new AgendaColaborador().obterPorAgendaColaborador(this.agenda, this.colaboradorConectado);

			}

		} else {

			this.agenda = (Agenda) selectEvent.getScheduleEvent().getData();
			this.agendaColaboradorSelecionado = new AgendaColaborador().obterPorAgendaColaborador(this.agenda, this.colaboradorConectado);

		}

		return null;
	}

	public String onDateSelect(DateSelectEvent selectEvent) {
		this.limpar();
		agenda.setDataInicial(selectEvent.getDate());
		agenda.setDataFinal(CenajurUtil.getDataMaisUmaHora(selectEvent.getDate()));
		return null;
	}

	public String onEventMove(ScheduleEntryMoveEvent event) {

		RequestContext context = RequestContext.getCurrentInstance();

		if (this.isUsuarioMaster()) {

			this.agenda = (Agenda) event.getScheduleEvent().getData();
			context.addCallbackParam("sucesso", true);

		} else {

			context.addCallbackParam("sucesso", false);
			// CenajurUtil.addErrorMessage("Voc� n�o tem permiss�o para alterar uma agenda");

		}

		return null;
	}

	public String onEventResize(ScheduleEntryResizeEvent event) {

		RequestContext context = RequestContext.getCurrentInstance();

		if (this.isUsuarioMaster()) {

			context.addCallbackParam("sucesso", true);
			this.agenda = (Agenda) event.getScheduleEvent().getData();

		} else {

			context.addCallbackParam("sucesso", false);
			// CenajurUtil.addErrorMessage("Voc� n�o tem permiss�o para alterar uma agenda");

		}

		return null;
	}

	private boolean validaAgenda() {

		boolean erro = false;

		RequestContext context = RequestContext.getCurrentInstance();

		if (!this.agenda.isTipoAudiencia() && !this.agenda.getFlagGeral() && TSUtil.isEmpty(this.agenda.getAgendasColaboradores())) {
			context.addCallbackParam("sucesso", false);
			CenajurUtil.addErrorMessage("Colaborador: Campo obrigat�rio");
			erro = true;
		}

		if (this.agenda.isTipoAudiencia()) {

			if (TSUtil.isEmpty(this.agenda.getProcessoNumero())) {
				context.addCallbackParam("sucesso", false);
				CenajurUtil.addErrorMessage("Processo: Campo obrigat�rio");
				erro = true;
			}

			if (TSUtil.isEmpty(this.agenda.getAgendasColaboradores())) {
				context.addCallbackParam("sucesso", false);
				CenajurUtil.addErrorMessage("Colaborador: Campo obrigat�rio para Audi�ncia");
				erro = true;
			}

			this.agenda.setFlagGeral(Boolean.FALSE);

		}

		if (this.agenda.isTipoVisitaDoCliente()) {

			if (TSUtil.isEmpty(this.agenda.getCliente())) {
				context.addCallbackParam("sucesso", false);
				CenajurUtil.addErrorMessage("Associado: Campo obrigat�rio");
				erro = true;
			}

			if (TSUtil.isEmpty(this.agenda.getAgendasColaboradores())) {
				context.addCallbackParam("sucesso", false);
				CenajurUtil.addErrorMessage("Colaborador: Campo obrigat�rio para Visita do Cliente");
				erro = true;
			}

			this.agenda.setFlagGeral(Boolean.FALSE);

		}

		if (this.agenda.getDataInicial().after(this.agenda.getDataFinal())) {
			context.addCallbackParam("sucesso", false);
			CenajurUtil.addErrorMessage("Data final n�o pode ser menor que data inicial");
			erro = true;
		}

		if (!erro) {
			context.addCallbackParam("sucesso", true);
		}

		return erro;

	}

	public String salvarAgenda() {

		if (validaAgenda()) {
			return null;
		}

		if (this.agenda.isTipoAudiencia() && !TSUtil.isEmpty(TSUtil.tratarLong(this.agenda.getLocalId()))) {
			this.agenda.setLocal(new Vara(this.agenda.getLocalId()).getById().getDescricao());
		}

		try {
			
			if (TSUtil.isEmpty(this.agenda.getId())) {

				this.agenda.setColaboradorSolicitante(this.colaboradorConectado);
				this.agenda.save();

			} else {

				this.agenda.setColaboradorAtualizacao(this.colaboradorConectado);
				this.agenda.update();

			}

			if (this.agenda.isTipoAudiencia()) {
				this.gerarAudiencia();
			}

		} catch (TSApplicationException e) {
			
			e.printStackTrace();
			super.addErrorMessageKey(e.getMessage());
			
		}
		
		super.addInfoMessage("Opera��o realizada com sucesso");
		
		return null;
	}

	public String addProcessoNumero() {
		this.agenda.setProcessoNumero(this.processoNumeroSelecionado);
		CenajurUtil.addInfoMessage("Processo adicionado com sucesso");
		return null;
	}

	public String addCliente() {
		this.agenda.setCliente(this.clienteSelecionado);
		CenajurUtil.addInfoMessage("Cliente adicionado com sucesso");
		return null;
	}

	public String addAgendaColaborador() {

		AgendaColaborador agendaColaborador = new AgendaColaborador();
		agendaColaborador.setAgenda(this.agenda);
		agendaColaborador.setColaborador(this.colaboradorSelecionado);
		agendaColaborador.setFlagConcluido(Boolean.FALSE);

		if (this.agenda.getAgendasColaboradores().contains(agendaColaborador)) {

			CenajurUtil.addErrorMessage("Esse colaborador j� foi adicionado");

		} else {

			this.agenda.getAgendasColaboradores().add(agendaColaborador);
			CenajurUtil.addInfoMessage("Colaborador adicionado com sucesso");

		}

		return null;
	}

	public String salvarAgendaColaborador() {

		RequestContext context = RequestContext.getCurrentInstance();

		if (TSUtil.isEmpty(this.agendaColaboradorSelecionado.getTextoResposta())) {
			context.addCallbackParam("sucesso", false);
		} else {
			context.addCallbackParam("sucesso", true);
		}

		if (this.agendaColaboradorSelecionado.getFlagConcluido() && this.agendaColaboradorSelecionado.getAgenda().isTipoVisitaDoCliente()) {
			context.addCallbackParam("imprimirFichaAtendimento", true);
		} else {
			context.addCallbackParam("imprimirFichaAtendimento", false);
		}

		if (this.agendaColaboradorSelecionado.getFlagConcluido() && this.agendaColaboradorSelecionado.getAgenda().isTipoAudiencia()) {

			context.addCallbackParam("criarAudiencia", true);

			Audiencia audiencia = new Audiencia().obterPorAgenda(this.agendaColaboradorSelecionado.getAgenda());

			this.processoAudienciaUtil = new ProcessoAudienciaUtil(this.agendaColaboradorSelecionado.getAgenda().getProcessoNumero().getProcesso());
			this.processoAudienciaUtil.getAudiencia().setProcessoNumero(this.agendaColaboradorSelecionado.getAgenda().getProcessoNumero());
			this.processoAudienciaUtil.setProcessoNumeroPrincipal(this.agendaColaboradorSelecionado.getAgenda().getProcessoNumero());
			this.processoAudienciaUtil.setAgendaColaboradorAux(this.agendaColaboradorSelecionado);

			if (TSUtil.isEmpty(audiencia)) {

				AudienciaAdvogado audienciaAdvogado;
				this.agendaColaboradorSelecionado.getAgenda().setAgendasColaboradores(
						this.agendaColaboradorSelecionado.perquisarPorAgenda(this.agendaColaboradorSelecionado.getAgenda()));

				for (AgendaColaborador agendaColaborador : this.agendaColaboradorSelecionado.getAgenda().getAgendasColaboradores()) {

					audienciaAdvogado = new AudienciaAdvogado();
					audienciaAdvogado.setAdvogado(agendaColaborador.getColaborador());
					audienciaAdvogado.setAudiencia(this.processoAudienciaUtil.getAudiencia());

					this.processoAudienciaUtil.getAudiencia().getAudienciasAdvogados().add(audienciaAdvogado);

				}

				this.processoAudienciaUtil.getAudiencia().setAgenda(this.agendaColaboradorSelecionado.getAgenda());
				this.processoAudienciaUtil.getAudiencia().setDescricao(this.agendaColaboradorSelecionado.getTextoResposta());
				this.processoAudienciaUtil.getAudiencia().setDataAudiencia(this.agendaColaboradorSelecionado.getAgenda().getDataInicial());

			} else {

				if(TSUtil.isEmpty(audiencia.getDescricao())){
					audiencia.setDescricao(this.agendaColaboradorSelecionado.getTextoResposta());
				} else{
					audiencia.setDescricao(audiencia.getDescricao() + " ------------ " + this.agendaColaboradorSelecionado.getTextoResposta());
				}
				
				audiencia.setSituacaoAudiencia(audiencia.getSituacaoAudiencia().getById());
				audiencia.setVara(audiencia.getVara().getById());

				this.processoAudienciaUtil.setAudiencia(audiencia);

			}

		} else {

			context.addCallbackParam("criarAudiencia", false);

		}
		
		try {

			if (this.agenda.isTipoVisitaDoCliente() && this.agendaColaboradorSelecionado.getFlagConcluido()) {
				new EmailLayoutUtil().enviarEmailVisita(this.agendaColaboradorSelecionado);
				new SMSLayoutUtil().enviarSMSVisita(this.agendaColaboradorSelecionado);
			}

			this.agendaColaboradorSelecionado.update();

		} catch (TSApplicationException e) {

			e.printStackTrace();
			super.addErrorMessageKey(e.getMessage());

		}

		super.addInfoMessage("opera��o realizada com sucesso");

		return null;
	}

	public String salvarAudiencia() {

		try {

			if (TSUtil.isEmpty(this.processoAudienciaUtil.getAudiencia().getId())) {

				this.processoAudienciaUtil.cadastrarAudiencia();

			} else {

				this.processoAudienciaUtil.alterarAudiencia();

			}

			this.processoAudienciaUtil.getAgendaColaboradorAux().update();

		} catch (TSApplicationException e) {

			e.printStackTrace();
			super.addErrorMessageKey(e.getMessage());

		}

		return null;
	}

	public String mudarStatusBusca() {

		if (this.buscaIndividual) {
			this.agendaBusca.getColaboradorBusca().setId(this.colaboradorConectado.getId());
		} else {
			this.agendaBusca.getColaboradorBusca().setId(null);
		}

		return null;
	}

	public String alterarTipoAgenda() {

		this.agenda.getAgendasColaboradores().clear();

		if (this.agenda.isTipoVisitaDoCliente()) {
			this.agenda.setTipoVisita(new TipoVisita());
		} else {
			this.agenda.setTipoVisita(null);
			this.agenda.setTelefoneCliente(null);
			this.agenda.setCliente(null);
		}
		return null;
	}

	public String removerAgendaColaborador() {
		this.agenda.getAgendasColaboradores().remove(this.agendaColaboradorSelecionado);
		this.agendaColaboradorSelecionado = new AgendaColaborador();
		return null;
	}

	public String imprimirFichaAtendimento() {

		Long idAgendaColaborador = CenajurUtil.getParamFormatado(TSFacesUtil.getRequestParameter("agendaColaboradorIdSubmit"));

		if (!TSUtil.isEmpty(idAgendaColaborador)) {

			try {

				Map<String, Object> parametros = CenajurUtil.getHashMapReport();

				parametros.put("P_AGENDA_COLABORADOR_ID", idAgendaColaborador);

				new JasperUtil().gerarRelatorio("fichaAtendimento.jasper", "ficha_atendimento", parametros);

			} catch (Exception ex) {

				CenajurUtil.addErrorMessage("N�o foi poss�vel gerar a ficha de atendimento.");

				ex.printStackTrace();

			}

		}

		return null;

	}

	private void gerarAudiencia() throws TSApplicationException {

		Audiencia audiencia = new Audiencia().obterPorAgenda(this.agenda);

		this.processoAudienciaUtil = new ProcessoAudienciaUtil(this.agenda.getProcessoNumero().getProcesso());
		this.processoAudienciaUtil.getAudiencia().setProcessoNumero(this.agenda.getProcessoNumero());
		this.processoAudienciaUtil.setProcessoNumeroPrincipal(this.agenda.getProcessoNumero());

		if (TSUtil.isEmpty(audiencia)) {

			AudienciaAdvogado audienciaAdvogado;

			for (AgendaColaborador agendaColaborador : this.agenda.getAgendasColaboradores()) {

				audienciaAdvogado = new AudienciaAdvogado();
				audienciaAdvogado.setAdvogado(agendaColaborador.getColaborador());
				audienciaAdvogado.setAudiencia(this.processoAudienciaUtil.getAudiencia());

				this.processoAudienciaUtil.getAudiencia().getAudienciasAdvogados().add(audienciaAdvogado);

			}

			this.processoAudienciaUtil.getAudiencia().setAgenda(this.agenda);
			this.processoAudienciaUtil.getAudiencia().setDataAudiencia(this.agenda.getDataInicial());
			this.processoAudienciaUtil.getAudiencia().setDescricao(this.agenda.getDescricao());
			this.processoAudienciaUtil.getAudiencia().setSituacaoAudiencia(new SituacaoAudiencia(Constantes.SITUACAO_AUDIENCIA_AGUARDANDO));
			this.processoAudienciaUtil.getAudiencia().setVara(new Vara(this.agenda.getLocalId()));
			this.processoAudienciaUtil.getAudiencia().setFlagClienteCiente(flagClienteCiente);

			this.processoAudienciaUtil.cadastrarAudiencia();

		} else {

			this.processoAudienciaUtil.setAudiencia(audiencia);
			
			this.processoAudienciaUtil.getAudiencia().setDescricao(this.agenda.getDescricao());
			this.processoAudienciaUtil.getAudiencia().setDataAudiencia(this.agenda.getDataInicial());
			this.processoAudienciaUtil.getAudiencia().setVara(new Vara(this.agenda.getLocalId()));

			this.processoAudienciaUtil.alterarAudiencia();

		}

	}

	public boolean isUsuarioMaster() {
		return this.colaboradorConectado.getFlagPermissaoAgenda();
	}

	public boolean isColaboradorSolicitante() {
		return TSUtil.isEmpty(this.agenda.getId()) ? false : this.colaboradorConectado.equals(this.agenda.getColaboradorSolicitante());
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

	public List<SelectItem> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<SelectItem> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	public void setLazyEventModel(ScheduleModel lazyEventModel) {
		this.lazyEventModel = lazyEventModel;
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

	public Agenda getAgendaBusca() {
		return agendaBusca;
	}

	public void setAgendaBusca(Agenda agendaBusca) {
		this.agendaBusca = agendaBusca;
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

	public boolean isBuscaIndividual() {
		return buscaIndividual;
	}

	public void setBuscaIndividual(boolean buscaIndividual) {
		this.buscaIndividual = buscaIndividual;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public List<SelectItem> getTiposVisitas() {
		return tiposVisitas;
	}

	public void setTiposVisitas(List<SelectItem> tiposVisitas) {
		this.tiposVisitas = tiposVisitas;
	}

	public boolean isFlagClienteCiente() {
		return flagClienteCiente;
	}

	public void setFlagClienteCiente(boolean flagClienteCiente) {
		this.flagClienteCiente = flagClienteCiente;
	}

}
