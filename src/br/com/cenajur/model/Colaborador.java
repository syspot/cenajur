package br.com.cenajur.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "colaboradores")
public class Colaborador extends TSActiveRecordAb<Colaborador>{

	@Id
	@GeneratedValue
	private Long id;
	
	private String nome;
	
	private String email;
	
	private String cpf;
	
	private String rg;
	
	private String ctps;
	
	private String pis;
	
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	private String cep;

	private String logradouro;
	
	private Integer numero;
	
	private String complemento;
	
	private String bairro;
	
	@ManyToOne
	private Cidade cidade;
	
	private String telefone;
	
	private String celular;
	
	@Column(name = "data_admissao")
	private Date dataAdmissao;
	
	@Column(name = "flag_agenda")
	private Boolean flagAgenda;
	
	@ManyToOne
	@JoinColumn(name = "tipo_colaborador_id")
	private TipoColaborador tipoColaborador;
	
	@ManyToOne
	@JoinColumn(name = "tipo_acesso_id")
	private TipoAcesso tipoAcesso;
	
	@Column(name = "flag_situacao")
	private Boolean flagSituacao;
	
	@Column(name = "data_desligamento")
	private Date dataDesligamento;
	
	private String observacao;
	
	@ManyToOne
	private Grupo grupo;
	
	private String login;
	
	private String senha;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoColaborador getTipoColaborador() {
		return tipoColaborador;
	}

	public void setTipoColaborador(TipoColaborador tipoColaborador) {
		this.tipoColaborador = tipoColaborador;
	}

	public TipoAcesso getTipoAcesso() {
		return tipoAcesso;
	}

	public void setTipoAcesso(TipoAcesso tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
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
		Colaborador other = (Colaborador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Colaborador autenticarPorLogin() {
		return super.get(" from Colaborador c where c.login = ? ", login);
	}	
	
}
	