package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.DocumentoAndamentoProcesso;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.TipoAndamentoProcesso;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;

@SessionScoped
@ManagedBean(name = "andamentoProcessoFaces")
public class AndamentoProcessoFaces extends CrudFaces<AndamentoProcesso> {

	private List<SelectItem> tiposAndamentosProcessos;
	private List<SelectItem> categoriasDocumentos;
	
	private Processo processoSelecionado;
	
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
		return "sucesso";
	}
	
	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("descricao");
		setCrudPesquisaModel(new AndamentoProcesso());
		getCrudPesquisaModel().setProcesso(new Processo());
		getCrudPesquisaModel().setTipoAndamentoProcesso(new TipoAndamentoProcesso());
		setGrid(new ArrayList<AndamentoProcesso>());
		return "sucesso";
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(TSUtil.isEmpty(getCrudModel().getProcesso().getId())){
			erro = true;
			CenajurUtil.addErrorMessage("Processo: Campo obrigatório");
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
	protected void posPersist() throws TSSystemException, TSApplicationException{
		
		AndamentoProcesso aux = getCrudModel().getById();
		
		int posicao = 0;
		
		for(DocumentoAndamentoProcesso doc : getCrudModel().getDocumentos()){
			
			if(!TSUtil.isEmpty(doc.getDocumento())){
				//TODO alterar esse pog depois da apresentação
				doc.setId(aux.getDocumentos().get(posicao).getId());
				doc.setArquivo(aux.getDocumentos().get(posicao).getId() + TSFile.obterExtensaoArquivo(doc.getArquivo()));
				CenajurUtil.criaArquivo(doc.getDocumento(), doc.getCaminhoUploadCompleto());
				
				doc.update();
				
			}
			
			posicao++;
		}
		
	}
	
	public String addProcesso(){
		getCrudModel().setProcesso(this.processoSelecionado);
		CenajurUtil.addInfoMessage("Processo adicionado com sucesso");
		return "sucesso";
	}
	
	public void enviarDocumento(FileUploadEvent event) {
		getDocumentoAndamentoProcesso().setDocumento(event.getFile());
		getDocumentoAndamentoProcesso().setArquivo(CenajurUtil.obterNomeArquivo(event.getFile()));
	}
		
	public String addDocumento(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoAndamentoProcesso().getDocumento())){
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
		return "sucesso";
	}
	
	public String removerDocumento(){
		getCrudModel().getDocumentos().remove(this.documentoSelecionado);
		return "sucesso";
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

	public Processo getProcessoSelecionado() {
		return processoSelecionado;
	}

	public void setProcessoSelecionado(Processo processoSelecionado) {
		this.processoSelecionado = processoSelecionado;
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
