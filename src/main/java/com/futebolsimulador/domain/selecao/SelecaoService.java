package com.futebolsimulador.domain.selecao;

import java.util.List;

public interface SelecaoService {
	
	Selecao cadastrar(Selecao selecao);
	List<Selecao> buscarTodos();
	Selecao buscarPorId(Long id);
	void excluir(Long IdSelecao);
	Selecao alterar(Selecao selecao, Long id);
	void preCadastroSelecoes();

}
