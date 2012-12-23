package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean(name = "relatorioProcessoPorObjetoFaces")
public class RelatorioProcessoPorObjetoFaces extends RelatorioModeloFaces{

	@PostConstruct
	public void init(){
		super.setNomeRelatorio("relatProcessoPorObjeto.jasper");
		super.setNomeRelatorioImpressao("relatorio_processo_objeto");
	}
		
}
