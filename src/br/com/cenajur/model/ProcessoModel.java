package br.com.cenajur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "processo")
public class ProcessoModel extends TSActiveRecordAb<ProcessoModel>{

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "tipo_processo_id")
	private TipoProcessoModel tipoProcessoModel;
}
