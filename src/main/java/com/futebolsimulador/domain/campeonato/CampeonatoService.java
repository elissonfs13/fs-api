package com.futebolsimulador.domain.campeonato;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebolsimulador.domain.grupo.Grupo;
import com.futebolsimulador.domain.grupo.GrupoService;
import com.futebolsimulador.domain.jogo.Jogo;
import com.futebolsimulador.domain.jogo.JogoService;
import com.futebolsimulador.domain.selecao.Selecao;

@Service
public class CampeonatoService {
	
	@Autowired
	private CampeonatoRepository campeonatoRepository;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private JogoService jogoService;
	
	public Campeonato novoCampeonato(ArrayList<Selecao> selecoes) {
		return geraCampeonato(selecoes);
	}
	
	public List<Campeonato> buscarTodos() {
		return campeonatoRepository.findAll();
	}
	
	public Campeonato buscarPorId(Long id) {
		return campeonatoRepository.findOne(id);
	}

	public Campeonato geraCampeonato(ArrayList<Selecao> selecoes) {
		Campeonato campeonato = criaNovoCampeonato(selecoes);
		grupoService.geraGrupos(campeonato, selecoes);
		geraOitavasFinal(campeonato);
		geraQuartasFinal(campeonato);
		geraSemiFinal(campeonato);
		geraFinais(campeonato);
		campeonato.geraClassificacao();
		return campeonatoRepository.save(campeonato);
	}

	private Campeonato criaNovoCampeonato(ArrayList<Selecao> selecoes) {
		return campeonatoRepository.save(new Campeonato(selecoes.get(0)));
	}

	private void geraFinais(Campeonato campeonato) {
		Boolean empate = Boolean.FALSE;
		List<Jogo> semiFinal = campeonato.getSemiFinal();
		campeonato.setTerceiroQuarto(geraJogo(semiFinal.get(0).getPerdedor(), semiFinal.get(1).getPerdedor(), empate));
		campeonato.setFinalCampeonato(geraJogo(semiFinal.get(0).getVencedor(), semiFinal.get(1).getVencedor(), empate));
	}

	private void geraSemiFinal(Campeonato campeonato) {
		Boolean empate = Boolean.FALSE;
		List<Jogo> quartasFinal = campeonato.getQuartasFinal();
		List<Jogo> semi = new ArrayList<Jogo>();
		semi.add(geraJogo(quartasFinal.get(0).getVencedor(), quartasFinal.get(1).getVencedor(), empate));
		semi.add(geraJogo(quartasFinal.get(2).getVencedor(), quartasFinal.get(3).getVencedor(), empate));
		campeonato.setSemiFinal(semi);
	}

	private void geraQuartasFinal(Campeonato campeonato) {
		Boolean empate = Boolean.FALSE;
		List<Jogo> oitavasFinal = campeonato.getOitavasFinal();
		List<Jogo> quartas = new ArrayList<Jogo>();
		quartas.add(geraJogo(oitavasFinal.get(0).getVencedor(), oitavasFinal.get(1).getVencedor(), empate));
		quartas.add(geraJogo(oitavasFinal.get(2).getVencedor(), oitavasFinal.get(3).getVencedor(), empate));
		quartas.add(geraJogo(oitavasFinal.get(4).getVencedor(), oitavasFinal.get(5).getVencedor(), empate));
		quartas.add(geraJogo(oitavasFinal.get(6).getVencedor(), oitavasFinal.get(7).getVencedor(), empate));
		campeonato.setQuartasFinal(quartas);
	}

	private void geraOitavasFinal(Campeonato campeonato) {
		Boolean empate = Boolean.FALSE;
		List<Grupo> grupos = campeonato.getGrupos();
		List<Jogo> oitavas = new ArrayList<Jogo>();
		oitavas.add(geraJogo(grupos.get(0).getPrimeiro(), grupos.get(7).getSegundo(), empate));
		oitavas.add(geraJogo(grupos.get(1).getPrimeiro(), grupos.get(6).getSegundo(), empate));
		oitavas.add(geraJogo(grupos.get(2).getPrimeiro(), grupos.get(5).getSegundo(), empate));
		oitavas.add(geraJogo(grupos.get(3).getPrimeiro(), grupos.get(4).getSegundo(), empate));
		oitavas.add(geraJogo(grupos.get(4).getPrimeiro(), grupos.get(3).getSegundo(), empate));
		oitavas.add(geraJogo(grupos.get(5).getPrimeiro(), grupos.get(2).getSegundo(), empate));
		oitavas.add(geraJogo(grupos.get(6).getPrimeiro(), grupos.get(1).getSegundo(), empate));
		oitavas.add(geraJogo(grupos.get(7).getPrimeiro(), grupos.get(0).getSegundo(), empate));
		campeonato.setOitavasFinal(oitavas);
	}

	private Jogo geraJogo(Selecao selecao1, Selecao selecao2, Boolean empate) {
		return jogoService.geraJogo(selecao1, selecao2, empate);
	}

}
