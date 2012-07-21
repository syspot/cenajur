package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Menu;
import br.com.cenajur.model.Permissao;
import br.com.cenajur.model.PermissaoGrupo;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
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

    
    public AutenticacaoFaces() {
    	
        clearFields();

        setTabAtiva(new Integer(1));

        setNomeTela("Área de Trabalho");
        
        currentFaces = "";
    }

    public String redirecionar() {
    	
        this.removeObjectInSession(this.currentFaces);
        setTela(this.permissaoSelecionada.getUrl());
        setNomeTela("Área de Trabalho > " + this.permissaoSelecionada.getMenu().getNome() + " > " + this.permissaoSelecionada.getDescricao());
        setTabAtiva(this.permissaoSelecionada.getTabAtiva());
        this.currentFaces = this.permissaoSelecionada.getFaces();
        
        this.obterPermissaoGrupoSelecionada();
        
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
        	
        	return "entrar";
        }
        
        CenajurUtil.addErrorMessage("Dados inválidos!");

        return null;
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

}
