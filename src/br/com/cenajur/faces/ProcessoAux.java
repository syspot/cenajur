package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.DocumentoProcesso;
import br.com.cenajur.model.ParteContraria;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.ProcessoParteContraria;
import br.com.cenajur.model.SituacaoProcessoCliente;
import br.com.cenajur.model.SituacaoProcessoParteContraria;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;

public class ProcessoAux extends TSMainFaces{

	private List<Processo> processos;
	
	private Processo processoSelecionado;
	private Cliente clienteSelecionado;
	private ProcessoNumero processoNumeroSelecionado;
	private ProcessoCliente processoClienteSelecionado;
	private ProcessoParteContraria processoParteContrariaSelecionada;
	private ParteContraria parteContrariaSelecionada;
	private DocumentoProcesso documentoProcesso;
	private CategoriaDocumento categoriaDocumento;
	private DocumentoProcesso documentoSelecionado;
	private ProcessoAndamentoUtil processoAndamentoUtil;
	private ProcessoAudienciaUtil processoAudienciaUtil;
	
	private int indexProcesso;
	
	public ProcessoAux() {
		this.documentoProcesso = new DocumentoProcesso();
		this.categoriaDocumento = new CategoriaDocumento();
		this.processoAndamentoUtil = new ProcessoAndamentoUtil();
		this.processoAudienciaUtil = new ProcessoAudienciaUtil();
	}
	
	public String iniciarNumerosProcessos(){

		if(TSUtil.isEmpty(this.processos.get(indexProcesso).getProcessosNumerosTemp())){
			this.processos.get(indexProcesso).setProcessosNumerosTemp(new ArrayList<ProcessoNumero>());
		}
		
		this.processoSelecionado = this.processos.get(indexProcesso); 
		
		return null;
	}
	
	protected boolean validaCampos() {

		boolean erro = false;
		
		if(this.processos.get(indexProcesso).getProcessosClientes().isEmpty()){
			this.addErrorMessage("Selecione ao menos um cliente");
			erro = true;
		}

		if(this.processos.get(indexProcesso).getProcessosPartesContrarias().isEmpty()){
			this.addErrorMessage("Selecione ao menos uma parte contrária");
			erro = true;
		}
		
		return erro;
	}
	
	private void preUpdate(){
		
		this.processos.get(indexProcesso).setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		this.processos.get(indexProcesso).setDataAtualizacao(new Date());

		if(TSUtil.isEmpty(this.processos.get(indexProcesso).getTurno()) || TSUtil.isEmpty(this.processos.get(indexProcesso).getTurno().getId())){
			this.processos.get(indexProcesso).setTurno(null);
		}
		
		ProcessoNumero processoNumero = this.processos.get(indexProcesso).getProcessoNumeroPrincipal();
		processoNumero.setFlagPrincipal(Boolean.TRUE);
		processoNumero.setProcesso(this.processos.get(indexProcesso));
		
		this.processos.get(indexProcesso).setProcessosNumeros(this.processos.get(indexProcesso).getProcessosNumerosTemp());
		this.processos.get(indexProcesso).getProcessosNumeros().add(processoNumero);
		
		for(ProcessoCliente processoCliente : this.processos.get(indexProcesso).getProcessosClientes()){
			processoCliente.setSituacaoProcessoCliente(processoCliente.getSituacaoProcessoClienteTemp());
		}
		
		for(ProcessoParteContraria processoParteContraria : this.processos.get(indexProcesso).getProcessosPartesContrarias()){
			processoParteContraria.setSituacaoProcessoParteContraria(processoParteContraria.getSituacaoProcessoParteContrariaTemp());
		}
		
	}
	
	private void posUpdate() throws TSApplicationException{
		this.processos.get(indexProcesso).setProcessosNumerosTemp(new ProcessoNumero().pesquisarOutrosNumerosProcessos(this.processos.get(indexProcesso)));
	}
	
	public String updateProcesso() throws TSApplicationException {
		
		if(validaCampos()){
			return null;
		}
		
		this.preUpdate();
		
		this.processos.get(indexProcesso).update();
		
		this.posUpdate();
		
		CenajurUtil.addInfoMessage("Alteração realizada com sucesso");
		
		return null;
		
	}
	
	public String addCliente(){
		
		ProcessoCliente processoClienteSelecionado = new ProcessoCliente(this.clienteSelecionado);
		processoClienteSelecionado.setProcesso(this.processos.get(indexProcesso));
		processoClienteSelecionado.setSituacaoProcessoClienteTemp(new SituacaoProcessoCliente(Constantes.SITUACAO_PROCESSO_CLIENTE_ATIVO));
		
		if(!this.processos.get(indexProcesso).getProcessosClientes().contains(processoClienteSelecionado)){
			
			this.processos.get(indexProcesso).getProcessosClientes().add(processoClienteSelecionado);
			CenajurUtil.addInfoMessage("Associado adicionado com sucesso");
			
		} else{
			
			CenajurUtil.addErrorMessage("Esse associado já foi adicionado");
			
		}
		
		return null;
	}
	
	public String removeCliente(){
		this.processos.get(indexProcesso).getProcessosClientes().remove(this.processoClienteSelecionado);
		CenajurUtil.addInfoMessage("Associado removido com sucesso");
		return null;
	}
	
