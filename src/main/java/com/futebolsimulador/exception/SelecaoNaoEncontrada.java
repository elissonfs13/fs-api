package com.futebolsimulador.exception;

public class SelecaoNaoEncontrada extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SelecaoNaoEncontrada(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getValue());
    }
	
	public SelecaoNaoEncontrada(String msg) {
		super(msg);
    }
}