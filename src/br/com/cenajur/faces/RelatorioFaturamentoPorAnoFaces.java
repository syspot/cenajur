package br.com.cenajur.faces;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.AnoModel;
import br.com.cenajur.model.Banco;
import br.com.cenajur.model.Cliente;

import com.ibm.icu.util.Calendar;

@ViewScoped
@ManagedBean(name = "relatorioFaturamentoPorAnoFaces")
public class RelatorioFaturamentoPorAnoFaces {

	private List<Banco> bancos;
	private Long ano;
	
	public RelatorioFaturamentoPorAnoFaces() {
		this.ano = new Long(Calendar.getInstance().get(Calendar.YEAR));
		this.atualizar();
	}
	
	public String atualizar(){
		this.bancos = new Banco().findAll("descricao");
		return null;
	}
	
	public List<AnoModel> pesquisarAdimplentes(Banco banco){
		return new Cliente().pesquisarAdimplenciaPorBanco(ano, banco);
	}
	
	public List<AnoModel> pesquisarInadimplentes(Banco banco){
		return new Cliente().pesquisarInadimplenciaPorBanco(ano, banco);
	}
	
	public List<AnoModel> pesquisarTotal(Banco banco){
		return new Cliente().pesquisarFaturamentoPorBanco(ano, banco);
	}
	
	public List<AnoModel> pesquisarAdimplentesSemLote(){
		return new Cliente().pesquisarAdimplenciaSemLote(ano);
	}
	
	public List<AnoModel> pesquisarInadimplentesSemLote(){
		return new Cliente().pesquisarInadimplenciaSemLote(ano);
	}
	
	public List<AnoModel> pesquisarTotalSemLote(){
		return new Cliente().pesquisarFaturamentoTotalSemLote(ano);
	}

	public List<Banco> getBancos() {
		return bancos;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}

	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}


}
