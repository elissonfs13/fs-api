package com.futebolsimulador.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.futebolsimulador.domain.commons.ApiErrors;
import com.futebolsimulador.exception.SelecaoNaoEncontrada;
import com.futebolsimulador.exception.SelecaoNaoValidada;
import com.futebolsimulador.exception.SelecaoParticipouCampeonatoException;


@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrors handleException(Exception ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
	
	@ExceptionHandler(SelecaoParticipouCampeonatoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleSelecaoParticipouCampeonatoException(SelecaoParticipouCampeonatoException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
	
	@ExceptionHandler(SelecaoNaoEncontrada.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleSelecaoNaoEncontrada(SelecaoNaoEncontrada ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
	
	@ExceptionHandler(SelecaoNaoValidada.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleSelecaoParticipouCampeonatoException(SelecaoNaoValidada ex){
        List<String> mensagensErro = ex.getMensagens();
        return new ApiErrors(mensagensErro);
    }

}
