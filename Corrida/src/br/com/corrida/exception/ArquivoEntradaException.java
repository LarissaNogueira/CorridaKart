package br.com.corrida.exception;


public class ArquivoEntradaException extends Exception {

	private static final long serialVersionUID = 1L;

	public ArquivoEntradaException(String message) {
		super(message);
	}
	public ArquivoEntradaException(Throwable t) {
		super(t);
	}
	public ArquivoEntradaException(String message, Throwable t) {
		super(message, t);
	}
}
