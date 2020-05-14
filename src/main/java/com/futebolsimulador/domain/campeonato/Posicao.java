package com.futebolsimulador.domain.campeonato;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Posicao {
	
	CAMPEAO(7),
	VICE_CAMPEAO(6),
	TERCEIRO(5),
	QUARTO(4),
	QUARTAS_FINAIS(3),
	OITAVAS_FINAIS(2),
	FASE_GRUPOS(1),
	NAO_PARTICIPOU(0);
	
	@Getter
	private Integer valor;

}
