package com.futebolsimulador.domain.jogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebolsimulador.domain.selecao.Selecao;
import com.futebolsimulador.infra.utils.GeradorAleatorioUtil;

@Service
public class JogoServiceImpl implements JogoService {
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@Autowired
	private GeradorAleatorioUtil gerador;
	
	public Jogo geraJogo(Selecao selecao1, Selecao selecao2, Boolean podeEmpatar){
		Jogo jogo = criarNovoJogo(selecao1, selecao2, podeEmpatar);
		return salvaJogo(jogo);
	}
	
	public Jogo salvaJogo(Jogo jogo) {
		return jogoRepository.save(jogo);
	}
	
	public boolean selecaoParticipouCampeonato(Selecao selecao) {
		List<Jogo> jogos = jogoRepository.findBySelecao1(selecao);
		return (jogos == null || jogos.isEmpty()) ? false : true;
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
