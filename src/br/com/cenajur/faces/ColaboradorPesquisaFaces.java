package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.TipoColaborador;

@ViewScoped
@ManagedBean(name = "colaboradorPesquisaFaces")
public class ColaboradorPesquisaFaces extends PesquisaFaces<Colaborador> {

	private List<SelectItem> tiposColaborador;
	
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
	
	private void initCombo(){
		this.tiposColaborador = super.initCombo(new TipoColaborador().findAll(), "id", "descricao");
	}
	
	public List<SelectItem> getTiposColaborador() {
		return tiposColaborador;
	}

	public void setTiposColaborador(List<SelectItem> tiposColaborador) {
		this.tiposColaborador = tiposColaborador;
	}

}
