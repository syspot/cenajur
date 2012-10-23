
package br.com.cenajur.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "audiencias_advogados")
public class AudienciaAdvogado extends TSActiveRecordAb<AudienciaAdvogado>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1938633766717220310L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "audiencias_advogados_id")
	@SequenceGenerator(name="audiencias_advogados_id", sequenceName="audiencias_advogados_id_seq")
	private Long id;
	
	@ManyToOne
	private Audiencia audiencia;
	
	@ManyToOne
	private Colaborador advogado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Audiencia getAudiencia() {
		return audiencia;
	}

	public void setAudiencia(Audiencia audiencia) {
		this.audiencia = audiencia;
	}

	public Colaborador getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Colaborador advogado) {
		this.advogado = advogado;
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
		AudienciaAdvogado other = (AudienciaAdvogado) obj;
		if (advogado == null) {
			if (other.advogado != null)
				return false;
		} else if (!advogado.equals(other.advogado))
			return false;
		if (audiencia == null) {
			if (other.audiencia != null)
				return false;
		} else if (!audiencia.equals(other.audiencia))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.advogado.getApelido();
	}
	
}
