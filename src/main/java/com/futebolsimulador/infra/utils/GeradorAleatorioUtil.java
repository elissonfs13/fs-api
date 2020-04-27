package com.futebolsimulador.infra.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GeradorAleatorioUtil {
	
	public Integer getNumRandom(Integer numMax){
		Random gerador = new Random();
		return gerador.nextInt(numMax);
	}

}
