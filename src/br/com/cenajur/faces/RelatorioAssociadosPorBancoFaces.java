package br.com.cenajur.faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Cliente;

@ViewScoped
@ManagedBean(name = "relatorioAssociadosPorBancoFaces")
public class RelatorioAssociadosPorBancoFaces extends RelatorioPorAnoFaces{

	@Override
	public String atualizar(){
		setGrid(new Cliente().pesquisarPorBancoPorAno(getAno()));
		setGridTotal(new Cliente().pesquisarBancoPorAno(getAno()));
		return null;
	}
	
}
