package br.com.cenajur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "planos")
public class Plano extends TSActiveRecordAb<Plano>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5882727544297798904L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="planos_id")
	@SequenceGenerator(name="planos_id", sequenceName="planos_id_seq")
	private Long id;
	
	private String descricao;
	
	private Double valor;
	
	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	
	@Column(name = "meses_duracao")
	private Integer mesesDuracao;

	public Plano() {
	}
	
	public Plano(Long id) {
		super();
		this.id = id;
	}
	
	public Plano(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public Plano(boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return CenajurUtil.tratarDouble(valor);
	}

	public void setValor(Double valor) {
		this.valor = CenajurUtil.tratarDouble(valor);
	}

	public Boolean getFlagAtivo() {
		return TSUtil.isEmpty(flagAtivo) ? false : flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getMesesDuracao() {
		return mesesDuracao;
	}

	public void setMesesDuracao(Integer mesesDuracao) {
		this.mesesDuracao = mesesDuracao;
	}
	
}
