package com.futebolsimulador.domain.grupo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.futebolsimulador.domain.campeonato.Campeonato;
import com.futebolsimulador.domain.selecao.Selecao;

@Component
public class GrupoFacade {
	
	@Autowired
	private GrupoService grupoService;
	
	public void geraGrupos(Campeonato campeonato, ArrayList<Selecao> selecoes){
		grupoService.geraGrupos(campeonato, selecoes);
	}

}
