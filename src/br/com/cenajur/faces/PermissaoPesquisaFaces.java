package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Menu;
import br.com.cenajur.model.Permissao;

@SessionScoped
@ManagedBean(name = "permissaoPesquisaFaces")
public class PermissaoPesquisaFaces extends PesquisaFaces<Permissao> {

	private List<SelectItem> menus;
	
	@PostConstruct
	protected void init() {
		this.limpar();
		this.initCombo();
		setFieldOrdem("descricao");
	}
	
	@Override
	public String limpar() {
		setModel(new Permissao());
		getModel().setMenu(new Menu());
		return this.find();
	}
	
	private void initCombo(){
		this.menus = super.initCombo(new Menu().findAll(), "id", "nome");
	}

	public List<SelectItem> getMenus() {
		return menus;
	}

	public void setMenus(List<SelectItem> menus) {
		this.menus = menus;
	}
	
}
