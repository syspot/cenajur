package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;

import br.com.cenajur.model.Agenda;
import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.Banco;
import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.DocumentoCliente;
import br.com.cenajur.model.Estado;
import br.com.cenajur.model.EstadoCivil;
import br.com.cenajur.model.Faturamento;
import br.com.cenajur.model.Graduacao;
import br.com.cenajur.model.Lotacao;
import br.com.cenajur.model.MotivoCancelamento;
import br.com.cenajur.model.Plano;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.ProcessoParteContraria;
import br.com.cenajur.model.SituacaoProcessoCliente;
import br.com.cenajur.model.SituacaoProcessoParteContraria;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.model.TipoPagamento;
import br.com.cenajur.model.Turno;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;

@SessionScoped
@ManagedBean(name = "clienteFaces")
public class ClienteFaces extends CrudFaces<Cliente> {

	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private List<SelectItem> cidadesPesquisa;
	private List<SelectItem> estadosCivis;
	private List<SelectItem> tiposPagamentos;
	private List<SelectItem> motivosCancelamentos;
	private List<SelectItem> bancos;
	private List<SelectItem> planos;
	private List<SelectItem> graduacoes;
	private List<SelectItem> categoriasDocumentos;
	
	private Lotacao lotacaoSelecionada;
	
	private AndamentoProcesso andamentoProcessoSelecionado;
	private Audiencia audienciaSelecionada;
	
	private CategoriaDocumento categoriaDocumento;
	private DocumentoCliente documentoCliente;
	private DocumentoCliente documentoSelecionado;
	private Cliente clienteSelecionado;
	private int statusCliente;
	
