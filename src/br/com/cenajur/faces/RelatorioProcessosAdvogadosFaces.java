package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Model;
import br.com.cenajur.model.Processo;

@RequestScoped
@ManagedBean(name = "relatorioProcessosAdvogadosFaces")
public class RelatorioProcessosAdvogadosFaces{

	private List<Colaborador> advogados;
	private List<String> anos;
	private Processo processo;
	
	public RelatorioProcessosAdvogadosFaces() {
		this.processo = new Processo();
		this.gerarRelatorio();
	}
	
	public String gerarRelatorio() {

       this.advogados = new Colaborador().findAllAdvogadosComProcessos();
       
       List<Model> models = new Processo().pesquisarAnosProcesso();
       
       this.anos = new ArrayList<String>();
       
       for(Model model : models){
    	   this.anos.add(model.getAno());
       }
       
       return null;

    }

	public List<Colaborador> getAdvogados() {
		return advogados;
	}

	public void setAdvogados(List<Colaborador> advogados) {
		this.advogados = advogados;
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
