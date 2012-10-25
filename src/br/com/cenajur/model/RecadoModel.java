package br.com.cenajur.model;

import java.util.Date;

import br.com.topsys.util.TSUtil;

public class RecadoModel {

	private Long id;
	
	private Date dataRecado;
	
	private SituacaoRecadoModel situacaoRecadoModel;
	
	private String remetente;
	
	private Colaborador destinatario;
	
	private Colaborador operadorCadastro;
	
	private String descricao;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataRecado() {
		return dataRecado;
	}

	public void setDataRecado(Date dataRecado) {
		this.dataRecado = dataRecado;
	}

	public SituacaoRecadoModel getSituacaoRecadoModel() {
		return situacaoRecadoModel;
	}

	public void setSituacaoRecadoModel(SituacaoRecadoModel situacaoRecadoModel) {
		this.situacaoRecadoModel = situacaoRecadoModel;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public Colaborador getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Colaborador destinatario) {
		this.destinatario = destinatario;
	}

	public Colaborador getOperadorCadastro() {
		return operadorCadastro;
	}

	public void setOperadorCadastro(Colaborador operadorCadastro) {
		this.operadorCadastro = operadorCadastro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		RecadoModel other = (RecadoModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
