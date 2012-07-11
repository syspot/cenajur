package br.com.cenajur.model;

import java.util.Date;
import java.util.List;

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
	
	private Long numero;
	
	private List<ClienteModel> clientes;
	
	private ObjetoModel objetoModel;
	
	private Integer lote;
	
	private List<ParteContrariaModel> parteContrariaModel;
	
	@Column(name = "tipo_processo_id")
	private TipoProcessoModel tipoProcessoModel;
	
	private VaraModel varaModel;
	
	private ComarcaModel comarcaModel;
	
	private ColaboradorModel advogado;
	
	private Date dataAbertura;
	
	private ParteModel parteModel;
	
	private SituacaoProcessoModel situacaoProcessoModel;
	
	private Date dataArquivamento;
	
	private ProcessoModel processoPrincipal;
	
	private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public List<ClienteModel> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteModel> clientes) {
		this.clientes = clientes;
	}

	public ObjetoModel getObjetoModel() {
		return objetoModel;
	}

	public void setObjetoModel(ObjetoModel objetoModel) {
		this.objetoModel = objetoModel;
	}

	public Integer getLote() {
		return lote;
	}

	public void setLote(Integer lote) {
		this.lote = lote;
	}

	public List<ParteContrariaModel> getParteContrariaModel() {
		return parteContrariaModel;
	}

	public void setParteContrariaModel(List<ParteContrariaModel> parteContrariaModel) {
		this.parteContrariaModel = parteContrariaModel;
	}

	public TipoProcessoModel getTipoProcessoModel() {
		return tipoProcessoModel;
	}

	public void setTipoProcessoModel(TipoProcessoModel tipoProcessoModel) {
		this.tipoProcessoModel = tipoProcessoModel;
	}

	public VaraModel getVaraModel() {
		return varaModel;
	}

	public void setVaraModel(VaraModel varaModel) {
		this.varaModel = varaModel;
	}

	public ComarcaModel getComarcaModel() {
		return comarcaModel;
	}

	public void setComarcaModel(ComarcaModel comarcaModel) {
		this.comarcaModel = comarcaModel;
	}

	public ColaboradorModel getAdvogado() {
		return advogado;
	}

	public void setAdvogado(ColaboradorModel advogado) {
		this.advogado = advogado;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public ParteModel getParteModel() {
		return parteModel;
	}

	public void setParteModel(ParteModel parteModel) {
		this.parteModel = parteModel;
	}

	public SituacaoProcessoModel getSituacaoProcessoModel() {
		return situacaoProcessoModel;
	}

	public void setSituacaoProcessoModel(SituacaoProcessoModel situacaoProcessoModel) {
		this.situacaoProcessoModel = situacaoProcessoModel;
	}

	public Date getDataArquivamento() {
		return dataArquivamento;
	}

	public void setDataArquivamento(Date dataArquivamento) {
		this.dataArquivamento = dataArquivamento;
	}

	public ProcessoModel getProcessoPrincipal() {
		return processoPrincipal;
	}

	public void setProcessoPrincipal(ProcessoModel processoPrincipal) {
		this.processoPrincipal = processoPrincipal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
		ProcessoModel other = (ProcessoModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
