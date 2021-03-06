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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.model.*;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "colaboradores")
public class Colaborador extends TSActiveRecordAb<Colaborador>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1762162497629022408L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String matricula;
	
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
	
	@OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocumentoColaborador> documentos;
	
	@Column(name = "url_imagem")
	private String urlImagem;
	
	private String observacoes;
	
	@Column(name = "ordem_impressao")
	private Integer ordemImpressao;
	
	@Column(name = "flag_procuracao_individual")
	private Boolean flagProcuracaoIndividual;
	
	@OneToMany(mappedBy = "advogado")
	private List<Processo> processos;
	
	@OneToMany(mappedBy = "colaborador")
	private List<AgendaColaborador> agendasColaboradores;
	
	@Transient
	private boolean pesquisaComQtdVisitas;
	
	@Transient
	private Long qtdVisitasDias;
	
	private String ctps;
	
	@Column(name = "ctps_serie")
	private String ctpsSerie;
	
	public Colaborador() {
	}
	
	public Colaborador(Long id) {
		this.id = id;
	}
	
	public Colaborador(Long id, String nome, String apelido) {
		super();
		this.id = id;
		this.nome = nome;
		this.apelido = apelido;
	}

	public Colaborador(Long id, String nome, String apelido, Long idTipoColaborador, String descricaoColaborador, Long qtd) {
		super();
		this.id = id;
		this.nome = nome;
		this.apelido = apelido;
		this.tipoColaborador = new TipoColaborador(idTipoColaborador, descricaoColaborador);
		this.qtdVisitasDias = qtd;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
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

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
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

	public List<DocumentoColaborador> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoColaborador> documentos) {
		this.documentos = documentos;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}
	
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	public Integer getOrdemImpressao() {
		return TSUtil.tratarInteger(ordemImpressao);
	}

	public void setOrdemImpressao(Integer ordemImpressao) {
		this.ordemImpressao = TSUtil.tratarInteger(ordemImpressao);
	}

	public Boolean getFlagProcuracaoIndividual() {
		return flagProcuracaoIndividual;
	}

	public void setFlagProcuracaoIndividual(Boolean flagProcuracaoIndividual) {
		this.flagProcuracaoIndividual = flagProcuracaoIndividual;
	}

	public String getCss(){
		return getFlagAtivo() ? "" : "situacaoCancelada";
	}

	public boolean isPesquisaComQtdVisitas() {
		return pesquisaComQtdVisitas;
	}

	public void setPesquisaComQtdVisitas(boolean pesquisaComQtdVisitas) {
		this.pesquisaComQtdVisitas = pesquisaComQtdVisitas;
	}

	public List<AgendaColaborador> getAgendasColaboradores() {
		return agendasColaboradores;
	}

	public void setAgendasColaboradores(List<AgendaColaborador> agendasColaboradores) {
		this.agendasColaboradores = agendasColaboradores;
	}

	public Long getQtdVisitasDias() {
		return qtdVisitasDias;
	}

	public void setQtdVisitasDias(Long qtdVisitasDias) {
		this.qtdVisitasDias = qtdVisitasDias;
	}
	
	public boolean isTipoColaboradorAdvogado(){
		return Constantes.TIPO_COLABORADOR_ADVOGADO.equals(tipoColaborador.getId());
	}
	
	public boolean isTipoColaboradorEstagiario(){
		return Constantes.TIPO_COLABORADOR_ESTAGIARIO.equals(tipoColaborador.getId());
	}

	public String getCtps() {
		return ctps;
	}

	public void setCtps(String ctps) {
		this.ctps = ctps;
	}

	public String getCtpsSerie() {
		return ctpsSerie;
	}

	public void setCtpsSerie(String ctpsSerie) {
		this.ctpsSerie = ctpsSerie;
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
		
		if(pesquisaComQtdVisitas){
			query.append(" select distinct new Colaborador(c.id, c.nome, c.apelido, c.tipoColaborador.id, c.tipoColaborador.descricao, count(a.id) as qtd) from Colaborador c left join c.agendasColaboradores ac left join ac.agenda a with a.tipoAgenda.id = 4 and date(a.dataInicial) = current_date where 1 = 1 ");
		} else{
			query.append(" from Colaborador c where 1 = 1 ");
		}
		
		if(!TSUtil.isEmpty(matricula)){
			query.append(" and c.matricula = ? ");
		}
		
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
		
		if(!TSUtil.isEmpty(matricula)){
			params.add(matricula);
		}
		
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
		
		if (pesquisaComQtdVisitas) {
			query.append(" group by c.id, c.nome, c.apelido, c.tipoColaborador.id, c.tipoColaborador.descricao order by qtd asc ");
		}
		
		return super.find(query.toString(), "c.nome", params.toArray());
	}
	
	public List<Colaborador> findAllAdvogados(){
		return super.find(" from Colaborador c where c.tipoColaborador.id = ? and c.flagAtivo = true ", "apelido", Constantes.TIPO_COLABORADOR_ADVOGADO);
	}
	
	public List<Colaborador> findAdvogadosProcuracaoIndividual(){
		return super.find(" from Colaborador c where c.tipoColaborador.id = ? and c.flagAtivo = true and c.flagProcuracaoIndividual = true ", "ordemImpressao", Constantes.TIPO_COLABORADOR_ADVOGADO);
	}
	
	public List<Colaborador> findColaboradoresProcuracaoColetiva(){
		return super.find(" from Colaborador c where (c.tipoColaborador.id = ? or c.tipoColaborador.id = ?) and c.flagAtivo = true ", "ordemImpressao", Constantes.TIPO_COLABORADOR_ADVOGADO, Constantes.TIPO_COLABORADOR_ESTAGIARIO);
	}
	
	public List<Colaborador> findAllAdvogadosComProcessos(){
		return super.find(" select distinct new Colaborador(c.id, c.nome, c.apelido) from Colaborador c inner join c.processos p where c.tipoColaborador.id = ? and c.flagAtivo = true  ", "c.nome", Constantes.TIPO_COLABORADOR_ADVOGADO);
	}
	
}
	