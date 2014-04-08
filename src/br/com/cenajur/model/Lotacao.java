package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "lotacoes")
public class Lotacao extends TSActiveRecordAb<Lotacao>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2371011903332641559L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lotacoes_id")
	@SequenceGenerator(name="lotacoes_id", sequenceName="lotacoes_id_seq")
	private Long id;
	
	private String descricao;
	
	private String bairro;
	
	private String telefone;
	
	@ManyToOne
	private Cidade cidade;

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

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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
		Lotacao other = (Lotacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<Lotacao> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Lotacao l where 1 = 1 ");
		
		if(!TSUtil.isEmpty(descricao)){
			query.append("and ").append(CenajurUtil.semAcento("l.descricao")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		if(!TSUtil.isEmpty(bairro)){
			query.append("and ").append(CenajurUtil.semAcento("l.bairro")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		if(!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getEstado()) && !TSUtil.isEmpty(cidade.getEstado().getId())){
			query.append("and l.cidade.estado.id = ? ");
		}
		
		if(!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getId())){
			query.append("and l.cidade.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(descricao)){
			params.add(CenajurUtil.tratarString(descricao));
		}
		
		if(!TSUtil.isEmpty(bairro)){
			params.add(CenajurUtil.tratarString(bairro));
		}
		
		if(!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getEstado()) && !TSUtil.isEmpty(cidade.getEstado().getId())){
			params.add(cidade.getEstado().getId());
		}
		
		if(!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getId())){
			params.add(cidade.getId());
		}
		
		return super.find(query.toString(), "descricao", params.toArray());
	}
	
}
