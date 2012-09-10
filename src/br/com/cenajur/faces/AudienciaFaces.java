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
import br.com.cenajur.model.DocumentoAudiencia;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.SituacaoAudiencia;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.model.Vara;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
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
		return null;
	}
	
	@Override
	public String limparPesquisa(){
		setCrudPesquisaModel(new Audiencia());
		getCrudPesquisaModel().setAdvogado(new Colaborador());
		getCrudPesquisaModel().setProcessoNumero(new ProcessoNumero());
		getCrudPesquisaModel().setSituacaoAudiencia(new SituacaoAudiencia());
		getCrudPesquisaModel().setVara(new Vara());
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
		
		for(DocumentoAudiencia doc : getCrudModel().getDocumentos()){
			
			if(!TSUtil.isEmpty(doc.getDocumento())){

				DocumentoAudiencia documento = doc.getByModel();
				
				doc.setId(documento.getId());
				doc.setArquivo(doc.getId() + TSFile.obterExtensaoArquivo(doc.getArquivo()));
				CenajurUtil.criaArquivo(doc.getDocumento(), doc.getCaminhoUploadCompleto());
				
				doc.update();
				
			}
			
		}
		
		//TODO - Enviar E-mail para o associado informando a mudança na Audiencia - Verificar com Roque as Configurações de envio de E-mail
		
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
		getDocumentoAudiencia().setDocumento(event.getFile());
		getDocumentoAudiencia().setArquivo(CenajurUtil.obterNomeTemporarioArquivo(event.getFile()));
		
		if(CenajurUtil.isDocumentoPdf(event.getFile())){
			getDocumentoAudiencia().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
		}
	}
		
	public String addDocumento(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoAudiencia().getDocumento())){
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
