package com.futebolsimulador.exception;

public class ObjetoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ObjetoNaoEncontradoException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getValue());
    }
	
	public ObjetoNaoEncontradoException(String msg) {
		super(msg);
    }
}