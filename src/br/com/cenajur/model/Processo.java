package br.com.cenajur.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	private Long numeroProcesso;
	
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

	public Long getNumeroProcesso() {
		return TSUtil.tratarLong(numeroProcesso);
	}

	public void setNumeroProcesso(Long numeroProcesso) {
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
	
	public boolean isProcessoArquivado(){
		return Constantes.SITUACAO_PROCESSO_ARQUIVADO.equals(situacaoProcesso.getId());
	}
}
