package br.com.corrida.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.corrida.model.VelocidadeMedia;

public class VelocidadeTableModel extends AbstractTableModel { 
	
	private static final long serialVersionUID = 1L;
	private String[] nomeColunas = {"Piloto", "Velocidade Média"}; 
	private List<VelocidadeMedia> velocidadesMedia; 
  
	public VelocidadeTableModel(List<VelocidadeMedia> velocidadesMedia) { 
		this.velocidadesMedia = velocidadesMedia;
	} 
	
	public int getColumnCount() {
		return nomeColunas.length; 
	} 
   
	public int getRowCount() {
		int tamanho;
		if (velocidadesMedia == null) { 
			tamanho = 0;
		} else {
			tamanho = velocidadesMedia.size();
		}
		
		return tamanho;
	} 
   
	public Object getValueAt(int row, int col) {
		Object temp = null;
		if (col == 0) {
			temp = velocidadesMedia.get(row).getNomePiloto();
		} else if (col == 1) {
			temp = String.format("%.2f", velocidadesMedia.get(row).getVelocidadeMedia());
		} 
		
		return temp;
	}
	
	public String getColumnName(int col) {
		return nomeColunas[col];
	}
}