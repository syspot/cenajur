package br.com.cenajur.faces;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.ColaboradorModel;
import br.com.cenajur.model.MenuModel;
import br.com.cenajur.model.PermissaoModel;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@SessionScoped
@ManagedBean(name = "autenticacaoFaces")
public class AutenticacaoFaces extends TSMainFaces{

    private ColaboradorModel colaboradorModel;
    private PermissaoModel permissaoSelecionada;
    private List<MenuModel> menus;
    private List<ColaboradorModel> colaboradoresConectados;
    private Integer tabAtiva;
    private String tela;
    private String nomeTela;
    private String currentFaces;

    
    public AutenticacaoFaces() {
    	
        clearFields();

        setTabAtiva(new Integer(-1));

        setNomeTela("Área de Trabalho");
        
        currentFaces = "";
    }

    public String redirecionar() {
        this.removeObjectInSession(this.currentFaces);
        setTela(this.permissaoSelecionada.getUrl());
        setNomeTela("Área de Trabalho > " + this.permissaoSelecionada.getMenuModel().getNome() + " > " + this.permissaoSelecionada.getDescricao());
        //setTabAtiva(Integer.valueOf(this.menusPrime.indexOf(this.permissaoPrimeModel.getMenuModel())));
        this.currentFaces = this.permissaoSelecionada.getFaces();
        return "sucesso";
    }

    public String logout() {
        TSFacesUtil.getRequest().getSession().invalidate();
        return "login";
    }

    protected void clearFields() {
    	
        this.colaboradorModel = new ColaboradorModel();

        this.menus = Collections.emptyList();

    }

    public String limpar() {
    	
    	ColaboradorUtil.getInstance().remover();

        TSFacesUtil.getRequest().getSession().getServletContext().setAttribute("colaboradoresConectados", colaboradoresConectados);

        clearFields();

        TSFacesUtil.removeObjectInSession("colaboradorConectado");

        TSFacesUtil.getRequest().getSession().invalidate();

        return "sair";
    }

    public String entrar() {
    	
        if (!validaCampos()) {
            return null;
        }

        colaboradorModel = ColaboradorUtil.autenticar(colaboradorModel);
        
        if (!TSUtil.isEmpty(colaboradorModel)) {
        	
        	this.menus = new MenuModel().findAll();

        	ColaboradorUtil.getInstance().adicionar(colaboradorModel);
        	
        	return "entrar";
        }

        super.addErrorMessage("Dados inválidos!");

        return null;
    }

    private boolean validaCampos() {
    	
        boolean retorno = true;

        if (TSUtil.isEmpty(this.colaboradorModel.getLogin())) {
            TSFacesUtil.addErrorMessage("usuario", " campo obrigatório");

            retorno = false;
        }

        if (TSUtil.isEmpty(this.colaboradorModel.getSenha())) {
            TSFacesUtil.addErrorMessage("senha", " campo obrigatório");

            retorno = false;
        }

        return retorno;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void obterColaboradoresConectados() {
    	
        this.colaboradoresConectados = ColaboradorUtil.getInstance().obterColaboradoresConectados();

        if (!TSUtil.isEmpty(this.colaboradoresConectados)) {
        	
            Comparator ordem = new Comparator() {

                public int compare(Object o1, Object o2) {
                	
                    ColaboradorModel n1 = (ColaboradorModel) o1;

                    ColaboradorModel n2 = (ColaboradorModel) o2;

                    int primeiraComparacao = n1.getNome().compareTo(n2.getNome());

                    return primeiraComparacao != 0 ? primeiraComparacao : n1.getNome().compareTo(n2.getNome());
                }
            };
            
            Collections.sort(this.colaboradoresConectados, ordem);
        }
    }

	public ColaboradorModel getColaboradorModel() {
		return colaboradorModel;
	}

	public void setColaboradorModel(ColaboradorModel colaboradorModel) {
		this.colaboradorModel = colaboradorModel;
	}

	public PermissaoModel getPermissaoSelecionada() {
		return permissaoSelecionada;
	}

	public void setPermissaoSelecionada(PermissaoModel permissaoSelecionada) {
		this.permissaoSelecionada = permissaoSelecionada;
	}

	public List<MenuModel> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuModel> menus) {
		this.menus = menus;
	}

	public List<ColaboradorModel> getColaboradoresConectados() {
		return colaboradoresConectados;
	}

	public void setColaboradoresConectados(
			List<ColaboradorModel> colaboradoresConectados) {
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

}
