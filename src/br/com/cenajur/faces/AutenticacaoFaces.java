package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.AgendaColaborador;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.MensagemDestinatario;
import br.com.cenajur.model.Menu;
import br.com.cenajur.model.Objeto;
import br.com.cenajur.model.Permissao;
import br.com.cenajur.model.PermissaoGrupo;
import br.com.cenajur.model.RegrasBloqueio;
import br.com.cenajur.model.TipoAgenda;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.EmailUtil;
import br.com.cenajur.util.Utilitarios;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

import com.ibm.icu.util.Calendar;

@SessionScoped
@ManagedBean(name = "autenticacaoFaces")
public class AutenticacaoFaces extends TSMainFaces{

    private Colaborador colaborador;
    private Permissao permissaoSelecionada;
    private PermissaoGrupo permissaoGrupoSelecionada;
    private List<Menu> menus;
    private List<Menu> menusPrime;
    private List<Colaborador> colaboradoresConectados;
    private Integer tabAtiva;
    private String tela;
    private String nomeTela;
    private String currentFaces;
    private Long opcao;
    private boolean flagBloqueado;
    private Colaborador colaboradorSelecionado;
    private Objeto objetoSelecionado;
    private Cliente clienteSelecionado;
    private Long situacaoProcesso;
    private String ano;
    private Date data;
    private String confirmaSenha;

    
    public AutenticacaoFaces() {
    	
        clearFields();

        setTabAtiva(new Integer(0));
        
    }
    
    private void obterPaginaInicial(){
    	
    	this.permissaoSelecionada = new Permissao(Constantes.PERMISSAO_MENSAGENS).getById();
    	
    	redirecionar();
    	
    }
    
    private void limparObjetos(){
    	this.colaboradorSelecionado = null;
    	this.objetoSelecionado = null;
    	this.situacaoProcesso = null;
    	this.ano = null;
    	this.data = null;
    }
    
    private boolean isPossuiPermissao(){
    	
    	boolean valida = false;
    	
    	rotuloLoop:
    		for(PermissaoGrupo permissaoGrupo : permissaoSelecionada.getPermissoesGrupos()){
    			
    			for(PermissaoGrupo permissaoGrupo2 : colaborador.getGrupo().getPermissoesGrupos()){
    				
    				if(permissaoGrupo.getId().equals(permissaoGrupo2.getId())){
    					valida = true;
    					break rotuloLoop;
    				}
    				
    			}
    			
    		}
    	
    	return valida;
    }
    
    public String redirecionar() {
    	
    	if(isFlagBloqueado()){
    		
    		this.flagBloqueado = false;
    		
    		this.verificarBloqueioPorMensagem();
        	this.verificarBloqueioPorTarefa();
        	this.verificarBloqueioPorAudiencia();
        	
        	if(isFlagBloqueado()){
	    		this.permissaoSelecionada = new Permissao(Constantes.PERMISSAO_MENSAGENS).getById();
        	}
        	
    	}
    	
        this.removeObjectInSession(this.currentFaces);
        setTela(this.permissaoSelecionada.getUrl());
        setNomeTela("Área de Trabalho > " + this.permissaoSelecionada.getMenu().getNome() + " > " + this.permissaoSelecionada.getDescricao());
        setTabAtiva(this.permissaoSelecionada.getTabAtiva());
        this.currentFaces = this.permissaoSelecionada.getFaces();
        
        this.obterPermissaoGrupoSelecionada();
        this.limparObjetos();
        
        return "sucesso";
    }
    
    public String redirecionarParaColaborador() {
    	
    	this.permissaoSelecionada = new Permissao(Constantes.PERMISSAO_COLABORADOR).getById();
    	
    	Colaborador colaborador = null;
    	
    	if(!TSUtil.isEmpty(colaboradorSelecionado)){
    		colaborador = new Colaborador(colaboradorSelecionado.getId());
    	}
    	
    	if(this.isPossuiPermissao()){
    		redirecionar();
    	}
    	
    	this.colaboradorSelecionado = colaborador;
    	
    	return "sucesso";
    }
    
