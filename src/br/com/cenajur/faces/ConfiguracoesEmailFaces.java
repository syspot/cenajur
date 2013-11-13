package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.ConfiguracoesEmail;
import br.com.cenajur.model.ConfiguracoesReplaceEmail;
import br.com.cenajur.model.RegrasEmail;

@ViewScoped
@ManagedBean(name = "configuracoesEmailFaces")
public class ConfiguracoesEmailFaces extends CrudFaces<ConfiguracoesEmail> {

	private List<SelectItem> regrasEmails;
	private List<ConfiguracoesReplaceEmail> configuracoesReplaceEmail;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.regrasEmails = super.initCombo(new RegrasEmail().findAll("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new ConfiguracoesEmail());
		getCrudModel().setRegrasEmail(new RegrasEmail());
		getCrudModel().setFlagImediato(Boolean.TRUE);
		setFlagAlterar(Boolean.FALSE);
		this.configuracoesReplaceEmail = new ConfiguracoesReplaceEmail().findAll("descricao");
		setTabIndex(1);
		return null;
	}

	@Override
	public String limparPesquisa(){
		setCrudPesquisaModel(new ConfiguracoesEmail());
		getCrudPesquisaModel().setRegrasEmail(new RegrasEmail());
		setGrid(new ArrayList<ConfiguracoesEmail>());
		return null;
	}

	public List<SelectItem> getRegrasEmails() {
		return regrasEmails;
	}

	public void setRegrasEmails(List<SelectItem> regrasEmails) {
		this.regrasEmails = regrasEmails;
	}

	public List<ConfiguracoesReplaceEmail> getConfiguracoesReplaceEmail() {
		return configuracoesReplaceEmail;
	}

	public void setConfiguracoesReplaceEmail(List<ConfiguracoesReplaceEmail> configuracoesReplaceEmail) {
		this.configuracoesReplaceEmail = configuracoesReplaceEmail;
	}
	
}
