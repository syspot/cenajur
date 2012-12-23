package br.com.cenajur.faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.ContadorSms;

@ViewScoped
@ManagedBean(name = "relatorioSMSFaces")
public class RelatorioSMSFaces extends RelatorioContadorFaces{

	public void executarPesquisa(){
		setGrid(new ContadorSms().pesquisarPorPeriodo(getDataInicio(), getDataFim(), getTipoInformacao()));
	}
	
}
