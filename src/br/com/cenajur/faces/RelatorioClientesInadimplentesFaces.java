package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Faturamento;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;

@RequestScoped
@ManagedBean(name = "relatorioClientesInadimplentesFaces")
public class RelatorioClientesInadimplentesFaces{

	private List<Cliente> clientes;
	private List<Integer> anos;
	
	public RelatorioClientesInadimplentesFaces() {
		this.gerarRelatorio();
	}
	
	public String gerarRelatorio() {

       this.clientes = new Cliente().pesquisarInadimplentes();
       
       int menorAno = Integer.valueOf(new Faturamento().obterMenorData().getAno());
       int maiorAno = Integer.valueOf(new Faturamento().obterMaiorData().getAno());
       
       this.anos = new ArrayList<Integer>();
       
       for(int i = menorAno ; i <= maiorAno ; i++){
    	   this.anos.add(i);
       }
       
       return null;

    }
	
	public String getDataAtual(){
		return TSParseUtil.dateToString(new Date(), TSDateUtil.DD_MM_YYYY_HH_MM_SS);
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Integer> getAnos() {
		return anos;
	}

	public void setAnos(List<Integer> anos) {
		this.anos = anos;
	}

}
