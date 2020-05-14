package com.futebolsimulador.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class GraficoValorDTO implements Comparable<GraficoValorDTO>{
	
	private String identificador;
	private Integer valor;
	
	public void adicionaValor() {
		this.valor++;
	}

	@Override
	public int compareTo(GraficoValorDTO outroValor) {
		if (this.valor > outroValor.getValor()) {
			return -1;
		} else if (this.valor < outroValor.getValor()) {
			return 1;
		}
		return 0;
	}

}
