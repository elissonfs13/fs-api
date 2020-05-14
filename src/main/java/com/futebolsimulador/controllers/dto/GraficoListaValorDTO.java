package com.futebolsimulador.controllers.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class GraficoListaValorDTO {
	
	private String identificador;
	private List<Integer> valores;

}
