package com.futebolsimulador.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.futebolsimulador.domain.commons.ApiErrors;
import com.futebolsimulador.exception.CampeonatoNaoGeradoException;
import com.futebolsimulador.exception.ObjetoNaoEncontradoException;
import com.futebolsimulador.exception.SelecaoNaoValidadaException;
import com.futebolsimulador.exception.SelecaoParticipouCampeonatoException;


@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrors handleException(Exception ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
	
	@ExceptionHandler(ObjetoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleObjetoNaoEncontradoException(ObjetoNaoEncontradoException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
	
	@ExceptionHandler(CampeonatoNaoGeradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleCampeonatoNaoGeradoException(CampeonatoNaoGeradoException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
	
	@ExceptionHandler(SelecaoParticipouCampeonatoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleSelecaoParticipouCampeonatoException(SelecaoParticipouCampeonatoException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
	
	@ExceptionHandler(SelecaoNaoValidadaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleSelecaoNaoValidadaException(SelecaoNaoValidadaException ex){
        List<String> mensagensErro = ex.getMensagens();
        return new ApiErrors(mensagensErro);
    }

}
