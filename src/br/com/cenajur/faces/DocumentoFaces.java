package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.DocumentoGeral;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "documentoFaces")
public class DocumentoFaces extends CrudFaces<DocumentoGeral> {

	private List<SelectItem> categoriasDocumentos;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.categoriasDocumentos = this.initCombo(getCrudModel().getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new DocumentoGeral());
		getCrudModel().setCategoriaDocumento(new CategoriaDocumento());
		getCrudModel().getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_GERAL));
		setFlagAlterar(Boolean.FALSE);
		setTabIndex(1);
		return null;
	}
	
	@Override
	public String limparPesquisa(){
		setCrudPesquisaModel(new DocumentoGeral());
		getCrudPesquisaModel().setCategoriaDocumento(new CategoriaDocumento());
		getCrudPesquisaModel().getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_GERAL));
		
		setGrid(new ArrayList<DocumentoGeral>());
		return null;
	}
	
	@Override
	protected void preInsert() {
		getCrudModel().setDataCadastro(new Date());
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(TSUtil.isEmpty(getCrudModel().getId()) && TSUtil.isEmpty(getCrudModel().getArquivo())){
			erro = true;
			CenajurUtil.addErrorMessage("Documento: Campo obrigatório");
		}
		
		if(getCrudModel().getDescricao().length() > 100){
			erro = true;
			CenajurUtil.addErrorMessage("Descrição: Campo muito longo, tamanho máximo de 500 caracteres");
		}
		
		return erro;
	}
	
	public void enviarDocumento(FileUploadEvent event) {
		
		getCrudModel().setArquivo(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		
		if(CenajurUtil.isDocumentoPdf(event.getFile())){
			getCrudModel().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
		}
		
		CenajurUtil.criaArquivo(event.getFile(), getCrudModel().getCaminhoUploadCompleto());
		
	}

	public List<SelectItem> getCategoriasDocumentos() {
		return categoriasDocumentos;
	}

	public void setCategoriasDocumentos(List<SelectItem> categoriasDocumentos) {
		this.categoriasDocumentos = categoriasDocumentos;
	}
	
}
