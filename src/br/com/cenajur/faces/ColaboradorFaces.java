package br.com.cenajur.faces;

import java.util.ArrayList;
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
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.Utilitarios;
import br.com.topsys.util.TSUtil;

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
		this.grupos = super.initCombo(new Grupo().findAll(), "id", "descricao");
		this.estados = super.initCombo(new Estado().findAll(), "id", "descricao");
		this.cidades = super.initCombo(new Cidade().findAll(), "id", "descricao");
		this.tiposColaborador = super.initCombo(new TipoColaborador().findAll(), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Colaborador());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		getCrudModel().setLogin(null);
		getCrudModel().setSenha(null);
		getCrudModel().setSenha2(null);
		getCrudModel().setCidade(new Cidade());
		getCrudModel().getCidade().setEstado(new Estado());
		getCrudModel().setTipoColaborador(new TipoColaborador());
		getCrudModel().setGrupo(new Grupo());
		setFlagAlterar(Boolean.FALSE);
		return SUCESSO;
	}
	
	@Override
	public String limparPesquisa(){
		super.setFieldOrdem("nome");
		setCrudPesquisaModel(new Colaborador());
		getCrudPesquisaModel().setGrupo(new Grupo());
		getCrudPesquisaModel().setTipoColaborador(new TipoColaborador());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		setGrid(new ArrayList<Colaborador>());
		return SUCESSO;
	}
	
	@Override
	protected void preInsert() {
		getCrudModel().setSenha(Utilitarios.gerarHash(getCrudModel().getSenha()));
	}
	
	@Override
	protected void preUpdate() {
		
		if(TSUtil.isEmpty(getCrudModel().getSenha())){
			
			Colaborador c = getCrudModel().getById();
			getCrudModel().setSenha(c.getSenha());
			
		} else{
			
			getCrudModel().setSenha(Utilitarios.gerarHash(getCrudModel().getSenha()));
			
		}
	}
	
	@Override
	protected void posDetail() {
		getCrudModel().setSenha(null);
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
	
	
}
