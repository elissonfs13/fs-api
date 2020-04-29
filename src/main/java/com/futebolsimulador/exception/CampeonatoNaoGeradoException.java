package com.futebolsimulador.exception;

import java.util.List;

import lombok.Getter;

public class CampeonatoNaoGeradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private List<String> mensagens;
	
	public CampeonatoNaoGeradoException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getValue());
    }
	
	public CampeonatoNaoGeradoException(String msg) {
		super(msg);
    }

}
