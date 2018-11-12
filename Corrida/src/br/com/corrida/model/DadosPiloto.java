package br.com.corrida.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DadosPiloto {
	
	private Integer numeroPiloto;
	private String nomePiloto;
	private Map<Integer, String> horariosPorVolta = new HashMap<Integer, String>();
	private Map<Integer, Long> temposPorVolta = new HashMap<Integer, Long>();
	private Map<Integer, Double> velocidadesMediasPorVolta = new HashMap<Integer, Double>();
	private Long tempoFinalPiloto = 0L;
	private Integer posicao;
	private SimpleDateFormat format = new SimpleDateFormat("mm:ss.SSS");
	
	public Integer getNumeroPiloto() {
		return numeroPiloto;
	}
	public void setNumeroPiloto(Integer numeroPiloto) {
		this.numeroPiloto = numeroPiloto;
	}
	public String getNomePiloto() {
		return nomePiloto;
	}
	public void setNomePiloto(String nomePiloto) {
		this.nomePiloto = nomePiloto;
	}
	public Map<Integer, String> getHorariosPorVolta() {
		return horariosPorVolta;
	}
	public void setHorariosPorVolta(Map<Integer, String> horariosPorVolta) {
		this.horariosPorVolta = horariosPorVolta;
	}
	public Map<Integer, Long> getTemposPorVolta() {
		return temposPorVolta;
	}
	public void setTemposPorVolta(Map<Integer, Long> temposPorVolta) {
		this.temposPorVolta = temposPorVolta;
	}
	public Map<Integer, Double> getVelocidadesMediasPorVolta() {
		return velocidadesMediasPorVolta;
	}
	public void setVelocidadesMediasPorVolta(Map<Integer, Double> velocidadesMediasPorVolta) {
		this.velocidadesMediasPorVolta = velocidadesMediasPorVolta;
	}
	public Integer getPosicao() {
		return posicao;
	}
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	
	public Long calcularTempoProva() throws ParseException {
		for (Entry<Integer, Long> tempoVolta : this.temposPorVolta.entrySet()) {
			this.tempoFinalPiloto += tempoVolta.getValue();
		}
		return this.tempoFinalPiloto;
	}
	
	public Long getTempoFinalPiloto() {
		return this.tempoFinalPiloto;
	}
	
	public String tempoProvaFormatado() {
		return format.format(new Date(this.tempoFinalPiloto));
	}
	
	public Integer voltasCompletadas() {
		return this.horariosPorVolta.keySet().size();
	}
	
	public MelhorVolta melhorVolta() throws ParseException {
		MelhorVolta melhorVolta = new MelhorVolta();
		Set<Integer> voltas = temposPorVolta.keySet();
		Integer voltaMaisRapida = 0;
		long tempo = 0;
		for (Integer volta : voltas) {
			long tempoEmMilisegundos = temposPorVolta.get(volta);
			if (tempo == 0) {
				tempo = tempoEmMilisegundos;
				voltaMaisRapida = volta;
			} else if (tempo > tempoEmMilisegundos) {
				tempo = tempoEmMilisegundos;
				voltaMaisRapida = volta;
			}
		}
		melhorVolta.setNomePiloto(this.nomePiloto);
		melhorVolta.setTempo(tempo);
		melhorVolta.setVolta(voltaMaisRapida);
		
		return melhorVolta;
	}
	
	public VelocidadeMedia velocidadeMediaPiloto() {
		VelocidadeMedia velocidadeMedia = new VelocidadeMedia();
		int voltasPorPiloto = velocidadesMediasPorVolta.keySet().size();
		Double velocidadeTotal = 0.0;
		for (Entry<Integer, Double> velocidadePorVolta : this.velocidadesMediasPorVolta.entrySet()) {
			velocidadeTotal += velocidadePorVolta.getValue();
		}
		velocidadeMedia.setNomePiloto(this.nomePiloto);
		velocidadeMedia.setVelocidadeMedia(velocidadeTotal / voltasPorPiloto);
		
		return velocidadeMedia;
	}
	
	public Long tempoPorVolta(Integer volta) {
		return temposPorVolta.get(volta);
	}
	
}
