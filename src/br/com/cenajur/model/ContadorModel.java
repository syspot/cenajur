package br.com.cenajur.model;

import java.math.BigInteger;

public class ContadorModel {

	private BigInteger qtd;
	
	private String descricao;

	public Integer getQtd() {
		return qtd.intValue();
	}

	public void setQtd(BigInteger qtd) {
		this.qtd = qtd;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
