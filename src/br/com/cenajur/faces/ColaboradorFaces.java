package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Estado;
import br.com.cenajur.model.Grupo;
import br.com.cenajur.model.TipoColaborador;

@SessionScoped
@ManagedBean(name = "colaboradorFaces")
public class ColaboradorFaces extends CrudFaces<Colaborador> {

	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private List<SelectItem> tiposColaborador;
	private List<SelectItem> grupos;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombos();
	}
	
	private void initCombos() {
		this.estados = super.initCombo(new Estado().findAll(), "id", "descricao");
		this.cidades = super.initCombo(new Cidade().findAll(), "id", "descricao");
		this.tiposColaborador = super.initCombo(new TipoColaborador().findAll(), "id", "descricao");
		this.grupos = super.initCombo(new Grupo().findAll(), "id", "descricao");
	}
	
	@Override
	public String limparPesquisa(){	
		super.setFieldOrdem("nome");
		return super.limparPesquisa();
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
	
	
}
