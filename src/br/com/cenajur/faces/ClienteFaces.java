package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;

import br.com.cenajur.model.Agenda;
import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.Banco;
import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.DocumentoCliente;
import br.com.cenajur.model.Estado;
import br.com.cenajur.model.EstadoCivil;
import br.com.cenajur.model.Faturamento;
import br.com.cenajur.model.Graduacao;
import br.com.cenajur.model.Lotacao;
import br.com.cenajur.model.MotivoCancelamento;
import br.com.cenajur.model.Plano;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.ProcessoParteContraria;
import br.com.cenajur.model.SituacaoProcessoCliente;
import br.com.cenajur.model.SituacaoProcessoParteContraria;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.model.TipoPagamento;
import br.com.cenajur.model.Turno;
import br.com.cenajur.relat.JasperUtil;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.Utilitarios;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@SessionScoped
@ManagedBean(name = "clienteFaces")
public class ClienteFaces extends CrudFaces<Cliente> {

	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private List<SelectItem> cidadesPesquisa;
	private List<SelectItem> estadosCivis;
	private List<SelectItem> tiposPagamentos;
	private List<SelectItem> motivosCancelamentos;
	private List<SelectItem> bancos;
	private List<SelectItem> planos;
	private List<SelectItem> graduacoes;
	private List<SelectItem> categoriasDocumentos;
	
	private Lotacao lotacaoSelecionada;
	
	private AndamentoProcesso andamentoProcessoSelecionado;
	private Audiencia audienciaSelecionada;
	
	private CategoriaDocumento categoriaDocumento;
	private DocumentoCliente documentoCliente;
	private DocumentoCliente documentoSelecionado;
	private Cliente clienteSelecionado;
	private int statusCliente;
	
	private ProcessoAux processoAux;
	
	private String senha;
	private Long idAgendaColaborador;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
		
		AutenticacaoFaces autenticacaoFaces = (AutenticacaoFaces) TSFacesUtil.getManagedBean("autenticacaoFaces");
		
