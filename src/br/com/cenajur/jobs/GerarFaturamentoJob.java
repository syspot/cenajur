package br.com.cenajur.jobs;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Faturamento;
import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@RequestScoped
@ManagedBean(name = "gerarFaturamentoJob")
public class GerarFaturamentoJob {

	@PostConstruct
	protected void init() throws TSApplicationException {
		
		List<Cliente> clientes = new Cliente().pesquisarClientesGeracaoFatura();
		
		if(!TSUtil.isEmpty(clientes)){
			
			Faturamento faturamento;
			
			for(Cliente cliente : clientes){
				
				if(cliente.getFlagAtivo()){
					
					faturamento = new Faturamento();
					
					faturamento.setCliente(cliente);
					faturamento.setAno(CenajurUtil.obterAnoAtual());
					faturamento.setMes(CenajurUtil.obterMesAtual());
					faturamento.setDataProcessamento(cliente.getDataProcessamento());
					faturamento.setFlagCancelado(Boolean.FALSE);
					faturamento.setFlagPago(Boolean.FALSE);
					faturamento.setPlano(cliente.getPlano());
					faturamento.setValor(cliente.getPlano().getValor());
					
					faturamento.save();
					
				}
				
				cliente.setDataProcessamento(CenajurUtil.alterarDataProcessamento(cliente.getPlano(), cliente.getDataProcessamento()));
				cliente.update();
				
			}
			
		}
		
	}
	
}
