package br.com.cenajur.faces;

import java.util.List;

import br.com.cenajur.model.AnoModel;

import com.ibm.icu.util.Calendar;

public abstract class RelatorioPorAnoFaces {

	private Long ano;
	private List<AnoModel> grid;
	private List<AnoModel> gridTotal;
	
	public RelatorioPorAnoFaces() {
		this.ano = new Long(Calendar.getInstance().get(Calendar.YEAR));
		this.atualizar();
	}
	
	public abstract String atualizar();
	
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
