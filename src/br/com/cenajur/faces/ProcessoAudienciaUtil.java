package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.AudienciaAdvogado;
import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.DocumentoAudiencia;
import br.com.cenajur.model.Processo;
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
import br.com.topsys.web.util.TSFacesUtil;

public class ProcessoAudienciaUtil {

	private Processo crudModel;
	private CategoriaDocumento categoriaDocumento;
	private List<SelectItem> categoriasDocumentos;
	
	private Audiencia audiencia;
	private Audiencia audienciaSelecionada;
	
	private DocumentoAudiencia documentoAudiencia;
	private DocumentoAudiencia documentoAudienciaSelecionado;
	private Colaborador advogadoSelecionado;

	public ProcessoAudienciaUtil(Processo processo) {
		setCrudModel(processo);
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_AUDIENCA));
		setDocumentoAudiencia(new DocumentoAudiencia());
		this.initAudiencia();
		this.initCombo();
	}
	
	private void initAudiencia(){
		this.audiencia = new Audiencia();
		this.audiencia.setAdvogado(new Colaborador());
		this.audiencia.setSituacaoAudiencia(new SituacaoAudiencia());
		this.audiencia.setVara(new Vara());
		this.audiencia.setDocumentos(new ArrayList<DocumentoAudiencia>());
		this.audiencia.setAudienciasAdvogados(new ArrayList<AudienciaAdvogado>());
	}
	
	private void initCombo(){
		this.categoriasDocumentos = TSFacesUtil.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}

	public String limpar(){
		this.initAudiencia();
		return null;
	}

	public void enviarDocumento(FileUploadEvent event) {
		getDocumentoAudiencia().setDocumento(event.getFile());
		getDocumentoAudiencia().setArquivo(CenajurUtil.obterNomeTemporarioArquivo(event.getFile()));
		getDocumentoAudiencia().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
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
		
		if(TSUtil.isEmpty(this.audiencia.getAudienciasAdvogados())){
			erro = true;
			CenajurUtil.addErrorMessage("Advogado: Campo obrigatório");
		}

		if(this.audiencia.getDescricao().length() > 500){
			erro = true;
			CenajurUtil.addErrorMessage("Descrição: Campo muito longo, tamanho máximo de 500 caracteres");
		}
		
		return erro;
	}
	
	public String cadastrarAudiencia() throws TSApplicationException{
		
		if(validaCampos()){
			return null;
		}
		
		this.audiencia.setVara(this.audiencia.getVara().getById());
		this.audiencia.setDataAtualizacao(new Date());
		this.audiencia.setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		this.audiencia.setDataCadastro(new Date());
		this.audiencia.save();

		for(DocumentoAudiencia doc : this.audiencia.getDocumentos()){
			
			if(!TSUtil.isEmpty(doc.getDocumento())){
				
				doc.setArquivo(doc.getId() + TSFile.obterExtensaoArquivo(doc.getArquivo()));
				CenajurUtil.criaArquivo(doc.getDocumento(), doc.getCaminhoUploadCompleto());
				
				doc.update();
			}
		}
		
		CenajurUtil.addInfoMessage("Audiência cadastrada com sucesso");
		this.initAudiencia();
		this.audiencia.setProcessoNumero(new ProcessoNumero().obterNumeroProcessoPrincipal(getCrudModel()));
		getCrudModel().setAudiencias(this.audiencia.findByModel("descricao"));
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
		
		for(DocumentoAudiencia doc : this.audiencia.getDocumentos()){
			
			if(!TSUtil.isEmpty(doc.getDocumento())){
				
				DocumentoAudiencia documento = doc.getByModel();
				
				doc.setId(documento.getId());
				doc.setArquivo(doc.getId() + TSFile.obterExtensaoArquivo(doc.getArquivo()));
				CenajurUtil.criaArquivo(doc.getDocumento(), doc.getCaminhoUploadCompleto());
				
				doc.update();
			}
			
		}
		
		this.initAudiencia();
		this.audiencia.setProcessoNumero(new ProcessoNumero().obterNumeroProcessoPrincipal(getCrudModel()));
		getCrudModel().setAudiencias(this.audiencia.findByModel("descricao"));
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

}
