package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean(name = "relatorioProcessoPorTipoFaces")
public class RelatorioProcessoPorTipoFaces extends RelatorioModeloFaces{

	@PostConstruct
	public void init(){
		super.setNomeRelatorio("relatProcessoPorTipo.jasper");
		super.setNomeRelatorioImpressao("relatorio_processo_tipo");
	}
}
