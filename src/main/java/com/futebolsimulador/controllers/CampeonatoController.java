package com.futebolsimulador.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.futebolsimulador.domain.campeonato.Campeonato;
import com.futebolsimulador.domain.campeonato.CampeonatoFacade;
import com.futebolsimulador.domain.selecao.Selecao;

@RestController
@RequestMapping("/campeonato")
public class CampeonatoController {
	
	@Autowired
	private CampeonatoFacade campeonatoFacade;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Campeonato> geraCampeonato(@RequestBody ArrayList<Selecao> selecoes){
		Campeonato campeonato = campeonatoFacade.novoCampeonato(selecoes);
		return new ResponseEntity<>(campeonato, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Campeonato>> buscarTodosCampeonatos(){
		List<Campeonato> campeonatosBuscadas = campeonatoFacade.buscarTodos();
		return new ResponseEntity<>(campeonatosBuscadas, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Campeonato> buscarCampeonatoPorId(@PathVariable Long id) {
		Campeonato campeonato = campeonatoFacade.buscarPorId(id);
		return ResponseEntity.ok(campeonato);
	}

}
