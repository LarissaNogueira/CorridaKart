package br.com.corrida.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.corrida.builder.CorridaBuilder;
import br.com.corrida.exception.ArquivoEntradaException;
import br.com.corrida.model.DadosPiloto;
import br.com.corrida.model.TempoPiloto;
import br.com.corrida.model.VelocidadeMedia;
import br.com.corrida.paralelismo.GerenciadorTarefas;
import br.com.corrida.util.MensagemUtil;
import br.com.corrida.view.CorridaTableModel;
import br.com.corrida.view.ResultadoCorridaTableModel;
import br.com.corrida.view.VelocidadeTableModel;

/*
 * Classe que mostra todos os dados da corrida
 * */
public class CorridaController implements ActionListener {
	
	private static final Integer QUANTIDADE_VOLTAS = 4;
	
	private JButton btnIniciar;
	private JLabel resultados;
	private JScrollPane primeiraVolta;
	private JScrollPane segundaVolta;
	private JScrollPane terceiraVolta;
	private JScrollPane quartaVolta;
	private CorridaTableModel corridaTableModel;
	private JTable tabelaResultadosVolta;
	private JScrollPane resultadoFinal;
	private ResultadoCorridaTableModel resultadoCorridaTableModel;
	private JTable tabelaResultadosCorrida;
	private JScrollPane velocidadeMediaPorPiloto;
	private List<VelocidadeMedia> velocidadesMedia;
	private VelocidadeTableModel velocidadeTableModel;
	private JTable tabelaVelocidadesCorrida;
	
	
	public CorridaController(JButton btnIniciar, JLabel resultados, JScrollPane primeiraVolta, JScrollPane segundaVolta, JScrollPane terceiraVolta,
			JScrollPane quartaVolta, JScrollPane resultadoFinal, JScrollPane velocidadeMediaPorPiloto) {
		this.btnIniciar = btnIniciar;
		this.resultados = resultados;
		this.primeiraVolta = primeiraVolta;
		this.segundaVolta = segundaVolta;
		this.terceiraVolta = terceiraVolta;
		this.quartaVolta = quartaVolta;
		this.resultadoFinal = resultadoFinal;
		this.velocidadeMediaPorPiloto = velocidadeMediaPorPiloto;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		GerenciadorTarefas gerenciadorTarefas = GerenciadorTarefas.getInstance();
		btnIniciar.setVisible(false);
		MensagemUtil.mensagem("CORRIDA INICIOU... :)", 2000);
		
		try {
			File arquivoInformacoesCorredores = new File("arquivos/arquivoEntrada.txt");
			/* Tempo para controlar os inicios de cada volta */
			Long tempoEsperaInicioVolta = 0L;
			
			HashMap<Integer, DadosPiloto> dadosPorCodigoPiloto = CorridaBuilder.criar(arquivoInformacoesCorredores);
			Collection<DadosPiloto> dadosPilotos = dadosPorCodigoPiloto.values();
			
			int numeroVolta = 1;
			/* Aqui estão as voltas da corrida */
			while (numeroVolta <= QUANTIDADE_VOLTAS) {
				List<TempoPiloto> temposPilotosPorVolta = new ArrayList<TempoPiloto>();
				for (DadosPiloto dadosPiloto : dadosPilotos) {
					TempoPiloto tempoPiloto = new TempoPiloto();
					tempoPiloto.setNomePiloto(dadosPiloto.getNomePiloto());
					tempoPiloto.setTempo(dadosPiloto.tempoPorVolta(numeroVolta));
					if (tempoPiloto.getTempo() != null) {
						temposPilotosPorVolta.add(tempoPiloto);
					}
				}
				
				/* Lista ordenada dos tempos */
				temposPilotosPorVolta.sort((a, b) -> a.getTempo().compareTo(b.getTempo()));
				
				/* Valores que serão mostrados na tabela conforme a volta da corrida */
				corridaTableModel = new CorridaTableModel(temposPilotosPorVolta);
				tabelaResultadosVolta = new JTable(corridaTableModel);
				
				/* Adiciona os tempos ordenados em uma tabela conforme a volta da corrida*/
				if (numeroVolta == 1) {
					primeiraVolta.getViewport().add(tabelaResultadosVolta);
					primeiraVolta.setBounds(125, 100, 150, 140);
					primeiraVolta.setVisible(false);
				} else if (numeroVolta == 2) {
					segundaVolta.getViewport().add(tabelaResultadosVolta);
					segundaVolta.setBounds(325, 100, 150, 140);
					segundaVolta.setVisible(false);
				} else if (numeroVolta == 3) {
					terceiraVolta.getViewport().add(tabelaResultadosVolta);
					terceiraVolta.setBounds(525, 100, 150, 140);
					terceiraVolta.setVisible(false);
				} else {
					quartaVolta.getViewport().add(tabelaResultadosVolta);
					quartaVolta.setBounds(725, 100, 150, 140);
					quartaVolta.setVisible(false);
				}
				
				/* Threads que executarão as voltas da corrida */
				Volta volta = new Volta(numeroVolta, temposPilotosPorVolta, tempoEsperaInicioVolta);
				/* Adiciona a thread em um gerenciador para que controle quando a corrida inicia e termina */
				gerenciadorTarefas.adicionar(volta);
				volta.start();
				
				tempoEsperaInicioVolta += temposPilotosPorVolta.get(0).getTempo();
				numeroVolta++;
			}
			
			/* Fica esperando todas as voltas terminarem, assim que o primeiro tempo da última volta acontecer, a corrida acaba. */
			while (gerenciadorTarefas.pegarTamanho() != 0) {
				MensagemUtil.mensagem("QUEM SERÁ O VENCEDOR? :)", 10000);
				for (DadosPiloto dadosPiloto : dadosPilotos) {
					MensagemUtil.mensagem(dadosPiloto.getNomePiloto() + "?", 10000);
				}
			}
			
			MensagemUtil.mensagem("CORRIDA ACABOU!!!", 5000);
			resultados(dadosPilotos);
		} catch (ArquivoEntradaException ex) {
			JOptionPane.showMessageDialog(null, "Erro ao ler os dados dos corredores!");
		} catch (ParseException e1) {
			JOptionPane.showMessageDialog(null, "Erro ao formatar!");
		}
	}

