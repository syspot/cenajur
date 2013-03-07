package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;

import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.DocumentoCliente;
import br.com.cenajur.model.DocumentoColaborador;
import br.com.cenajur.model.Estado;
import br.com.cenajur.model.Grupo;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.model.TipoColaborador;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.Utilitarios;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@ManagedBean(name = "colaboradorFaces")
public class ColaboradorFaces extends CrudFaces<Colaborador> {

	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private List<SelectItem> tiposColaborador;
	private List<SelectItem> grupos;
	private DocumentoColaborador documentoColaborador;
	private CategoriaDocumento categoriaDocumento;
	private List<SelectItem> categoriasDocumentos;
	private DocumentoCliente documentoSelecionado;
	
	private String senha;
	private String senha2;
	
	@PostConstruct
	protected void init() {
		
		this.clearFields();
		this.initCombos();
		
		AutenticacaoFaces autenticacaoFaces = (AutenticacaoFaces) TSFacesUtil.getManagedBean("autenticacaoFaces");
		
		if(!TSUtil.isEmpty(autenticacaoFaces) && !TSUtil.isEmpty(autenticacaoFaces.getColaboradorSelecionado())){
			this.setCrudModel(autenticacaoFaces.getColaboradorSelecionado());
			this.detailEvent();
		}
		
	}
	
	private void initCombos() {
		this.grupos = super.initCombo(new Grupo().findAll(), "id", "descricao");
		this.estados = super.initCombo(new Estado().findAll(), "id", "descricao");
		this.cidades = super.initCombo(new Cidade().findAll(), "id", "descricao");
		this.tiposColaborador = super.initCombo(new TipoColaborador().findAll(), "id", "descricao");
		this.categoriasDocumentos = this.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Colaborador());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		getCrudModel().setLogin(null);
		getCrudModel().setCidade(new Cidade());
		getCrudModel().getCidade().setEstado(new Estado());
		getCrudModel().setTipoColaborador(new TipoColaborador());
		getCrudModel().setGrupo(new Grupo());
		getCrudModel().setDocumentos(new ArrayList<DocumentoColaborador>());
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_COLABORADOR));
		setDocumentoColaborador(new DocumentoColaborador());
		setFlagAlterar(Boolean.FALSE);
		this.senha = null;
		this.senha2 = null;
		return null;
	}
	
	@Override
	public String limparPesquisa(){
		setCrudPesquisaModel(new Colaborador());
		getCrudPesquisaModel().setGrupo(new Grupo());
		getCrudPesquisaModel().setTipoColaborador(new TipoColaborador());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		setGrid(new ArrayList<Colaborador>());
		return null;
	}
	
	@Override
	protected void prePersist() {
		getCrudModel().setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		getCrudModel().setDataAtualizacao(new Date());
		
		if(!TSUtil.isEmpty(this.senha)){
			getCrudModel().setSenha(Utilitarios.gerarHash(this.senha));
		}
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(TSUtil.isEmpty(getCrudModel().getId()) && !TSUtil.isValidCPF(TSUtil.removerNaoDigitos(getCrudModel().getCpf()))){
			erro = true;
			CenajurUtil.addErrorMessage("CPF inválido");
		}
		
		return erro;
	}
	
	@Override
	protected void posDetail() {
		this.atualizarComboCidades();
	}
	
	public String removerDocumento(){
		getCrudModel().getDocumentos().remove(this.documentoSelecionado);
		return null;
	}
	
	public String atualizarComboCidades(){
		this.cidades = super.initCombo(getCrudModel().getCidade().findCombo(), "id", "descricao");
		return null;
	}
	
	public void enviarDocumento(FileUploadEvent event) {
		
		getDocumentoColaborador().setArquivo(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		
		if(CenajurUtil.isDocumentoPdf(event.getFile())){
			getDocumentoColaborador().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
		}
		
		CenajurUtil.criaArquivo(event.getFile(), getDocumentoColaborador().getCaminhoUploadCompleto());
		
	}
	
	public String addDocumento(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoColaborador().getArquivo())){
			CenajurUtil.addErrorMessage("Documento: Campo obrigatório");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		if(getDocumentoColaborador().getDescricao().length() > 100){
			CenajurUtil.addErrorMessage("Descrição: Campo muito longo, tamanho máximo de 100 caracteres");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		context.addCallbackParam("sucesso", true);
		
		getDocumentoColaborador().setColaborador(getCrudModel());
		getDocumentoColaborador().setCategoriaDocumento(getCategoriaDocumento().getById());
		getCrudModel().getDocumentos().add(getDocumentoColaborador());
		getCategoriaDocumento().setId(null);
		setDocumentoColaborador(new DocumentoColaborador());
		return null;
	}
	
	public void oncapture(CaptureEvent captureEvent) {  
        
		String nomeFoto = TSUtil.gerarId() + ".jpg";
        
		getCrudModel().setUrlImagem(Constantes.PASTA_DOWNLOAD_IMAGEM + nomeFoto);
		
		CenajurUtil.criaArquivo(captureEvent.getData(), Constantes.PASTA_UPLOAD_IMAGEM + nomeFoto);
		
    }
	
	public boolean isAdvogado(){
		return Constantes.TIPO_COLABORADOR_ADVOGADO.equals(getCrudModel().getTipoColaborador().getId());
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

	public List<SelectItem> getTiposColaborador() {
		return tiposColaborador;
	}

	public void setTiposColaborador(List<SelectItem> tiposColaborador) {
		this.tiposColaborador = tiposColaborador;
	}

	public List<SelectItem> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<SelectItem> grupos) {
		this.grupos = grupos;
	}

	public DocumentoColaborador getDocumentoColaborador() {
		return documentoColaborador;
	}

	public void setDocumentoColaborador(DocumentoColaborador documentoColaborador) {
		this.documentoColaborador = documentoColaborador;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}

	public List<SelectItem> getCategoriasDocumentos() {
		return categoriasDocumentos;
	}

	public void setCategoriasDocumentos(List<SelectItem> categoriasDocumentos) {
		this.categoriasDocumentos = categoriasDocumentos;
	}

	public DocumentoCliente getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocumentoCliente documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}
	
}
