package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Faturamento;
import br.com.cenajur.model.Plano;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "faturamentoFaces")
public class FaturamentoFaces extends CrudFaces<Faturamento> {

	private List<SelectItem> planos;
	
	private Cliente clienteSelecionado;
	
	private Colaborador colaboradorConectado;
	
	private boolean inserindo;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
		this.colaboradorConectado = ColaboradorUtil.obterColaboradorConectado();
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Faturamento());
		getCrudModel().setPlano(new Plano());
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new Faturamento());
		getCrudPesquisaModel().setPlano(new Plano());
		setGrid(new ArrayList<Faturamento>());
		return null;
	}
	
	public void initCombo(){
		this.planos = super.initCombo(new Plano(Boolean.TRUE).findByModel("descricao"), "id", "descricao");
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getCrudModel().getCliente()) || TSUtil.isEmpty(getCrudModel().getCliente().getId())){
			
			erro = true;
			CenajurUtil.addErrorMessage("Cliente: Campo obrigat�rio");
			context.addCallbackParam("sucesso", false);
			
		} else
		
		if(inserindo){
			
			if(getCrudModel().getObservacao().length() > 200){
				erro = true;
				CenajurUtil.addErrorMessage("O campo Descri��o excedeu o limite de 200 caracteres");
				context.addCallbackParam("sucesso", false);
			}
			
			Faturamento faturamento = getCrudModel().obterFaturamentoAtivo();
			
			if(!TSUtil.isEmpty(faturamento)){
				erro = true;
				CenajurUtil.addErrorMessage("Existe um faturamento ativo para o cliente no m�s selecionado");
				context.addCallbackParam("sucesso", false);
			}
			
		}
		
		if(!erro){
			context.addCallbackParam("sucesso", true);
		}
		
		return erro;
	}
	
	@Override
	protected void preInsert() {
		
		getCrudModel().setDataProcessamento(getCrudModel().getCliente().getDataProcessamento());
		getCrudModel().setPlano(getCrudModel().getCliente().getPlano());
		getCrudModel().setFlagCancelado(Boolean.FALSE);
		
		if(getCrudModel().getFlagPago()){
			getCrudModel().setDataBaixa(new Date());
			getCrudModel().setColaboradorBaixa(this.colaboradorConectado);
		}
		
	}
	
	@Override
	protected void preUpdate() {
		getCrudModel().setDataBaixa(new Date());
		getCrudModel().setColaboradorBaixa(this.colaboradorConectado);
	}
	
	@Override
	protected void posPersist() throws TSApplicationException {
		this.limpar();
	}
	
	public String addCliente(){
		
		if(inserindo){
			getCrudModel().setCliente(this.clienteSelecionado);
		} else{
			getCrudPesquisaModel().setCliente(this.clienteSelecionado);
		}
		
		return null;
	}
	
	public String removerCliente(){
		getCrudPesquisaModel().setCliente(null);
		return null;
	}

	public List<SelectItem> getPlanos() {
		return planos;
	}

	public void setPlanos(List<SelectItem> planos) {
		this.planos = planos;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public boolean isInserindo() {
		return inserindo;
	}

	public void setInserindo(boolean inserindo) {
		this.inserindo = inserindo;
	}
	
}
