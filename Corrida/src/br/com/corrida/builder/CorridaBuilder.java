package br.com.corrida.builder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.corrida.exception.ArquivoEntradaException;
import br.com.corrida.model.DadosPiloto;

/*
 * Classe que recupera todos os dados da corrida
 * */
public class CorridaBuilder {
	
	/**
	(\\w+:\\w+:\\w+.\\w+)	//HORA
	(\\d{3})				//NUMERO PILOTO
	(\\w+.\\w+)				//NOME DO PILOTO
	(\\d{1})				//NUMERO DA VOLTA
	(\\w+:\\w+.\\w+)		//TEMPO-VOLTA
	(\\w+,\\w+)				//VELOCIDADE-VOLTA
	 */

	private static final String REGEX = "^(\\w+:\\w+:\\w+.\\w+)\\s+(\\d{3})\\s+.\\s+(\\w+.\\w+)\\s+(\\d{1})\\s+(\\w+:\\w+.\\w+)\\s+(\\w+,\\w+)";

	public static HashMap<Integer, DadosPiloto> criar(File arquivo) throws ArquivoEntradaException {
		Scanner scanner = null;
		HashMap<Integer, DadosPiloto> pilotos = new HashMap<Integer, DadosPiloto>();
		
		try {
			scanner = new Scanner(arquivo, "UTF-8");
			if (scanner.hasNext()) {
				//Ignorando a primeira linha do arquivo!
				scanner.nextLine();
				
				String detalhesLinha = null;
				Pattern pattern = Pattern.compile(REGEX);
				if (scanner.hasNext()) {
					detalhesLinha = scanner.nextLine();
					
					do {
						Matcher matcher = pattern.matcher(detalhesLinha);
						if (matcher.find()) {
							Set<Integer> numerosPiloto = pilotos.keySet();
							Integer numeroPilotoLinha = Integer.parseInt(matcher.group(2));
							
							if (!numerosPiloto.contains(numeroPilotoLinha)) {
								DadosPiloto piloto = new DadosPiloto();
								piloto.setNumeroPiloto(numeroPilotoLinha);
								piloto.setNomePiloto(matcher.group(3));
								piloto.getHorariosPorVolta().put(Integer.parseInt(matcher.group(4)), matcher.group(1));
								piloto.getTemposPorVolta().put(Integer.parseInt(matcher.group(4)), new SimpleDateFormat("mm:ss.SSS").parse(matcher.group(5)).getTime());
								piloto.getVelocidadesMediasPorVolta().put(Integer.parseInt(matcher.group(4)), new Double(matcher.group(6).replace(',','.')));
								
								pilotos.put(numeroPilotoLinha, piloto);
							} else {
								DadosPiloto piloto = pilotos.get(numeroPilotoLinha);
								piloto.getHorariosPorVolta().put(Integer.parseInt(matcher.group(4)), matcher.group(1));
								piloto.getTemposPorVolta().put(Integer.parseInt(matcher.group(4)), new SimpleDateFormat("mm:ss.SSS").parse(matcher.group(5)).getTime());
								piloto.getVelocidadesMediasPorVolta().put(Integer.parseInt(matcher.group(4)), new Double(matcher.group(6).replace(',','.')));
							}
						}
					} while(scanner.hasNext() && (detalhesLinha = scanner.nextLine()) != null);
				}
			} else {
				throw new ArquivoEntradaException("Arquivo vazio!!");
			}
		} catch (Exception e) {
			throw new ArquivoEntradaException(e);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		
		return pilotos;
	}

}
