package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Comarca;
import br.com.cenajur.model.Objeto;
import br.com.cenajur.model.ParteContraria;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoParteContraria;
import br.com.cenajur.model.SituacaoAudiencia;
import br.com.cenajur.model.SituacaoProcesso;
import br.com.cenajur.model.SituacaoProcessoCliente;
import br.com.cenajur.model.SituacaoProcessoParteContraria;
import br.com.cenajur.model.TipoAndamentoProcesso;
import br.com.cenajur.model.TipoParte;
import br.com.cenajur.model.TipoProcesso;
import br.com.cenajur.model.Vara;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.exception.TSApplicationException;

@SessionScoped
@ManagedBean(name = "processoFaces")
public class ProcessoFaces extends CrudFaces<Processo> {

	private List<SelectItem> objetos;
	private List<SelectItem> tiposProcessos;
	private List<SelectItem> varas;
	private List<SelectItem> comarcas;
	private List<SelectItem> tiposPartes;
	private List<SelectItem> situacoesProcessos;
	private List<SelectItem> situacoesProcessosClientes;
	private List<SelectItem> situacoesProcessosPartesContrarias;
	private List<SelectItem> advogados;
	private List<SelectItem> tiposAndamentosProcessos;
	private List<SelectItem> situacoesAudiencias;
	
	private Cliente clienteSelecionado;
	private ParteContraria parteContrariaSelecionada;
	
	private ProcessoCliente processoClienteSelecionado;
	private ProcessoParteContraria processoParteContrariaSelecionada;
	
	private AndamentoProcesso andamentoProcesso;
	private Audiencia audiencia;
	
	private AndamentoProcesso andamentoProcessoSelecionado;
	private Audiencia audienciaSelecionada;
	
