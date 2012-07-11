package br.com.cenajur.model;

import java.util.Date;

public class ClienteModel {

	private Long id;
	
	private String matricula;
	
	private String nome;
	
	private String email;
	
	private String cpf;
	
	private String rg;
	
	private Date dataNascimento;
	
	private String endereco;
	
	private String bairro;
	
	private CidadeModel cidadeModel;
	
	private EstadoModel estadoModel;
	
	private String cep;
	
	private String telefone1;
	
	private String telefone2;
	
	private String celular;
	
	private String nomePai;
	
	private String nomeMae;
	
	private EstadoCivilModel estadoCivilModel;
	
	private Date dataAdesao;
	
	private Boolean flagRematricula;
	
	private Boolean flagAgenda;
	
	private TipoClienteModel tipoClienteModel;
	
	private SituacaoModel situacaoModel;
	
	private TipoPagamentoModel tipoPagamentoModel;
	
	private Date dataDesligamento;
	
	private String motivoCancelamento;
	
	private ClienteModel titular;
	
	private BancoModel bancoModel;
	
	private String agencia;
	
	private String conta;
	
	private Integer lote;
	
	private String enderecoFuncional;
	
	private String bairroFuncional;
	
	private String telefoneFuncional;
	
	private CidadeModel cidadeFuncional;
	
	private EstadoModel estadoFuncional;
	
	private GraduacaoModel graduacaoModel;
	
	private String cadastroPM;
	
	private Boolean situacao;
	
	private String observacao;
	
	private String recado;

	public Long getId() {
		return id;
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

	public CidadeModel getCidadeModel() {
		return cidadeModel;
	}

	public void setCidadeModel(CidadeModel cidadeModel) {
		this.cidadeModel = cidadeModel;
	}

	public EstadoModel getEstadoModel() {
		return estadoModel;
	}

	public void setEstadoModel(EstadoModel estadoModel) {
		this.estadoModel = estadoModel;
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

	public EstadoCivilModel getEstadoCivilModel() {
		return estadoCivilModel;
	}

	public void setEstadoCivilModel(EstadoCivilModel estadoCivilModel) {
		this.estadoCivilModel = estadoCivilModel;
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

	public TipoClienteModel getTipoClienteModel() {
		return tipoClienteModel;
	}

	public void setTipoClienteModel(TipoClienteModel tipoClienteModel) {
		this.tipoClienteModel = tipoClienteModel;
	}

	public SituacaoModel getSituacaoModel() {
		return situacaoModel;
	}

	public void setSituacaoModel(SituacaoModel situacaoModel) {
		this.situacaoModel = situacaoModel;
	}

	public TipoPagamentoModel getTipoPagamentoModel() {
		return tipoPagamentoModel;
	}

	public void setTipoPagamentoModel(TipoPagamentoModel tipoPagamentoModel) {
		this.tipoPagamentoModel = tipoPagamentoModel;
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

	public ClienteModel getTitular() {
		return titular;
	}

	public void setTitular(ClienteModel titular) {
		this.titular = titular;
	}

	public BancoModel getBancoModel() {
		return bancoModel;
	}

	public void setBancoModel(BancoModel bancoModel) {
		this.bancoModel = bancoModel;
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

	public CidadeModel getCidadeFuncional() {
		return cidadeFuncional;
	}

	public void setCidadeFuncional(CidadeModel cidadeFuncional) {
		this.cidadeFuncional = cidadeFuncional;
	}

	public EstadoModel getEstadoFuncional() {
		return estadoFuncional;
	}

	public void setEstadoFuncional(EstadoModel estadoFuncional) {
		this.estadoFuncional = estadoFuncional;
	}

	public GraduacaoModel getGraduacaoModel() {
		return graduacaoModel;
	}

	public void setGraduacaoModel(GraduacaoModel graduacaoModel) {
		this.graduacaoModel = graduacaoModel;
	}

	public String getCadastroPM() {
		return cadastroPM;
	}

	public void setCadastroPM(String cadastroPM) {
		this.cadastroPM = cadastroPM;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
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
		ClienteModel other = (ClienteModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
