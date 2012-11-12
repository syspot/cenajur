package br.com.cenajur.faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Cliente;

@ViewScoped
@ManagedBean(name = "relatorioCancelamentoPorMotivoFaces")
public class RelatorioCancelamentoPorMotivoFaces extends RelatorioPorAnoFaces {

	private boolean flagAssociado;
	
	@Override
	public String atualizar(){
		setGrid(new Cliente().pesquisarCanceladosPorAno(getAno(), flagAssociado));
		setGridTotal(new Cliente().pesquisarCanceladosPorAnoTotal(getAno(), flagAssociado));
		return null;
	}

	public boolean isFlagAssociado() {
		return flagAssociado;
	}

	public void setFlagAssociado(boolean flagAssociado) {
		this.flagAssociado = flagAssociado;
	}

}
