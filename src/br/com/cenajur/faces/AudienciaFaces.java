package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

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

@ViewScoped
@ManagedBean(name = "audienciaFaces")
public class AudienciaFaces extends CrudFaces<Audiencia> {

	private List<SelectItem> varas;
	private List<SelectItem> advogados;
	private List<SelectItem> situacoesAudiencias;
	private List<SelectItem> categoriasDocumentos;
	
	private ProcessoNumero processoNumeroSelecionado;
	
	private CategoriaDocumento categoriaDocumento;
	private DocumentoAudiencia documentoAudiencia;
	private DocumentoAudiencia documentoSelecionado;
	private Colaborador advogadoSelecionado;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.varas = this.initCombo(new Vara().findAll("descricao"), "id", "descricao");
		this.advogados = this.initCombo(new Colaborador().findAllAdvogados(), "id", "apelido");
		this.situacoesAudiencias = this.initCombo(new SituacaoAudiencia().findAll("descricao"), "id", "descricao");
		this.categoriasDocumentos = this.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Audiencia());
		getCrudModel().setAdvogado(new Colaborador());
		getCrudModel().setSituacaoAudiencia(new SituacaoAudiencia());
		getCrudModel().setVara(new Vara());
		getCrudModel().setAudienciasAdvogados(new ArrayList<AudienciaAdvogado>());
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_AUDIENCA));
		getCrudModel().setDocumentos(new ArrayList<DocumentoAudiencia>());
		setDocumentoAudiencia(new DocumentoAudiencia());
		setFlagAlterar(Boolean.FALSE);
		setTabIndex(1);
		return null;
	}
	
	@Override
	public String limparPesquisa(){
		setCrudPesquisaModel(new Audiencia());
		getCrudPesquisaModel().setAdvogado(new Colaborador());
		getCrudPesquisaModel().setProcessoNumero(new ProcessoNumero());
		getCrudPesquisaModel().setSituacaoAudiencia(new SituacaoAudiencia());
		getCrudPesquisaModel().setVara(new Vara());
		getCrudPesquisaModel().setDataInicial(new Date());
		getCrudPesquisaModel().setDataFinal(new Date());
		setGrid(new ArrayList<Audiencia>());
		return null;
	}
	
	@Override
	protected void preInsert() {
		getCrudModel().setDataCadastro(new Date());
	}
	
	@Override
	protected void prePersist() {
		getCrudModel().setDataAtualizacao(new Date());
		getCrudModel().setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
	}
	
	@Override
	protected void posPersist() throws TSApplicationException{
		
		RegrasEmail regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_AUDIENCIA).getById();
		
		TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_AUDIENCIA_ID);
		
		EmailUtil emailUtil = new EmailUtil();
		
		ConfiguracoesReplaceEmail configuracaoReplace;
		
		Processo processo = getCrudModel().getProcessoNumero().getProcesso().getById();
		
		for(ConfiguracoesEmail configuracoesEmail : regrasEmail.getConfiguracoesEmails()){
			
			if(configuracoesEmail.getFlagImediato()){
				
				StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
				
				corpoEmail.append(configuracoesEmail.getCorpoEmail());
				
				corpoEmail.append(CenajurUtil.getRodapeEmail());
				
				String texto = corpoEmail.toString();
				
				configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_PROCESSO).getById();
				
				texto = texto.replace(configuracaoReplace.getCodigo(), new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());
				
				configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ADVOGADO).getById();
				
				texto = texto.replace(configuracaoReplace.getCodigo(), getCrudModel().getAudienciasAdvogados().toString());
				
				configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA).getById();
				
				texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(getCrudModel().getDataAudiencia(), TSDateUtil.DD_MM_YYYY));
				
				configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_HORA).getById();
				
				texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(getCrudModel().getDataAudiencia(), TSDateUtil.HH_MM));
				
				configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_LOCAL).getById();
				
				texto = texto.replace(configuracaoReplace.getCodigo(), getCrudModel().getVara().getById().getDescricao());
				
				for(ProcessoCliente processoCliente : processo.getProcessosClientes()){
					
					if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail())){
						
						emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), configuracoesEmail.getAssunto(), texto, "text/html");
						new ContadorEmail().gravarPorTipo(tipoInformacao);
						new LogEnvioEmail(configuracoesEmail.getAssunto(), texto, processoCliente.getCliente(), processoCliente.getCliente().getEmail()).save();
						
					}
					
				}
				
				Colaborador advogado = null;
				
				for(AudienciaAdvogado audienciaAdvogado : getCrudModel().getAudienciasAdvogados()){
					
					advogado = audienciaAdvogado.getAdvogado().getById();
					
					if(!TSUtil.isEmpty(advogado.getEmail())){
						
						emailUtil.enviarEmailTratado(advogado.getEmail(), configuracoesEmail.getAssunto(), texto, "text/html");
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(TSUtil.isEmpty(getCrudModel().getProcessoNumero()) || TSUtil.isEmpty(getCrudModel().getProcessoNumero().getId())){
			erro = true;
			CenajurUtil.addErrorMessage("Processo: Campo obrigatório");
		}
		
		if(TSUtil.isEmpty(getCrudModel().getAudienciasAdvogados())){
			erro = true;
			CenajurUtil.addErrorMessage("Advogado: Campo obrigatório");
		}

		if(getCrudModel().getDescricao().length() > 500){
			erro = true;
			CenajurUtil.addErrorMessage("Descrição: Campo muito longo, tamanho máximo de 500 caracteres");
		}
		
		if(TSUtil.isEmpty(getCrudModel().getSituacaoAudiencia()) 
				|| TSUtil.isEmpty(getCrudModel().getSituacaoAudiencia().getId())){
			
			erro = true;
			CenajurUtil.addErrorMessage("Situação: Campo obrigatório");
		}
		
		if(TSUtil.isEmpty(getCrudModel().getVara()) 
				|| TSUtil.isEmpty(getCrudModel().getVara().getId())){
			
			erro = true;
			CenajurUtil.addErrorMessage("Vara: Campo obrigatório");
		}
		
		return erro;
	}
	
	public String addProcessoNumero(){
		getCrudModel().setProcessoNumero(this.processoNumeroSelecionado);
		CenajurUtil.addInfoMessage("Processo adicionado com sucesso");
		return null;
	}
	
	public String addAdvogado(){
		
		AudienciaAdvogado audienciaAdvogado = new AudienciaAdvogado();
		audienciaAdvogado.setAudiencia(getCrudModel());
		audienciaAdvogado.setAdvogado(this.advogadoSelecionado);
		
		if(!getCrudModel().getAudienciasAdvogados().contains(audienciaAdvogado)){
			getCrudModel().getAudienciasAdvogados().add(audienciaAdvogado);
			CenajurUtil.addInfoMessage("Advogado adicionado com sucesso");
		} else{
			CenajurUtil.addErrorMessage("Esse Advogado já foi adicionado");
		}
		
		
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
		
		getDocumentoAudiencia().setAudiencia(getCrudModel());
		getDocumentoAudiencia().setCategoriaDocumento(getCategoriaDocumento().getById());
		getCrudModel().getDocumentos().add(getDocumentoAudiencia());
		getCategoriaDocumento().setId(null);
		setDocumentoAudiencia(new DocumentoAudiencia());
		return null;
	}
	
	public String removerDocumento(){
		getCrudModel().getDocumentos().remove(this.documentoSelecionado);
		return null;
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

	public List<SelectItem> getCategoriasDocumentos() {
		return categoriasDocumentos;
	}

	public List<SelectItem> getAdvogados() {
		return advogados;
	}

	public void setAdvogados(List<SelectItem> advogados) {
		this.advogados = advogados;
	}

	public void setCategoriasDocumentos(List<SelectItem> categoriasDocumentos) {
		this.categoriasDocumentos = categoriasDocumentos;
	}

	public ProcessoNumero getProcessoNumeroSelecionado() {
		return processoNumeroSelecionado;
	}

	public void setProcessoNumeroSelecionado(ProcessoNumero processoNumeroSelecionado) {
		this.processoNumeroSelecionado = processoNumeroSelecionado;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}

	public DocumentoAudiencia getDocumentoAudiencia() {
		return documentoAudiencia;
	}

	public void setDocumentoAudiencia(DocumentoAudiencia documentoAudiencia) {
		this.documentoAudiencia = documentoAudiencia;
	}

	public DocumentoAudiencia getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocumentoAudiencia documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public Colaborador getAdvogadoSelecionado() {
		return advogadoSelecionado;
	}

	public void setAdvogadoSelecionado(Colaborador advogadoSelecionado) {
		this.advogadoSelecionado = advogadoSelecionado;
	}
	
}
