package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Comarca;
import br.com.cenajur.model.Objeto;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.SituacaoProcesso;
import br.com.cenajur.model.TipoParte;
import br.com.cenajur.model.TipoProcesso;
import br.com.cenajur.model.Vara;

@ViewScoped
@ManagedBean(name = "processoPesquisaFaces")
public class ProcessoPesquisaFaces extends PesquisaFaces<Processo> {

	private List<SelectItem> situacoesProcessos;
	private List<SelectItem> advogados;
	
	private Processo processoSelecionado;
	
	@PostConstruct
	protected void init() {
		this.initCombo();
		this.limpar();
	}
	
	private void initCombo(){
		this.situacoesProcessos = this.initCombo(new SituacaoProcesso().findAll("descricao"), "id", "descricao");
		this.advogados = this.initCombo(new Colaborador().findAllAdvogados(), "id", "apelido");
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
		getModel().setProcessoNumeroPrincipal(new ProcessoNumero());
		this.processoSelecionado = new Processo();
		return null;
	}
	
	@Override
	protected void posFind() {
		
		for(Processo processo: getGrid()){
			processo.setProcessoNumeroPrincipal(new ProcessoNumero().obterNumeroProcessoPrincipal(processo));
		}
		
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

	public Processo getProcessoSelecionado() {
		return processoSelecionado;
	}

	public void setProcessoSelecionado(Processo processoSelecionado) {
		this.processoSelecionado = processoSelecionado;
	}
	
}
