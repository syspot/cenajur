package br.com.cenajur.faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.ContadorEmail;

@ViewScoped
@ManagedBean(name = "relatorioEmailFaces")
public class RelatorioEmailFaces extends RelatorioContadorFaces{

	public void executarPesquisa(){
		setGrid(new ContadorEmail().pesquisarPorPeriodo(getDataInicio(), getDataFim(), getTipoInformacao()));
	}
	
}
