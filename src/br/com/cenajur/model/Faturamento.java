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
import javax.persistence.Transient;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "faturamento")
public class Faturamento extends TSActiveRecordAb<Faturamento>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2314602226026074016L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="faturamento_id")
	@SequenceGenerator(name="faturamento_id", sequenceName="faturamento_id_seq")
	private Long id;
	
	@ManyToOne
	private Cliente cliente;
	
	@Column(name = "data_baixa")
	private Date dataBaixa;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_baixa_id")
	private Colaborador colaboradorBaixa;
	
	@ManyToOne
	private Plano plano;
	
	@Column(name = "data_processamento")
	private Date dataProcessamento;
	
	private Double valor;
	
	@Column(name = "flag_pago")
	private Boolean flagPago;
	
	@Column(name = "flag_cancelado")
	private Boolean flagCancelado;
	
	private String observacao;
	
	private String identificacao;

	private Integer mes;
	
	private Integer ano;
	
	@Transient
	private Boolean flagSelecionado;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_geracao_id")
	private Colaborador colaboradorGeracao;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDataBaixa() {
		return dataBaixa;
	}

	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}

	public Colaborador getColaboradorBaixa() {
		return colaboradorBaixa;
	}

	public void setColaboradorBaixa(Colaborador colaboradorBaixa) {
		this.colaboradorBaixa = colaboradorBaixa;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public Date getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public Boolean getFlagPago() {
		return flagPago;
	}

	public void setFlagPago(Boolean flagPago) {
		this.flagPago = flagPago;
	}

	public Boolean getFlagCancelado() {
		return flagCancelado;
	}

	public void setFlagCancelado(Boolean flagCancelado) {
		this.flagCancelado = flagCancelado;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getMes() {
		return TSUtil.tratarInteger(mes);
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return TSUtil.tratarInteger(ano);
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Colaborador getColaboradorGeracao() {
		return colaboradorGeracao;
	}

	public void setColaboradorGeracao(Colaborador colaboradorGeracao) {
		this.colaboradorGeracao = colaboradorGeracao;
	}

	public Boolean getFlagSelecionado() {
		return TSUtil.isEmpty(flagSelecionado) ? false : flagSelecionado;
	}

	public void setFlagSelecionado(Boolean flagSelecionado) {
		this.flagSelecionado = flagSelecionado;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
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
		Faturamento other = (Faturamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<Faturamento> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder(" from Faturamento f where 1 = 1 ");
		
		if(!TSUtil.isEmpty(getCliente()) && !TSUtil.isEmpty(getCliente().getId())){
			query.append(" and f.cliente.id = ?");
		}
		
		if(!TSUtil.isEmpty(getPlano()) && !TSUtil.isEmpty(getPlano().getId())){
			query.append(" and f.plano.id = ?");
		}
		
		if(!TSUtil.isEmpty(getMes())){
			query.append(" and f.mes = ?");
		}
		
		if(!TSUtil.isEmpty(getAno())){
			query.append(" and f.ano = ?");
		}
		
		if(!TSUtil.isEmpty(getFlagPago())){
			query.append(" and f.flagPago = ?");
		}
		
		if(!TSUtil.isEmpty(getFlagCancelado())){
			query.append(" and f.flagCancelado = ?");
		}
		
		
		List<Object> params = new ArrayList<Object>();

		
		if(!TSUtil.isEmpty(getCliente()) && !TSUtil.isEmpty(getCliente().getId())){
			params.add(getCliente().getId());
		}
		
		if(!TSUtil.isEmpty(getPlano()) && !TSUtil.isEmpty(getPlano().getId())){
			params.add(getPlano().getId());
		}
		
		if(!TSUtil.isEmpty(getMes())){
			params.add(getMes());
		}
		
		if(!TSUtil.isEmpty(getAno())){
			params.add(getAno());
		}
		
		if(!TSUtil.isEmpty(getFlagPago())){
			params.add(getFlagPago());
		}
		
		if(!TSUtil.isEmpty(getFlagCancelado())){
			params.add(getFlagCancelado());
		}
		
		return super.find(query.toString(), null, params.toArray());
		
	}
	
	public Faturamento obterFaturamentoAtivo(){
		return super.get(" from Faturamento f where f.flagCancelado = false and f.cliente.id = ? and f.mes = ? and f.ano = ?", getCliente().getId(), getMes(), getAno());
	}
	
	public List<Faturamento> pesquisarFaturasAbertas(){
		return super.find(" from Faturamento f where f.flagPago = false and f.flagCancelado = false and f.cliente.id = ? and f.mes < ? and f.ano <= ?", null, getCliente().getId(), getMes(), getAno());
	}
	
	public void gerarFaturamento(){
		try{
			getSession().createSQLQuery("select fc_gerar_faturamento(" + colaboradorGeracao.getId() + ")").executeUpdate();
		}catch(Exception e){
			// a function é executada, mas sobe exceção no retorno que deve ser abafada.
		}
	}
	
}
