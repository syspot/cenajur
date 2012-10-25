package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.ParteContraria;
import br.com.cenajur.model.TipoDocumento;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.topsys.exception.TSApplicationException;

@ViewScoped
@ManagedBean(name = "parteContrariaFaces")
public class ParteContrariaFaces extends CrudFaces<ParteContraria> {

	private ParteContraria parteContraria;
	
	private List<SelectItem> tiposDocumentos;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombos();
	}
	
	private void initCombos(){
		this.tiposDocumentos = this.initCombo(new TipoDocumento().findAll("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new ParteContraria());
		getCrudModel().setTipoDocumento(new TipoDocumento());
		this.parteContraria = new ParteContraria();
		this.parteContraria.setTipoDocumento(new TipoDocumento());
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa(){
		setCrudPesquisaModel(new ParteContraria());
		getCrudPesquisaModel().setTipoDocumento(new TipoDocumento());
		
		setGrid(new ArrayList<ParteContraria>());
		return null;
	}

	@Override
	protected void prePersist() {
		getCrudModel().setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		getCrudModel().setDataAtualizacao(new Date());
	}
	
	public String inserirPorDialogPesquisa() throws TSApplicationException{
		this.parteContraria.setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		this.parteContraria.setDataAtualizacao(new Date());
		this.parteContraria.save();
		return null;
	}
	
	public List<SelectItem> getTiposDocumentos() {
		return tiposDocumentos;
	}

	public void setTiposDocumentos(List<SelectItem> tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

	public ParteContraria getParteContraria() {
		return parteContraria;
	}

	public void setParteContraria(ParteContraria parteContraria) {
		this.parteContraria = parteContraria;
	}

}
