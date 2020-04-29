package com.futebolsimulador.domain.campeonato;

import java.util.ArrayList;
import java.util.List;

import com.futebolsimulador.domain.selecao.Selecao;

public interface CampeonatoService {
	
	Campeonato novoCampeonato(ArrayList<Selecao> selecoes);
	List<Campeonato> buscarTodos();
	Campeonato buscarPorId(Long id);

}
