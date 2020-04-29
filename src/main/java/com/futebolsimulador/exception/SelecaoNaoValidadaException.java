package com.futebolsimulador.exception;

import java.util.List;

import lombok.Getter;

public class SelecaoNaoValidadaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private List<String> mensagens;
	
	public SelecaoNaoValidadaException(ExceptionMessage em) {
        super(em.getValue());
    }
	
	public SelecaoNaoValidadaException(List<String> msgs) {
		super(msgs.toString());
		this.mensagens = msgs;
    }

}
