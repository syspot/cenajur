package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

public class ProcessoAndamentoUtil {

	private Processo crudModel;
	private CategoriaDocumento categoriaDocumento;
	private List<SelectItem> categoriasDocumentos;
	
	private AndamentoProcesso andamentoProcesso;
	private AndamentoProcesso andamentoProcessoSelecionado;
	
	private DocumentoAndamentoProcesso documentoAndamentoProcesso;
	private DocumentoAndamentoProcesso documentoAndamentoProcessoSelecionado;
	
	public ProcessoAndamentoUtil(Processo processo) {
		setCrudModel(processo);
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_ANDAMENTO_PROCESSO));
		setDocumentoAndamentoProcesso(new DocumentoAndamentoProcesso());
		this.initAndamentoProcesso();
		this.initCombo();
	}
	
	private void initAndamentoProcesso(){
		this.andamentoProcesso = new AndamentoProcesso();
		this.andamentoProcesso.setTipoAndamentoProcesso(new TipoAndamentoProcesso());
		this.andamentoProcesso.setDocumentos(new ArrayList<DocumentoAndamentoProcesso>());
		this.andamentoProcesso.setProcesso(getCrudModel());
	}
	
	private void initCombo(){
		this.categoriasDocumentos = TSFacesUtil.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}
	
	public String limpar(){
		this.initAndamentoProcesso();
		return null;
	}
	
	public void enviarDocumentoAndamentoProcesso(FileUploadEvent event) {
		getDocumentoAndamentoProcesso().setDocumento(event.getFile());
		getDocumentoAndamentoProcesso().setArquivo(CenajurUtil.obterNomeTemporarioArquivo(event.getFile()));
		getDocumentoAndamentoProcesso().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
	}
		
	public String addDocumentoAndamentoProcesso(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoAndamentoProcesso().getDocumento())){
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
		

		if(this.andamentoProcesso.getDescricao().length() > 500){
			erro = true;
			CenajurUtil.addErrorMessage("Descrição: Campo muito longo, tamanho máximo de 500 caracteres");
		}
		
		return erro;
	}
	
	public String cadastrarAndamentoProcesso() throws TSApplicationException{
		
		if(validaCampos()){
			return null;
		}
		
		this.andamentoProcesso.setTipoAndamentoProcesso(this.andamentoProcesso.getTipoAndamentoProcesso().getById());
		this.andamentoProcesso.setDataAtualizacao(new Date());
		this.andamentoProcesso.setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		this.andamentoProcesso.setDataCadastro(new Date());
		this.andamentoProcesso.save();
		
		for(DocumentoAndamentoProcesso doc : this.andamentoProcesso.getDocumentos()){
			
			if(!TSUtil.isEmpty(doc.getDocumento())){
				
				doc.setArquivo(doc.getId() + TSFile.obterExtensaoArquivo(doc.getArquivo()));
				CenajurUtil.criaArquivo(doc.getDocumento(), doc.getCaminhoUploadCompleto());
				
				doc.update();
			}
		}
		
		CenajurUtil.addInfoMessage("Andamento cadastrado com sucesso");
		this.initAndamentoProcesso();
		getCrudModel().setAndamentos(this.andamentoProcesso.findByModel("descricao"));
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
		
		AndamentoProcesso aux = this.andamentoProcesso.getById();
		
		int posicao = 0;
		
		for(DocumentoAndamentoProcesso doc : this.andamentoProcesso.getDocumentos()){
			
			if(!TSUtil.isEmpty(doc.getDocumento())){
				
				doc.setId(aux.getDocumentos().get(posicao).getId());
				doc.setArquivo(doc.getId() + TSFile.obterExtensaoArquivo(doc.getArquivo()));
				CenajurUtil.criaArquivo(doc.getDocumento(), doc.getCaminhoUploadCompleto());
				
				doc.update();
			}
			posicao++;
		}
		
		this.initAndamentoProcesso();
		getCrudModel().setAndamentos(this.andamentoProcesso.findByModel("descricao"));
		CenajurUtil.addInfoMessage("Alteração realizada com sucesso");
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

}