    public String redirecionarParaObjeto() {
    	
    	this.permissaoSelecionada = new Permissao(Constantes.PERMISSAO_OBJETO).getById();
    	
    	Objeto objeto = null;
    	
    	if(!TSUtil.isEmpty(objetoSelecionado)){
    		objeto = new Objeto(objetoSelecionado.getId());
    	}
    	
    	if(this.isPossuiPermissao()){
    		redirecionar();
    	}
    	
    	this.objetoSelecionado = objeto;
    	
    	return "sucesso";
    }
    
    public String redirecionarParaLogEmail() {
    	
    	this.permissaoSelecionada = new Permissao(Constantes.PERMISSAO_LOG_ENVIO_EMAIL).getById();
    	
    	Calendar c = Calendar.getInstance();
    	c.setTime(data);
    	
    	if(this.isPossuiPermissao()){
    		redirecionar();
    	}
    	
    	this.data = c.getTime();
    	
    	return "sucesso";
    }
    
    public String redirecionarParaProcesso() {
    	
    	this.permissaoSelecionada = new Permissao(Constantes.PERMISSAO_PROCESSO).getById();
    	
    	Long situacaoProcessoId = null;
    	String ano = null;
    	Colaborador colaborador = null;
    	Objeto objeto = null;
    	
    	if(!TSUtil.isEmpty(this.situacaoProcesso)){
    		situacaoProcessoId = new Long(this.situacaoProcesso);
    	}
    	
    	if(!TSUtil.isEmpty(this.ano)){
    		ano = new String(this.ano);
    	}
    	
    	if(!TSUtil.isEmpty(colaboradorSelecionado)){
    		colaborador = new Colaborador(colaboradorSelecionado.getId());
    	}
    	
    	if(!TSUtil.isEmpty(objetoSelecionado)){
    		objeto = new Objeto(objetoSelecionado.getId());
    	}
    	
    	if(this.isPossuiPermissao()){
    		redirecionar();
    	}
    	
    	this.situacaoProcesso = situacaoProcessoId;
    	this.ano = ano;
    	this.colaboradorSelecionado = colaborador;
    	this.objetoSelecionado = objeto;
    	
    	return "sucesso";
    }
    
    public String redirecionarParaCliente() {
    	
    	this.permissaoSelecionada = new Permissao(Constantes.PERMISSAO_CLIENTE).getById();
    	
    	Cliente cliente = null;
    	
    	if(!TSUtil.isEmpty(clienteSelecionado)){
    		cliente = new Cliente(clienteSelecionado.getId());
    	}
    	
    	if(this.isPossuiPermissao()){
    		redirecionar();
    	}
    	
    	this.clienteSelecionado = cliente;
    	
    	return "sucesso";
    }
    
    public void obterPermissaoGrupoSelecionada(){
    	
        this.permissaoGrupoSelecionada.setGrupo(this.colaborador.getGrupo());
        this.permissaoGrupoSelecionada.setPermissao(this.permissaoSelecionada);
        
        this.permissaoGrupoSelecionada = this.permissaoGrupoSelecionada.obterPorGrupoPermissao();
    }

    public String logout() {
        TSFacesUtil.getRequest().getSession().invalidate();
        return "login";
    }

    protected void clearFields() {
    	
        this.colaborador = new Colaborador();

        this.menus = Collections.emptyList();
        
        this.menusPrime = new ArrayList<Menu>();
        
        this.permissaoGrupoSelecionada = new PermissaoGrupo();
        
        this.opcao = 1L;

    }

    public String limpar() {
    	
    	ColaboradorUtil.remover();

        TSFacesUtil.getRequest().getSession().getServletContext().setAttribute("colaboradoresConectados", colaboradoresConectados);

        clearFields();

        TSFacesUtil.removeObjectInSession("colaboradorConectado");

        TSFacesUtil.getRequest().getSession().invalidate();

        return "sair";
    }

