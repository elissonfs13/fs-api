package com.futebolsimulador.domain.grupo;

import java.util.ArrayList;

import com.futebolsimulador.domain.campeonato.Campeonato;
import com.futebolsimulador.domain.selecao.Selecao;

public interface GrupoService {
	
	void geraGrupos(Campeonato campeonato, ArrayList<Selecao> selecoes);

}
