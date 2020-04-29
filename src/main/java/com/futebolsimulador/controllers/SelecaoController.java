package com.futebolsimulador.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.futebolsimulador.domain.selecao.Selecao;
import com.futebolsimulador.domain.selecao.SelecaoFacade;

@RestController
@RequestMapping("/selecao")
public class SelecaoController {
	
	@Autowired
	private SelecaoFacade selecaoFacade;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Selecao> cadastrarSelecao(@RequestBody Selecao selecao){
		Selecao selecaoCadastrada = selecaoFacade.cadastrar(selecao);
		return ResponseEntity.ok().body(selecaoCadastrada);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Selecao>> buscarTodasSelecoes(){
		List<Selecao> selecoesBuscadas = selecaoFacade.buscarTodos();
		return ResponseEntity.ok().body(selecoesBuscadas);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Selecao> buscarSelecaoPorId(@PathVariable Long id) {
		Selecao selecao = selecaoFacade.buscarPorId(id);
		return ResponseEntity.ok().body(selecao);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}" )
	public ResponseEntity<Selecao> excluirSelecao(@PathVariable Long id) {
		selecaoFacade.excluir(id);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Selecao> alterarSelecao(@RequestBody Selecao selecao, @PathVariable Long id) {
		Selecao selecaoAlterada = selecaoFacade.alterar(selecao, id);
		return ResponseEntity.ok().body(selecaoAlterada);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/pre" )
	public ResponseEntity<Selecao> preCadastro() {
		selecaoFacade.preCadastroSelecoes();
		return ResponseEntity.ok().build();
	}

}
