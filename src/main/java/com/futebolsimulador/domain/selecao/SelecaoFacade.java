package com.futebolsimulador.domain.selecao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.futebolsimulador.controllers.dto.GraficoListaValorDTO;

@Component
public class SelecaoFacade {
	
	@Autowired
	private SelecaoService selecaoService;
	
	public Selecao cadastrar(Selecao selecao) {
		return selecaoService.cadastrar(selecao);
	}
	
	public List<Selecao> buscarTodos() {
		return selecaoService.buscarTodos();
	}
	
	public Selecao buscarPorId(Long id) {
		return selecaoService.buscarPorId(id);
	}
	
	public void excluir(Long idSelecao) {
		selecaoService.excluir(idSelecao);
	}
	
	public Selecao alterar(Selecao selecao, Long id) {
		return selecaoService.alterar(selecao, id);
	}
	
	public void preCadastroSelecoes(){
		selecaoService.preCadastroSelecoes();
	}

	public GraficoListaValorDTO buscarPosicoesSelecao(Long idSelecao) {
		return selecaoService.buscarPosicoesSelecao(idSelecao);
	}

}