	/*
	 * Método que mostrará todos os resultados da corrida
	 * */
	private void resultados(Collection<DadosPiloto> dadosPilotos) throws ParseException {
		resultados.setBounds(400, 30, 300, 45);
		
		primeiraVolta.setVisible(true);
		segundaVolta.setVisible(true);
		terceiraVolta.setVisible(true);
		quartaVolta.setVisible(true);
		
		List<DadosPiloto> dadosAtualizadosPilotos = resultadosFinais(dadosPilotos);
		resultadoCorridaTableModel = new ResultadoCorridaTableModel(dadosAtualizadosPilotos);
		tabelaResultadosCorrida = new JTable(resultadoCorridaTableModel);
		resultadoFinal.getViewport().add(tabelaResultadosCorrida);
		resultadoFinal.setBounds(125, 310, 550, 140);
		
		velocidadeTableModel = new VelocidadeTableModel(velocidadesMedia);
		tabelaVelocidadesCorrida = new JTable(velocidadeTableModel);
		velocidadeMediaPorPiloto.getViewport().add(tabelaVelocidadesCorrida);
		velocidadeMediaPorPiloto.setBounds(725, 310, 150, 140);
	}

	/*
	 * Método para atualizar os dados dos pilotos para aparecer todos os resultados da corrida
	 * */
	private List<DadosPiloto> resultadosFinais(Collection<DadosPiloto> dadosPilotos) throws ParseException {
		List<DadosPiloto> dadosAtualizadosPilotos = new ArrayList<DadosPiloto>();
		for (DadosPiloto dadosPiloto : new ArrayList<DadosPiloto>(dadosPilotos)) {
			dadosPiloto.calcularTempoProva();
			if (velocidadesMedia == null) {
				velocidadesMedia = new ArrayList<VelocidadeMedia>();
			}
			velocidadesMedia.add(dadosPiloto.velocidadeMediaPiloto());
			dadosAtualizadosPilotos.add(dadosPiloto);
		}
		
		dadosAtualizadosPilotos.sort((a, b) -> a.getTempoFinalPiloto().compareTo(b.getTempoFinalPiloto()));
		return dadosAtualizadosPilotos;
	}
}
