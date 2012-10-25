package br.com.cenajur.faces;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.AnoModel;
import br.com.cenajur.model.Processo;

import com.ibm.icu.util.Calendar;

@ViewScoped
@ManagedBean(name = "relatorioCancelamentoPorMotivoFaces")
public class RelatorioCancelamentoPorMotivoFaces {

	private Long ano;
	private List<AnoModel> grid;
	private List<AnoModel> gridTotal;
	
	public RelatorioCancelamentoPorMotivoFaces() {
		this.ano = new Long(Calendar.getInstance().get(Calendar.YEAR));
		this.atualizar();
	}
	
	public String atualizar(){
		this.grid = new Processo().pesquisarCanceladosPorAno(this.ano);
		this.gridTotal = new Processo().pesquisarCanceladosPorAnoTotal(this.ano);
		return null;
	}
	
	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public List<AnoModel> getGrid() {
		return grid;
	}

	public void setGrid(List<AnoModel> grid) {
		this.grid = grid;
	}

	public List<AnoModel> getGridTotal() {
		return gridTotal;
	}

	public void setGridTotal(List<AnoModel> gridTotal) {
		this.gridTotal = gridTotal;
	}
	
}
