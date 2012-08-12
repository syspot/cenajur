package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Estado;
import br.com.cenajur.model.Lotacao;

@ViewScoped
@ManagedBean(name = "lotacaoPesquisaFaces")
public class LotacaoPesquisaFaces extends PesquisaFaces<Lotacao> {

	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	
	@PostConstruct
	protected void init() {
		this.initCombo();
		this.limpar();
	}
	
	@Override
	public String limpar() {
		setModel(new Lotacao());
		getModel().setCidade(new Cidade());
		getModel().getCidade().setEstado(new Estado());
		return null;
	}
	
	private void initCombo(){
		this.estados = super.initCombo(new Estado().findAll(), "id", "descricao");
	}
	
	public String atualizarComboCidades(){
		this.cidades = super.initCombo(getModel().getCidade().findByModel("descricao"), "id", "descricao");
		return null;
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
	
}
