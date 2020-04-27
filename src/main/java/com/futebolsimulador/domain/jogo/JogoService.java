package com.futebolsimulador.domain.jogo;

import com.futebolsimulador.domain.selecao.Selecao;

public interface JogoService {
	
	Jogo geraJogo(Selecao selecao1, Selecao selecao2, Boolean podeEmpatar);
	Jogo salvaJogo(Jogo jogo);
	boolean selecaoParticipouCampeonato(Selecao selecao);

}
