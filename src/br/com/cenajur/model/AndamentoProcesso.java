package br.com.cenajur.model;

import java.util.Date;

import br.com.topsys.util.TSUtil;

public class AndamentoProcesso {

	private Long id;
	
	private Processo processo;
	
	private String descricao;
	
	private Date dataAndamento;

	private TipoAndamentoProcesso tipoAndamentoProcesso;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Date getDataAndamento() {
		return dataAndamento;
	}
	
	public void setDataAndamento(Date dataAndamento) {
		this.dataAndamento = dataAndamento;
	}
	
	public TipoAndamentoProcesso getTipoAndamentoProcesso() {
		return tipoAndamentoProcesso;
	}

	public void setTipoAndamentoProcesso(TipoAndamentoProcesso tipoAndamentoProcesso) {
		this.tipoAndamentoProcesso = tipoAndamentoProcesso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AndamentoProcesso other = (AndamentoProcesso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