    public String entrar() {
    	
        Colaborador colaborador = ColaboradorUtil.autenticar(this.colaborador);
        
        if (!TSUtil.isEmpty(colaborador)) {
        	
        	this.colaborador = colaborador;
        	this.menus = new Menu(true).findByModel("ordem");

        	ColaboradorUtil.adicionar(colaborador);
        	
        	this.menusPrime.clear();
        	List<Permissao> permissoes; 
        	
        	for(Menu menu : this.menus){
        		
        		permissoes = new ArrayList<Permissao>();
        		
        		for(Permissao permissao : menu.getPermissoes()){
        			
        			for(PermissaoGrupo permissaoGrupo : permissao.getPermissoesGrupos()){
        				
        				if(colaborador.getGrupo().getPermissoesGrupos().contains(permissaoGrupo)){

    						permissoes.add(permissao);
    						
        				}
        				
        			}
        			
        		}
        		
        		if(!TSUtil.isEmpty(permissoes)){
        			menu.setPermissoes(permissoes);
        			this.menusPrime.add(menu);
        		}
        		
        	}
        	
        	this.verificarBloqueioPorMensagem();
        	this.verificarBloqueioPorTarefa();
        	this.verificarBloqueioPorAudiencia();
        	
        	obterPaginaInicial();
        	
        	return "entrar";
        	
        }
        
        CenajurUtil.addErrorMessage("Dados inválidos!");

        return null;
    }
    
    private void verificarBloqueioPorMensagem(){
    	
    	RegrasBloqueio rb = new RegrasBloqueio(Constantes.REGRA_BLOQUEIO_MENSAGEM).getById();
    	
    	List<MensagemDestinatario> mensagensNaoLidas = new MensagemDestinatario().perquisarMensagensNaoLidas(ColaboradorUtil.obterColaboradorConectado(), rb.getDiasBloqueio());
    	
		if(!TSUtil.isEmpty(mensagensNaoLidas)){
			
			this.flagBloqueado = true;
			this.addErrorMessage("SISTEMA BLOQUEADO! Você possui mensagens não lidas");
			
		}
    		
    }
    
    private void verificarBloqueioPorTarefa(){
    	
    	RegrasBloqueio rb = new RegrasBloqueio(Constantes.REGRA_BLOQUEIO_TAREFA).getById();
    	
    	List<AgendaColaborador> agendaTarefa = new AgendaColaborador().perquisarNaoFechadas(colaborador, rb.getDiasBloqueio(), new TipoAgenda(Constantes.TIPO_AGENDA_TAREFA));
    	
    	if(!TSUtil.isEmpty(agendaTarefa)){
			
			this.flagBloqueado = true;
			this.addErrorMessage("SISTEMA BLOQUEADO! Você possui uma ou mais Tarefas não finalizadas");
			
		}

    }
    
    private void verificarBloqueioPorAudiencia(){
    	
    	RegrasBloqueio rb = new RegrasBloqueio(Constantes.REGRA_BLOQUEIO_AUDIENCIA).getById();
    	
    	List<AgendaColaborador> agendaTarefa = new AgendaColaborador().perquisarNaoFechadas(colaborador, rb.getDiasBloqueio(), new TipoAgenda(Constantes.TIPO_AGENDA_AUDIENCIA));
    	
    	if(!TSUtil.isEmpty(agendaTarefa)){
    		
    		this.flagBloqueado = true;
    		this.addErrorMessage("SISTEMA BLOQUEADO! Você possui uma ou mais Audiências não finalizadas");
    		
    	}
    	
    }
    
    public String recuperarSenha(){
    	
    	Colaborador colaborador = this.colaborador.autenticarPorLogin();
    	
    	if(TSUtil.isEmpty(colaborador)){
    		super.addErrorMessage("Usuário não localizado");
    	} else{
    		
    		try {
    			
    			String novaSenha = "" + CenajurUtil.gerarNumeroAleatorio();
    			
    			colaborador.setSenha(Utilitarios.gerarHash(novaSenha));
    		
				colaborador.update();
	    		
	    		String texto = "Prezado(a), sua nova senha para acessar o sistema do Cenajur é: " + novaSenha;
	    		
	    		new EmailUtil().sendMail(colaborador.getEmail(), "Recuperação de Senha", texto, null);
	    		
	    		super.addInfoMessage("Uma nova senha foi enviada para seu e-mail");
	    		
			} catch (Exception e) { 
				e.printStackTrace();
				super.addErrorMessage("Ocorreu um erro ao tentar enviar a senha para o email cadastrado");
			}
    		
    	}
    	
    	return "login";
    	
    }
    
