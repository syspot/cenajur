package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Comarca;
import br.com.cenajur.model.Objeto;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.SituacaoProcesso;
import br.com.cenajur.model.TipoParte;
import br.com.cenajur.model.TipoProcesso;
import br.com.cenajur.model.Vara;

@SessionScoped
@ManagedBean(name = "processoPesquisaFaces")
public class ProcessoPesquisaFaces extends PesquisaFaces<Processo> {

	private List<SelectItem> situacoesProcessos;
	private List<SelectItem> advogados;
	
	@PostConstruct
	protected void init() {
		setFieldOrdem("numeroProcesso");
		this.initCombo();
		this.limpar();
	}
	
	private void initCombo(){
		this.situacoesProcessos = this.initCombo(new SituacaoProcesso().findAll("descricao"), "id", "descricao");
		this.advogados = this.initCombo(new Colaborador().findAll("nome"), "id", "nome");
	}
	
	@Override
	public String limpar() {
		setModel(new Processo());
		getModel().setObjeto(new Objeto());
		getModel().setTipoProcesso(new TipoProcesso());
		getModel().setVara(new Vara());
		getModel().setComarca(new Comarca());
		getModel().setTipoParte(new TipoParte());
		getModel().setSituacaoProcesso(new SituacaoProcesso());
		getModel().setAdvogado(new Colaborador());
		return "sucesso";
	}

	public List<SelectItem> getSituacoesProcessos() {
		return situacoesProcessos;
	}

	public void setSituacoesProcessos(List<SelectItem> situacoesProcessos) {
		this.situacoesProcessos = situacoesProcessos;
	}

	public List<SelectItem> getAdvogados() {
		return advogados;
	}

	public void setAdvogados(List<SelectItem> advogados) {
		this.advogados = advogados;
	}
	
}
