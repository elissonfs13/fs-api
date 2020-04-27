package com.futebolsimulador.exception;

import java.util.List;

import lombok.Getter;

public class SelecaoNaoValidada extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private List<String> mensagens;
	
	public SelecaoNaoValidada(ExceptionMessage em) {
        super(em.getValue());
    }
	
	public SelecaoNaoValidada(List<String> msgs) {
		super(msgs.toString());
		this.mensagens = msgs;
    }

}
