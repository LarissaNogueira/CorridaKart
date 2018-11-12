package br.com.corrida.paralelismo;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorTarefas {
	
	private static GerenciadorTarefas gerenciadorTarefas;
	private List<Tarefa> tarefas = new ArrayList<Tarefa>();
	
	private GerenciadorTarefas() {}
	
	public synchronized static GerenciadorTarefas getInstance() {
		if (gerenciadorTarefas == null) {
			gerenciadorTarefas = new GerenciadorTarefas();
		}
		return gerenciadorTarefas;
	}

	public boolean adicionar(Tarefa tarefa) {
		synchronized (tarefas) {
			return this.tarefas.add(tarefa);
		}
	}
	
	public boolean remover(Tarefa tarefa) {
		synchronized (tarefas) {
			return this.tarefas.remove(tarefa);
		}
	}
	
	public int pegarTamanho() {
		return tarefas.size();
	}
}
