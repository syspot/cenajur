package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Estado;
import br.com.cenajur.model.Lotacao;

@ViewScoped
@ManagedBean(name = "lotacaoFaces")
public class LotacaoFaces extends CrudFaces<Lotacao> {

	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private List<SelectItem> cidadesPesquisa;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.estados = super.initCombo(new Estado().findAll("descricao"), "id", "descricao");
	}
	
	public String atualizarComboCidades(){
		this.cidades = super.initCombo(getCrudModel().getCidade().findCombo(), "id", "descricao");
		return null;
	}
	
	public String atualizarComboCidadesPesquisa(){
		this.cidadesPesquisa = super.initCombo(getCrudPesquisaModel().getCidade().findCombo(), "id", "descricao");
		return null;
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Lotacao());
		getCrudModel().setCidade(new Cidade());
		getCrudModel().getCidade().setEstado(new Estado());
		setFlagAlterar(Boolean.FALSE);
		return null;
	}

	@Override
	public String limparPesquisa(){
		
		setCrudPesquisaModel(new Lotacao());
		getCrudPesquisaModel().setCidade(new Cidade());
		getCrudPesquisaModel().getCidade().setEstado(new Estado());
		setGrid(new ArrayList<Lotacao>());
		return null;
	}
	
	@Override
	protected void posDetail() {
		this.atualizarComboCidades();
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

	public List<SelectItem> getCidadesPesquisa() {
		return cidadesPesquisa;
	}

	public void setCidadesPesquisa(List<SelectItem> cidadesPesquisa) {
		this.cidadesPesquisa = cidadesPesquisa;
	}
	
}
