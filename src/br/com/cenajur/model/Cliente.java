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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "clientes")
public class Cliente extends TSActiveRecordAb<Cliente>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8123932933312312633L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="clientes_id")
	@SequenceGenerator(name="clientes_id", sequenceName="clientes_id_seq")
	private Long id;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	
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
	
	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_atualizacao_id")
	private Colaborador colaboradorAtualizacao;
	
	@Column(name = "url_imagem")
	private String urlImagem;
	
	@OneToMany
	@JoinTable(name = "processos_clientes", joinColumns = {
	@JoinColumn(name = "cliente_id") }, inverseJoinColumns = {
	@JoinColumn(name = "processo_id") })
	@OrderBy(value = "situacaoProcesso asc, tipoProcesso")
	private List<Processo> processos;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocumentoCliente> documentos;
	
	@Column(name = "flag_associado")
	private Boolean flagAssociado;
	
	@ManyToOne
	private Plano plano;
	
	@Column(name = "data_processamento")
	private Date dataProcessamento;
	
	@Transient
	private byte[] bytesImagem;
	
	@Transient
	private String caminhoImagem;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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

	public void setId(Long id) {
		this.id = id;
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

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	public List<DocumentoCliente> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoCliente> documentos) {
		this.documentos = documentos;
	}

	public Boolean getFlagAssociado() {
		return flagAssociado;
	}

	public void setFlagAssociado(Boolean flagAssociado) {
		this.flagAssociado = flagAssociado;
	}

	public byte[] getBytesImagem() {
		return bytesImagem;
	}

	public void setBytesImagem(byte[] bytesImagem) {
		this.bytesImagem = bytesImagem;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
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

	public String getTipo(){
		return getFlagAssociado() ? "Associado" : "Dependente";
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
	
	private String obterCondicionalQuery(){
		
		StringBuilder query = new StringBuilder();
		
		if(!TSUtil.isEmpty(matricula)){
			query.append(" and ").append(CenajurUtil.semAcento("c.matricula")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		if(!TSUtil.isEmpty(nome)){
			query.append(" and ").append(CenajurUtil.semAcento("c.nome")).append(" like ").append(CenajurUtil.semAcento("?")).append(" ");
		}
		
		if(!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getEstado()) && !TSUtil.isEmpty(cidade.getEstado().getId())){
			query.append(" and c.cidade.estado.id = ? ");
		}
		
		if(!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getId())){
			query.append(" and c.cidade.id = ? ");
		}
		
		if(!TSUtil.isEmpty(flagAtivo)){
			query.append(" and c.flagAtivo = ? ");
		}
		
		return query.toString();
	}
	
	private List<Object> obterCondicionalParans(){
		
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

		if(!TSUtil.isEmpty(flagAtivo)){
			params.add(flagAtivo);
		}
		
		return params;
		
	}
	
	@Override
	public List<Cliente> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Cliente c where 1 = 1 ");
		
		query.append(this.obterCondicionalQuery());
		
		query.append(" and c.flagAssociado = true ");
		
		return super.find(query.toString(), "nome", this.obterCondicionalParans().toArray());
	}
	
	public List<Cliente> pesquisarExceto(Cliente cliente){
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Cliente c where 1 = 1 ");
		
		if(!TSUtil.isEmpty(cliente) && !TSUtil.isEmpty(cliente.getId())){
			query.append(" and c.id != " + cliente.getId());
		}
		
		query.append(this.obterCondicionalQuery());
		
		query.append(" and c.flagAssociado = true ");
		
		return super.find(query.toString(), "nome", this.obterCondicionalParans().toArray());
		
	}
	
	public List<Cliente> pesquisarComDependentes(){
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Cliente c where 1 = 1 ");
		
		query.append(this.obterCondicionalQuery());
		
		return super.find(query.toString(), "nome", this.obterCondicionalParans().toArray());
		
	}
	
	public void gerarFaturamento(){
		super.find(" from Cliente c where c.dataProcessamento < CURRENT_DATE ", null);
	}
	
}
