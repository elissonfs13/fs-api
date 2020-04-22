package com.futebolsimulador.domain.jogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebolsimulador.domain.selecao.Selecao;
import com.futebolsimulador.infra.utils.GeradorAleatorio;

@Service
public class JogoService {
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@Autowired
	private GeradorAleatorio gerador;
	
	public Jogo geraJogo(Selecao selecao1, Selecao selecao2, Boolean podeEmpatar){
		Jogo jogo = criarNovoJogo(selecao1, selecao2, podeEmpatar);
		return salvaJogo(jogo);
	}
	
	public Jogo salvaJogo(Jogo jogo) {
		return jogoRepository.save(jogo);
	}

	private Jogo criarNovoJogo(Selecao selecao1, Selecao selecao2, Boolean podeEmpatar) {
		Integer gols1, gols2;
		do {
			gols1 = geraGols(selecao1.getNivel());
			gols2 = geraGols(selecao2.getNivel());
		} while (!podeEmpatar && gols1.equals(gols2));
		
		Jogo jogo = Jogo.builder()
				.selecao1(selecao1)
				.selecao2(selecao2)
				.podeEmpatar(podeEmpatar)
				.gols1(gols1)
				.gols2(gols2)
				.build();
		
		return jogo;
	}

	private Integer geraGols(Integer nivel) {
		return gerador.getNumRandom(nivel);
	}

	

}
