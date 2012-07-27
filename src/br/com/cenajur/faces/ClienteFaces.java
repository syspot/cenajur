package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Banco;
import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Estado;
import br.com.cenajur.model.EstadoCivil;
import br.com.cenajur.model.Graduacao;
import br.com.cenajur.model.Lotacao;
import br.com.cenajur.model.MotivoCancelamento;
import br.com.cenajur.model.TipoPagamento;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.topsys.util.TSUtil;

@SessionScoped
@ManagedBean(name = "clienteFaces")
public class ClienteFaces extends CrudFaces<Cliente> {

	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private List<SelectItem> estadosCivis;
	private List<SelectItem> tiposPagamentos;
	private List<SelectItem> motivosCancelamentos;
	private List<SelectItem> bancos;
	private List<SelectItem> graduacoes;
	
	private Lotacao lotacaoSelecionada;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.estados = super.initCombo(new Estado().findAll("descricao"), "id", "descricao");
		this.cidades = super.initCombo(new Cidade().findAll("descricao"), "id", "descricao");
		this.estadosCivis = super.initCombo(new EstadoCivil().findAll("descricao"), "id", "descricao");
		this.tiposPagamentos = super.initCombo(new TipoPagamento().findAll("descricao"), "id", "descricao");
		this.motivosCancelamentos = super.initCombo(new MotivoCancelamento().findAll("descricao"), "id", "descricao");
		this.bancos = super.initCombo(new Banco().findAll("descricao"), "id", "descricao");
		this.graduacoes = super.initCombo(new Graduacao().findAll("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Cliente());
		getCrudModel().setCidade(new Cidade());
		getCrudModel().getCidade().setEstado(new Estado());
		getCrudModel().setEstadoCivil(new EstadoCivil());
		getCrudModel().setBanco(new Banco());
		getCrudModel().setGraduacao(new Graduacao());
		getCrudModel().setTipoPagamento(new TipoPagamento());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		getCrudModel().setFlagStatusPM(Boolean.TRUE);
		setFlagAlterar(Boolean.FALSE);
		return SUCESSO;
	}

	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("nome");
		setGrid(new ArrayList<Cliente>());
		setCrudPesquisaModel(new Cliente());
		getCrudPesquisaModel().setCidade(new Cidade());
		getCrudPesquisaModel().getCidade().setEstado(new Estado());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		return "sucesso";
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(TSUtil.isEmpty(getCrudModel().getLotacao()) || TSUtil.isEmpty(getCrudModel().getLotacao().getId())){
			erro = true;
			CenajurUtil.addErrorMessage("Lotação: Campo obrigatório");
		}
		
		return erro;
		
	}

	@Override
	protected void preInsert() {
		getCrudModel().setColaboradorCadastro(ColaboradorUtil.obterColaboradorConectado());
		getCrudModel().setDataCadastro(new Date());
		getCrudModel().setBanco(null);
	}
	
	@Override
	protected void preUpdate(){
		getCrudModel().setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		getCrudModel().setDataAtualizacao(new Date());
	}
	@Override
	protected void posDetail() {
		if(TSUtil.isEmpty(getCrudModel().getBanco())){
			getCrudModel().setBanco(new Banco());
		}
	}
	
	public String mudarStatusCliente(){
		if(!getCrudModel().getFlagAtivo()){
			getCrudModel().setMotivoCancelamento(new MotivoCancelamento());
		}
		return "sucesso";
	}
	
	public String atualizarComboCidades(){
		this.cidades = super.initCombo(getCrudModel().getCidade().findByModel("descricao"), "id", "descricao");
		return "sucesso";
	}
	
	public String addLotacao(){
		getCrudModel().setLotacao(this.lotacaoSelecionada);
		return "sucesso";
	}
	
	public List<SelectItem> getEstados() {
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public List<SelectItem> getEstadosCivis() {
		return estadosCivis;
	}

	public void setEstadosCivis(List<SelectItem> estadosCivis) {
		this.estadosCivis = estadosCivis;
	}

	public List<SelectItem> getTiposPagamentos() {
		return tiposPagamentos;
	}

	public void setTiposPagamentos(List<SelectItem> tiposPagamentos) {
		this.tiposPagamentos = tiposPagamentos;
	}

	public List<SelectItem> getMotivosCancelamentos() {
		return motivosCancelamentos;
	}

	public void setMotivosCancelamentos(List<SelectItem> motivosCancelamentos) {
		this.motivosCancelamentos = motivosCancelamentos;
	}

	public List<SelectItem> getBancos() {
		return bancos;
	}

	public void setBancos(List<SelectItem> bancos) {
		this.bancos = bancos;
	}

	public List<SelectItem> getGraduacoes() {
		return graduacoes;
	}

	public void setGraduacoes(List<SelectItem> graduacoes) {
		this.graduacoes = graduacoes;
	}

	public Lotacao getLotacaoSelecionada() {
		return lotacaoSelecionada;
	}

	public void setLotacaoSelecionada(Lotacao lotacaoSelecionada) {
		this.lotacaoSelecionada = lotacaoSelecionada;
	}
	
}
