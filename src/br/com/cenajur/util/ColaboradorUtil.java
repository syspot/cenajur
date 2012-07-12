package br.com.cenajur.util;

import java.util.ArrayList;
import java.util.List;

import br.com.cenajur.model.ColaboradorModel;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

public class ColaboradorUtil {
	
	private final String OPERADOR_CONECTADO = "colaboradorConectado";
    private final String OPERADORES_CONECTADOS = "colaboradoresConectados";
    
    private static ColaboradorUtil factory;

    public static ColaboradorUtil getInstance() {
        if (factory == null) {
            factory = new ColaboradorUtil();
        }

        return factory;
    }

    public ColaboradorModel obterColaboradorConectado() {
        return (ColaboradorModel) TSFacesUtil.getObjectInSession(OPERADOR_CONECTADO);
    }

    @SuppressWarnings("unchecked")
	public List<ColaboradorModel> obterColaboradoresConectados() {
        return (List<ColaboradorModel>) TSFacesUtil.getRequest().getSession().getServletContext().getAttribute(OPERADORES_CONECTADOS);
    }

    @SuppressWarnings("unchecked")
	public void adicionar(ColaboradorModel colaborador) {
    	
        List<ColaboradorModel> colaboradoresConectados = (List<ColaboradorModel>) TSFacesUtil.getRequest().getSession().getServletContext().getAttribute("colaboradoresConectados");

        if (TSUtil.isEmpty(colaboradoresConectados)) {
        	colaboradoresConectados = new ArrayList<ColaboradorModel>();
        }

        TSFacesUtil.addObjectInSession(OPERADOR_CONECTADO, colaborador);

        if (!colaboradoresConectados.contains(colaborador)) {
        	colaboradoresConectados.add(colaborador);
        }

        TSFacesUtil.getRequest().getSession().getServletContext().setAttribute(OPERADORES_CONECTADOS, colaboradoresConectados);
    }

    @SuppressWarnings("unchecked")
	public void remover() {
    	
        List<ColaboradorModel> colaboradoresConectados = (List<ColaboradorModel>) TSFacesUtil.getRequest().getSession().getServletContext().getAttribute("operadoresConectados");

        if (!TSUtil.isEmpty(colaboradoresConectados)) {
        	colaboradoresConectados.remove(obterColaboradoresConectados());
        }

        TSFacesUtil.getRequest().getSession().getServletContext().setAttribute(OPERADORES_CONECTADOS, colaboradoresConectados);
    
    }

    public static ColaboradorModel autenticar(ColaboradorModel model) {
    	
    	ColaboradorModel colaboradorModel = model.autenticarPorLogin();

        if ((!TSUtil.isEmpty(colaboradorModel)) && (!colaboradorModel.getSenha().equals(Utilitarios.gerarHash(model.getSenha())))) {
        	colaboradorModel = null;
        }

        return colaboradorModel;
            
    }
}
