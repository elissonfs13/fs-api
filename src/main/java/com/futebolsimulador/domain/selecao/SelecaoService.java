package com.futebolsimulador.domain.selecao;

import java.util.List;

import com.futebolsimulador.controllers.dto.GraficoListaValorDTO;

public interface SelecaoService {
	
	Selecao cadastrar(Selecao selecao);
	List<Selecao> buscarTodos();
	Selecao buscarPorId(Long id);
	void excluir(Long IdSelecao);
	Selecao alterar(Selecao selecao, Long id);
	void preCadastroSelecoes();
	GraficoListaValorDTO buscarPosicoesSelecao(Long idSelecao);

}
