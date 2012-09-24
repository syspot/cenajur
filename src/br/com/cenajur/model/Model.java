package br.com.cenajur.model;

import java.math.BigInteger;

public class Model {

	private BigInteger qtd;
	
	private String ano;

	public Integer getQtd() {
		return qtd.intValue();
	}

	public void setQtd(BigInteger qtd) {
		this.qtd = qtd;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
	
	

}
