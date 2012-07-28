package br.com.cenajur.faces;

import java.util.ArrayList;
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
import br.com.cenajur.model.SituacaoAudiencia;
import br.com.cenajur.model.SituacaoProcesso;
import br.com.cenajur.model.TipoAndamentoProcesso;
import br.com.cenajur.model.TipoParte;
import br.com.cenajur.model.TipoProcesso;
import br.com.cenajur.model.Vara;
import br.com.cenajur.util.CenajurUtil;
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
	private List<SelectItem> advogados;
	private List<SelectItem> tiposAndamentosProcessos;
	private List<SelectItem> situacoesAudiencias;
	
	private Cliente clienteSelecionado;
	private ParteContraria parteContrariaSelecionada;
	
	private AndamentoProcesso andamentoProcesso;
	private Audiencia audiencia;
	
	private AndamentoProcesso andamentoProcessoSelecionado;
	private Audiencia audienciaSelecionada;
	
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
		getCrudModel().setClientes(new ArrayList<Cliente>());
		getCrudModel().setPartesContrarias(new ArrayList<ParteContraria>());
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
	protected boolean validaCampos() {

		boolean erro = false;
		
		if(getCrudModel().getClientes().isEmpty()){
			this.addErrorMessage("Selecione ao menos um cliente");
			erro = true;
		}

		if(getCrudModel().getPartesContrarias().isEmpty()){
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
		getCrudModel().getClientes().remove(this.clienteSelecionado);
		return "sucesso";
	}
	
	public String removeParteContraria(){
		getCrudModel().getPartesContrarias().remove(this.parteContrariaSelecionada);
		return "sucesso";
	}
	
	public String addCliente(){
		
		if(!this.getCrudModel().getClientes().contains(this.clienteSelecionado)){
			
			this.getCrudModel().getClientes().add(this.clienteSelecionado);
			CenajurUtil.addInfoMessage("Cliente adicionado com sucesso");
			
		} else{
			
			CenajurUtil.addErrorMessage("Esse cliente já foi adicionado");
			
		}
		
		return "sucesso";
	}
	
	public String addParteContraria(){
		
		if(!this.getCrudModel().getPartesContrarias().contains(this.parteContrariaSelecionada)){
			
			getCrudModel().getPartesContrarias().add(this.parteContrariaSelecionada);
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

}
