package br.com.corrida.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.corrida.model.TempoPiloto;

public class CorridaTableModel extends AbstractTableModel { 
	
	private static final long serialVersionUID = 1L;
	private String[] nomeColunas = {"Piloto", "Tempo"}; 
	private List<TempoPiloto> temposPilotos; 
	private SimpleDateFormat format = new SimpleDateFormat("mm:ss.SSS");
  
	public CorridaTableModel(List<TempoPiloto> temposPilotos) { 
		this.temposPilotos = temposPilotos;
	} 
	
	public int getColumnCount() {
		return nomeColunas.length; 
	} 
   
	public int getRowCount() {
		int tamanho;
		if (temposPilotos == null) { 
			tamanho = 0;
		} else {
			tamanho = temposPilotos.size();
		}
		
		return tamanho;
	} 
   
	public Object getValueAt(int row, int col) {
		Object temp = null;
		if (col == 0) {
			temp = temposPilotos.get(row).getNomePiloto();
		} else if (col == 1) {
			temp = format.format(new Date(temposPilotos.get(row).getTempo()));
		} 
		
		return temp;
	}
	
	public String getColumnName(int col) {
		return nomeColunas[col];
	}
}