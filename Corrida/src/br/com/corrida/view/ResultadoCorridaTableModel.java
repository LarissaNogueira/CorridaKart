package br.com.corrida.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.corrida.model.DadosPiloto;

public class ResultadoCorridaTableModel extends AbstractTableModel { 
	
	private static final long serialVersionUID = 1L;
	private String[] nomeColunas = {"Código Piloto", "Nome Piloto", "Voltas Completas", "Tempo Total"}; 
	private List<DadosPiloto> pilotos; 
  
	public ResultadoCorridaTableModel(List<DadosPiloto> pilotos) { 
		this.pilotos = pilotos;
	} 
	
	public int getColumnCount() {
		return nomeColunas.length; 
	} 
   
	public int getRowCount() {
		int tamanho;
		if (pilotos == null) { 
			tamanho = 0;
		} else {
			tamanho = pilotos.size();
		}
		
		return tamanho;
	} 
   
	public Object getValueAt(int row, int col) {
		Object temp = null;
		if (col == 0) {
			temp = pilotos.get(row).getNumeroPiloto();
		} else if (col == 1) {
			temp = pilotos.get(row).getNomePiloto();
		} else if (col == 2) {
			temp = pilotos.get(row).voltasCompletadas();
		} else if (col == 3) {
			temp = pilotos.get(row).tempoProvaFormatado();
		}
		
		return temp;
	}
	
	public String getColumnName(int col) {
		return nomeColunas[col];
	}
}