package com.futebolsimulador.domain.campeonato;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futebolsimulador.controllers.dto.GraficoListaValorDTO;
import com.futebolsimulador.controllers.dto.GraficoValorDTO;
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
	
	@Override
	public Campeonato novoCampeonato(ArrayList<Selecao> selecoes) {
		try {
			return geraCampeonato(selecoes);
		} catch (Exception e) {
			throw new CampeonatoNaoGeradoException(messageUtil.getMensagemErro(ExceptionMessage.CAMPEONATO_ERRO_GERAR));
		}
	}
	
	@Override
	public List<Campeonato> buscarTodos() {
		return campeonatoRepository.findAllByOrderByIdAsc();
	}
	
	@Override
	public Campeonato buscarPorId(Long id) {
		Campeonato campeonato = campeonatoRepository.findOne(id);
		if (campeonato == null) {
			throw new ObjetoNaoEncontradoException(messageUtil.getMensagemErro(
					ExceptionMessage.CAMPEONATO_ID_NAO_ENCONTRADO, Arrays.asList(id)));
		}
		return campeonato;
	}
	
	@Override
	public List<GraficoValorDTO> buscarCampeoesPorConfederacao() {
		List<GraficoValorDTO> selCampeas = new ArrayList<>();
		List<Campeonato> campeonatos = buscarTodos();
		for (Campeonato campeonato : campeonatos) {
			geraListaSelecoes(selCampeas, campeonato.getPrimeiro().getConfederacao().toString());
		}
		Collections.sort(selCampeas);
		return selCampeas;
	}
	
	@Override
	public List<GraficoValorDTO> buscarCampeoesPorSelecao() {
		List<GraficoValorDTO> selCampeas = new ArrayList<>();
		List<Campeonato> campeonatos = buscarTodos();
		for (Campeonato campeonato : campeonatos) {
			geraListaSelecoes(selCampeas, campeonato.getPrimeiro().getNome());
		}
		Collections.sort(selCampeas);
		return selCampeas;
	}
	
	@Override
	public List<GraficoValorDTO> buscarCampeoesPorBandeira() {
		List<GraficoValorDTO> selCampeas = new ArrayList<>();
		List<Campeonato> campeonatos = buscarTodos();
		for (Campeonato campeonato : campeonatos) {
			geraListaSelecoes(selCampeas, campeonato.getPrimeiro().getBandeira().substring(0, 2));
		}
		return selCampeas;
	}
	
	@Override
	public GraficoListaValorDTO buscarSelecaoEmCampeonato(Selecao selecao) {
		List<Campeonato> campeonatos = buscarTodos();
		List<Integer> posicoes = campeonatos.stream()
				.map(campeonato -> campeonato.verificaPosicao(selecao))
				.collect(Collectors.toList());
		
		return GraficoListaValorDTO.builder()
							  .identificador(selecao.getNome())
							  .valores(posicoes)
							  .build();
	}
	
	@Override
	public List<GraficoValorDTO> buscarPosicoesPorCampeonato(Long campeonatoId) {
		List<GraficoValorDTO> posicaoSelecoes = new ArrayList<>();
		Campeonato campeonato = buscarPorId(campeonatoId);
		
		posicaoSelecoes.add(geraPosicaoSelecao(campeonato.getPrimeiro(), Posicao.CAMPEAO));
		posicaoSelecoes.add(geraPosicaoSelecao(campeonato.getSegundo(), Posicao.VICE_CAMPEAO));
		posicaoSelecoes.add(geraPosicaoSelecao(campeonato.getTerceiro(), Posicao.TERCEIRO));
		posicaoSelecoes.add(geraPosicaoSelecao(campeonato.getQuarto(), Posicao.QUARTO));
		
		campeonato.getQuartasFinal().stream().forEach(jogo -> 
			posicaoSelecoes.add(geraPosicaoSelecao(jogo.getPerdedor(), Posicao.QUARTAS_FINAIS)));
		
		campeonato.getOitavasFinal().stream().forEach(jogo -> 
			posicaoSelecoes.add(geraPosicaoSelecao(jogo.getPerdedor(), Posicao.OITAVAS_FINAIS)));
		
		campeonato.getGrupos().stream().forEach(grupo -> {
			posicaoSelecoes.add(geraPosicaoSelecao(grupo.getTerceiro(), Posicao.FASE_GRUPOS));
			posicaoSelecoes.add(geraPosicaoSelecao(grupo.getQuarto(), Posicao.FASE_GRUPOS));
		});
		
		return posicaoSelecoes;
	}
	
	
	@Override
	public List<GraficoValorDTO> buscarParticipantesPorSelecao() {
		List<GraficoValorDTO> selParticipantes = new ArrayList<>();
		List<Campeonato> campeonatos = buscarTodos();
		for (Campeonato campeonato : campeonatos) {
			campeonato.selecoesParticipantes().stream()
				.forEach(selecao -> geraListaSelecoes(selParticipantes, selecao.getNome()));
		}
		Collections.sort(selParticipantes);
		return selParticipantes;
	}
	
	@Override
	public List<GraficoValorDTO> buscarParticipantesPorBandeira() {
		List<GraficoValorDTO> selParticipantes = new ArrayList<>();
		List<Campeonato> campeonatos = buscarTodos();
		for (Campeonato campeonato : campeonatos) {
			campeonato.selecoesParticipantes().stream()
				.forEach(selecao -> geraListaSelecoes(selParticipantes, selecao.getBandeira().substring(0, 2)));
		}
		Collections.sort(selParticipantes);
		return selParticipantes;
	}
	
	private GraficoValorDTO geraPosicaoSelecao(Selecao selecao, Posicao posicao) {
		return GraficoValorDTO.builder()
				.identificador(selecao.getBandeira().substring(0, 2))
				.valor(posicao.getValor())
				.build();
	}
	
	private void geraListaSelecoes(List<GraficoValorDTO> valores, String identificador) {
		for (GraficoValorDTO valor : valores) {
			if (valor.getIdentificador().equals(identificador)) {
				valor.adicionaValor();
				return;
			}
		}
		
		valores.add(GraficoValorDTO.builder()
								   .identificador(identificador)
								   .valor(new Integer(1))
								   .build());
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
