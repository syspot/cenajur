package br.com.cenajur.util;

import java.util.ArrayList;
import java.util.List;

import br.com.cenajur.model.Colaborador;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

public class ColaboradorUtil {
	
	private static final String OPERADOR_CONECTADO = "colaboradorConectado";
    private static final String OPERADORES_CONECTADOS = "colaboradoresConectados";
    
    private ColaboradorUtil(){
    	
    }
    
    public static Colaborador obterColaboradorConectado() {
        return (Colaborador) TSFacesUtil.getObjectInSession(OPERADOR_CONECTADO);
    }

    @SuppressWarnings("unchecked")
	public static List<Colaborador> obterColaboradoresConectados() {
        return (List<Colaborador>) TSFacesUtil.getRequest().getSession().getServletContext().getAttribute(OPERADORES_CONECTADOS);
    }

    @SuppressWarnings("unchecked")
	public static void adicionar(Colaborador colaborador) {
    	
        List<Colaborador> colaboradoresConectados = (List<Colaborador>) TSFacesUtil.getRequest().getSession().getServletContext().getAttribute("colaboradoresConectados");

        if (TSUtil.isEmpty(colaboradoresConectados)) {
        	colaboradoresConectados = new ArrayList<Colaborador>();
        }

        TSFacesUtil.addObjectInSession(OPERADOR_CONECTADO, colaborador);

        if (!colaboradoresConectados.contains(colaborador)) {
        	colaboradoresConectados.add(colaborador);
        }

        TSFacesUtil.getRequest().getSession().getServletContext().setAttribute(OPERADORES_CONECTADOS, colaboradoresConectados);
    }

    @SuppressWarnings("unchecked")
	public static void remover() {
    	
        List<Colaborador> colaboradoresConectados = (List<Colaborador>) TSFacesUtil.getRequest().getSession().getServletContext().getAttribute("operadoresConectados");

        if (!TSUtil.isEmpty(colaboradoresConectados)) {
        	colaboradoresConectados.remove(obterColaboradoresConectados());
        }

        TSFacesUtil.getRequest().getSession().getServletContext().setAttribute(OPERADORES_CONECTADOS, colaboradoresConectados);
    
    }

    public static Colaborador autenticar(Colaborador model) {
    	
    	Colaborador colaboradorModel = model.autenticarPorLogin();

        if ((!TSUtil.isEmpty(colaboradorModel)) && (!colaboradorModel.getSenha().equals(Utilitarios.gerarHash(model.getSenha())))) {
        	colaboradorModel = null;
        }

        return colaboradorModel;
            
    }
    
}
