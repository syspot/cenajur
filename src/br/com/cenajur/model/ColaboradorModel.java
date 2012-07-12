package br.com.cenajur.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "colaboradores")
public class ColaboradorModel extends TSActiveRecordAb<ColaboradorModel>{

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "rg")
	private String rg;
	
	@Column(name = "ctps")
	private String ctps;
	
	@Column(name = "pis")
	private String pis;
	
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "bairro")
	private String bairro;
	
	@OneToOne
	@JoinColumn(name = "cidade_id")
	private CidadeModel cidadeModel;

	@Column(name = "cep")
	private String cep;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "celular")
	private String celular;
	
	@Column(name = "data_admissao")
	private Date dataAdmissao;
	
	@Column(name = "flag_agenda")
	private Boolean flagAgenda;
	
	@OneToOne
	@JoinColumn(name = "tipo_colaborador_id")
	private TipoColaboradorModel tipoColaboradorModel;
	
	@OneToOne
	@JoinColumn(name = "tipo_acesso_id")
	private TipoAcessoModel tipoAcessoModel;
	
	@Column(name = "flag_situacao")
	private Boolean flagSituacao;
	
	@Column(name = "data_desligamento")
	private Date dataDesligamento;
	
	@Column(name = "observacao")
	private String observacao;
	
	@OneToOne
	@JoinColumn(name = "grupo_id")
	private GrupoModel grupoModel;
	
	@Column(name = "login")
	private String login;
	
	@Column(name = "senha")
	private String senha;
	

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

	public GrupoModel getGrupoModel() {
		return grupoModel;
	}

	public void setGrupoModel(GrupoModel grupoModel) {
		this.grupoModel = grupoModel;
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
	
	public ColaboradorModel autenticarPorLogin() {
		return super.get(" from ColaboradorModel c where c.login = ? ", login);
	}	
	
}
