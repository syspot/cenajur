package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "partes_contrarias")
public class ParteContraria extends TSActiveRecordAb<ParteContraria>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4464316026730321664L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="partes_contrarias_id")
	@SequenceGenerator(name="partes_contrarias_id", sequenceName="partes_contrarias_id_seq")
	private Long id;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "tipo_documento_id")
	private TipoDocumento tipoDocumento;
	
	@Column(name = "numero_documento")
	private String numeroDocumento;
	
	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_atualizacao_id")
	private Colaborador colaboradorAtualizacao;
	
	@Column(name = "nome_advogado")
	private String nomeAdvogado;
	
	@Column(name = "oab_advogado")
	private String oabAdvogado;
	
	private String telefone;

	public Long getId() {
		return TSUtil.tratarLong(id);
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

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
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

	public String getNomeAdvogado() {
		return nomeAdvogado;
	}

	public void setNomeAdvogado(String nomeAdvogado) {
		this.nomeAdvogado = nomeAdvogado;
	}

	public String getOabAdvogado() {
		return oabAdvogado;
	}

	public void setOabAdvogado(String oabAdvogado) {
		this.oabAdvogado = oabAdvogado;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
		ParteContraria other = (ParteContraria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<ParteContraria> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from ParteContraria pc where 1 = 1 ");
		
		if(!TSUtil.isEmpty(descricao)){
			query.append("and ").append(CenajurUtil.semAcento("pc.descricao")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		if(!TSUtil.isEmpty(tipoDocumento) && !TSUtil.isEmpty(tipoDocumento.getId())){
			query.append("and pc.tipoDocumento.id = ? ");
		}
		
		if(!TSUtil.isEmpty(numeroDocumento)){
			query.append("and pc.numeroDocumento = ? ");
		}
		
		if(!TSUtil.isEmpty(nomeAdvogado)){
			query.append("and ").append(CenajurUtil.semAcento("pc.nomeAdvogado")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		if(!TSUtil.isEmpty(oabAdvogado)){
			query.append("and ").append(CenajurUtil.semAcento("pc.oabAdvogado")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		if(!TSUtil.isEmpty(tipoDocumento) && !TSUtil.isEmpty(tipoDocumento.getId())){
			params.add(tipoDocumento.getId());
		}
		
		if(!TSUtil.isEmpty(numeroDocumento)){
			params.add(numeroDocumento);
		}
		
		if(!TSUtil.isEmpty(nomeAdvogado)){
			params.add(nomeAdvogado);
		}
		
		if(!TSUtil.isEmpty(oabAdvogado)){
			params.add(oabAdvogado);
		}
		
		return super.find(query.toString(), "descricao", params.toArray());
	}	
	
}