	private Integer indexProcessoCliente;
	private Integer indexProcessoParteContraria;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombos();
	}
	
	private void initCombos(){
		this.objetos = this.initCombo(new Objeto().findAll("descricao"), "id", "descricao");
		this.tiposProcessos = this.initCombo(new TipoProcesso().findAll("descricao"), "id", "descricao");
		this.varas = this.initCombo(new Vara().findAll("descricao"), "id", "descricao");
		this.comarcas = this.initCombo(new Comarca().findAll("descricao"), "id", "descricao");
		this.tiposPartes = this.initCombo(new TipoParte().findAll("descricao"), "id", "descricao");
		this.situacoesProcessos = this.initCombo(new SituacaoProcesso().findAll("descricao"), "id", "descricao");
		this.situacoesProcessosClientes = this.initCombo(new SituacaoProcessoCliente().findAll("descricao"), "id", "descricao");
		this.situacoesProcessosPartesContrarias = this.initCombo(new SituacaoProcessoParteContraria().findAll("descricao"), "id", "descricao");
		this.advogados = this.initCombo(new Colaborador().findAll("nome"), "id", "nome");
		this.tiposAndamentosProcessos = this.initCombo(new TipoAndamentoProcesso().findAll("descricao"), "id", "descricao");
		this.situacoesAudiencias = this.initCombo(new SituacaoProcesso().findAll("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Processo());
		getCrudModel().setObjeto(new Objeto());
		getCrudModel().setTipoProcesso(new TipoProcesso());
		getCrudModel().setVara(new Vara());
		getCrudModel().setComarca(new Comarca());
		getCrudModel().setTipoParte(new TipoParte());
		getCrudModel().setSituacaoProcesso(new SituacaoProcesso());
		getCrudModel().setAdvogado(new Colaborador());
		getCrudModel().setProcessosClientes(new ArrayList<ProcessoCliente>());
		getCrudModel().setProcessosPartesContrarias(new ArrayList<ProcessoParteContraria>());
		this.processoClienteSelecionado = new ProcessoCliente();
		this.processoClienteSelecionado.setSituacaoProcessoCliente(new SituacaoProcessoCliente());
		this.processoParteContrariaSelecionada = new ProcessoParteContraria();
		this.processoParteContrariaSelecionada.setSituacaoProcessoParteContraria(new SituacaoProcessoParteContraria());
		this.initAndamentoProcesso();
		this.initAudiencia();
		setFlagAlterar(Boolean.FALSE);
		return "sucessso";
	}
	
	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("numeroProcesso");
		setCrudPesquisaModel(new Processo());
		getCrudPesquisaModel().setObjeto(new Objeto());
		getCrudPesquisaModel().setTipoProcesso(new TipoProcesso());
		getCrudPesquisaModel().setVara(new Vara());
		getCrudPesquisaModel().setComarca(new Comarca());
		getCrudPesquisaModel().setTipoParte(new TipoParte());
		getCrudPesquisaModel().setSituacaoProcesso(new SituacaoProcesso());
		getCrudPesquisaModel().setAdvogado(new Colaborador());
		setGrid(new ArrayList<Processo>());
		return "sucesso";
	}
	
	private void initAndamentoProcesso(){
		this.andamentoProcesso = new AndamentoProcesso();
		this.andamentoProcesso.setTipoAndamentoProcesso(new TipoAndamentoProcesso());
	}
	
	private void initAudiencia(){
		this.audiencia = new Audiencia();
		this.audiencia.setAdvogado(new Colaborador());
		this.audiencia.setSituacaoAudiencia(new SituacaoAudiencia());
		this.audiencia.setVara(new Vara());
	}

	@Override
	protected void prePersist() {
		getCrudModel().setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		getCrudModel().setDataAtualizacao(new Date());
	}
	
	@Override
	protected boolean validaCampos() {

		boolean erro = false;
		
		if(getCrudModel().getProcessosClientes().isEmpty()){
			this.addErrorMessage("Selecione ao menos um cliente");
			erro = true;
		}

		if(getCrudModel().getProcessosPartesContrarias().isEmpty()){
			this.addErrorMessage("Selecione ao menos uma parte contrária");
			erro = true;
		}
		
		return erro;
	}
	
	@Override
	protected void posDetail() {
		this.andamentoProcesso.setProcesso(getCrudModel());
		this.audiencia.setProcesso(getCrudModel());
	}
	
	public String removeCliente(){
		getCrudModel().getProcessosClientes().remove(this.processoClienteSelecionado);
		return "sucesso";
	}
	
	public String removeParteContraria(){
		getCrudModel().getProcessosPartesContrarias().remove(this.processoParteContrariaSelecionada);
		return "sucesso";
	}
	
	public String addCliente(){
		
		ProcessoCliente processoClienteSelecionado = new ProcessoCliente(this.clienteSelecionado);
		processoClienteSelecionado.setProcesso(getCrudModel());
		processoClienteSelecionado.setSituacaoProcessoCliente(new SituacaoProcessoCliente(Constantes.SITUACAO_PROCESSO_CLIENTE_ATIVO));
		
		if(!this.getCrudModel().getProcessosClientes().contains(processoClienteSelecionado)){
			
			this.getCrudModel().getProcessosClientes().add(processoClienteSelecionado);
			CenajurUtil.addInfoMessage("Cliente adicionado com sucesso");
			
		} else{
			
			CenajurUtil.addErrorMessage("Esse cliente já foi adicionado");
			
		}
		
		return "sucesso";
	}
	
	public String addParteContraria(){
		
		ProcessoParteContraria processoClienteSelecionado = new ProcessoParteContraria(this.parteContrariaSelecionada);
		processoClienteSelecionado.setProcesso(getCrudModel());
		processoClienteSelecionado.setSituacaoProcessoParteContraria(new SituacaoProcessoParteContraria(Constantes.SITUACAO_PROCESSO_PARTE_CONTRARIA_ATIVO));
		
		if(!this.getCrudModel().getProcessosPartesContrarias().contains(processoClienteSelecionado)){
			
			getCrudModel().getProcessosPartesContrarias().add(processoClienteSelecionado);
			CenajurUtil.addInfoMessage("Parte contrária adicionada com sucesso");
			
		} else{
			
			CenajurUtil.addErrorMessage("Essa parte contrária já foi adicionada");
			
		}
		
		return "sucesso";
	}
	
	public String cadastrarAndamentoProcesso() throws TSApplicationException{
		getCrudModel().getAndamentos().add(this.andamentoProcesso);
		getCrudModel().update();
		CenajurUtil.addInfoMessage("Andamento cadastrado com sucesso");
		this.initAndamentoProcesso();
		this.detail();
		return "sucesso";
	}
	
	public String cadastrarAudiencia() throws TSApplicationException{
		getCrudModel().getAudiencias().add(this.audiencia);
		getCrudModel().update();
		CenajurUtil.addInfoMessage("Audiência cadastrada com sucesso");
		this.initAudiencia();
		this.detail();
		return "sucesso";
	}
	
	public String removerAndamentoProcesso() throws TSApplicationException{
		getCrudModel().getAndamentos().remove(this.andamentoProcessoSelecionado);
		getCrudModel().update();
		CenajurUtil.addInfoMessage("Andamento removido com sucesso");
		return "sucesso";
	}
	
	public String removerAudiencia() throws TSApplicationException{
		getCrudModel().getAudiencias().remove(this.audienciaSelecionada);
		getCrudModel().update();
		CenajurUtil.addInfoMessage("Audiência removida com sucesso");
		return "sucesso";
	}
	
	public String atualizarProcessoCliente() throws TSApplicationException{
		getCrudModel().getProcessosClientes().get(indexProcessoCliente).setSituacaoProcessoCliente(this.processoClienteSelecionado.getSituacaoProcessoCliente());
		getCrudModel().getProcessosClientes().get(indexProcessoCliente).setDataArquivamento(this.processoClienteSelecionado.getDataArquivamento());
		CenajurUtil.addInfoMessage("Alteração realizada com sucesso");
		return "sucesso";
	}
	
	public String atualizarProcessoParteContraria() throws TSApplicationException{
		getCrudModel().getProcessosPartesContrarias().get(indexProcessoParteContraria).setSituacaoProcessoParteContraria(this.processoParteContrariaSelecionada.getSituacaoProcessoParteContraria());
		getCrudModel().getProcessosPartesContrarias().get(indexProcessoParteContraria).setDataArquivamento(this.processoParteContrariaSelecionada.getDataArquivamento());
		CenajurUtil.addInfoMessage("Alteração realizada com sucesso");
		return "sucesso";
	}
	
	public String limparDataArquivamentoProcessoCliente(){
		this.processoClienteSelecionado.setDataArquivamento(null);
		return "sucesso";
	}
	
	public String limparDataArquivamentoProcessoParteContraria(){
		this.processoParteContrariaSelecionada.setDataArquivamento(null);
		return "sucesso";
	}
	
	public List<SelectItem> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<SelectItem> objetos) {
		this.objetos = objetos;
	}

	public List<SelectItem> getTiposProcessos() {
		return tiposProcessos;
	}

	public void setTiposProcessos(List<SelectItem> tiposProcessos) {
		this.tiposProcessos = tiposProcessos;
	}

	public List<SelectItem> getVaras() {
		return varas;
	}

	public void setVaras(List<SelectItem> varas) {
		this.varas = varas;
	}

	public List<SelectItem> getComarcas() {
		return comarcas;
	}

	public void setComarcas(List<SelectItem> comarcas) {
		this.comarcas = comarcas;
	}

	public List<SelectItem> getTiposPartes() {
		return tiposPartes;
	}

	public void setTiposPartes(List<SelectItem> tiposPartes) {
		this.tiposPartes = tiposPartes;
	}

	public List<SelectItem> getSituacoesProcessos() {
		return situacoesProcessos;
	}

	public List<SelectItem> getSituacoesProcessosClientes() {
		return situacoesProcessosClientes;
	}

	public void setSituacoesProcessosClientes(List<SelectItem> situacoesProcessosClientes) {
		this.situacoesProcessosClientes = situacoesProcessosClientes;
	}

	public List<SelectItem> getSituacoesProcessosPartesContrarias() {
		return situacoesProcessosPartesContrarias;
	}

	public void setSituacoesProcessosPartesContrarias(List<SelectItem> situacoesProcessosPartesContrarias) {
		this.situacoesProcessosPartesContrarias = situacoesProcessosPartesContrarias;
	}

	public void setSituacoesProcessos(List<SelectItem> situacoesProcessos) {
		this.situacoesProcessos = situacoesProcessos;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public ParteContraria getParteContrariaSelecionada() {
		return parteContrariaSelecionada;
	}

	public void setParteContrariaSelecionada(ParteContraria parteContrariaSelecionada) {
		this.parteContrariaSelecionada = parteContrariaSelecionada;
	}

	public ProcessoCliente getProcessoClienteSelecionado() {
		return processoClienteSelecionado;
	}

	public void setProcessoClienteSelecionado(ProcessoCliente processoClienteSelecionado) {
		this.processoClienteSelecionado = processoClienteSelecionado;
	}

	public ProcessoParteContraria getProcessoParteContrariaSelecionada() {
		return processoParteContrariaSelecionada;
	}

	public void setProcessoParteContrariaSelecionada(ProcessoParteContraria processoParteContrariaSelecionada) {
		this.processoParteContrariaSelecionada = processoParteContrariaSelecionada;
	}

	public List<SelectItem> getAdvogados() {
		return advogados;
	}

	public void setAdvogados(List<SelectItem> advogados) {
		this.advogados = advogados;
	}

	public List<SelectItem> getTiposAndamentosProcessos() {
		return tiposAndamentosProcessos;
	}

	public void setTiposAndamentosProcessos(List<SelectItem> tiposAndamentosProcessos) {
		this.tiposAndamentosProcessos = tiposAndamentosProcessos;
	}

	public List<SelectItem> getSituacoesAudiencias() {
		return situacoesAudiencias;
	}

	public void setSituacoesAudiencias(List<SelectItem> situacoesAudiencias) {
		this.situacoesAudiencias = situacoesAudiencias;
	}

	public AndamentoProcesso getAndamentoProcesso() {
		return andamentoProcesso;
	}

	public void setAndamentoProcesso(AndamentoProcesso andamentoProcesso) {
		this.andamentoProcesso = andamentoProcesso;
	}

	public Audiencia getAudiencia() {
		return audiencia;
	}

	public void setAudiencia(Audiencia audiencia) {
		this.audiencia = audiencia;
	}

	public AndamentoProcesso getAndamentoProcessoSelecionado() {
		return andamentoProcessoSelecionado;
	}

	public void setAndamentoProcessoSelecionado(AndamentoProcesso andamentoProcessoSelecionado) {
		this.andamentoProcessoSelecionado = andamentoProcessoSelecionado;
	}

	public Audiencia getAudienciaSelecionada() {
		return audienciaSelecionada;
	}

	public void setAudienciaSelecionada(Audiencia audienciaSelecionada) {
		this.audienciaSelecionada = audienciaSelecionada;
	}

	public Integer getIndexProcessoCliente() {
		return indexProcessoCliente;
	}

	public void setIndexProcessoCliente(Integer indexProcessoCliente) {
		this.indexProcessoCliente = indexProcessoCliente;
	}

	public Integer getIndexProcessoParteContraria() {
		return indexProcessoParteContraria;
	}

	public void setIndexProcessoParteContraria(Integer indexProcessoParteContraria) {
		this.indexProcessoParteContraria = indexProcessoParteContraria;
	}

}
