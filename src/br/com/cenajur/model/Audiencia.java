package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "audiencias")
public class Audiencia extends TSActiveRecordAb<Audiencia>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	private Processo processo;
	
	@Column(name = "data_audiencia")
	private Date dataAudiencia;
	
	@ManyToOne
	@JoinColumn(name = "situacao_audiencia_id")
	private SituacaoAudiencia situacaoAudiencia;
	
	@ManyToOne
	private Vara vara;
	
	@ManyToOne
	private Colaborador advogado;
	
	private String descricao;
	
	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_atualizacao_id")
	private Colaborador colaboradorAtualizacao;

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

	public Date getDataAudiencia() {
		return dataAudiencia;
	}

	public void setDataAudiencia(Date dataAudiencia) {
		this.dataAudiencia = dataAudiencia;
	}

	public SituacaoAudiencia getSituacaoAudiencia() {
		return situacaoAudiencia;
	}

	public void setSituacaoAudiencia(SituacaoAudiencia situacaoAudiencia) {
		this.situacaoAudiencia = situacaoAudiencia;
	}

	public Vara getVara() {
		return vara;
	}

	public void setVara(Vara vara) {
		this.vara = vara;
	}

	public Colaborador getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Colaborador advogado) {
		this.advogado = advogado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Colaborador getColaboradorAtualizacao() {
		return colaboradorAtualizacao;
	}

	public void setColaboradorAtualizacao(Colaborador colaboradorAtualizacao) {
		this.colaboradorAtualizacao = colaboradorAtualizacao;
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
		Audiencia other = (Audiencia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<Audiencia> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Audiencia a where 1 = 1 ");
		
		if(!TSUtil.isEmpty(processo) && !TSUtil.isEmpty(processo.getNumeroProcesso())){
			query.append("and ").append(CenajurUtil.semAcento("a.processo.numeroProcesso")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		if(!TSUtil.isEmpty(dataAudiencia)){
			query.append("and day(a.dataAudiencia) = ? and month(a.dataAudiencia) = ? and year(a.dataAudiencia) = ? ");
		}
		
		if(!TSUtil.isEmpty(situacaoAudiencia) && !TSUtil.isEmpty(situacaoAudiencia.getId())){
			query.append("and a.situacaoAudiencia.id = ? ");
		}
		
		if(!TSUtil.isEmpty(advogado) && !TSUtil.isEmpty(advogado.getId())){
			query.append("and a.advogado.id = ? ");
		}
		
		if(!TSUtil.isEmpty(vara) && !TSUtil.isEmpty(vara.getId())){
			query.append("and a.vara.id = ? ");
		}
		
		if(!TSUtil.isEmpty(descricao)){
			query.append("and ").append(CenajurUtil.semAcento("a.descricao")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(processo) && !TSUtil.isEmpty(processo.getNumeroProcesso())){
			params.add(CenajurUtil.tratarString(processo.getNumeroProcesso()));
		}
		
		if(!TSUtil.isEmpty(dataAudiencia)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataAudiencia);
			params.add(calendar.get(Calendar.DAY_OF_MONTH));
			params.add(calendar.get(Calendar.MONTH) + 1);
			params.add(calendar.get(Calendar.YEAR));
		}
		
		if(!TSUtil.isEmpty(situacaoAudiencia) && !TSUtil.isEmpty(situacaoAudiencia.getId())){
			params.add(situacaoAudiencia.getId());
		}
		
		if(!TSUtil.isEmpty(advogado) && !TSUtil.isEmpty(advogado.getId())){
			params.add(advogado.getId());
		}
		
		if(!TSUtil.isEmpty(vara) && !TSUtil.isEmpty(vara.getId())){
			params.add(vara.getId());
		}
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		return super.find(query.toString(), params.toArray());
	}
}
