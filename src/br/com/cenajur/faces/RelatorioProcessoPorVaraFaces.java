package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean(name = "relatorioProcessoPorVaraFaces")
public class RelatorioProcessoPorVaraFaces extends RelatorioModeloFaces{

	@PostConstruct
	public void init(){
		super.setNomeRelatorio("relatProcessoPorVara.jasper");
		super.setNomeRelatorioImpressao("relatorio_processo_vara");
	}
	
}
