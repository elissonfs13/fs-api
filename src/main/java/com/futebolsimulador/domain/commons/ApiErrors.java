package com.futebolsimulador.domain.commons;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErrors {

    @Getter
    private List<String> mensagens;

    public ApiErrors(List<String> errors) {
        this.mensagens = errors;
    }

    public ApiErrors(String mensagemErro){
        this.mensagens = Arrays.asList(mensagemErro);
    }
}
