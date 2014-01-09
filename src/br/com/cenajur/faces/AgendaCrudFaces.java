package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

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
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@ManagedBean(name = "agendaCrudFaces")
public class AgendaCrudFaces extends CrudFaces<Agenda>{
	
	private List<SelectItem> tiposAgendas;
	private List<SelectItem> varas;
	private List<SelectItem> situacoesAudiencias;
	private List<SelectItem> tiposVisitas;
	
	private ProcessoAudienciaUtil processoAudienciaUtil;
	
	private Cliente clienteSelecionado;
	private Colaborador colaboradorSelecionado;
	private ProcessoNumero processoNumeroSelecionado;
	private AgendaColaborador agendaColaboradorSelecionado;
	
	private Colaborador colaboradorConectado;
	
	private boolean buscaIndividual;
	
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.colaboradorConectado = ColaboradorUtil.obterColaboradorConectado();
		this.initCombo();
	}
	
	@Override
	public String limpar(){
		this.setCrudModel(new Agenda());
		this.getCrudModel().setTipoAgenda(new TipoAgenda());
		this.getCrudModel().setAgendasColaboradores(new ArrayList<AgendaColaborador>());
		this.getCrudModel().setTipoVisita(new TipoVisita());
		this.getCrudModel().setDataInicial(new Date());
		this.setGrid(new ArrayList<Agenda>());
		this.agendaColaboradorSelecionado = new AgendaColaborador();
		setFlagAlterar(Boolean.FALSE);
		setTabIndex(1);
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		this.setCrudPesquisaModel(new Agenda());
		this.getCrudPesquisaModel().setTipoAgenda(new TipoAgenda());
		this.getCrudPesquisaModel().setDataInicial(new Date());
		this.getCrudPesquisaModel().setDataFinal(CenajurUtil.getDataOperacaoMinuto(30));
		this.getCrudPesquisaModel().setColaboradorBusca(new Colaborador());
		this.getCrudPesquisaModel().setClienteBusca(new Cliente());
		return null;
	}
	
	private void initCombo(){
		this.tiposAgendas = TSFacesUtil.initCombo(new TipoAgenda().findAll("descricao"), "id", "descricao");
		this.situacoesAudiencias = TSFacesUtil.initCombo(new SituacaoAudiencia().findAll("descricao"), "id", "descricao");
		this.varas = TSFacesUtil.initCombo(new Vara().findAll("descricao"), "id", "descricao");
		this.tiposVisitas = TSFacesUtil.initCombo(new TipoVisita().findAll("descricao"), "id", "descricao");
	}
    
	@Override
    public boolean validaCampos(){
    	
    	boolean erro = false;
    	
    	RequestContext context = RequestContext.getCurrentInstance();
		
		if(!this.getCrudModel().isTipoAudiencia() && !this.getCrudModel().isTipoVisitaDoCliente() 
				&& !this.getCrudModel().getFlagGeral()	&& TSUtil.isEmpty(this.getCrudModel().getAgendasColaboradores())){
			context.addCallbackParam("sucesso", false);
			CenajurUtil.addErrorMessage("Colaborador: Campo obrigatório");
			erro = true;
		}
		
		if(this.getCrudModel().isTipoAudiencia()){
			
			if(TSUtil.isEmpty(this.getCrudModel().getProcessoNumero())){
				context.addCallbackParam("sucesso", false);
				CenajurUtil.addErrorMessage("Processo: Campo obrigatório");
				erro = true;
			}
			
			if(TSUtil.isEmpty(this.getCrudModel().getAgendasColaboradores())){
				context.addCallbackParam("sucesso", false);
				CenajurUtil.addErrorMessage("Colaborador: Campo obrigatório para Audiência");
				erro = true;	
			}
			
			this.getCrudModel().setFlagGeral(Boolean.FALSE);
			
		}
		
		if(this.getCrudModel().isTipoVisitaDoCliente()){
			
			if(TSUtil.isEmpty(this.getCrudModel().getCliente())){
				context.addCallbackParam("sucesso", false);
				CenajurUtil.addErrorMessage("Associado: Campo obrigatório");
				erro = true;
			}
			
			if(TSUtil.isEmpty(this.getCrudModel().getAgendasColaboradores())){
				context.addCallbackParam("sucesso", false);
				CenajurUtil.addErrorMessage("Colaborador: Campo obrigatório para Visita do Cliente");
				erro = true;	
			}
			
			this.getCrudModel().setFlagGeral(Boolean.FALSE);
			
		}
		
		if(!erro){
			context.addCallbackParam("sucesso", true);
		}
		
		return erro;
		
    }
	
	@Override
	protected void prePersist() {
		
		if(getCrudModel().isTipoAudiencia() && !TSUtil.isEmpty(TSUtil.tratarLong(getCrudModel().getLocalId()))){
			getCrudModel().setLocal(new Vara(getCrudModel().getLocalId()).getById().getDescricao());
		}
		
		getCrudModel().setDataFinal(CenajurUtil.getDataOperacaoMinuto(getCrudModel().getDataInicial(), 15));
		
	}
	
	@Override
	protected void preInsert() {
		this.getCrudModel().setColaboradorSolicitante(this.colaboradorConectado);
	}
	
	@Override
	protected void preUpdate() {
		this.getCrudModel().setColaboradorAtualizacao(this.colaboradorConectado);
	}
	
	@Override
	protected void posPersist() throws TSApplicationException {
		
		if (this.getCrudModel().isTipoAudiencia()) {
			this.gerarAudiencia();
		}
		
	}
	
	private void gerarAudiencia() throws TSApplicationException {

		Audiencia audiencia = new Audiencia().obterPorAgenda(this.getCrudModel());

		this.processoAudienciaUtil = new ProcessoAudienciaUtil(this.getCrudModel().getProcessoNumero().getProcesso());
		this.processoAudienciaUtil.getAudiencia().setProcessoNumero(this.getCrudModel().getProcessoNumero());
		this.processoAudienciaUtil.setProcessoNumeroPrincipal(this.getCrudModel().getProcessoNumero());

		if (TSUtil.isEmpty(audiencia)) {

			AudienciaAdvogado audienciaAdvogado;

			for (AgendaColaborador agendaColaborador : this.getCrudModel().getAgendasColaboradores()) {

				audienciaAdvogado = new AudienciaAdvogado();
				audienciaAdvogado.setAdvogado(agendaColaborador.getColaborador());
				audienciaAdvogado.setAudiencia(this.processoAudienciaUtil.getAudiencia());

				this.processoAudienciaUtil.getAudiencia().getAudienciasAdvogados().add(audienciaAdvogado);

			}

			this.processoAudienciaUtil.getAudiencia().setAgenda(this.getCrudModel());
			this.processoAudienciaUtil.getAudiencia().setDataAudiencia(this.getCrudModel().getDataInicial());
			this.processoAudienciaUtil.getAudiencia().setDescricao(this.getCrudModel().getDescricao());
			this.processoAudienciaUtil.getAudiencia().setSituacaoAudiencia(new SituacaoAudiencia(Constantes.SITUACAO_AUDIENCIA_AGUARDANDO));
			this.processoAudienciaUtil.getAudiencia().setVara(new Vara(this.getCrudModel().getLocalId()));
			this.processoAudienciaUtil.getAudiencia().setFlagClienteCiente(false);

			this.processoAudienciaUtil.cadastrarAudiencia();

		} else {

			this.processoAudienciaUtil.setAudiencia(audiencia);

			this.processoAudienciaUtil.getAudiencia().setDataAudiencia(this.getCrudModel().getDataInicial());
			this.processoAudienciaUtil.getAudiencia().setVara(new Vara(this.getCrudModel().getLocalId()));

			this.processoAudienciaUtil.alterarAudiencia();

		}

	}
	
	public String addProcessoNumero(){
		this.getCrudModel().setProcessoNumero(this.processoNumeroSelecionado);
		CenajurUtil.addInfoMessage("Processo adicionado com sucesso");
		return null;
	}
	
	public String addCliente(){
		this.getCrudModel().setCliente(this.clienteSelecionado);
		CenajurUtil.addInfoMessage("Cliente adicionado com sucesso");
		return null;
	}
	
	public String addAgendaColaborador(){
		
		AgendaColaborador agendaColaborador = new AgendaColaborador();
		agendaColaborador.setAgenda(this.getCrudModel());
		agendaColaborador.setColaborador(this.colaboradorSelecionado);
		agendaColaborador.setFlagConcluido(Boolean.FALSE);
		
		if(this.getCrudModel().getAgendasColaboradores().contains(agendaColaborador)){
		
			CenajurUtil.addErrorMessage("Esse colaborador já foi adicionado");
			
		} else{
			
			this.getCrudModel().getAgendasColaboradores().add(agendaColaborador);
			CenajurUtil.addInfoMessage("Colaborador adicionado com sucesso");
			
		}
		
		return null;
	}
	
	public String mudarStatusBusca(){
		
		if(this.buscaIndividual){
			this.getCrudPesquisaModel().getColaboradorBusca().setId(this.colaboradorConectado.getId());
		} else{
			this.getCrudPesquisaModel().getColaboradorBusca().setId(null);
		}
		
		return null;
	}
	
	public String alterarTipoAgenda(){
		if(this.getCrudModel().isTipoVisitaDoCliente()){
			this.getCrudModel().setTipoVisita(new TipoVisita());
		} else{
			this.getCrudModel().setTipoVisita(null);
			this.getCrudModel().setTelefoneCliente(null);
			this.getCrudModel().setCliente(null);
		}
		return null;
	}
	
	public String limparAgendasColaboradores(){
		this.getCrudModel().getAgendasColaboradores().clear();
		return null;
	}
	
	public String removerAgendaColaborador(){
		this.getCrudModel().getAgendasColaboradores().remove(this.agendaColaboradorSelecionado);
		this.agendaColaboradorSelecionado = new AgendaColaborador();
		return null;
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

	public Colaborador getColaboradorConectado() {
		return colaboradorConectado;
	}

	public void setColaboradorConectado(Colaborador colaboradorConectado) {
		this.colaboradorConectado = colaboradorConectado;
	}

}
