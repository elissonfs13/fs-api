package com.futebolsimulador.domain.jogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.futebolsimulador.domain.selecao.Selecao;

@Component
public class JogoFacade {
	
	@Autowired
	private JogoService jogoService;
	
	public Jogo geraJogo(Selecao selecao1, Selecao selecao2, Boolean podeEmpatar){
		return jogoService.geraJogo(selecao1, selecao2, podeEmpatar);
	}
	
	public Jogo salvaJogo(Jogo jogo) {
		return jogoService.salvaJogo(jogo);
	}
	
	public boolean selecaoParticipouCampeonato(Selecao selecao) {
		return jogoService.selecaoParticipouCampeonato(selecao);
	}

}
