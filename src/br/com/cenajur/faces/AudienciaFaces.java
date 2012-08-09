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

import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.DocumentoAudiencia;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.SituacaoAudiencia;
import br.com.cenajur.model.SituacaoProcesso;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.model.Vara;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;

@SessionScoped
@ManagedBean(name = "audienciaFaces")
public class AudienciaFaces extends CrudFaces<Audiencia> {

	private List<SelectItem> varas;
	private List<SelectItem> advogados;
	private List<SelectItem> situacoesAudiencias;
	private List<SelectItem> categoriasDocumentos;
	
	private Processo processoSelecionado;
	
	private CategoriaDocumento categoriaDocumento;
	private DocumentoAudiencia documentoAudiencia;
	private DocumentoAudiencia documentoSelecionado;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.varas = this.initCombo(new Vara().findAll("descricao"), "id", "descricao");
		this.advogados = this.initCombo(new Colaborador().findAll("nome"), "id", "nome");
		this.situacoesAudiencias = this.initCombo(new SituacaoProcesso().findAll("descricao"), "id", "descricao");
		this.categoriasDocumentos = this.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Audiencia());
		getCrudModel().setAdvogado(new Colaborador());
		getCrudModel().setProcesso(new Processo());
		getCrudModel().setSituacaoAudiencia(new SituacaoAudiencia());
		getCrudModel().setVara(new Vara());
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_AUDIENCA));
		getCrudModel().setDocumentos(new ArrayList<DocumentoAudiencia>());
		setDocumentoAudiencia(new DocumentoAudiencia());
		setFlagAlterar(Boolean.FALSE);
		return "sucesso";
	}
	
	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("descricao");
		setCrudPesquisaModel(new Audiencia());
		getCrudPesquisaModel().setAdvogado(new Colaborador());
		getCrudPesquisaModel().setProcesso(new Processo());
		getCrudPesquisaModel().setSituacaoAudiencia(new SituacaoAudiencia());
		getCrudPesquisaModel().setVara(new Vara());
		setGrid(new ArrayList<Audiencia>());
		return "sucesso";
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
	protected void posPersist() throws TSSystemException, TSApplicationException{
		
		Audiencia aux = getCrudModel().getById();
		
		int posicao = 0;
		
		for(DocumentoAudiencia doc : getCrudModel().getDocumentos()){
			
			if(!TSUtil.isEmpty(doc.getDocumento())){
		
				doc.setId(aux.getDocumentos().get(posicao).getId());
				doc.setArquivo(doc.getId() + TSFile.obterExtensaoArquivo(doc.getArquivo()));
				CenajurUtil.criaArquivo(doc.getDocumento(), doc.getCaminhoUploadCompleto());
				
				doc.update();
				
			}
			
			posicao++;
		}
		
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
	
	public String addProcesso(){
		getCrudModel().setProcesso(this.processoSelecionado);
		CenajurUtil.addInfoMessage("Processo adicionado com sucesso");
		return "sucesso";
	}
	
	public void enviarDocumento(FileUploadEvent event) {
		getDocumentoAudiencia().setDocumento(event.getFile());
		getDocumentoAudiencia().setArquivo(CenajurUtil.obterNomeArquivo(event.getFile()));
	}
		
	public String addDocumento(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoAudiencia().getDocumento())){
			CenajurUtil.addErrorMessage("Documento: Campo obrigatório");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		context.addCallbackParam("sucesso", true);
		
		getDocumentoAudiencia().setAudiencia(getCrudModel());
		getDocumentoAudiencia().setCategoriaDocumento(getCategoriaDocumento().getById());
		getCrudModel().getDocumentos().add(getDocumentoAudiencia());
		getCategoriaDocumento().setId(null);
		setDocumentoAudiencia(new DocumentoAudiencia());
		return "sucesso";
	}
	
	public String removerDocumento(){
		getCrudModel().getDocumentos().remove(this.documentoSelecionado);
		return "sucesso";
	}

	public List<SelectItem> getVaras() {
		return varas;
	}

	public void setVaras(List<SelectItem> varas) {
		this.varas = varas;
	}

	public List<SelectItem> getAdvogados() {
		return advogados;
	}

	public void setAdvogados(List<SelectItem> advogados) {
		this.advogados = advogados;
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
	
}
