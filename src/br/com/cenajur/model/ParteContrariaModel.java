package br.com.cenajur.model;

public class ParteContrariaModel {

	private Long id;
	
	private String descricao;
	
	private TipoDocumentoModel tipoDocumentoModel;
	
	private String numeroDocumento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoDocumentoModel getTipoDocumentoModel() {
		return tipoDocumentoModel;
	}

	public void setTipoDocumentoModel(TipoDocumentoModel tipoDocumentoModel) {
		this.tipoDocumentoModel = tipoDocumentoModel;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
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
		ParteContrariaModel other = (ParteContrariaModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
