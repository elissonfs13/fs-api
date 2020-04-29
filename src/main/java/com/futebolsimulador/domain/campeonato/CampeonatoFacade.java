package com.futebolsimulador.domain.campeonato;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.futebolsimulador.domain.selecao.Selecao;

@Component
public class CampeonatoFacade {
	
	@Autowired
	private CampeonatoService campeonatoService;
	
	public Campeonato novoCampeonato(ArrayList<Selecao> selecoes) {
		return campeonatoService.novoCampeonato(selecoes);
	}
	
	public List<Campeonato> buscarTodos() {
		return campeonatoService.buscarTodos();
	}
	
	public Campeonato buscarPorId(Long id) {
		return campeonatoService.buscarPorId(id);
	}

}
