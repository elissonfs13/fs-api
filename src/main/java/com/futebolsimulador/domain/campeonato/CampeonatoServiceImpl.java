package com.futebolsimulador.domain.campeonato;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futebolsimulador.domain.grupo.Grupo;
import com.futebolsimulador.domain.grupo.GrupoFacade;
import com.futebolsimulador.domain.jogo.Jogo;
import com.futebolsimulador.domain.jogo.JogoFacade;
import com.futebolsimulador.domain.selecao.Selecao;
import com.futebolsimulador.exception.CampeonatoNaoGeradoException;
import com.futebolsimulador.exception.ExceptionMessage;
import com.futebolsimulador.exception.ObjetoNaoEncontradoException;
import com.futebolsimulador.infra.utils.MessageUtils;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {
	
	@Autowired
	private CampeonatoRepository campeonatoRepository;
	
	@Autowired
	private GrupoFacade grupoFacade;
	
	@Autowired
	private JogoFacade jogoFacade;
	
	@Autowired
	protected MessageUtils messageUtil;
	
	public Campeonato novoCampeonato(ArrayList<Selecao> selecoes) {
		try {
			return geraCampeonato(selecoes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CampeonatoNaoGeradoException(messageUtil.getMensagemErro(ExceptionMessage.CAMPEONATO_ERRO_GERAR));
		}
	}
	
	public List<Campeonato> buscarTodos() {
		return campeonatoRepository.findAll();
	}
	
	public Campeonato buscarPorId(Long id) {
		Campeonato campeonato = campeonatoRepository.findOne(id);
		if (campeonato == null) {
			throw new ObjetoNaoEncontradoException(messageUtil.getMensagemErro(
					ExceptionMessage.CAMPEONATO_ID_NAO_ENCONTRADO, Arrays.asList(id)));
		}
		return campeonato;
	}

	@Transactional
	private Campeonato geraCampeonato(ArrayList<Selecao> selecoes) {
		Campeonato campeonato = criaNovoCampeonato(selecoes);
		grupoFacade.geraGrupos(campeonato, selecoes);
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
		semi.add(geraJogoSemis(quartasFinal.get(0).getVencedor(), quartasFinal.get(1).getVencedor(), empate, campeonato));
		semi.add(geraJogoSemis(quartasFinal.get(2).getVencedor(), quartasFinal.get(3).getVencedor(), empate, campeonato));
		campeonato.setSemiFinal(semi);
	}

	private void geraQuartasFinal(Campeonato campeonato) {
		Boolean empate = Boolean.FALSE;
		List<Jogo> oitavasFinal = campeonato.getOitavasFinal();
		List<Jogo> quartas = new ArrayList<Jogo>();
		quartas.add(geraJogoQuartas(oitavasFinal.get(0).getVencedor(), oitavasFinal.get(1).getVencedor(), empate, campeonato));
		quartas.add(geraJogoQuartas(oitavasFinal.get(2).getVencedor(), oitavasFinal.get(3).getVencedor(), empate, campeonato));
		quartas.add(geraJogoQuartas(oitavasFinal.get(4).getVencedor(), oitavasFinal.get(5).getVencedor(), empate, campeonato));
		quartas.add(geraJogoQuartas(oitavasFinal.get(6).getVencedor(), oitavasFinal.get(7).getVencedor(), empate, campeonato));
		campeonato.setQuartasFinal(quartas);
	}

	private void geraOitavasFinal(Campeonato campeonato) {
		Boolean empate = Boolean.FALSE;
		List<Grupo> grupos = campeonato.getGrupos();
		List<Jogo> oitavas = new ArrayList<Jogo>();
		oitavas.add(geraJogoOitavas(grupos.get(0).getPrimeiro(), grupos.get(7).getSegundo(), empate, campeonato));
		oitavas.add(geraJogoOitavas(grupos.get(1).getPrimeiro(), grupos.get(6).getSegundo(), empate, campeonato));
		oitavas.add(geraJogoOitavas(grupos.get(2).getPrimeiro(), grupos.get(5).getSegundo(), empate, campeonato));
		oitavas.add(geraJogoOitavas(grupos.get(3).getPrimeiro(), grupos.get(4).getSegundo(), empate, campeonato));
		oitavas.add(geraJogoOitavas(grupos.get(4).getPrimeiro(), grupos.get(3).getSegundo(), empate, campeonato));
		oitavas.add(geraJogoOitavas(grupos.get(5).getPrimeiro(), grupos.get(2).getSegundo(), empate, campeonato));
		oitavas.add(geraJogoOitavas(grupos.get(6).getPrimeiro(), grupos.get(1).getSegundo(), empate, campeonato));
		oitavas.add(geraJogoOitavas(grupos.get(7).getPrimeiro(), grupos.get(0).getSegundo(), empate, campeonato));
		campeonato.setOitavasFinal(oitavas);
	}

	private Jogo geraJogoOitavas(Selecao selecao1, Selecao selecao2, Boolean empate, Campeonato campeonato) {
		Jogo jogo = geraJogo(selecao1, selecao2, empate);
		jogo.setOitavas(campeonato);
		return jogo;
	}
	
	private Jogo geraJogoQuartas(Selecao selecao1, Selecao selecao2, Boolean empate, Campeonato campeonato) {
		Jogo jogo = geraJogo(selecao1, selecao2, empate);
		jogo.setQuartas(campeonato);
		return jogo;
	}
	
	private Jogo geraJogoSemis(Selecao selecao1, Selecao selecao2, Boolean empate, Campeonato campeonato) {
		Jogo jogo = geraJogo(selecao1, selecao2, empate);
		jogo.setSemis(campeonato);
		return jogo;
	}
	
	private Jogo geraJogo(Selecao selecao1, Selecao selecao2, Boolean empate) {
		return jogoFacade.geraJogo(selecao1, selecao2, empate);
	}

}
