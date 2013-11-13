package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Faturamento;
import br.com.cenajur.model.Plano;
import br.com.cenajur.relat.JasperUtil;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

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
		setTabIndex(1);
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
			CenajurUtil.addErrorMessage("Cliente: Campo obrigatório");
			context.addCallbackParam("sucesso", false);
			
		} else
		
		if(inserindo){
			
			if(getCrudModel().getObservacao().length() > 200){
				erro = true;
				CenajurUtil.addErrorMessage("O campo Descrição excedeu o limite de 200 caracteres");
				context.addCallbackParam("sucesso", false);
			}
			
			Faturamento faturamento = getCrudModel().obterFaturamentoAtivo();
			
			if(!TSUtil.isEmpty(faturamento)){
				erro = true;
				CenajurUtil.addErrorMessage("Existe um faturamento ativo para o cliente no mês selecionado");
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
		
		getCrudModel().setDataProcessamento(new Date());
		getCrudModel().setPlano(getCrudModel().getCliente().getPlano());
		getCrudModel().setFlagCancelado(Boolean.FALSE);
		getCrudModel().setColaboradorGeracao(this.colaboradorConectado);
		
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
	
	public String gerarFaturamento() throws TSApplicationException{
		
		Faturamento faturamento = new Faturamento();
		
		faturamento.setColaboradorGeracao(this.colaboradorConectado);
		
		faturamento.gerarFaturamento();
		
		super.addInfoMessage("Faturamento gerado com sucesso");
		
		return null;
	}
	
	public String pagarSelecionados() throws TSApplicationException{
		
		for(Faturamento faturamento : getGrid()){
			
			if(faturamento.getFlagSelecionado()){
				
				faturamento.setIdentificacao(TSUtil.gerarId());
				faturamento.setFlagPago(Boolean.TRUE);
				faturamento.setDataBaixa(new Date());
				faturamento.setColaboradorBaixa(this.colaboradorConectado);
				faturamento.pagar();
				
			}
			
		}
		
		super.addInfoMessage("Operação realizada com sucesso");
		
		return null;
	}
	
	public String cancelarSelecionados() throws TSApplicationException{
		
		for(Faturamento faturamento : getGrid()){
			
			if(faturamento.getFlagSelecionado()){
				
				faturamento.setFlagCancelado(Boolean.TRUE);
				faturamento.update();
				
			}
			
		}
		
		super.addInfoMessage("Operação realizada com sucesso");

		return null;
	}
	
	public String gerarComprovante(){
		
		try {

            Map<String, Object> parametros = CenajurUtil.getHashMapReport();

            parametros.put("P_FATURAMENTO_ID", getCrudModel().getId());
            parametros.put("DIR_IMAGE", TSFacesUtil.getServletContext().getRealPath("resources/images/"));

            new JasperUtil().gerarRelatorio("comprovantePagamento.jasper", "comprovante", parametros);

        } catch (Exception ex) {

            CenajurUtil.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

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
