package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Menu;
import br.com.cenajur.model.Permissao;

@ViewScoped
@ManagedBean(name = "permissaoPesquisaFaces")
public class PermissaoPesquisaFaces extends PesquisaFaces<Permissao> {

	private List<SelectItem> menus;
	
	@PostConstruct
	protected void init() {
		this.initCombo();
		this.limpar();
	}
	
	@Override
	public String limpar() {
		setModel(new Permissao());
		getModel().setMenu(new Menu());
		return null;
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
