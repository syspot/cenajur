package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.ConfiguracoesEmail;
import br.com.cenajur.model.ConfiguracoesReplaceEmail;
import br.com.cenajur.model.ContadorEmail;
import br.com.cenajur.model.ContadorSms;
import br.com.cenajur.model.DocumentoAndamentoProcesso;
import br.com.cenajur.model.LogEnvioEmail;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.RegrasEmail;
import br.com.cenajur.model.TipoAndamentoProcesso;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.model.TipoInformacao;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.EmailUtil;
import br.com.cenajur.util.SMSUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

public class ProcessoAndamentoUtil {
	
	private ProcessoNumero processoNumeroPrincipal;
	private ProcessoNumero processoNumeroBackup;

	private Processo crudModel;
	private CategoriaDocumento categoriaDocumento;
	private List<SelectItem> categoriasDocumentos;
	
	private AndamentoProcesso andamentoProcesso;
	private AndamentoProcesso andamentoProcessoSelecionado;
	
	private DocumentoAndamentoProcesso documentoAndamentoProcesso;
	private DocumentoAndamentoProcesso documentoAndamentoProcessoSelecionado;
	
	public ProcessoAndamentoUtil() {
		this.crudModel = new Processo();
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_ANDAMENTO_PROCESSO));
		setDocumentoAndamentoProcesso(new DocumentoAndamentoProcesso());
		this.initAndamentoProcesso();
		this.initCombo();
	}
	
	public ProcessoAndamentoUtil(Processo processo) {
		this();
		setCrudModel(processo);
	}
	
	private void initAndamentoProcesso(){
		
		this.andamentoProcesso = new AndamentoProcesso();
		this.andamentoProcesso.setTipoAndamentoProcesso(new TipoAndamentoProcesso());
		this.andamentoProcesso.setDocumentos(new ArrayList<DocumentoAndamentoProcesso>());
		this.andamentoProcesso.setDataAndamento(new Date());
		this.processoNumeroPrincipal = this.processoNumeroBackup;
		
		if(TSUtil.isEmpty(processoNumeroPrincipal)){
			
			this.processoNumeroPrincipal = new ProcessoNumero();
			
		} else {
			
			this.andamentoProcesso.setProcessoNumero(this.processoNumeroPrincipal);
			
		}
		
	}
	
	private void initCombo(){
		this.categoriasDocumentos = TSFacesUtil.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}
	
	public String limpar(){
		this.initAndamentoProcesso();
		return null;
	}
	
	public void enviarDocumento(FileUploadEvent event) {

		getDocumentoAndamentoProcesso().setArquivo(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		
		if(CenajurUtil.isDocumentoPdf(event.getFile())){
			getDocumentoAndamentoProcesso().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
		}
		
		CenajurUtil.criaArquivo(event.getFile(), getDocumentoAndamentoProcesso().getCaminhoUploadCompleto());
			
	}
		
	public String addDocumento(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoAndamentoProcesso().getArquivo())){
			CenajurUtil.addErrorMessage("Documento: Campo obrigatório");
			context.addCallbackParam("sucesso", false);
			return null;
		}

		if(getDocumentoAndamentoProcesso().getDescricao().length() > 100){
			CenajurUtil.addErrorMessage("Descrição: Campo muito longo, tamanho máximo de 100 caracteres");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		context.addCallbackParam("sucesso", true);
		
		getDocumentoAndamentoProcesso().setAndamentoProcesso(this.andamentoProcesso);
		getDocumentoAndamentoProcesso().setCategoriaDocumento(getCategoriaDocumento().getById());
		getAndamentoProcesso().getDocumentos().add(getDocumentoAndamentoProcesso());
		getCategoriaDocumento().setId(null);
		setDocumentoAndamentoProcesso(new DocumentoAndamentoProcesso());
		return null;
	}
	
	public String removerDocumentoAndamentoProcesso(){
		this.andamentoProcesso.getDocumentos().remove(this.documentoAndamentoProcessoSelecionado);
		return null;
	}
	
	private boolean validaCampos(){
		
		boolean erro = false;

		RequestContext context = RequestContext.getCurrentInstance();
    	
		if(TSUtil.isEmpty(this.andamentoProcesso.getTipoAndamentoProcesso()) 
				|| TSUtil.isEmpty(this.andamentoProcesso.getTipoAndamentoProcesso().getId())){
			
			erro = true;
			CenajurUtil.addErrorMessage("Tipo do Andamento: Campo obrigatório");
		}

		context.addCallbackParam("sucesso", !erro);
		
		return erro;
	}
	
	private void enviarEmail() throws TSApplicationException{
		
		RegrasEmail regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_ANDAMENTO_PROCESSO).getById();
		
		TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_ANDAMENTO_ID);
		
		EmailUtil emailUtil = new EmailUtil();
		
		Processo processo = getCrudModel().getById();
		
		ConfiguracoesReplaceEmail configuracaoReplace;
		
		for(ConfiguracoesEmail configuracoesEmail : regrasEmail.getConfiguracoesEmails()){
			
			if(configuracoesEmail.getFlagImediato()){
				
				for(ProcessoCliente processoCliente : processo.getProcessosClientes()){
					
					if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail())){
						
						StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
						
						corpoEmail.append(configuracoesEmail.getCorpoEmail());
						
						corpoEmail.append(CenajurUtil.getRodapeEmail());
						
						String texto = corpoEmail.toString();
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_PROCESSO).getById();
						
						texto = texto.replace(configuracaoReplace.getCodigo(), new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ASSOCIADO).getById();
						
						texto = texto.replace(configuracaoReplace.getCodigo(), processoCliente.getCliente().getNome());
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA_ATUAL).getById();
						
						texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(new Date(), TSDateUtil.DD_MM_YYYY_HH_MM));
						
						emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), configuracoesEmail.getAssunto(), texto, "text/html");
						new ContadorEmail().gravarPorTipo(tipoInformacao);
						
						new LogEnvioEmail(configuracoesEmail.getAssunto(), texto, processoCliente.getCliente(), processoCliente.getCliente().getEmail()).save();
						
					}
					
				}
				
			}
			
		}
	}
	
	private void enviarSMS(){
		
		Processo processo = getCrudModel().getById();
		
		String msg = Constantes.TEMPLATE_SMS_ANDAMENTO;

		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_NUMERO_PROCESSO, new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());
		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_LOCAL, processo.getVara().getById().getDescricao());
		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_COLABORADOR, processo.getAdvogado().getApelido());

		SMSUtil smsUtil = new SMSUtil();

		for (ProcessoCliente processoCliente : processo.getProcessosClientes()) {

			if (!TSUtil.isEmpty(processoCliente.getCliente().getCelular())
					&& Constantes.SITUACAO_PROCESSO_CLIENTE_ATIVO.equals(processoCliente.getSituacaoProcessoCliente().getId())) {

				smsUtil.enviarMensagem(processoCliente.getCliente().getCelular(), msg.toString());
				new ContadorSms().gravarPorTipo(new TipoInformacao(Constantes.TIPO_INFORMACAO_ANDAMENTO_ID));

			}

		}

	}
	
	public String cadastrarAndamentoProcesso() throws TSApplicationException{
		
		if(validaCampos()){
			return null;
		}
		
		this.andamentoProcesso.setProcessoNumero(processoNumeroPrincipal);
		this.andamentoProcesso.setTipoAndamentoProcesso(this.andamentoProcesso.getTipoAndamentoProcesso().getById());
		this.andamentoProcesso.setDataAtualizacao(new Date());
		this.andamentoProcesso.setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		this.andamentoProcesso.setDataCadastro(new Date());
		this.andamentoProcesso.save();
		
		this.enviarEmail();
		this.enviarSMS();
		
		CenajurUtil.addInfoMessage("Andamento cadastrado com sucesso");

		this.initAndamentoProcesso();
		
		getCrudModel().setAndamentos(new AndamentoProcesso().findByProcesso(getCrudModel()));
		
		return null;
	}
	
	public String removerAndamentoProcesso() throws TSApplicationException{
		getCrudModel().getAndamentos().remove(this.andamentoProcessoSelecionado);
		getCrudModel().update();
		CenajurUtil.addInfoMessage("Andamento removido com sucesso");
		return null;
	}
	
	public String alterarAndamentoProcesso() throws TSApplicationException{
		
		if(validaCampos()){
			return null;
		}
		
		this.andamentoProcesso.setDataAtualizacao(new Date());
		this.andamentoProcesso.setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		this.andamentoProcesso.update();
		
		this.enviarEmail();
		this.enviarSMS();
		
		this.processoNumeroBackup = this.processoNumeroPrincipal;
		
		this.initAndamentoProcesso();
		
		getCrudModel().setAndamentos(new AndamentoProcesso().findByProcesso(getCrudModel()));
		
		CenajurUtil.addInfoMessage("Alteração realizada com sucesso");
		
		return null;
	}
	
	public String abrirDialogAndamento(){
		this.andamentoProcesso = this.andamentoProcesso.getById();
		this.processoNumeroPrincipal = this.processoNumeroPrincipal.getById();
		this.crudModel = this.crudModel.getById();
		return null;
	}
	
	public String obterAndamentoProcesso(){
		this.andamentoProcesso = this.andamentoProcesso.getById();
		return null;
	}
	
	public List<SelectItem> getCategoriasDocumentos() {
		return categoriasDocumentos;
	}

	public void setCategoriasDocumentos(List<SelectItem> categoriasDocumentos) {
		this.categoriasDocumentos = categoriasDocumentos;
	}

	public Processo getCrudModel() {
		return crudModel;
	}

	public void setCrudModel(Processo crudModel) {
		this.crudModel = crudModel;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}

	public AndamentoProcesso getAndamentoProcesso() {
		return andamentoProcesso;
	}

	public void setAndamentoProcesso(AndamentoProcesso andamentoProcesso) {
		this.andamentoProcesso = andamentoProcesso;
	}

	public AndamentoProcesso getAndamentoProcessoSelecionado() {
		return andamentoProcessoSelecionado;
	}

	public void setAndamentoProcessoSelecionado(AndamentoProcesso andamentoProcessoSelecionado) {
		this.andamentoProcessoSelecionado = andamentoProcessoSelecionado;
	}

	public DocumentoAndamentoProcesso getDocumentoAndamentoProcesso() {
		return documentoAndamentoProcesso;
	}

	public void setDocumentoAndamentoProcesso(DocumentoAndamentoProcesso documentoAndamentoProcesso) {
		this.documentoAndamentoProcesso = documentoAndamentoProcesso;
	}

	public DocumentoAndamentoProcesso getDocumentoAndamentoProcessoSelecionado() {
		return documentoAndamentoProcessoSelecionado;
	}

	public void setDocumentoAndamentoProcessoSelecionado(DocumentoAndamentoProcesso documentoAndamentoProcessoSelecionado) {
		this.documentoAndamentoProcessoSelecionado = documentoAndamentoProcessoSelecionado;
	}

	public ProcessoNumero getProcessoNumeroPrincipal() {
		return processoNumeroPrincipal;
	}

	public void setProcessoNumeroPrincipal(ProcessoNumero processoNumeroPrincipal) {
		this.processoNumeroPrincipal = processoNumeroPrincipal;
	}

	public ProcessoNumero getProcessoNumeroBackup() {
		return processoNumeroBackup;
	}

	public void setProcessoNumeroBackup(ProcessoNumero processoNumeroBackup) {
		this.processoNumeroBackup = processoNumeroBackup;
	}

}
