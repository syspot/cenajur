package br.com.cenajur.model;

import java.util.Date;

import br.com.topsys.util.TSUtil;

public class Cliente {

	private Long id;
	
	private String matricula;
	
	private String nome;
	
	private String email;
	
	private String cpf;
	
	private String rg;
	
	private Date dataNascimento;
	
	private String endereco;
	
	private String bairro;
	
	private Cidade cidade;
	
	private Estado estado;
	
	private String cep;
	
	private String telefone1;
	
	private String telefone2;
	
	private String celular;
	
	private String nomePai;
	
	private String nomeMae;
	
	private EstadoCivil estadoCivil;
	
	private Date dataAdesao;
	
	private Boolean flagRematricula;
	
	private Boolean flagAgenda;
	
	private TipoCliente tipoCliente;
	
	private Situacao situacao;
	
	private TipoPagamento tipoPagamento;
	
	private Date dataDesligamento;
	
	private String motivoCancelamento;
	
	private Cliente titular;
	
	private Banco banco;
	
	private String agencia;
	
	private String conta;
	
	private Integer lote;
	
	private String enderecoFuncional;
	
	private String bairroFuncional;
	
	private String telefoneFuncional;
	
	private Cidade cidadeFuncional;
	
	private Estado estadoFuncional;
	
	private Graduacao graduacao;
	
	private String cadastroPM;
	
	private Boolean flagSituacao;
	
	private String observacao;
	
	private String recado;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
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

	public Date getDataAdesao() {
		return dataAdesao;
	}

	public void setDataAdesao(Date dataAdesao) {
		this.dataAdesao = dataAdesao;
	}

	public Boolean getFlagRematricula() {
		return flagRematricula;
	}

	public void setFlagRematricula(Boolean flagRematricula) {
		this.flagRematricula = flagRematricula;
	}

	public Boolean getFlagAgenda() {
		return flagAgenda;
	}

	public void setFlagAgenda(Boolean flagAgenda) {
		this.flagAgenda = flagAgenda;
	}

	public Date getDataDesligamento() {
		return dataDesligamento;
	}

	public void setDataDesligamento(Date dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public Cliente getTitular() {
		return titular;
	}

	public void setTitular(Cliente titular) {
		this.titular = titular;
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

	public String getEnderecoFuncional() {
		return enderecoFuncional;
	}

	public void setEnderecoFuncional(String enderecoFuncional) {
		this.enderecoFuncional = enderecoFuncional;
	}

	public String getBairroFuncional() {
		return bairroFuncional;
	}

	public void setBairroFuncional(String bairroFuncional) {
		this.bairroFuncional = bairroFuncional;
	}

	public String getTelefoneFuncional() {
		return telefoneFuncional;
	}

	public void setTelefoneFuncional(String telefoneFuncional) {
		this.telefoneFuncional = telefoneFuncional;
	}

	public Cidade getCidadeFuncional() {
		return cidadeFuncional;
	}

	public void setCidadeFuncional(Cidade cidadeFuncional) {
		this.cidadeFuncional = cidadeFuncional;
	}

	public Estado getEstadoFuncional() {
		return estadoFuncional;
	}

	public void setEstadoFuncional(Estado estadoFuncional) {
		this.estadoFuncional = estadoFuncional;
	}

	public String getCadastroPM() {
		return cadastroPM;
	}

	public void setCadastroPM(String cadastroPM) {
		this.cadastroPM = cadastroPM;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getRecado() {
		return recado;
	}

	public void setRecado(String recado) {
		this.recado = recado;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Boolean getFlagSituacao() {
		return flagSituacao;
	}

	public void setFlagSituacao(Boolean flagSituacao) {
		this.flagSituacao = flagSituacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Graduacao getGraduacao() {
		return graduacao;
	}

	public void setGraduacao(Graduacao graduacao) {
		this.graduacao = graduacao;
	}

	public Situacao getSituacao() {
		return situacao;
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
	
}
