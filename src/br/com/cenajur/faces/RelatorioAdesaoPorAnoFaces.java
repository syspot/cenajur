package br.com.cenajur.faces;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.AnoModel;
import br.com.cenajur.model.Cliente;

import com.ibm.icu.util.Calendar;

@ViewScoped
@ManagedBean(name = "relatorioAdesaoPorAnoFaces")
public class RelatorioAdesaoPorAnoFaces {

	
	private Long ano;
	private List<AnoModel> grid1;
	private List<AnoModel> grid2;
	private List<AnoModel> grid3;
	
	public RelatorioAdesaoPorAnoFaces() {
		this.ano = new Long(Calendar.getInstance().get(Calendar.YEAR));
		this.atualizar();
	}
	
	public String atualizar(){
		setGrid1(new Cliente().pesquisarAdesaoPorAno(getAno()));
		setGrid2(new Cliente().pesquisarRematriculadosPorAno(getAno()));
		setGrid3(new Cliente().pesquisarCanceladosPorAno2(getAno()));
		return null;
	}
	
	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public List<AnoModel> getGrid1() {
		return grid1;
	}

	public void setGrid1(List<AnoModel> grid1) {
		this.grid1 = grid1;
	}

	public List<AnoModel> getGrid2() {
		return grid2;
	}

	public void setGrid2(List<AnoModel> grid2) {
		this.grid2 = grid2;
	}

	public List<AnoModel> getGrid3() {
		return grid3;
	}

	public void setGrid3(List<AnoModel> grid3) {
		this.grid3 = grid3;
	}
	
}
