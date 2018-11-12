package br.com.corrida.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import br.com.corrida.controller.CorridaController;

public class Corrida extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel painel;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Corrida corrida = new Corrida();
					corrida.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao iniciar a corrida!");
				}
			}
		});
	}
	
	public Corrida() {
		setTitle("BEM-VINDOS À FÓRMULA 1!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 1005, 578);
		setResizable(false);
		
		painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(painel);
		painel.setLayout(null);
		JLabel fundo = new JLabel();
		fundo.setBounds(0, 0, 1000, 550);
		fundo.setIcon(new ImageIcon("imagens/fundo.jpg"));
		
		JLabel fundoBotaoIniciar = new JLabel();
		fundoBotaoIniciar.setIcon(new ImageIcon("imagens/botao-iniciar.png"));
		
		JButton btnIniciar = new JButton();
		btnIniciar.setBounds(20, 30, 125, 45);
		btnIniciar.setBackground(Color.white);
		btnIniciar.add(fundoBotaoIniciar);
		
		JLabel labelResultados = new JLabel();
		labelResultados.setText("Resultados");
		labelResultados.setFont(new Font("Serif", Font.BOLD, 30));
		labelResultados.setForeground(Color.white);
		
		painel.add(labelResultados);
		
		/*
		 * JScrollPane para mostrar os resultados de cada piloto da primeira volta
		 * */
		JScrollPane primeiraVoltaScrollPane = new JScrollPane();
		primeiraVoltaScrollPane.setBorder(BorderFactory.createTitledBorder ("PRIMEIRA VOLTA"));
		
		painel.add(primeiraVoltaScrollPane);
		
		/*
		 * JScrollPane para mostrar os resultados de cada piloto da segunda volta
		 * */
		JScrollPane segundaVoltaScrollPane = new JScrollPane();
		segundaVoltaScrollPane.setBorder(BorderFactory.createTitledBorder("SEGUNDA VOLTA"));
		
		painel.add(segundaVoltaScrollPane);
		
		/*
		 * JScrollPane para mostrar os resultados de cada piloto da terceira volta
		 * */
		JScrollPane terceiraVoltaScrollPane = new JScrollPane();
		terceiraVoltaScrollPane.setBorder(BorderFactory.createTitledBorder("TERCEIRA VOLTA"));
		
		painel.add(terceiraVoltaScrollPane);
		
		/*
		 * JScrollPane para mostrar os resultados de cada piloto da última volta
		 * */
		JScrollPane quartaVoltaScrollPane = new JScrollPane();
		quartaVoltaScrollPane.setBorder(BorderFactory.createTitledBorder("QUARTA VOLTA"));
		
		painel.add(quartaVoltaScrollPane);
		
		/*
		 * JScrollPane para mostrar os resultados finais da corrida
		 * */
		JScrollPane resultadoFinal = new JScrollPane();
		resultadoFinal.setBorder(BorderFactory.createTitledBorder("RESULTADO FINAL"));
		
		painel.add(resultadoFinal);
		
		/*
		 * JScrollPane para mostrar a velocidade média de cada piloto
		 * */
		JScrollPane velocidadeMediaPorPiloto = new JScrollPane();
		velocidadeMediaPorPiloto.setBorder(BorderFactory.createTitledBorder("VELOCIDADES MÉDIAS"));
		
		painel.add(velocidadeMediaPorPiloto);
		
		painel.add(btnIniciar);
		painel.add(fundo);
		
		CorridaController corridaController = new CorridaController(btnIniciar, labelResultados, primeiraVoltaScrollPane, segundaVoltaScrollPane, terceiraVoltaScrollPane,
				quartaVoltaScrollPane, resultadoFinal, velocidadeMediaPorPiloto);
		btnIniciar.addActionListener(corridaController);
	}

}
