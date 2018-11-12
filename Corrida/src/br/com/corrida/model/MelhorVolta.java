package br.com.corrida.model;

public class MelhorVolta {
	
	private Integer volta;
	private String nomePiloto;
	private Long tempo;
	
	public Integer getVolta() {
		return volta;
	}
	public void setVolta(Integer volta) {
		this.volta = volta;
	}
	public String getNomePiloto() {
		return nomePiloto;
	}
	public void setNomePiloto(String nomePiloto) {
		this.nomePiloto = nomePiloto;
	}
	public Long getTempo() {
		return tempo;
	}
	public void setTempo(Long tempo) {
		this.tempo = tempo;
	}
}
