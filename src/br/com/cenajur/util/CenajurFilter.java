package br.com.cenajur.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.topsys.util.TSUtil;

/**
 * Servlet Filter implementation class Cenajur
 */
@WebFilter("/Cenajur")
public class CenajurFilter implements Filter {

	@Override
	public void destroy() {
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)arg0;

	    HttpServletResponse response = (HttpServletResponse)arg1;

	    String pagina = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);

	    String pasta = request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/"));

	    pasta = pasta.substring(pasta.lastIndexOf("/") + 1, pasta.length());

	    int indiceFimUri = pagina.lastIndexOf("?") == -1 ? pagina.length() : pagina.lastIndexOf("?");

	    pagina = pagina.substring(pagina.lastIndexOf("/") + 1, indiceFimUri);

	    if((pasta.equals("pages")) && (!pagina.contains("login.xhtml"))) {
	      
	    	if (TSUtil.isEmpty(request.getSession().getAttribute("colaboradorConectado"))) {
	    		response.sendRedirect(request.getContextPath() + "/pages/login.xhtml");
	    	}
	    	
	    } else if ((pagina.contains("login.xhtml")) && (!TSUtil.isEmpty(request.getSession().getAttribute("colaboradorConectado")))) {
	    	
	    	request.getSession().removeAttribute("autenticacaoFaces");

	    	request.getSession().removeAttribute("colaboradorConectado");
	    	
	    }

	    if (!response.isCommitted()) {
	    	chain.doFilter(request, response);
	    }
	    
	}
	
}
