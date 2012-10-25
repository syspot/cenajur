package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.model.Model;
import br.com.cenajur.model.Objeto;
import br.com.cenajur.model.Processo;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;

@RequestScoped
@ManagedBean(name = "relatorioProcessosColetivosFaces")
public class RelatorioProcessosColetivosFaces{

	private List<Objeto> objetos;
	private List<String> anos;
	private Processo processo;
	
	public RelatorioProcessosColetivosFaces() {
		this.processo = new Processo();
		this.gerarRelatorio();
	}
	
	public String gerarRelatorio() {

       this.objetos = new Objeto().findAll();
       
       List<Model> models = new Processo().pesquisarAnosProcesso();
       
       this.anos = new ArrayList<String>();
       
       for(Model model : models){
    	   this.anos.add(model.getAno());
       }
       
       return null;

    }
	
	public String getDataAtual(){
		return TSParseUtil.dateToString(new Date(), TSDateUtil.DD_MM_YYYY_HH_MM_SS);
	}
	
	public List<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<Objeto> objetos) {
		this.objetos = objetos;
	}

	public List<String> getAnos() {
		return anos;
	}

	public void setAnos(List<String> anos) {
		this.anos = anos;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

}