	public String addParteContraria(){
		
		ProcessoParteContraria processoClienteSelecionado = new ProcessoParteContraria(this.parteContrariaSelecionada);
		processoClienteSelecionado.setProcesso(this.processos.get(indexProcesso));
		processoClienteSelecionado.setSituacaoProcessoParteContrariaTemp(new SituacaoProcessoParteContraria(Constantes.SITUACAO_PROCESSO_PARTE_CONTRARIA_ATIVO));
		
		if(!this.processos.get(indexProcesso).getProcessosPartesContrarias().contains(processoClienteSelecionado)){
			
			this.processos.get(indexProcesso).getProcessosPartesContrarias().add(processoClienteSelecionado);
			CenajurUtil.addInfoMessage("Parte contrária adicionada com sucesso");
			
		} else{
			
			CenajurUtil.addErrorMessage("Essa parte contrária já foi adicionada");
			
		}
		
		return null;
	}
	
	public String removeParteContraria(){
		this.processos.get(indexProcesso).getProcessosPartesContrarias().remove(this.processoParteContrariaSelecionada);
		CenajurUtil.addInfoMessage("Parte Contrária removida com sucesso");
		return null;
	}
	
	public String addNumeroProcesso(){
		ProcessoNumero processoNumero = new ProcessoNumero();
		processoNumero.setFlagPrincipal(Boolean.FALSE);
		processoNumero.setProcesso(this.processos.get(indexProcesso));
		this.processos.get(indexProcesso).getProcessosNumerosTemp().add(processoNumero);
		return null;
	}
	
	public String removerNumeroProcesso() {

		if(TSUtil.isEmpty(this.processoNumeroSelecionado.getId())){
			
			this.processos.get(indexProcesso).getProcessosNumerosTemp().remove(this.processoNumeroSelecionado);
			
		} else{
			
			try {
				
				this.processoNumeroSelecionado.delete();
				this.processos.get(indexProcesso).getProcessosNumerosTemp().remove(this.processoNumeroSelecionado);
				CenajurUtil.addInfoMessage("Remoção realizada com sucesso");
				
			} catch (TSApplicationException e) {
				
				this.addErrorMessageKey(e.getMessage());
				
			}
			
		}
		
		return null;
	}
	
	public String addDocumento(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoProcesso().getArquivo())){
			CenajurUtil.addErrorMessage("Documento: Campo obrigatório");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		context.addCallbackParam("sucesso", true);
		
		getDocumentoProcesso().setProcesso(this.processos.get(indexProcesso));
		getDocumentoProcesso().setCategoriaDocumento(getCategoriaDocumento().getById());
		this.processos.get(indexProcesso).getDocumentos().add(getDocumentoProcesso());
		getCategoriaDocumento().setId(null);
		setDocumentoProcesso(new DocumentoProcesso());
		return null;
	}
	
	public void enviarDocumento(FileUploadEvent event) {
		
		getDocumentoProcesso().setArquivo(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		
		if(CenajurUtil.isDocumentoPdf(event.getFile())){
			getDocumentoProcesso().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
		}
		
		CenajurUtil.criaArquivo(event.getFile(), getDocumentoProcesso().getCaminhoUploadCompleto());
		
	}
	
	public String removerDocumento(){
		this.processos.get(indexProcesso).getDocumentos().remove(this.documentoSelecionado);
		return null;
	}
	
	public String limparDataArquivamentoProcessoCliente(){
		this.processoClienteSelecionado.setDataArquivamento(null);
		return null;
	}
	
	public String limparDataArquivamentoProcessoParteContraria(){
		this.processoParteContrariaSelecionada.setDataArquivamento(null);
		return null;
	}

	public int getIndexProcesso() {
		return indexProcesso;
	}

	public void setIndexProcesso(int indexProcesso) {
		this.indexProcesso = indexProcesso;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	public ProcessoNumero getProcessoNumeroSelecionado() {
		return processoNumeroSelecionado;
	}

	public void setProcessoNumeroSelecionado(ProcessoNumero processoNumeroSelecionado) {
		this.processoNumeroSelecionado = processoNumeroSelecionado;
	}

	public Processo getProcessoSelecionado() {
		return processoSelecionado;
	}

	public void setProcessoSelecionado(Processo processoSelecionado) {
		this.processoSelecionado = processoSelecionado;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public ProcessoCliente getProcessoClienteSelecionado() {
		return processoClienteSelecionado;
	}

	public void setProcessoClienteSelecionado(ProcessoCliente processoClienteSelecionado) {
		this.processoClienteSelecionado = processoClienteSelecionado;
	}

	public ParteContraria getParteContrariaSelecionada() {
		return parteContrariaSelecionada;
	}

	public void setParteContrariaSelecionada(ParteContraria parteContrariaSelecionada) {
		this.parteContrariaSelecionada = parteContrariaSelecionada;
	}

	public ProcessoParteContraria getProcessoParteContrariaSelecionada() {
		return processoParteContrariaSelecionada;
	}

	public void setProcessoParteContrariaSelecionada(ProcessoParteContraria processoParteContrariaSelecionada) {
		this.processoParteContrariaSelecionada = processoParteContrariaSelecionada;
	}

	public DocumentoProcesso getDocumentoProcesso() {
		return documentoProcesso;
	}

	public void setDocumentoProcesso(DocumentoProcesso documentoProcesso) {
		this.documentoProcesso = documentoProcesso;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}

	public DocumentoProcesso getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocumentoProcesso documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public ProcessoAndamentoUtil getProcessoAndamentoUtil() {
		return processoAndamentoUtil;
	}

	public void setProcessoAndamentoUtil(ProcessoAndamentoUtil processoAndamentoUtil) {
		this.processoAndamentoUtil = processoAndamentoUtil;
	}

	public ProcessoAudienciaUtil getProcessoAudienciaUtil() {
		return processoAudienciaUtil;
	}

	public void setProcessoAudienciaUtil(ProcessoAudienciaUtil processoAudienciaUtil) {
		this.processoAudienciaUtil = processoAudienciaUtil;
	}
	
}
