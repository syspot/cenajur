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

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "colaboradores")
public class Colaborador extends TSActiveRecordAb<Colaborador>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1762162497629022408L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="colaboradores_id")
	@SequenceGenerator(name="colaboradores_id", sequenceName="colaboradores_id_seq")
	private Long id;
	
	private String nome;
	
	private String apelido;
	
	private String email;
	
	private String cpf;
	
	private String rg;
	
	private String oab;
	
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
	
	@ManyToOne
	@JoinColumn(name = "tipo_colaborador_id")
	private TipoColaborador tipoColaborador;
	
	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	
	@Column(name = "data_desligamento")
	private Date dataDesligamento;
	
	@ManyToOne
	private Grupo grupo;
	
	private String login;
	
	private String senha;
	
	@Transient
	private String senha2;
	
	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_atualizacao_id")
	private Colaborador colaboradorAtualizacao;
	
	@Column(name = "flag_permissao_agenda")
	private Boolean flagPermissaoAgenda;
	
	public Colaborador() {
	}
	
	public Colaborador(TipoColaborador tipoColaborador) {
		this.tipoColaborador = tipoColaborador;
	}
	
	public Colaborador(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
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

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
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

	public String getOab() {
		return oab;
	}

	public void setOab(String oab) {
		this.oab = oab;
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

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Date getDataDesligamento() {
		return dataDesligamento;
	}

	public void setDataDesligamento(Date dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
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

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Colaborador getColaboradorAtualizacao() {
		return colaboradorAtualizacao;
	}

	public void setColaboradorAtualizacao(Colaborador colaboradorAtualizacao) {
		this.colaboradorAtualizacao = colaboradorAtualizacao;
	}

	public Boolean getFlagPermissaoAgenda() {
		return flagPermissaoAgenda;
	}

	public void setFlagPermissaoAgenda(Boolean flagPermissaoAgenda) {
		this.flagPermissaoAgenda = flagPermissaoAgenda;
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
	
	@Override
	public List<Colaborador> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Colaborador c where 1 = 1 ");
		
		if(!TSUtil.isEmpty(nome)){
			query.append(CenajurUtil.getParamSemAcento("c.nome"));
		}
		
		if(!TSUtil.isEmpty(apelido)){
			query.append(CenajurUtil.getParamSemAcento("c.apelido"));
		}
		
		if(!TSUtil.isEmpty(email)){
			query.append(CenajurUtil.getParamSemAcento("c.email"));
		}
		
		if(!TSUtil.isEmpty(grupo) && !TSUtil.isEmpty(grupo.getId())){
			query.append("and c.grupo.id = ? ");
		}
		
		if(!TSUtil.isEmpty(tipoColaborador) && !TSUtil.isEmpty(tipoColaborador.getId())){
			query.append("and c.tipoColaborador.id = ? ");
		}
		
		if(!TSUtil.isEmpty(flagAtivo)){
			query.append("and c.flagAtivo = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(nome)){
			params.add(CenajurUtil.tratarString(nome));
		}
		
		if(!TSUtil.isEmpty(apelido)){
			params.add(CenajurUtil.tratarString(apelido));
		}
		
		if(!TSUtil.isEmpty(email)){
			params.add(CenajurUtil.tratarString(email));
		}
		
		if(!TSUtil.isEmpty(grupo) && !TSUtil.isEmpty(grupo.getId())){
			params.add(grupo.getId());
		}
		
		if(!TSUtil.isEmpty(tipoColaborador) && !TSUtil.isEmpty(tipoColaborador.getId())){
			params.add(tipoColaborador.getId());
		}
		
		if(!TSUtil.isEmpty(flagAtivo)){
			params.add(flagAtivo);
		}
		
		return super.find(query.toString(), "nome", params.toArray());
	}
	
	public List<Colaborador> findAllAdvogados(){
		return super.find(" from Colaborador c where c.tipoColaborador.id = ? and c.flagAtivo = true ", "apelido", Constantes.TIPO_COLABORADOR_ADVOGADO);
	}
	
}
	