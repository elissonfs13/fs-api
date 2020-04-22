package com.futebolsimulador.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.futebolsimulador.domain.campeonato.Campeonato;
import com.futebolsimulador.domain.campeonato.CampeonatoService;
import com.futebolsimulador.domain.selecao.Selecao;

@CrossOrigin
@RestController
@RequestMapping("/campeonato")
public class CampeonatoController {
	
	@Autowired
	private CampeonatoService campeonatoService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Campeonato> geraCampeonato(@RequestBody ArrayList<Selecao> selecoes){
		Campeonato campeonato = campeonatoService.geraCampeonato(selecoes);
		return new ResponseEntity<>(campeonato, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Campeonato>> buscarTodosCampeonatos(){
		List<Campeonato> campeonatosBuscadas = campeonatoService.buscarTodos();
		return new ResponseEntity<>(campeonatosBuscadas, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Campeonato> buscarCampeonatoPorId(@PathVariable Long id) {
		Campeonato campeonato = campeonatoService.buscarPorId(id);
		return new ResponseEntity<>(campeonato, HttpStatus.OK);
	}

}
