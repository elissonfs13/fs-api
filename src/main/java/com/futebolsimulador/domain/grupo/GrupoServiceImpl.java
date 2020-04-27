package com.futebolsimulador.domain.grupo;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebolsimulador.domain.campeonato.Campeonato;
import com.futebolsimulador.domain.jogo.Jogo;
import com.futebolsimulador.domain.jogo.JogoFacade;
import com.futebolsimulador.domain.jogo.Resultado;
import com.futebolsimulador.domain.selecao.Selecao;

@Service
public class GrupoServiceImpl implements GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private JogoFacade jogoFacade;
	
	@Autowired
	private InfoSelecaoNoGrupoRepository infoSelecaoNoGrupoRepository;
	
	public void geraGrupos(Campeonato campeonato, ArrayList<Selecao> selecoes){
		ArrayList<Grupo> grupos = new ArrayList<Grupo>();
		String[] nomeGrupos = {"A", "B", "C", "D", "E", "F", "G", "H"};
		int num = 0;
		for (int i = 0; i < 8; i++){
			grupos.add(novoGrupo(campeonato, nomeGrupos[i], selecoes.get(num), selecoes.get(num+1), selecoes.get(num+2), selecoes.get(num+3)));
			num = num + 4;
		}
		campeonato.setGrupos(grupos);
	}
	
	private Grupo novoGrupo(Campeonato campeonato, String nome, Selecao sel1, Selecao sel2, Selecao sel3, Selecao sel4){
		Boolean empate = Boolean.TRUE;
		Grupo grupo = new Grupo(nome);
		ArrayList<InfoSelecaoNoGrupo> infoSelecoes = getInfosSels(grupo, sel1, sel2, sel3, sel4);
		
		geraJogosGrupo(grupo, empate, infoSelecoes);
		calculaSaldoGols(infoSelecoes);
		calculaClassificacao(infoSelecoes);
		grupo.setInfoSelecoes(infoSelecoes);
		grupo.setCampeonato(campeonato);
		return grupoRepository.save(grupo);
	}

	private void geraJogosGrupo(Grupo grupo, Boolean empate, ArrayList<InfoSelecaoNoGrupo> infoSels) {
		ArrayList<Jogo> jogos = new ArrayList<Jogo>();
		jogos.add(geraJogo(grupo, infoSels.get(0), infoSels.get(1), empate));
		jogos.add(geraJogo(grupo, infoSels.get(2), infoSels.get(3), empate));
		jogos.add(geraJogo(grupo, infoSels.get(0), infoSels.get(2), empate));
		jogos.add(geraJogo(grupo, infoSels.get(1), infoSels.get(3), empate));
		jogos.add(geraJogo(grupo, infoSels.get(3), infoSels.get(0), empate));
		jogos.add(geraJogo(grupo, infoSels.get(2), infoSels.get(1), empate));
		grupo.setJogos(jogos);
	}
	
	private Jogo geraJogo(Grupo grupo, InfoSelecaoNoGrupo infoSelecao1, InfoSelecaoNoGrupo infoSelecao2, Boolean podeEmpatar){
		Jogo jogo = jogoFacade.geraJogo(infoSelecao1.getSelecao(), infoSelecao2.getSelecao(), podeEmpatar);
		jogo.setGrupo(grupo);
		
		if (Resultado.TIME1.equals(jogo.verificarResultado())){
			infoSelecao1.adicionaPontos(3);
		} else if (Resultado.TIME2.equals(jogo.verificarResultado())){
			infoSelecao2.adicionaPontos(3);
		} else if (Resultado.EMPATE.equals(jogo.verificarResultado())){
			infoSelecao1.adicionaPontos(1);
			infoSelecao2.adicionaPontos(1);
		}
		
		infoSelecao1.adicionaGolsMarcados(jogo.getGols1());
		infoSelecao1.adicionaGolsSofridos(jogo.getGols2());
		infoSelecao2.adicionaGolsMarcados(jogo.getGols2());
		infoSelecao2.adicionaGolsSofridos(jogo.getGols1());
		
		return jogoFacade.salvaJogo(jogo);
	}

	private void calculaClassificacao(ArrayList<InfoSelecaoNoGrupo> infoSelecoes) {
		Collections.sort(infoSelecoes);
		int posicao = 1;
		for (InfoSelecaoNoGrupo infoSelecao : infoSelecoes){
			infoSelecao.setClassificacao(new Integer(posicao));
			posicao++;
		}
	}

	private void calculaSaldoGols(ArrayList<InfoSelecaoNoGrupo> infoSelecoes) {
		for (InfoSelecaoNoGrupo infoSelecao : infoSelecoes){
			infoSelecao.calculaSaldoGols();
		}
	}

	private ArrayList<InfoSelecaoNoGrupo> getInfosSels(Grupo grupo, Selecao sel1, Selecao sel2, Selecao sel3, Selecao sel4) {
		ArrayList<InfoSelecaoNoGrupo> infoSels = new ArrayList<InfoSelecaoNoGrupo>();
		infoSels.add(createInfoSelecaoNoGrupo(sel1, grupo));
		infoSels.add(createInfoSelecaoNoGrupo(sel2, grupo));
		infoSels.add(createInfoSelecaoNoGrupo(sel3, grupo));
		infoSels.add(createInfoSelecaoNoGrupo(sel4, grupo));
		return infoSels;
	}

	private InfoSelecaoNoGrupo createInfoSelecaoNoGrupo(Selecao selecao, Grupo grupo) {
		InfoSelecaoNoGrupo info = InfoSelecaoNoGrupo.builder()
				.grupo(grupo)
				.selecao(selecao)
				.pontos(new Integer(0))
				.golsMarcados(new Integer(0))
				.golsSofridos(new Integer(0))
				.saldoGols(new Integer(0))
				.build();

		return infoSelecaoNoGrupoRepository.save(info);
	}

}
