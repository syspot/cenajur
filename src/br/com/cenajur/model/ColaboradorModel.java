package br.com.cenajur.model;

import java.util.Date;

public class ColaboradorModel {

	private Long id;
	
	private Long codigo;
	
	private String nome;
	
	private String email;
	
	private String cpf;
	
	private String rg;
	
	private String ctps;
	
	private String pis;
	
	private Date dataNascimento;
	
	private String endereco;
	
	private String bairro;
	
	private CidadeModel cidadeModel;
	
	private EstadoModel estadoModel;
	
	private String cep;
	
	private String telefone;
	
	private String celular;
	
	private Date dataAdmissao;
	
	private Boolean flagAgenda;
	
	private TipoColaboradorModel tipoColaboradorModel;
	
	private TipoAcessoModel tipoAcessoModel;
	
	private Boolean flagSituacao;
	
	private Date dataDesligamento;
	
	private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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

	public String getCtps() {
		return ctps;
	}

	public void setCtps(String ctps) {
		this.ctps = ctps;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
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

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Boolean getFlagAgenda() {
		return flagAgenda;
	}

	public void setFlagAgenda(Boolean flagAgenda) {
		this.flagAgenda = flagAgenda;
	}

	public TipoColaboradorModel getTipoColaboradorModel() {
		return tipoColaboradorModel;
	}

	public void setTipoColaboradorModel(TipoColaboradorModel tipoColaboradorModel) {
		this.tipoColaboradorModel = tipoColaboradorModel;
	}

	public TipoAcessoModel getTipoAcessoModel() {
		return tipoAcessoModel;
	}

	public void setTipoAcessoModel(TipoAcessoModel tipoAcessoModel) {
		this.tipoAcessoModel = tipoAcessoModel;
	}

	public Boolean getFlagSituacao() {
		return flagSituacao;
	}

	public void setFlagSituacao(Boolean flagSituacao) {
		this.flagSituacao = flagSituacao;
	}

	public Date getDataDesligamento() {
		return dataDesligamento;
	}

	public void setDataDesligamento(Date dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
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
		ColaboradorModel other = (ColaboradorModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
