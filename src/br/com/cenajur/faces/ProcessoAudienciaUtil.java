package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.cenajur.model.AgendaColaborador;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.AudienciaAdvogado;
import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.ConfiguracoesEmail;
import br.com.cenajur.model.ConfiguracoesReplaceEmail;
import br.com.cenajur.model.ContadorEmail;
import br.com.cenajur.model.DocumentoAudiencia;
import br.com.cenajur.model.LogEnvioEmail;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.RegrasEmail;
import br.com.cenajur.model.SituacaoAudiencia;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.model.TipoInformacao;
import br.com.cenajur.model.Vara;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.EmailUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

public class ProcessoAudienciaUtil {

	private ProcessoNumero processoNumeroPrincipal;
	private ProcessoNumero processoNumeroBackup;
	
	private Processo crudModel;
	private CategoriaDocumento categoriaDocumento;
	private List<SelectItem> categoriasDocumentos;
	
	private Audiencia audiencia;
	private Audiencia audienciaSelecionada;
	
	private DocumentoAudiencia documentoAudiencia;
	private DocumentoAudiencia documentoAudienciaSelecionado;
	private Colaborador advogadoSelecionado;
	
	private AgendaColaborador agendaColaboradorAux;
	
	public ProcessoAudienciaUtil() {
		this.crudModel = new Processo();
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_AUDIENCA));
		setDocumentoAudiencia(new DocumentoAudiencia());
		this.initAudiencia();
		this.initCombo();
	}
	
	public ProcessoAudienciaUtil(Processo processo) {
		this();
		setCrudModel(processo);
	}
	
	private void initAudiencia(){
		this.audiencia = new Audiencia();
		this.audiencia.setAdvogado(new Colaborador());
		this.audiencia.setSituacaoAudiencia(new SituacaoAudiencia());
		this.audiencia.setVara(new Vara());
		this.audiencia.setDocumentos(new ArrayList<DocumentoAudiencia>());
		this.audiencia.setAudienciasAdvogados(new ArrayList<AudienciaAdvogado>());
		this.processoNumeroPrincipal = this.processoNumeroBackup;
		if(TSUtil.isEmpty(processoNumeroPrincipal)){
			processoNumeroPrincipal = new ProcessoNumero();
		}
	}
	
	private void initCombo(){
		this.categoriasDocumentos = TSFacesUtil.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}

	public String limpar(){
		this.initAudiencia();
		return null;
	}

	public void enviarDocumento(FileUploadEvent event) {
		
		getDocumentoAudiencia().setArquivo(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		
		if(CenajurUtil.isDocumentoPdf(event.getFile())){
			getDocumentoAudiencia().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
		}
		
		CenajurUtil.criaArquivo(event.getFile(), getDocumentoAudiencia().getCaminhoUploadCompleto());
		
	}
	
	public String addDocumento(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoAudiencia().getArquivo())){
			CenajurUtil.addErrorMessage("Documento: Campo obrigatório");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		if(getDocumentoAudiencia().getDescricao().length() > 100){
			CenajurUtil.addErrorMessage("Descrição: Campo muito longo, tamanho máximo de 100 caracteres");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		context.addCallbackParam("sucesso", true);
		
		getDocumentoAudiencia().setAudiencia(this.audiencia);
		getDocumentoAudiencia().setCategoriaDocumento(getCategoriaDocumento().getById());
		getAudiencia().getDocumentos().add(getDocumentoAudiencia());
		getCategoriaDocumento().setId(null);
		setDocumentoAudiencia(new DocumentoAudiencia());
		return null;
	}
	
	public String removerDocumentoAudiencia(){
		this.audiencia.getDocumentos().remove(this.documentoAudienciaSelecionado);
		return null;
	}
	
	private boolean validaCampos(){
		
		boolean erro = false;
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(this.audiencia.getAudienciasAdvogados())){
			erro = true;
			CenajurUtil.addErrorMessage("Advogado: Campo obrigatório");
		}

		if(this.audiencia.getDescricao().length() > 500){
			erro = true;
			CenajurUtil.addErrorMessage("Descrição: Campo muito longo, tamanho máximo de 500 caracteres");
		}
		
		if(TSUtil.isEmpty(this.audiencia.getSituacaoAudiencia()) 
				|| TSUtil.isEmpty(this.audiencia.getSituacaoAudiencia().getId())){
			
			erro = true;
			CenajurUtil.addErrorMessage("Situação: Campo obrigatório");
		}
		
		if(TSUtil.isEmpty(this.audiencia.getVara()) 
				|| TSUtil.isEmpty(this.audiencia.getVara().getId())){
			
			erro = true;
			CenajurUtil.addErrorMessage("Vara: Campo obrigatório");
		}
		
		context.addCallbackParam("sucesso", !erro);
		
		return erro;
	}
	
	private void enviarEmail2(){
		
		RegrasEmail regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_AUDIENCIA).getById();
		
		TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_AUDIENCIA_ID);
		
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
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ADVOGADO).getById();
						
						texto = texto.replace(configuracaoReplace.getCodigo(), this.audiencia.getAudienciasAdvogados().toString());
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA).getById();
						
						texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(this.audiencia.getDataAudiencia(), TSDateUtil.DD_MM_YYYY));
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_HORA).getById();
						
						texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(this.audiencia.getDataAudiencia(), TSDateUtil.HH_MM));
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_LOCAL).getById();
						
						texto = texto.replace(configuracaoReplace.getCodigo(), this.audiencia.getVara().getDescricao());
						
						emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), configuracoesEmail.getAssunto(), texto, "text/html");
						new ContadorEmail().gravarPorTipo(tipoInformacao);
						try {
							new LogEnvioEmail(configuracoesEmail.getAssunto(), texto, processoCliente.getCliente(), processoCliente.getCliente().getEmail()).save();
						} catch (TSApplicationException e) {
							e.printStackTrace();
						}
						
					}
					
				}
				
			}
			
		}
	}
	
	public String cadastrarAudiencia() throws TSApplicationException{
		
		if(validaCampos()){
			return null;
		}
		
		this.audiencia.setProcessoNumero(processoNumeroPrincipal);
		this.audiencia.setVara(this.audiencia.getVara().getById());
		this.audiencia.setDataAtualizacao(new Date());
		this.audiencia.setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		this.audiencia.setDataCadastro(new Date());
		this.audiencia.save();
		
		//this.enviarEmail();
		
		CenajurUtil.addInfoMessage("Audiência cadastrada com sucesso");
		
		this.initAudiencia();
		
		getCrudModel().setAudiencias(new Audiencia().findByProcesso(getCrudModel()));
		
		return null;
	}
	
	public String removerAudiencia() throws TSApplicationException{
		getCrudModel().getAudiencias().remove(this.audienciaSelecionada);
		getCrudModel().update();
		CenajurUtil.addInfoMessage("Audiência removida com sucesso");
		return null;
	}
	
	public String alterarAudiencia() throws TSApplicationException{
		
		if(validaCampos()){
			return null;
		}
		
		this.audiencia.setDataAtualizacao(new Date());
		this.audiencia.setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		this.audiencia.update();
		
		//this.enviarEmail();
		
		this.initAudiencia();
		
		getCrudModel().setAudiencias(new Audiencia().findByProcesso(getCrudModel()));
		
		CenajurUtil.addInfoMessage("Alteração realizada com sucesso");
		
		return null;
	}
	
	public String addAdvogado(){
		
		AudienciaAdvogado audienciaAdvogado = new AudienciaAdvogado();
		audienciaAdvogado.setAudiencia(this.audiencia);
		audienciaAdvogado.setAdvogado(this.advogadoSelecionado);
		
		if(!this.audiencia.getAudienciasAdvogados().contains(audienciaAdvogado)){
			this.audiencia.getAudienciasAdvogados().add(audienciaAdvogado);
			CenajurUtil.addInfoMessage("Advogado adicionado com sucesso");
		} else{
			CenajurUtil.addErrorMessage("Esse Advogado já foi adicionado");
		}
		
		return null;
		
	}
	
	public String abrirDialogAudiencia(){
		this.audiencia = this.audiencia.getById();
		this.processoNumeroPrincipal = this.processoNumeroPrincipal.getById();
		this.crudModel = this.crudModel.getById();
		return null;
	}
	
	public String obterAudiencia(){
		this.audiencia = this.audiencia.getById();
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

	public Audiencia getAudiencia() {
		return audiencia;
	}

	public void setAudiencia(Audiencia audiencia) {
		this.audiencia = audiencia;
	}

	public Audiencia getAudienciaSelecionada() {
		return audienciaSelecionada;
	}

	public void setAudienciaSelecionada(Audiencia audienciaSelecionada) {
		this.audienciaSelecionada = audienciaSelecionada;
	}

	public DocumentoAudiencia getDocumentoAudiencia() {
		return documentoAudiencia;
	}

	public void setDocumentoAudiencia(DocumentoAudiencia documentoAudiencia) {
		this.documentoAudiencia = documentoAudiencia;
	}

	public DocumentoAudiencia getDocumentoAudienciaSelecionado() {
		return documentoAudienciaSelecionado;
	}

	public void setDocumentoAudienciaSelecionado(DocumentoAudiencia documentoAudienciaSelecionado) {
		this.documentoAudienciaSelecionado = documentoAudienciaSelecionado;
	}

	public Colaborador getAdvogadoSelecionado() {
		return advogadoSelecionado;
	}

	public void setAdvogadoSelecionado(Colaborador advogadoSelecionado) {
		this.advogadoSelecionado = advogadoSelecionado;
	}

	public AgendaColaborador getAgendaColaboradorAux() {
		return agendaColaboradorAux;
	}

	public void setAgendaColaboradorAux(AgendaColaborador agendaColaboradorAux) {
		this.agendaColaboradorAux = agendaColaboradorAux;
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
