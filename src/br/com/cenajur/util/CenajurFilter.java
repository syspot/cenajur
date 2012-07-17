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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	    HttpServletRequest r = (HttpServletRequest) request;

	    String uri = r.getRequestURI();
	   
	    if (uri != null) {
	      uri = uri.substring(uri.lastIndexOf("/"), uri.length());
	    }
	    if (!TSUtil.isEmpty(r.getSession().getAttribute(Constantes.COLABORADOR_CONECTADO)) || uri.equals("/login.xhtml")) {
	      chain.doFilter(request, response);
	    } else {
	      r.getRequestDispatcher("/pages/login.xhtml").forward(request, response);
	    }

	  }


	
}
