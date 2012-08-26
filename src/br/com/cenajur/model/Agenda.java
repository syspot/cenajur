package br.com.cenajur.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "agendas")
public class Agenda extends TSActiveRecordAb<Agenda>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4541430641342646947L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="agendas_id")
	@SequenceGenerator(name="agendas_id", sequenceName="agendas_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tipo_agenda_id")
	private TipoAgenda tipoAgenda;
	
	private Date data;
	
	private String descricao;
	
	@ManyToOne
	private Processo processo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoAgenda getTipoAgenda() {
		return tipoAgenda;
	}

	public void setTipoAgenda(TipoAgenda tipoAgenda) {
		this.tipoAgenda = tipoAgenda;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
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
		Agenda other = (Agenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
