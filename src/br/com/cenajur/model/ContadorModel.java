package br.com.cenajur.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@SuppressWarnings("serial")
public class ContadorModel implements Serializable{

	private BigInteger qtd;
	
	private String descricao;
	
	private Date data;

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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
