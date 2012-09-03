package br.com.cenajur.jobs;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.topsys.exception.TSApplicationException;

@RequestScoped
@ManagedBean(name = "gerarFaturamentoJob")
public class GerarFaturamentoJob {

	@PostConstruct
	protected void init() throws TSApplicationException {
		
		
		
	}
	
}
