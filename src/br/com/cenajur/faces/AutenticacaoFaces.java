package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.AgendaColaborador;
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
    private Long situacaoProcesso;
    private String ano;

    
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
    	
    	boolean valida = false;
    	
    	this.permissaoSelecionada = new Permissao(Constantes.PERMISSAO_COLABORADOR).getById();
    	
    	rotuloLoop:
    	for(PermissaoGrupo permissaoGrupo : permissaoSelecionada.getPermissoesGrupos()){
			
    		for(PermissaoGrupo permissaoGrupo2 : colaborador.getGrupo().getPermissoesGrupos()){
    			
    			if(permissaoGrupo.getId().equals(permissaoGrupo2.getId())){
    				valida = true;
    				break rotuloLoop;
    			}
    			
    		}
			
		}
    	
    	Colaborador colaborador = null;
    	
    	if(!TSUtil.isEmpty(colaboradorSelecionado)){
    		colaborador = new Colaborador(colaboradorSelecionado.getId());
    	}
    	
    	if(valida){
    		redirecionar();
    	}
    	
    	this.colaboradorSelecionado = colaborador;
    	
    	return "sucesso";
    }
    
    public String redirecionarParaObjeto() {
    	
    	boolean valida = false;
    	
    	this.permissaoSelecionada = new Permissao(Constantes.PERMISSAO_OBJETO).getById();
    	
    	rotuloLoop:
    	for(PermissaoGrupo permissaoGrupo : permissaoSelecionada.getPermissoesGrupos()){
    		
    		for(PermissaoGrupo permissaoGrupo2 : colaborador.getGrupo().getPermissoesGrupos()){
    			
    			if(permissaoGrupo.getId().equals(permissaoGrupo2.getId())){
    				valida = true;
    				break rotuloLoop;
    			}
    			
    		}
    		
    	}
    	
    	Objeto objeto = null;
    	
    	if(!TSUtil.isEmpty(objetoSelecionado)){
    		objeto = new Objeto(objetoSelecionado.getId());
    	}
    	
    	if(valida){
    		redirecionar();
    	}
    	
    	this.objetoSelecionado = objeto;
    	
    	return "sucesso";
    }
    
    public String redirecionarParaProcesso() {
    	
    	boolean valida = false;
    	
    	this.permissaoSelecionada = new Permissao(Constantes.PERMISSAO_PROCESSO).getById();
    	
    	rotuloLoop:
		for(PermissaoGrupo permissaoGrupo : permissaoSelecionada.getPermissoesGrupos()){
			
			for(PermissaoGrupo permissaoGrupo2 : colaborador.getGrupo().getPermissoesGrupos()){
				
				if(permissaoGrupo.getId().equals(permissaoGrupo2.getId())){
					valida = true;
					break rotuloLoop;
				}
				
			}
			
		}
    	
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
    	
    	if(valida){
    		redirecionar();
    	}
    	
    	this.situacaoProcesso = situacaoProcessoId;
    	this.ano = ano;
    	this.colaboradorSelecionado = colaborador;
    	this.objetoSelecionado = objeto;
    	
    	return "sucesso";
    }
    
    public void obterPermissaoGrupoSelecionada(){
    	
        this.permissaoGrupoSelecionada.setGrupo(this.colaborador.getGrupo());
        this.permissaoGrupoSelecionada.setPermissao(this.permissaoSelecionada);
        
        int posicao = this.colaborador.getGrupo().getPermissoesGrupos().indexOf(permissaoGrupoSelecionada);
        
        this.permissaoGrupoSelecionada = this.colaborador.getGrupo().getPermissoesGrupos().get(posicao);
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
    		CenajurUtil.addErrorMessage("Usuário não localizado");
    	} else{
    		
    		try {
    			
    			String novaSenha = "" + CenajurUtil.gerarNumeroAleatorio();
    			
    			colaborador.setSenha(Utilitarios.gerarHash(novaSenha));
    		
				colaborador.update();
	    		
	    		String texto = "Prezado(a), sua nova senha para acessar o sistema do Cenajur é: " + novaSenha;
	    		
	    		new EmailUtil().sendMail(colaborador.getEmail(), "Recuperação de Senha", texto, null);
	    		
	    		CenajurUtil.addInfoMessage("Uma nova senha foi enviada para seu e-mail");
	    		
			} catch (Exception e) { 
				e.printStackTrace();
				CenajurUtil.addInfoMessage("Ocorreu um erro ao tentar enviar a senha para o email cadastrado");
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
	
	public boolean isMostrarDialogAdvogado(){
		return Constantes.PERMISSAO_AUDIENCIA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAdvogado2(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAdvogado3(){
		return Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAdvogado4(){
		return Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAdvogado5(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAdvogado6(){
		return Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogAgenda(){
		return Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCam(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCliente(){
		return Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCliente2(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCliente3(){
		return Constantes.PERMISSAO_FATURAMENTO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCliente4(){
		return Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCliente5(){
		return Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogCliente6(){
		return Constantes.PERMISSAO_AGENDA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogColaborador(){
		return Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogColaborador2(){
		return Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogColaborador3(){
		return Constantes.PERMISSAO_AGENDA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogFaturamento(){
		return Constantes.PERMISSAO_FATURAMENTO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogLotacao(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogMensagem(){
		return Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogNumeroProcesso(){
		return Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogNumeroProcesso2(){
		return Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogParteContraria(){
		return Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogParteContraria2(){
		return Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogPermissao(){
		return Constantes.PERMISSAO_GRUPO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcesso(){
		return Constantes.PERMISSAO_AUDIENCIA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcesso2(){
		return Constantes.PERMISSAO_ANDAMENTO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcesso3(){
		return Constantes.PERMISSAO_MENSAGENS.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcesso4(){
		return Constantes.PERMISSAO_AGENDA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcessoCliente(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcessoCliente2(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcessoParteContraria(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogProcessoParteContraria2(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId()) || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoAndamentoProcesso(){
		return Constantes.PERMISSAO_ANDAMENTO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoAudiencia(){
		return Constantes.PERMISSAO_AUDIENCIA.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoCliente(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoColaborador(){
		return Constantes.PERMISSAO_COLABORADOR.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoProcesso(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoProcesso2(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoTabAndamentoProcesso(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoTabAndamentoProcesso2(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoTabAudiencia(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogDocumentoTabAudiencia2(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId())  || Constantes.PERMISSAO_PROCESSO.equals(this.permissaoSelecionada.getId());
	}
	
	public boolean isMostrarDialogViewFotoCliente(){
		return Constantes.PERMISSAO_CLIENTE.equals(this.permissaoSelecionada.getId());
	}

}