		if(!TSUtil.isEmpty(autenticacaoFaces) && !TSUtil.isEmpty(autenticacaoFaces.getClienteSelecionado())){
			this.setCrudModel(autenticacaoFaces.getClienteSelecionado());
			this.detailEvent();
		}
		
	}
	
	private void initCombo(){
		this.estados = super.initCombo(new Estado().findAll("descricao"), "id", "descricao");
		this.estadosCivis = super.initCombo(new EstadoCivil().findAll("descricao"), "id", "descricao");
		this.tiposPagamentos = super.initCombo(new TipoPagamento().findAll("descricao"), "id", "descricao");
		this.motivosCancelamentos = super.initCombo(new MotivoCancelamento().findAll("descricao"), "id", "descricao");
		this.bancos = super.initCombo(new Banco().findAll("descricao"), "id", "descricao");
		this.planos = super.initCombo(new Plano(Boolean.TRUE).findByModel("descricao"), "id", "descricao");
		this.graduacoes = super.initCombo(new Graduacao().findAll("descricao"), "id", "descricao");
		this.categoriasDocumentos = this.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Cliente());
		getCrudModel().setCidade(new Cidade());
		getCrudModel().getCidade().setEstado(new Estado());
		getCrudModel().setEstadoCivil(new EstadoCivil());
		getCrudModel().setBanco(new Banco());
		getCrudModel().setGraduacao(new Graduacao());
		getCrudModel().setTipoPagamento(new TipoPagamento());
		getCrudModel().setPlano(new Plano(Constantes.PLANO_MENSAL));
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		getCrudModel().setFlagStatusPM(Boolean.TRUE);
		getCrudModel().setFlagAssociado(Boolean.TRUE);
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_CLIENTE));
		getCrudModel().setDocumentos(new ArrayList<DocumentoCliente>());
		getCrudModel().setDiaVencimento(1);
		setDocumentoCliente(new DocumentoCliente());
		setFlagAlterar(Boolean.FALSE);
		this.processoAux = new ProcessoAux();
		this.senha = null;
		setTabIndex(1);
		return null;
	}

	@Override
	public String limparPesquisa(){
		setGrid(new ArrayList<Cliente>());
		setCrudPesquisaModel(new Cliente());
		getCrudPesquisaModel().setCidade(new Cidade());
		getCrudPesquisaModel().getCidade().setEstado(new Estado());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		getCrudPesquisaModel().setFlagAssociado(Boolean.TRUE);
		return null;
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean erro = false;
		
		if(!TSUtil.isValidCPF(TSUtil.removerNaoDigitos(getCrudModel().getCpf()))){
			erro = true;
			CenajurUtil.addErrorMessage("CPF inválido");
		}
		
		if(getCrudModel().getDiaVencimento() > 31 || getCrudModel().getDiaVencimento() < 1){
			erro = true;
			CenajurUtil.addErrorMessage("Dia de vencimento inválido");
		}
		
		if(TSUtil.isEmpty(getCrudModel().getId())){
			
			Cliente cliente = getCrudModel().obterPorCPF();
			
			if(!TSUtil.isEmpty(cliente)){
				erro = true;
				CenajurUtil.addErrorMessage("Já existe um associado cadastrado para esse CPF");
			}
			
		}
		
		return erro;
		
	}
	
	private void iniciaObjetosCombo(){
		if(TSUtil.isEmpty(getCrudModel().getBanco())){
			getCrudModel().setBanco(new Banco());
		}
		if(TSUtil.isEmpty(getCrudModel().getGraduacao())){
			getCrudModel().setGraduacao(new Graduacao());
		}
		if(TSUtil.isEmpty(getCrudModel().getMotivoCancelamento())){
			getCrudModel().setMotivoCancelamento(new MotivoCancelamento());
		}
	}
	
	@Override
	protected void prePersist() {
		
		getCrudModel().setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		getCrudModel().setDataAtualizacao(new Date());
		
		if(TSUtil.isEmpty(getCrudModel().getBanco()) || TSUtil.isEmpty(getCrudModel().getBanco().getId())){
			getCrudModel().setBanco(null);
		}
		
		if(TSUtil.isEmpty(getCrudModel().getGraduacao()) || TSUtil.isEmpty(getCrudModel().getGraduacao().getId())){
			getCrudModel().setGraduacao(null);
		}
		
		if(TSUtil.isEmpty(getCrudModel().getCidade()) || TSUtil.isEmpty(getCrudModel().getCidade().getId())){
			getCrudModel().setCidade(null);
		}
		
		if(TSUtil.isEmpty(getCrudModel().getMotivoCancelamento()) || TSUtil.isEmpty(getCrudModel().getMotivoCancelamento().getId())){
			getCrudModel().setMotivoCancelamento(null);
		}
		
		if(getCrudModel().getFlagAtivo()){
			getCrudModel().setDataCancelamento(null);
			getCrudModel().setMotivoCancelamento(null);
		}
		
		if(!TSUtil.isEmpty(getCrudModel().getTitular()) && !TSUtil.isEmpty(getCrudModel().getTitular().getId())){
			getCrudModel().setFlagAssociado(Boolean.FALSE);
		}
		
		if(TSUtil.isEmpty(getCrudModel().getPlano()) || TSUtil.isEmpty(getCrudModel().getPlano().getId())){
			getCrudModel().setPlano(null);
		}
		
		if(!TSUtil.isEmpty(this.senha)){
			getCrudModel().setSenha(Utilitarios.gerarHash(this.senha));
		}
	}
	
	@Override
	protected void preInsert() {
		getCrudModel().setDataCadastro(new Date());
	}
	
	@Override
	protected void posDetail() {
		
		if(getCrudModel().getDataAtualizacao().before(CenajurUtil.getTrimestreAnterior())){
			CenajurUtil.addDangerMessage("O endereço e telefone estão desatualizados");
		}
		
		if(TSUtil.isEmpty(getCrudModel().getCidade())){
			
			getCrudModel().setCidade(new Cidade());
			getCrudModel().getCidade().setEstado(new Estado());
			
		} else{
			
			this.atualizarComboCidades();
			
		}
		
		if(TSUtil.isEmpty(getCrudModel().getMotivoCancelamento())){
			getCrudModel().setMotivoCancelamento(new MotivoCancelamento());
		}
		
		if(TSUtil.isEmpty(getCrudModel().getBanco())){
			getCrudModel().setBanco(new Banco());
		}
		
		if(TSUtil.isEmpty(getCrudModel().getGraduacao())){
			getCrudModel().setGraduacao(new Graduacao());
		}
		
		if(TSUtil.isEmpty(getCrudModel().getPlano())){
			getCrudModel().setPlano(new Plano());
		}
		
		Faturamento faturamento = CenajurUtil.obterFaturamentoDevedor();
		
		faturamento.setCliente(getCrudModel());
		
		List<Faturamento> faturasAbertas = faturamento.pesquisarFaturasAbertas();
		
		getCrudModel().setFaturasAbertas("");
		
		for(Faturamento fatura : faturasAbertas){
			getCrudModel().setFaturasAbertas(getCrudModel().getFaturasAbertas() + " " + fatura.getMes() + "/" + fatura.getAno() + " ");
		}
		
		for(Processo processo : getCrudModel().getProcessos()){
			
			processo.setProcessoNumeroPrincipal(new ProcessoNumero().obterNumeroProcessoPrincipal(processo));
			processo.setAudiencias(new Audiencia().findByProcesso(processo));
			processo.setAndamentos(new AndamentoProcesso().findByProcesso(processo));
			processo.setProcessosNumerosTemp(new ProcessoNumero().pesquisarOutrosNumerosProcessos(processo));
			
			if(TSUtil.isEmpty(processo.getTurno())){
				processo.setTurno(new Turno());
			}
			
			for(ProcessoParteContraria processoParteContraria : processo.getProcessosPartesContrarias()){
				processoParteContraria.setSituacaoProcessoParteContrariaTemp(new SituacaoProcessoParteContraria(processoParteContraria.getSituacaoProcessoParteContraria().getId()));
			}

			for(ProcessoCliente processoCliente : processo.getProcessosClientes()){
				processoCliente.setSituacaoProcessoClienteTemp(new SituacaoProcessoCliente(processoCliente.getSituacaoProcessoCliente().getId()));
			}
			
		}
		
		this.iniciaObjetosCombo();
		
		this.processoAux.setProcessos(getCrudModel().getProcessos());
		
		getCrudModel().setVisitas(new Agenda().pesquisarVisitasPorCliente(getCrudModel()));
		
	}
	
	@Override
	protected void posPersist() throws TSApplicationException{
		
		if(!getCrudModel().getFlagAtivo()){
			getCrudModel().inativarDependentes();
		}
		
		this.iniciaObjetosCombo();
		
	}
	
	@Override
	protected void tratarException() {
		this.iniciaObjetosCombo();
	}
	
	public String mudarStatusCliente(){
		if(!getCrudModel().getFlagAtivo()){
			getCrudModel().setMotivoCancelamento(new MotivoCancelamento());
		}
		return null;
	}
	
	public String atualizarComboCidades(){
		this.cidades = super.initCombo(getCrudModel().getCidade().findCombo(), "id", "descricao");
		return null;
	}

	public String atualizarComboCidadesPesquisa(){
		this.cidadesPesquisa = super.initCombo(getCrudPesquisaModel().getCidade().findCombo(), "id", "descricao");
		return null;
	}
	
	public String addLotacao(){
		getCrudModel().setLotacao(this.lotacaoSelecionada);
		return null;
	}
	
	public String addLotacaoPesquisa(){
		getCrudPesquisaModel().setLotacao(this.lotacaoSelecionada);
		return null;
	}
	
	public String addCliente(){
		getCrudModel().setTitular(this.clienteSelecionado);
		return null;
	}
	
	public void oncapture(CaptureEvent captureEvent) {  
          
        String nomeFoto = TSUtil.gerarId() + ".jpg";
        
        getCrudModel().setUrlImagem(Constantes.PASTA_DOWNLOAD_IMAGEM + nomeFoto);
		
		CenajurUtil.criaArquivo(captureEvent.getData(), Constantes.PASTA_UPLOAD_IMAGEM + nomeFoto);
		
    }
	
	public void enviarDocumento(FileUploadEvent event) {
		
		getDocumentoCliente().setArquivo(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		
		if(CenajurUtil.isDocumentoPdf(event.getFile())){
			getDocumentoCliente().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
		}
		
		CenajurUtil.criaArquivo(event.getFile(), getDocumentoCliente().getCaminhoUploadCompleto());
		
	}
		
	public String addDocumento(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoCliente().getArquivo())){
			CenajurUtil.addErrorMessage("Documento: Campo obrigatório");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		if(getDocumentoCliente().getDescricao().length() > 100){
			CenajurUtil.addErrorMessage("Descrição: Campo muito longo, tamanho máximo de 100 caracteres");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		context.addCallbackParam("sucesso", true);
		
		getDocumentoCliente().setCliente(getCrudModel());
		getDocumentoCliente().setCategoriaDocumento(getCategoriaDocumento().getById());
		getCrudModel().getDocumentos().add(getDocumentoCliente());
		getCategoriaDocumento().setId(null);
		setDocumentoCliente(new DocumentoCliente());
		return null;
	}
	
	public String removerDocumento(){
		getCrudModel().getDocumentos().remove(this.documentoSelecionado);
		return null;
	}
	
	@Override
	protected void preFind() {
		this.tratarSituacao();
	}
	
	private void tratarSituacao(){
		
		switch(statusCliente){
			case 1: getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE); break;
			case 2: getCrudPesquisaModel().setFlagAtivo(Boolean.FALSE); break;
			default: getCrudPesquisaModel().setFlagAtivo(null); break;
		}
		
	}
	
	private String gerarRelatorio(String nomeRelatorio, String nomeImpressao, String msgErro){
		try {
            
			Map<String, Object> parametros = CenajurUtil.getHashMapReport();
            parametros.put("P_CLIENTE_ID", getCrudModel().getId());

            new JasperUtil().gerarRelatorio(nomeRelatorio, nomeImpressao, parametros);
            
        } catch (Exception ex) {
            CenajurUtil.addErrorMessage(msgErro);
            ex.printStackTrace();
        }
		
		return null;
		
	}
	
	public String imprimirTermoCancelamentoContrato(){
		return this.gerarRelatorio("cancelamentoContrato.jasper", "termoCancelamentoContrato", "Não foi possível gerar o termo de cancelamento de contrato");
	}
	
	public String imprimirAtestadoPobreza() {
		return this.gerarRelatorio("declaracaoSituacaoEconomica.jasper", "declaracao_situacao_economica", "Não foi possível gerar a declaração de situação econômica");
    }
	
	public String imprimirCartaCancelamentoContrato() {
		return this.gerarRelatorio("cartaCancelamentoContrato.jasper", "carta_cancelamento_contrato", "Não foi possível gerar a carta de cancelamento de contrato");
	}
	
	public String imprimirFichaAtendimento(){
		
		if(!TSUtil.isEmpty(TSUtil.tratarLong(idAgendaColaborador))){
			
			try {
				
				Map<String, Object> parametros = CenajurUtil.getHashMapReport();
				
				parametros.put("P_AGENDA_COLABORADOR_ID", idAgendaColaborador);
				
				new JasperUtil().gerarRelatorio("fichaAtendimento.jasper", "ficha_atendimento", parametros);
				
			} catch (Exception ex) {
				
				CenajurUtil.addErrorMessage("Não foi possível gerar a ficha de atendimento.");
				
				ex.printStackTrace();
				
			}
		
		}
		
		return null;
	}
	
	private String gerarProcuracao(List<Colaborador> advogados){
		
		try {

        	StringBuilder outorgante = new StringBuilder("OUTORGANTE: ");
        	
        	outorgante.append("").append(getCrudModel().getNome()).append("");
        	
        	if(!TSUtil.isEmpty(getCrudModel().getRg())){
        		outorgante.append(" RG: ").append(getCrudModel().getRg());
        	}
        	
        	if(!TSUtil.isEmpty(getCrudModel().getCpf())){
        		outorgante.append(" CPF: ").append(getCrudModel().getCpf());
        	}
        	
    		outorgante.append(TSUtil.isEmpty(getCrudModel().getLogradouro()) ? "" : " ENDEREÇO: " + getCrudModel().getLogradouro() + ", ");
        	outorgante.append(TSUtil.isEmpty(getCrudModel().getNumero()) ? "" : getCrudModel().getNumero() + ", ");
        	outorgante.append(TSUtil.isEmpty(getCrudModel().getComplemento()) ? "" : getCrudModel().getComplemento() + ", ");
        	outorgante.append(TSUtil.isEmpty(getCrudModel().getBairro()) ? "" : getCrudModel().getBairro() + ", ");
        	outorgante.append(TSUtil.isEmpty(getCrudModel().getCidade()) || TSUtil.isEmpty(getCrudModel().getCidade().getId()) ? "" : getCrudModel().getCidade().getNomeCompleto());
        	outorgante.append(TSUtil.isEmpty(getCrudModel().getCep()) ? "" : getCrudModel().getCep());
        	
        	if(!TSUtil.isEmpty(getCrudModel().getTelefone())){
        		outorgante.append(" TEL: ").append(getCrudModel().getTelefone());
        	}
        	
        	StringBuilder outorgados = new StringBuilder("OUTORGADOS: ");
        	
    		for(Colaborador advogado : advogados){
    			outorgados.append("").append(advogado.getNome()).append("").append(!TSUtil.isEmpty(advogado.getOab()) ? " (OAB/BA n. " + advogado.getOab() + "), " : " (RG " + advogado.getRg() + "), ");
    		}
        		
    		outorgados.delete(outorgados.length() - 2, outorgados.length() - 1);
        		
        	outorgados.append("todos com escritório profissional na Alameda dos Umbuzeiros, n. 638, Edf. Alameda Centro, " +
        			"Terraço - Caminho das Árvores, Salvador - BA, CEP 41.820-680, nesta Capital.");
        	
        	String texto = outorgante.toString() + "\n\n" + outorgados.toString() + "\n\n" + "Pelo presente instrumento particular de mandato e na melhor  forma de direito, o outorgante acima qualificado, nomeia e constitui seu procurador o outorgado supramencionado com o fim de representá-lo junto aos Órgãos Federais, Estaduais e Municipais, Autarquias e Fundações, Juízos Comuns e Especiais, Instituições Financeiras e seguradoras em geral, onde figure como autor ou réu, assistente ou opoente, podendo desistir, transigir, fazer acordo, assumir compromissos, receber, passar recibos e dar quitação, exercer a adjudicação e assinar o auto e carta respectiva, substabelecer com ou sem reservas e praticar os atos necessários ao bom desempenho deste mandato, por mais especiais que sejam, além dos poderes citados na cláusula Ad Judicia.";
        	
            Map<String, Object> parametros = CenajurUtil.getHashMapReport();

            parametros.put("P_TEXTO", texto);

            new JasperUtil().gerarRelatorio("procuracao.jasper", "procuracao", parametros);

        } catch (Exception ex) {

            CenajurUtil.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        }
		
		return null;
		
	}
	
	public String imprimirProcuracaoIndividual(){
		return this.gerarProcuracao(new Colaborador().findAdvogadosProcuracaoIndividual());
	}
	
	public String imprimirProcuracaoColetiva(){
		return this.gerarProcuracao(new Colaborador().findAllAdvogadosOrderByOrdemImpressao());
	}
	
	public List<SelectItem> getEstados() {
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public List<SelectItem> getCidadesPesquisa() {
		return cidadesPesquisa;
	}

	public void setCidadesPesquisa(List<SelectItem> cidadesPesquisa) {
		this.cidadesPesquisa = cidadesPesquisa;
	}

	public List<SelectItem> getEstadosCivis() {
		return estadosCivis;
	}

	public void setEstadosCivis(List<SelectItem> estadosCivis) {
		this.estadosCivis = estadosCivis;
	}

	public List<SelectItem> getTiposPagamentos() {
		return tiposPagamentos;
	}

	public void setTiposPagamentos(List<SelectItem> tiposPagamentos) {
		this.tiposPagamentos = tiposPagamentos;
	}

	public List<SelectItem> getMotivosCancelamentos() {
		return motivosCancelamentos;
	}

	public void setMotivosCancelamentos(List<SelectItem> motivosCancelamentos) {
		this.motivosCancelamentos = motivosCancelamentos;
	}

	public List<SelectItem> getCategoriasDocumentos() {
		return categoriasDocumentos;
	}

	public void setCategoriasDocumentos(List<SelectItem> categoriasDocumentos) {
		this.categoriasDocumentos = categoriasDocumentos;
	}

	public List<SelectItem> getBancos() {
		return bancos;
	}

	public void setBancos(List<SelectItem> bancos) {
		this.bancos = bancos;
	}

	public List<SelectItem> getPlanos() {
		return planos;
	}

	public void setPlanos(List<SelectItem> planos) {
		this.planos = planos;
	}

	public List<SelectItem> getGraduacoes() {
		return graduacoes;
	}

	public void setGraduacoes(List<SelectItem> graduacoes) {
		this.graduacoes = graduacoes;
	}

	public Lotacao getLotacaoSelecionada() {
		return lotacaoSelecionada;
	}

	public void setLotacaoSelecionada(Lotacao lotacaoSelecionada) {
		this.lotacaoSelecionada = lotacaoSelecionada;
	}

	public AndamentoProcesso getAndamentoProcessoSelecionado() {
		return andamentoProcessoSelecionado;
	}

	public void setAndamentoProcessoSelecionado(AndamentoProcesso andamentoProcessoSelecionado) {
		this.andamentoProcessoSelecionado = andamentoProcessoSelecionado;
	}

	public Audiencia getAudienciaSelecionada() {
		return audienciaSelecionada;
	}

	public void setAudienciaSelecionada(Audiencia audienciaSelecionada) {
		this.audienciaSelecionada = audienciaSelecionada;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}

	public DocumentoCliente getDocumentoCliente() {
		return documentoCliente;
	}

	public void setDocumentoCliente(DocumentoCliente documentoCliente) {
		this.documentoCliente = documentoCliente;
	}

	public DocumentoCliente getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocumentoCliente documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public int getStatusCliente() {
		return statusCliente;
	}

	public void setStatusCliente(int statusCliente) {
		this.statusCliente = statusCliente;
	}

	public ProcessoAux getProcessoAux() {
		return processoAux;
	}

	public void setProcessoAux(ProcessoAux processoAux) {
		this.processoAux = processoAux;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getIdAgendaColaborador() {
		return idAgendaColaborador;
	}

	public void setIdAgendaColaborador(Long idAgendaColaborador) {
		this.idAgendaColaborador = idAgendaColaborador;
	}
	
}
