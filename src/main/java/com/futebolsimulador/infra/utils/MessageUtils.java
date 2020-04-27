package com.futebolsimulador.infra.utils;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.futebolsimulador.exception.ExceptionMessage;

@Component
public class MessageUtils {
	
	@Autowired
	protected MessageSource messageSource;
	
	public String getMensagemErro(String msg, List<Object> params) {
		return messageSource.getMessage(msg, params.toArray(), Locale.US);
	}
	
	public String getMensagemErro(ExceptionMessage exceptionMessage, List<Object> params) {
		return messageSource.getMessage(exceptionMessage.getValue(), params.toArray(), Locale.US);
	}
	
	public String getMensagemErro(ExceptionMessage exceptionMessage) {
		return messageSource.getMessage(exceptionMessage.getValue(), null, Locale.US);
	}

}
