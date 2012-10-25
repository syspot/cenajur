package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean(name = "relatorioProcessoFaces")
public class RelatorioProcessoFaces extends RelatorioModeloFaces{

	@PostConstruct
	public void init(){
		super.setNomeRelatorio("relatProcesso.jasper");
		super.setNomeRelatorioImpressao("relatorio_processo");
	}
	
}
