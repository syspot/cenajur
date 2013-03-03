package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "contador_emails")
public class ContadorEmail extends TSActiveRecordAb<ContadorEmail>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1560879297650206658L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="contador_emails_id")
	@SequenceGenerator(name="contador_emails_id", sequenceName="contador_emails_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tipo_informacao_id")
	private TipoInformacao tipoInformacao;
	
	@Temporal(TemporalType.DATE)
	private Date data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoInformacao getTipoInformacao() {
		return tipoInformacao;
	}

	public void setTipoInformacao(TipoInformacao tipoInformacao) {
		this.tipoInformacao = tipoInformacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
		ContadorEmail other = (ContadorEmail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public void gravarPorTipo(TipoInformacao tipoInformacao){
		
		this.setData(new Date());
		this.setTipoInformacao(tipoInformacao);
		
		try {
			super.save();
		} catch (TSApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ContadorModel> pesquisarPorPeriodo(Date dataInicio, Date dataFim, TipoInformacao tipoInformacao){
		
		StringBuilder query = new StringBuilder("select ti.descricao, count(ce.*) as qtd, ce.data from contador_emails ce inner join tipos_informacoes ti on ce.tipo_informacao_id = ti.id where 1 = 1 ");
		
		if(!TSUtil.isEmpty(dataInicio) && !TSUtil.isEmpty(dataFim)){
			query.append(" and data between ? and ? ");
		}
		
		if(!TSUtil.isEmpty(tipoInformacao) && !TSUtil.isEmpty(tipoInformacao.getId())){
			query.append(" and tipo_informacao_id = ? ");
		}
		
		query.append(" group by ti.descricao, ce.data");
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(dataInicio) && !TSUtil.isEmpty(dataFim)){
			params.add(dataInicio);
			params.add(dataFim);
		}
		
		if(!TSUtil.isEmpty(tipoInformacao) && !TSUtil.isEmpty(tipoInformacao.getId())){
			params.add(tipoInformacao.getId());
		}
		
		return super.findBySQL(ContadorModel.class, new String[]{"descricao", "qtd", "data"}, query.toString() , params.toArray());
	}
	
}
