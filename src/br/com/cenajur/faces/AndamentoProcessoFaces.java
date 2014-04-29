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

import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.DocumentoAndamentoProcesso;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.TipoAndamentoProcesso;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.EmailLayoutUtil;
import br.com.cenajur.util.SMSLayoutUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "andamentoProcessoFaces")
public class AndamentoProcessoFaces extends CrudFaces<AndamentoProcesso> {

	private List<SelectItem> tiposAndamentosProcessos;
	private List<SelectItem> categoriasDocumentos;
	
	private ProcessoNumero processoNumeroSelecionado;
	
	private CategoriaDocumento categoriaDocumento;
	private DocumentoAndamentoProcesso documentoAndamentoProcesso;
	private DocumentoAndamentoProcesso documentoSelecionado;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.tiposAndamentosProcessos = this.initCombo(new TipoAndamentoProcesso().findAll("descricao"), "id", "descricao");
		this.categoriasDocumentos = this.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new AndamentoProcesso());
		getCrudModel().setTipoAndamentoProcesso(new TipoAndamentoProcesso());
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_ANDAMENTO_PROCESSO));
		getCrudModel().setDocumentos(new ArrayList<DocumentoAndamentoProcesso>());
		setDocumentoAndamentoProcesso(new DocumentoAndamentoProcesso());
		setFlagAlterar(Boolean.FALSE);
		setTabIndex(1);
		return null;
	}
	
	@Override
	public String limparPesquisa(){
		setCrudPesquisaModel(new AndamentoProcesso());
		getCrudPesquisaModel().setProcessoNumero(new ProcessoNumero());
		getCrudPesquisaModel().setTipoAndamentoProcesso(new TipoAndamentoProcesso());
		getCrudPesquisaModel().setDataInicial(new Date());
		getCrudPesquisaModel().setDataFinal(new Date());
		setGrid(new ArrayList<AndamentoProcesso>());
		return null;
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(TSUtil.isEmpty(getCrudModel().getProcessoNumero()) || TSUtil.isEmpty(getCrudModel().getProcessoNumero().getId())){
			erro = true;
			CenajurUtil.addErrorMessage("Processo: Campo obrigatório");
		}
		
		if(TSUtil.isEmpty(getCrudModel().getTipoAndamentoProcesso()) 
				|| TSUtil.isEmpty(getCrudModel().getTipoAndamentoProcesso().getId())){
			
			erro = true;
			CenajurUtil.addErrorMessage("Tipo do Andamento: Campo obrigatório");
		}

		return erro;
	}
	
	@Override
	protected void preInsert() {
		getCrudModel().setDataCadastro(new Date());
	}
	
	@Override
	protected void prePersist() {
		getCrudModel().setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		getCrudModel().setDataAtualizacao(new Date());
	}
	
	@Override
	protected void posPersist() throws TSApplicationException{
		
		new EmailLayoutUtil().enviarEmailAndamentoProcesso(getCrudModel());
		new SMSLayoutUtil().enviarSMSAndamentoProcesso(getCrudModel().getProcessoNumero().getProcesso().getById());
		
	}
	
	public String addProcessoNumero(){
		getCrudModel().setProcessoNumero(this.processoNumeroSelecionado);
		CenajurUtil.addInfoMessage("Processo adicionado com sucesso");
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
		
		context.addCallbackParam("sucesso", true);
		
		getDocumentoAndamentoProcesso().setAndamentoProcesso(getCrudModel());
		getDocumentoAndamentoProcesso().setCategoriaDocumento(getCategoriaDocumento().getById());
		getCrudModel().getDocumentos().add(getDocumentoAndamentoProcesso());
		getCategoriaDocumento().setId(null);
		setDocumentoAndamentoProcesso(new DocumentoAndamentoProcesso());
		return null;
	}
	
	public String removerDocumento(){
		getCrudModel().getDocumentos().remove(this.documentoSelecionado);
		return null;
	}

	public List<SelectItem> getTiposAndamentosProcessos() {
		return tiposAndamentosProcessos;
	}

	public void setTiposAndamentosProcessos(List<SelectItem> tiposAndamentosProcessos) {
		this.tiposAndamentosProcessos = tiposAndamentosProcessos;
	}

	public List<SelectItem> getCategoriasDocumentos() {
		return categoriasDocumentos;
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

	public DocumentoAndamentoProcesso getDocumentoAndamentoProcesso() {
		return documentoAndamentoProcesso;
	}

	public void setDocumentoAndamentoProcesso(DocumentoAndamentoProcesso documentoAndamentoProcesso) {
		this.documentoAndamentoProcesso = documentoAndamentoProcesso;
	}

	public DocumentoAndamentoProcesso getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocumentoAndamentoProcesso documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}
}
