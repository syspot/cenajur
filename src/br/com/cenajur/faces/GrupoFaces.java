package br.com.cenajur.faces;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Grupo;
import br.com.cenajur.model.Permissao;
import br.com.cenajur.model.PermissaoGrupo;
import br.com.cenajur.util.CenajurUtil;

@SessionScoped
@ManagedBean(name = "grupoFaces")
public class GrupoFaces extends CrudFaces<Grupo> {

	private Permissao permissaoSelecionada;
	private PermissaoGrupo permissaoGrupoSelecionada;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Grupo());
		getCrudModel().setPermissoesGrupos(new ArrayList<PermissaoGrupo>());
		setFlagAlterar(Boolean.FALSE);
		return SUCESSO;
	}
		
	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("descricao");
		return super.limparPesquisa();
	}
	
	public String addPermissao(){
		
		PermissaoGrupo permissaoGrupo = new PermissaoGrupo();
		
		permissaoGrupo.setGrupo(getCrudModel());
		permissaoGrupo.setPermissao(this.permissaoSelecionada);
		permissaoGrupo.setFlagInserir(Boolean.TRUE);
		permissaoGrupo.setFlagAlterar(Boolean.TRUE);
		permissaoGrupo.setFlagExcluir(Boolean.TRUE);
		
		if(!this.getCrudModel().getPermissoesGrupos().contains(permissaoGrupo)){
			
			this.getCrudModel().getPermissoesGrupos().add(permissaoGrupo);
			CenajurUtil.addInfoMessage("Permissão adicionada com sucesso");
			
		} else{
			
			CenajurUtil.addErrorMessage("Essa permissão já foi adicionada");
		}
		
		return SUCESSO;
	}
	
	public String removePermissao(){
		getCrudModel().getPermissoesGrupos().remove(this.permissaoGrupoSelecionada);
		return "sucesso";
	}

	public Permissao getPermissaoSelecionada() {
		return permissaoSelecionada;
	}

	public void setPermissaoSelecionada(Permissao permissaoSelecionada) {
		this.permissaoSelecionada = permissaoSelecionada;
	}

	public PermissaoGrupo getPermissaoGrupoSelecionada() {
		return permissaoGrupoSelecionada;
	}

	public void setPermissaoGrupoSelecionada(PermissaoGrupo permissaoGrupoSelecionada) {
		this.permissaoGrupoSelecionada = permissaoGrupoSelecionada;
	}

}
