package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Estado;

@SessionScoped
@ManagedBean(name = "clientePesquisaFaces")
public class ClientePesquisaFaces extends PesquisaFaces<Cliente> {

	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	
	@PostConstruct
	protected void init() {
		setFieldOrdem("nome");
		this.initCombo();
		this.limpar();
	}
	
	private void initCombo(){
		this.estados = super.initCombo(new Estado().findAll("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setModel(new Cliente());
		getModel().setCidade(new Cidade());
		getModel().getCidade().setEstado(new Estado());
		getModel().setFlagAtivo(Boolean.TRUE);
		return "sucesso";
	}
	
	public String atualizarComboCidades(){
		this.cidades = super.initCombo(getModel().getCidade().findCombo(), "id", "descricao");
		return "sucesso";
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