	private ProcessoAux processoAux;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.estados = super.initCombo(new Estado().findAll("descricao"), "id", "descricao");
		this.estadosCivis = super.initCombo(new EstadoCivil().findAll("descricao"), "id", "descricao");
		this.tiposPagamentos = super.initCombo(new TipoPagamento().findAll("descricao"), "id", "descricao");
		this.motivosCancelamentos = super.initCombo(new MotivoCancelamento().findAll("descricao"), "id", "descricao");
		this.bancos = super.initCombo(new Banco().findAll("descricao"), "id", "descricao");
		this.planos = super.initCombo(new Plano(Boolean.TRUE).findByModel("descricao"), "id", "descricao");
		this.graduacoes = super.initCombo(new Graduacao().findAll("descricao"), "id", "descricao");
		this.categoriasDocumentos = this.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
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
		getCrudModel().setPlano(new Plano());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		getCrudModel().setFlagStatusPM(Boolean.TRUE);
		getCrudModel().setFlagAssociado(Boolean.TRUE);
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_CLIENTE));
		getCrudModel().setDocumentos(new ArrayList<DocumentoCliente>());
		setDocumentoCliente(new DocumentoCliente());
		setFlagAlterar(Boolean.FALSE);
		this.processoAux = new ProcessoAux();
		return null;
	}

	@Override
	public String limparPesquisa(){
		setGrid(new ArrayList<Cliente>());
		setCrudPesquisaModel(new Cliente());
		getCrudPesquisaModel().setCidade(new Cidade());
		getCrudPesquisaModel().getCidade().setEstado(new Estado());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		getCrudPesquisaModel().setFlagAssociado(Boolean.TRUE);
		return null;
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(!TSUtil.isValidCPF(TSUtil.removerNaoDigitos(getCrudModel().getCpf()))){
			erro = true;
			CenajurUtil.addErrorMessage("CPF inválido");
		}
		
		if(getCrudModel().getDiaVencimento() > 31 || getCrudModel().getDiaVencimento() < 1){
			erro = true;
			CenajurUtil.addErrorMessage("Dia de vencimento inválido");
		}
		
		if(TSUtil.isEmpty(getCrudModel().getId())){
			
			Cliente cliente = getCrudModel().obterPorCPF();
			
			if(!TSUtil.isEmpty(cliente)){
				erro = true;
				CenajurUtil.addErrorMessage("Já existe um associado cadastrado para esse CPF");
			}
			
		}
		
		return erro;
		
	}
	
	private void iniciaObjetosCombo(){
		if(TSUtil.isEmpty(getCrudModel().getBanco())){
			getCrudModel().setBanco(new Banco());
		}
		if(TSUtil.isEmpty(getCrudModel().getGraduacao())){
			getCrudModel().setGraduacao(new Graduacao());
		}
		if(TSUtil.isEmpty(getCrudModel().getMotivoCancelamento())){
			getCrudModel().setMotivoCancelamento(new MotivoCancelamento());
		}
	}
	
	@Override
	protected void prePersist() {
		
		getCrudModel().setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		getCrudModel().setDataAtualizacao(new Date());
		
		if(TSUtil.isEmpty(getCrudModel().getBanco()) || TSUtil.isEmpty(getCrudModel().getBanco().getId())){
			getCrudModel().setBanco(null);
		}
		
		if(TSUtil.isEmpty(getCrudModel().getGraduacao()) || TSUtil.isEmpty(getCrudModel().getGraduacao().getId())){
			getCrudModel().setGraduacao(null);
		}
		
		if(getCrudModel().getFlagAtivo()){
			getCrudModel().setDataCancelamento(null);
			getCrudModel().setMotivoCancelamento(null);
		}
	}
	
	@Override
	protected void preInsert() {
		getCrudModel().setDataCadastro(new Date());
	}
	
	@Override
	protected void posDetail() {
		
		if(getCrudModel().getDataAtualizacao().before(CenajurUtil.getTrimestreAnterior())){
			CenajurUtil.addDangerMessage("O endereço e telefone estão desatualizados");
		}
		
		Calendar c = Calendar.getInstance();
		
		Faturamento faturamento = new Faturamento();
		
		faturamento.setCliente(getCrudModel());
		faturamento.setAno(c.get(Calendar.YEAR));
		faturamento.setMes(c.get(Calendar.MONTH + 1));
		
		List<Faturamento> faturasAbertas = faturamento.pesquisarFaturasAbertas();
		
		getCrudModel().setFaturasAbertas("");
		
		for(Faturamento fatura : faturasAbertas){
			
			getCrudModel().setFaturasAbertas(getCrudModel().getFaturasAbertas() + " " + fatura.getMes() + "/" + fatura.getAno() + " ");
			
		}
		
		this.atualizarComboCidades();
		
		for(Processo processo : getCrudModel().getProcessos()){
			
			processo.setProcessoNumeroPrincipal(new ProcessoNumero().obterNumeroProcessoPrincipal(processo));
			processo.setAudiencias(new Audiencia().findByProcesso(processo));
			processo.setAndamentos(new AndamentoProcesso().findByProcesso(processo));
			
			if(TSUtil.isEmpty(processo.getTurno()) || TSUtil.isEmpty(processo.getTurno().getId())){
				processo.setTurno(new Turno());
			}
			
			processo.setProcessoNumeroPrincipal(new ProcessoNumero().obterNumeroProcessoPrincipal(processo));
			processo.setProcessosNumerosTemp(new ProcessoNumero().pesquisarOutrosNumerosProcessos(processo));
			
			processo.setAudiencias(new Audiencia().findByProcesso(processo));
			processo.setAndamentos(new AndamentoProcesso().findByProcesso(processo));
			
			for(ProcessoParteContraria processoParteContraria : processo.getProcessosPartesContrarias()){
				processoParteContraria.setSituacaoProcessoParteContrariaTemp(new SituacaoProcessoParteContraria(processoParteContraria.getSituacaoProcessoParteContraria().getId()));
			}

			for(ProcessoCliente processoCliente : processo.getProcessosClientes()){
				processoCliente.setSituacaoProcessoClienteTemp(new SituacaoProcessoCliente(processoCliente.getSituacaoProcessoCliente().getId()));
			}
			
		}
		
		this.iniciaObjetosCombo();
		
		this.processoAux.setProcessos(getCrudModel().getProcessos());
		
		getCrudModel().setVisitas(new Agenda().pesquisarVisitasPorCliente(getCrudModel()));
		
	}
	
	@Override
	protected void posPersist() throws TSApplicationException{
		
		if(!TSUtil.isEmpty(getCrudModel().getBytesImagem())){
			
			String nomeImagem = getCrudModel().getId() + TSFile.obterExtensaoArquivo(getCrudModel().getUrlImagem());
			
			getCrudModel().setUrlImagem(Constantes.PASTA_DOWNLOAD_IMAGEM + CenajurUtil.getAnoMesWeb(getCrudModel().getDataCadastro()) + nomeImagem);
			
			CenajurUtil.criaArquivo(getCrudModel().getBytesImagem(), Constantes.PASTA_UPLOAD_IMAGEM + CenajurUtil.getAnoMes(getCrudModel().getDataCadastro()) + nomeImagem);
			
			getCrudModel().update();
			
		}
		
		this.iniciaObjetosCombo();
		
	}
	
	public String mudarStatusCliente(){
		if(!getCrudModel().getFlagAtivo()){
			getCrudModel().setMotivoCancelamento(new MotivoCancelamento());
		}
		return null;
	}
	
	public String atualizarComboCidades(){
		this.cidades = super.initCombo(getCrudModel().getCidade().findCombo(), "id", "descricao");
		return null;
	}

	public String atualizarComboCidadesPesquisa(){
		this.cidadesPesquisa = super.initCombo(getCrudPesquisaModel().getCidade().findCombo(), "id", "descricao");
		return null;
	}
	
	public String addLotacao(){
		getCrudModel().setLotacao(this.lotacaoSelecionada);
		return null;
	}
	
	public String addCliente(){
		getCrudModel().setTitular(this.clienteSelecionado);
		return null;
	}
	
	public void oncapture(CaptureEvent captureEvent) {  
          
        byte[] data = captureEvent.getData();  
        
        String nomeFoto = CenajurUtil.gerarNumeroAleatorio() + Constantes.FOTO_CAM_TEMP;
		String arquivo = Constantes.PASTA_UPLOAD_IMAGEM_TEMP + nomeFoto;
		
		CenajurUtil.criaArquivo(data, arquivo);
		
		getCrudModel().setUrlImagem(Constantes.PASTA_DOWNLOAD_IMAGEM_TMP + nomeFoto);
		getCrudModel().setBytesImagem(data);
		
    }
	
	public void enviarDocumento(FileUploadEvent event) {
		
		getDocumentoCliente().setArquivo(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		
		if(CenajurUtil.isDocumentoPdf(event.getFile())){
			getDocumentoCliente().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
		}
		
		CenajurUtil.criaArquivo(event.getFile(), getDocumentoCliente().getCaminhoUploadCompleto());
		
	}
		
	public String addDocumento(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoCliente().getArquivo())){
			CenajurUtil.addErrorMessage("Documento: Campo obrigatório");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		if(getDocumentoCliente().getDescricao().length() > 100){
			CenajurUtil.addErrorMessage("Descrição: Campo muito longo, tamanho máximo de 100 caracteres");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		context.addCallbackParam("sucesso", true);
		
		getDocumentoCliente().setCliente(getCrudModel());
		getDocumentoCliente().setCategoriaDocumento(getCategoriaDocumento().getById());
		getCrudModel().getDocumentos().add(getDocumentoCliente());
		getCategoriaDocumento().setId(null);
		setDocumentoCliente(new DocumentoCliente());
		return null;
	}
	
	public String removerDocumento(){
		getCrudModel().getDocumentos().remove(this.documentoSelecionado);
		return null;
	}
	
	@Override
	protected void preFind() {
		this.tratarSituacao();
	}
	
	private void tratarSituacao(){
		
		switch(statusCliente){
			case 1: getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE); break;
			case 2: getCrudPesquisaModel().setFlagAtivo(Boolean.FALSE); break;
			default: getCrudPesquisaModel().setFlagAtivo(null); break;
		}
		
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

	public List<SelectItem> getCidadesPesquisa() {
		return cidadesPesquisa;
	}

	public void setCidadesPesquisa(List<SelectItem> cidadesPesquisa) {
		this.cidadesPesquisa = cidadesPesquisa;
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

	public List<SelectItem> getCategoriasDocumentos() {
		return categoriasDocumentos;
	}

	public void setCategoriasDocumentos(List<SelectItem> categoriasDocumentos) {
		this.categoriasDocumentos = categoriasDocumentos;
	}

	public List<SelectItem> getBancos() {
		return bancos;
	}

	public void setBancos(List<SelectItem> bancos) {
		this.bancos = bancos;
	}

	public List<SelectItem> getPlanos() {
		return planos;
	}

	public void setPlanos(List<SelectItem> planos) {
		this.planos = planos;
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

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}

	public DocumentoCliente getDocumentoCliente() {
		return documentoCliente;
	}

	public void setDocumentoCliente(DocumentoCliente documentoCliente) {
		this.documentoCliente = documentoCliente;
	}

	public DocumentoCliente getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocumentoCliente documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public int getStatusCliente() {
		return statusCliente;
	}

	public void setStatusCliente(int statusCliente) {
		this.statusCliente = statusCliente;
	}

	public ProcessoAux getProcessoAux() {
		return processoAux;
	}

	public void setProcessoAux(ProcessoAux processoAux) {
		this.processoAux = processoAux;
	}
	
}
