package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.SituacaoAudiencia;
import br.com.cenajur.model.SituacaoProcesso;
import br.com.cenajur.model.Vara;
import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.util.TSUtil;

@SessionScoped
@ManagedBean(name = "audienciaFaces")
public class AudienciaFaces extends CrudFaces<Audiencia> {

	private List<SelectItem> varas;
	private List<SelectItem> advogados;
	private List<SelectItem> situacoesAudiencias;
	
	private Processo processoSelecionado;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}
	
	private void initCombo(){
		this.varas = this.initCombo(new Vara().findAll("descricao"), "id", "descricao");
		this.advogados = this.initCombo(new Colaborador().findAll("nome"), "id", "nome");
		this.situacoesAudiencias = this.initCombo(new SituacaoProcesso().findAll("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Audiencia());
		getCrudModel().setAdvogado(new Colaborador());
		getCrudModel().setProcesso(new Processo());
		getCrudModel().setSituacaoAudiencia(new SituacaoAudiencia());
		getCrudModel().setVara(new Vara());
		setFlagAlterar(Boolean.FALSE);
		return "sucesso";
	}
	
	@Override
	public String limparPesquisa(){
		this.setFieldOrdem("descricao");
		setCrudPesquisaModel(new Audiencia());
		getCrudPesquisaModel().setAdvogado(new Colaborador());
		getCrudPesquisaModel().setProcesso(new Processo());
		getCrudPesquisaModel().setSituacaoAudiencia(new SituacaoAudiencia());
		getCrudPesquisaModel().setVara(new Vara());
		return "sucesso";
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(TSUtil.isEmpty(getCrudModel().getProcesso().getId())){
			erro = true;
			CenajurUtil.addErrorMessage("Processo: Campo obrigatório");
		}
		
		return erro;
	}
	
	public String addProcesso(){
		getCrudModel().setProcesso(this.processoSelecionado);
		CenajurUtil.addInfoMessage("Processo adicionado com sucesso");
		return "sucesso";
	}

	public List<SelectItem> getVaras() {
		return varas;
	}

	public void setVaras(List<SelectItem> varas) {
		this.varas = varas;
	}

	public List<SelectItem> getAdvogados() {
		return advogados;
	}

	public void setAdvogados(List<SelectItem> advogados) {
		this.advogados = advogados;
	}

	public List<SelectItem> getSituacoesAudiencias() {
		return situacoesAudiencias;
	}

	public void setSituacoesAudiencias(List<SelectItem> situacoesAudiencias) {
		this.situacoesAudiencias = situacoesAudiencias;
	}

	public Processo getProcessoSelecionado() {
		return processoSelecionado;
	}

	public void setProcessoSelecionado(Processo processoSelecionado) {
		this.processoSelecionado = processoSelecionado;
	}
	
}
