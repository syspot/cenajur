package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean(name = "relatorioProcessoPorAdvogadoFaces")
public class RelatorioProcessoPorAdvogadoFaces extends RelatorioModeloFaces{

	@PostConstruct
	public void init(){
		super.setNomeRelatorio("relatProcessoPorAdvogado.jasper");
		super.setNomeRelatorioImpressao("relatorio_processo_advogado");
	}
	
}
