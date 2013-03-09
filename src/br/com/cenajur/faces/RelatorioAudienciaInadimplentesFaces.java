package br.com.cenajur.faces;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Faturamento;
import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@ManagedBean(name = "relatorioAudienciaInadimplentesFaces")
public class RelatorioAudienciaInadimplentesFaces extends TSMainFaces {

	private Date dataInicial;
	private Date dataFinal;
	
	private Cliente cliente;
	private List<Cliente> clientes;
	
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.cliente = new Cliente();
		this.dataInicial = new Date();
		this.dataFinal = new Date();
	}
	
	public String find(){
		
		Faturamento faturamento = CenajurUtil.obterFaturamentoDevedor();
		
		this.clientes = this.cliente.pesquisarClientesInadimplentesComAudiencia(dataInicial, dataFinal, faturamento.getMes(), faturamento.getAno());
		
		List<Faturamento> faturasAbertas = null;
		
		for(Cliente cliente : this.clientes){
			
			faturamento.setCliente(cliente);
			
			faturasAbertas = faturamento.pesquisarFaturasAbertas();
			
			cliente.setFaturasAbertas("");
			
			for(Faturamento fatura : faturasAbertas){
				cliente.setFaturasAbertas(cliente.getFaturasAbertas() + " " + fatura.getMes() + "/" + fatura.getAno() + " ");
			}
			
		}
		
		TSFacesUtil.gerarResultadoLista(clientes);
		
		return null;
		
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
}
