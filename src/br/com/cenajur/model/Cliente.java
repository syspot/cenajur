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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "clientes")
public class Cliente extends TSActiveRecordAb<Cliente>{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String matricula;
	
	private String nome;
	
	private String email;
	
	private String cpf;
	
	private String rg;
	
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	private String cep;
	
	private String logradouro;
	
	private String numero;
	
	private String complemento;
	
	private String bairro;
	
	@ManyToOne
	private Cidade cidade;
	
	private String telefone;
	
	private String celular;
	
	@Column(name = "nome_pai")
	private String nomePai;
	
	@Column(name = "nome_mae")
	private String nomeMae;
	
	@ManyToOne
	@JoinColumn(name = "estado_civil_id")
	private EstadoCivil estadoCivil;
	
	@Column(name = "data_adesao")
	private Date dataAdesao;
	
	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	
	@ManyToOne
	@JoinColumn(name = "tipo_pagamento_id")
	private TipoPagamento tipoPagamento;
	
	@Column(name = "data_cancelamento")
	private Date dataCancelamento;
	
	@ManyToOne
	@JoinColumn(name = "motivo_cancelamento_id")
	private MotivoCancelamento motivoCancelamento;
	
	@ManyToOne
	private Cliente titular;
	
	@ManyToOne
	private Banco banco;
	
	private String agencia;
	
	private String conta;
	
	private Integer lote;
	
	@ManyToOne
	private Lotacao lotacao;
	
	@ManyToOne
	private Graduacao graduacao;
	
	@Column(name = "cadastro_pm")
	private String cadastroPM;
	
	@Column(name = "flag_status_pm")
	private Boolean flagStatusPM;
	
	private String observacao;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	
	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_cadastro_id")
	private Colaborador colaboradorCadastro;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_atualizacao_id")
	private Colaborador colaboradorAtualizacao;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Lotacao getLotacao() {
		return lotacao;
	}

	public void setLotacao(Lotacao lotacao) {
		this.lotacao = lotacao;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Date getDataAdesao() {
		return dataAdesao;
	}

	public void setDataAdesao(Date dataAdesao) {
		this.dataAdesao = dataAdesao;
	}

	public Boolean getFlagStatusPM() {
		return flagStatusPM;
	}

	public void setFlagStatusPM(Boolean flagStatusPM) {
		this.flagStatusPM = flagStatusPM;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public MotivoCancelamento getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(MotivoCancelamento motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public Cliente getTitular() {
		return titular;
	}

	public void setTitular(Cliente titular) {
		this.titular = titular;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public Integer getLote() {
		return lote;
	}

	public void setLote(Integer lote) {
		this.lote = lote;
	}

	public Graduacao getGraduacao() {
		return graduacao;
	}

	public void setGraduacao(Graduacao graduacao) {
		this.graduacao = graduacao;
	}

	public String getCadastroPM() {
		return cadastroPM;
	}

	public void setCadastroPM(String cadastroPM) {
		this.cadastroPM = cadastroPM;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Colaborador getColaboradorCadastro() {
		return colaboradorCadastro;
	}

	public void setColaboradorCadastro(Colaborador colaboradorCadastro) {
		this.colaboradorCadastro = colaboradorCadastro;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public List<Cliente> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Cliente c where 1 = 1 ");
		
		if(!TSUtil.isEmpty(matricula)){
			query.append("and lower(c.matricula) like ? ");
		}
		
		if(!TSUtil.isEmpty(nome)){
			query.append("and lower(c.nome) like ? ");
		}
		
		if(!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getEstado()) && !TSUtil.isEmpty(cidade.getEstado().getId())){
			query.append("and c.cidade.estado.id = ? ");
		}
		
		if(!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getId())){
			query.append("and c.cidade.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(matricula)){
			params.add(CenajurUtil.tratarString(matricula));
		}
		
		if(!TSUtil.isEmpty(nome)){
			params.add(CenajurUtil.tratarString(nome));
		}
		
		if(!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getEstado()) && !TSUtil.isEmpty(cidade.getEstado().getId())){
			params.add(cidade.getEstado().getId());
		}
		
		if(!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getId())){
			params.add(cidade.getId());
		}
		
		return super.find(query.toString(), params.toArray());
	}
	
}
