package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.ParteContraria;
import br.com.cenajur.model.TipoDocumento;

@ViewScoped
@ManagedBean(name = "parteContrariaPesquisaFaces")
public class ParteContrariaPesquisaFaces extends PesquisaFaces<ParteContraria> {

	private List<SelectItem> tiposDocumentos;
	
	private boolean cadastrando;
	
	@PostConstruct
	protected void init() {
		this.initCombo();
		this.limpar();
	}
	
	@Override
	public String limpar() {
		setModel(new ParteContraria());
		getModel().setTipoDocumento(new TipoDocumento());
		return null;
	}
	
	private void initCombo(){
		this.tiposDocumentos = super.initCombo(new TipoDocumento().findAll(), "id", "descricao");
	}

	public List<SelectItem> getTiposDocumentos() {
		return tiposDocumentos;
	}

	public void setTiposDocumentos(List<SelectItem> tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

	public boolean isCadastrando() {
		return cadastrando;
	}

	public void setCadastrando(boolean cadastrando) {
		this.cadastrando = cadastrando;
	}

}
