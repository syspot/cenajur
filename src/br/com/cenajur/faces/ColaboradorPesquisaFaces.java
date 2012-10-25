package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.TipoColaborador;
import br.com.cenajur.util.Constantes;

@ViewScoped
@ManagedBean(name = "colaboradorPesquisaFaces")
public class ColaboradorPesquisaFaces extends PesquisaFaces<Colaborador> {

	private List<SelectItem> tiposColaborador;
	
	private Boolean flagBuscaAdvogado;
	
	@PostConstruct
	protected void init() {
		this.initCombo();
		this.limpar();
	}
	
	@Override
	public String limpar() {
		setModel(new Colaborador());
		getModel().setCidade(new Cidade());
		getModel().setTipoColaborador(new TipoColaborador());
		return null;
	}
	
	@Override
	protected void preFind() {
		if(getFlagBuscaAdvogado()){
			this.getModel().setTipoColaborador(new TipoColaborador(Constantes.TIPO_COLABORADOR_ADVOGADO));
		}
	}
	
	private void initCombo(){
		this.tiposColaborador = super.initCombo(new TipoColaborador().findAll(), "id", "descricao");
	}
	
	public List<SelectItem> getTiposColaborador() {
		return tiposColaborador;
	}

	public void setTiposColaborador(List<SelectItem> tiposColaborador) {
		this.tiposColaborador = tiposColaborador;
	}

	public Boolean getFlagBuscaAdvogado() {
		return flagBuscaAdvogado;
	}

	public void setFlagBuscaAdvogado(Boolean flagBuscaAdvogado) {
		this.flagBuscaAdvogado = flagBuscaAdvogado;
	}

}
