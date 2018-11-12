package br.com.corrida.controller;

import java.util.List;

import javax.swing.JOptionPane;

import br.com.corrida.model.TempoPiloto;
import br.com.corrida.paralelismo.GerenciadorTarefas;
import br.com.corrida.paralelismo.Tarefa;

public class Volta extends Tarefa {
	
	private static final long MILISECONDS = 1000;
	private static final long SECONDS = 60;
	
	private int volta;
	private List<TempoPiloto> pilotos;
	private Long tempoEsperaInicioVolta;
	
	public Volta(int volta, List<TempoPiloto> pilotos, Long tempoEsperaInicioVolta) {
		this.volta = volta;
		this.pilotos = pilotos;
		this.tempoEsperaInicioVolta = tempoEsperaInicioVolta;
	}
	
	@Override
	public void run() {
		try {
			Long minutosEspera = (tempoEsperaInicioVolta / (1000*60)) % 60;
			
			/* Tempo que demora para iniciar a volta! */
			Thread.sleep(minutosEspera * MILISECONDS * SECONDS);
			
			if (volta == 4) {
				/* Verifica se é a última volta, se for, assim que o tempo mais rápido passar, a corrida acaba!! */
				Long tempoUltimaVolta = (pilotos.get(0).getTempo() / (1000*60)) % 60;
				Thread.sleep(tempoUltimaVolta * MILISECONDS * SECONDS);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao executar a volta!");
		}
		GerenciadorTarefas.getInstance().remover(this);
	}

	@Override
	public void interromper() {}
}
