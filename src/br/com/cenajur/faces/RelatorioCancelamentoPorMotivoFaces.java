package br.com.cenajur.faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Cliente;

@ViewScoped
@ManagedBean(name = "relatorioCancelamentoPorMotivoFaces")
public class RelatorioCancelamentoPorMotivoFaces extends RelatorioPorAnoFaces {

	@Override
	public String atualizar(){
		setGrid(new Cliente().pesquisarCanceladosPorAno(getAno()));
		setGridTotal(new Cliente().pesquisarCanceladosPorAnoTotal(getAno()));
		return null;
	}
	
}
