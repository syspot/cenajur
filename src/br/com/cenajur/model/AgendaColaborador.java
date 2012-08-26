package br.com.cenajur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "agendas_colaboradores")
public class AgendaColaborador extends TSActiveRecordAb<AgendaColaborador>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4541430641342646947L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="agendas_colaboradores_id")
	@SequenceGenerator(name="agendas_colaboradores_id", sequenceName="agendas_colaboradores_id_seq")
	private Long id;
	
	@ManyToOne
	private Agenda agenda;
	
	@ManyToOne
	private Colaborador colaborador;
	
	private String descricao;
	
	@Column(name = "flag_concluido")
	private Boolean flagConcluido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getFlagConcluido() {
		return flagConcluido;
	}

	public void setFlagConcluido(Boolean flagConcluido) {
		this.flagConcluido = flagConcluido;
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
		AgendaColaborador other = (AgendaColaborador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