    public String alterarSenha() throws TSApplicationException{
    	
    	Colaborador colaborador = ColaboradorUtil.autenticar(this.colaborador);
    	
    	if(TSUtil.isEmpty(colaborador)){
    		CenajurUtil.addErrorMessage("Usuário ou Senha inválidos");
    	} else{
    		colaborador.setSenha(Utilitarios.gerarHash(this.colaborador.getSenha2()));
    		colaborador.update();
    		CenajurUtil.addInfoMessage("Senha alterada com sucesso");
    		this.opcao = 1L;
    	}
    	
    	return "login";
    	
    }

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Permissao getPermissaoSelecionada() {
		return permissaoSelecionada;
	}

	public void setPermissaoSelecionada(Permissao permissaoSelecionada) {
		this.permissaoSelecionada = permissaoSelecionada;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Colaborador> getColaboradoresConectados() {
		return colaboradoresConectados;
	}

	public void setColaboradoresConectados(List<Colaborador> colaboradoresConectados) {
		this.colaboradoresConectados = colaboradoresConectados;
	}

	public Integer getTabAtiva() {
		return tabAtiva;
	}

	public void setTabAtiva(Integer tabAtiva) {
		this.tabAtiva = tabAtiva;
	}

	public String getTela() {
		return tela;
	}

	public void setTela(String tela) {
		this.tela = tela;
	}

	public String getNomeTela() {
		return nomeTela;
	}

	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}

	public String getCurrentFaces() {
		return currentFaces;
	}

	public void setCurrentFaces(String currentFaces) {
		this.currentFaces = currentFaces;
	}

	public List<Menu> getMenusPrime() {
		return menusPrime;
	}

	public void setMenusPrime(List<Menu> menusPrime) {
		this.menusPrime = menusPrime;
	}

	public PermissaoGrupo getPermissaoGrupoSelecionada() {
		return permissaoGrupoSelecionada;
	}

	public void setPermissaoGrupoSelecionada(PermissaoGrupo permissaoGrupoSelecionada) {
		this.permissaoGrupoSelecionada = permissaoGrupoSelecionada;
	}

	public Long getOpcao() {
		return opcao;
	}

	public void setOpcao(Long opcao) {
		this.opcao = opcao;
	}

	public boolean isFlagBloqueado() {
		return flagBloqueado;
	}

	public void setFlagBloqueado(boolean flagBloqueado) {
		this.flagBloqueado = flagBloqueado;
	}

	public Colaborador getColaboradorSelecionado() {
		return colaboradorSelecionado;
	}

	public void setColaboradorSelecionado(Colaborador colaboradorSelecionado) {
		this.colaboradorSelecionado = colaboradorSelecionado;
	}

	public Objeto getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Objeto objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public Long getSituacaoProcesso() {
		return situacaoProcesso;
	}

	public void setSituacaoProcesso(Long situacaoProcesso) {
		this.situacaoProcesso = situacaoProcesso;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
	
	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public boolean isMostrarDialogAdvogado(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_AUDIENCIA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAdvogado2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogAdvogado3(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAdvogado4(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAdvogado5(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAdvogado6(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_AGENDA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAgenda(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCam(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCam2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_COLABORADOR.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCliente(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogCliente2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCliente3(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_FATURAMENTO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCliente4(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogCliente5(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCliente6(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_AGENDA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogColaborador(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogColaborador2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogColaborador3(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_AGENDA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogFaturamento(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_FATURAMENTO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogLotacao(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogMensagem(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogNumeroProcesso(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogNumeroProcesso2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogParteContraria(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogParteContraria2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogPermissao(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_GRUPO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcesso(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_AUDIENCIA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcesso2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_ANDAMENTO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcesso3(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcesso4(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_AGENDA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcessoCliente(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogProcessoCliente2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogProcessoParteContraria(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogProcessoParteContraria2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogDocumentoAndamentoProcesso(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_ANDAMENTO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoAudiencia(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_AUDIENCIA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoCliente(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoColaborador(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_COLABORADOR.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoProcesso(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogDocumentoProcesso2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogDocumentoTabAndamentoProcesso(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogDocumentoTabAndamentoProcesso2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogDocumentoTabAudiencia(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogDocumentoTabAudiencia2(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && (Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()));
	}
	
	public boolean isMostrarDialogViewFotoCliente(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogViewFotoColaborador(){
		return (!TSUtil.isEmpty(this.permissaoSelecionada)) && Constantes.PERMISSAO_COLABORADOR.equals(this.permissaoSelecionada.getId());
	}

}
