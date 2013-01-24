package br.com.cenajur.model;

import java.io.Serializable;

public class FaturamentoAux implements Serializable {

	private Cliente cliente;

	private Plano plano;

	private Faturamento faturamento;
	
	private static final long serialVersionUID = 2314602226026074016L;

	public FaturamentoAux(Faturamento faturamento, Cliente cliente, Plano plano) {
		this.faturamento = faturamento;
		this.cliente = cliente;
		this.plano = plano;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public Faturamento getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(Faturamento faturamento) {
		this.faturamento = faturamento;
	}

}
