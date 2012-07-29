package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "processos")
public class Processo extends TSActiveRecordAb<Processo>{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "data_ajuizamento")
	private Date dataAjuizamento;
	
	@Column(name = "numero_processo")
	private String numeroProcesso;
	
	@ManyToMany
	@JoinTable(name = "processos_clientes", joinColumns = { 
	@JoinColumn(name = "processo_id") }, inverseJoinColumns = { @JoinColumn(name = "cliente_id") })
	private List<Cliente> clientes;
	
	@ManyToOne
	private Objeto objeto;
	
	private Integer lote;
	
	@ManyToMany
	@JoinTable(name = "processos_partes_contrarias", joinColumns = { 
	@JoinColumn(name = "processo_id") }, inverseJoinColumns = { @JoinColumn(name = "parte_contraria_id") })
	private List<ParteContraria> partesContrarias;
	
	@ManyToOne
	@JoinColumn(name = "tipo_processo_id")
	private TipoProcesso tipoProcesso;
	
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL)
	private List<AndamentoProcesso> andamentos;
	
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL)
	private List<Audiencia> audiencias;
	
	@ManyToOne
	private Vara vara;
	
	@ManyToOne
	private Comarca comarca;
	
	@ManyToOne
	private Colaborador advogado;
	
	@Column(name = "data_abertura")
	private Date dataAbertura;
	
	@ManyToOne
	@JoinColumn(name = "tipo_parte_id")
	private TipoParte tipoParte;
	
	@ManyToOne
	@JoinColumn(name = "situacao_processo_id")
	private SituacaoProcesso situacaoProcesso;
	
	@Column(name = "data_arquivamento")
	private Date dataArquivamento;
	
	@ManyToOne
	private Processo processo;
	
	private String observacao;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public Integer getLote() {
		return TSUtil.tratarInteger(lote);
	}

	public void setLote(Integer lote) {
		this.lote = lote;
	}

	public List<ParteContraria> getPartesContrarias() {
		return partesContrarias;
	}

	public void setPartesContrarias(List<ParteContraria> partesContrarias) {
		this.partesContrarias = partesContrarias;
	}

	public TipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(TipoProcesso tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public List<AndamentoProcesso> getAndamentos() {
		return andamentos;
	}

	public void setAndamentos(List<AndamentoProcesso> andamentos) {
		this.andamentos = andamentos;
	}

	public List<Audiencia> getAudiencias() {
		return audiencias;
	}

	public void setAudiencias(List<Audiencia> audiencias) {
		this.audiencias = audiencias;
	}

	public Vara getVara() {
		return vara;
	}

	public void setVara(Vara vara) {
		this.vara = vara;
	}

	public Comarca getComarca() {
		return comarca;
	}

	public void setComarca(Comarca comarca) {
		this.comarca = comarca;
	}

	public Colaborador getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Colaborador advogado) {
		this.advogado = advogado;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public TipoParte getTipoParte() {
		return tipoParte;
	}

	public void setTipoParte(TipoParte tipoParte) {
		this.tipoParte = tipoParte;
	}

	public SituacaoProcesso getSituacaoProcesso() {
		return situacaoProcesso;
	}

	public void setSituacaoProcesso(SituacaoProcesso situacaoProcesso) {
		this.situacaoProcesso = situacaoProcesso;
	}

	public Date getDataArquivamento() {
		return dataArquivamento;
	}

	public void setDataArquivamento(Date dataArquivamento) {
		this.dataArquivamento = dataArquivamento;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public Date getDataAjuizamento() {
		return dataAjuizamento;
	}

	public void setDataAjuizamento(Date dataAjuizamento) {
		this.dataAjuizamento = dataAjuizamento;
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
		Processo other = (Processo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<Processo> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Processo p where 1 = 1 ");
		
		if(!TSUtil.isEmpty(numeroProcesso)){
			query.append("and lower(p.numeroProcesso) like ? ");
		}
		
		if(!TSUtil.isEmpty(dataAbertura)){
			query.append("and p.dataAbertura = ? ");
		}
		
		if(!TSUtil.isEmpty(dataAjuizamento)){
			query.append("and p.dataAjuizamento = ? ");
		}
		
		if(!TSUtil.isEmpty(tipoProcesso) && !TSUtil.isEmpty(tipoProcesso.getId())){
			query.append("and p.tipoProcesso.id = ? ");
		}
		
		if(!TSUtil.isEmpty(tipoParte) && !TSUtil.isEmpty(tipoParte.getId())){
			query.append("and p.tipoParte.id = ? ");
		}
		
		if(!TSUtil.isEmpty(advogado) && !TSUtil.isEmpty(advogado.getId())){
			query.append("and p.advogado.id = ? ");
		}
		
		if(!TSUtil.isEmpty(objeto) && !TSUtil.isEmpty(objeto.getId())){
			query.append("and p.objeto.id = ? ");
		}
		
		if(!TSUtil.isEmpty(comarca) && !TSUtil.isEmpty(comarca.getId())){
			query.append("and p.comarca.id = ? ");
		}
		
		if(!TSUtil.isEmpty(vara) && !TSUtil.isEmpty(vara.getId())){
			query.append("and p.vara.id = ? ");
		}
		
		if(!TSUtil.isEmpty(situacaoProcesso) && !TSUtil.isEmpty(situacaoProcesso.getId())){
			query.append("and p.situacaoProcesso.id = ? ");
		}
		
		if(!TSUtil.isEmpty(dataArquivamento)){
			query.append("and p.dataArquivamento = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(numeroProcesso)){
			params.add(CenajurUtil.tratarString(numeroProcesso));
		}
		
		if(!TSUtil.isEmpty(dataAbertura)){
			params.add(dataAbertura);
		}
		
		if(!TSUtil.isEmpty(dataAjuizamento)){
			params.add(dataAjuizamento);
		}
		
		if(!TSUtil.isEmpty(tipoProcesso) && !TSUtil.isEmpty(tipoProcesso.getId())){
			params.add(tipoProcesso.getId());
		}
		
		if(!TSUtil.isEmpty(tipoParte) && !TSUtil.isEmpty(tipoParte.getId())){
			params.add(tipoParte.getId());
		}
		
		if(!TSUtil.isEmpty(advogado) && !TSUtil.isEmpty(advogado.getId())){
			params.add(advogado.getId());
		}
		
		if(!TSUtil.isEmpty(objeto) && !TSUtil.isEmpty(objeto.getId())){
			params.add(objeto.getId());
		}
		
		if(!TSUtil.isEmpty(comarca) && !TSUtil.isEmpty(comarca.getId())){
			params.add(comarca.getId());
		}
		
		if(!TSUtil.isEmpty(vara) && !TSUtil.isEmpty(vara.getId())){
			params.add(vara.getId());
		}
		
		if(!TSUtil.isEmpty(situacaoProcesso) && !TSUtil.isEmpty(situacaoProcesso.getId())){
			params.add(situacaoProcesso.getId());
		}
		
		if(!TSUtil.isEmpty(dataArquivamento)){
			params.add(dataArquivamento);
		}
		
		return super.find(query.toString(), params.toArray());
	}
	
	public boolean isProcessoArquivado(){
		return Constantes.SITUACAO_PROCESSO_ARQUIVADO.equals(situacaoProcesso.getId());
	}
}
