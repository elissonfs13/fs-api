package com.futebolsimulador.domain.campeonato;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.futebolsimulador.controllers.dto.GraficoListaValorDTO;
import com.futebolsimulador.controllers.dto.GraficoValorDTO;
import com.futebolsimulador.domain.grupo.Grupo;
import com.futebolsimulador.domain.grupo.GrupoFacade;
import com.futebolsimulador.domain.grupo.InfoSelecaoNoGrupo;
import com.futebolsimulador.domain.jogo.Jogo;
import com.futebolsimulador.domain.jogo.JogoFacade;
import com.futebolsimulador.domain.selecao.Confederacao;
import com.futebolsimulador.domain.selecao.Selecao;
import com.futebolsimulador.exception.CampeonatoNaoGeradoException;
import com.futebolsimulador.exception.ObjetoNaoEncontradoException;
import com.futebolsimulador.infra.utils.MessageUtils;

@RunWith(MockitoJUnitRunner.class)
public class CampeonatoServiceTest {
	
	@Mock
	private CampeonatoRepository campeonatoRepository;
	
	@Mock
	private GrupoFacade grupoFacade;
	
	@Mock
	private JogoFacade jogoFacade;
	
	@Mock
	protected MessageUtils messageUtil;
	
	@InjectMocks
	private CampeonatoServiceImpl campeonatoService;
	
	private static final Long CAMPEONATO_ID = 1L;
	private Campeonato campeonato;
	private Campeonato campeonatoCompleto;
	private List<Campeonato> campeonatos;
	private List<Campeonato> campeonatosCompletos;
	private ArrayList<Selecao> selecoes;
	private Jogo jogo;
	
	@Before
	public void init() {
		jogo = criaJogo();
		selecoes = criaListaSelecoes();
		campeonato = criaCampeonato(selecoes.get(0));
		campeonatoCompleto = criaCampeonatoCompleto(selecoes.get(0));
		campeonatos = new ArrayList<>();
		campeonatos.add(campeonato);
		campeonatosCompletos = new ArrayList<>();
		campeonatosCompletos.add(campeonatoCompleto);
	}
	
	@Test
	public void buscarPorIdTest(){
		when(campeonatoRepository.findOne(CAMPEONATO_ID)).thenReturn(campeonato);
		final Campeonato campeonatoRetornado = campeonatoService.buscarPorId(CAMPEONATO_ID);
		assertNotNull(campeonatoRetornado);
		assertEquals(campeonato, campeonatoRetornado);
		verify(campeonatoRepository, times(1)).findOne(CAMPEONATO_ID);
	}
	
	@Test(expected = ObjetoNaoEncontradoException.class)
	public void buscarPorIdCampeonatoNaoEncontradoTest(){
		when(campeonatoRepository.findOne(CAMPEONATO_ID)).thenReturn(null);
		campeonatoService.buscarPorId(CAMPEONATO_ID);
	}
	
	@Test
	public void buscarTodosTest(){
		when(campeonatoRepository.findAllByOrderByIdAsc()).thenReturn(campeonatos);
		final List<Campeonato> campeonatosRetornados = campeonatoService.buscarTodos();
		assertNotNull(campeonatosRetornados);
		assertEquals(1, campeonatosRetornados.size());
		assertEquals(campeonatos, campeonatosRetornados);
		assertEquals(campeonato, campeonatosRetornados.get(0));
		verify(campeonatoRepository, times(1)).findAllByOrderByIdAsc();
	}
	
	@Test
	public void novoCampeonatoTest(){
		when(campeonatoRepository.save(Mockito.any(Campeonato.class))).thenReturn(campeonato);
		when(jogoFacade.geraJogo(Mockito.any(Selecao.class), 
				Mockito.any(Selecao.class),Mockito.any(Boolean.class))).thenReturn(jogo);
		
		final Campeonato campeonatoRetornado = campeonatoService.novoCampeonato(selecoes);
		assertNotNull(campeonatoRetornado);
		assertNotNull(campeonatoRetornado.getOitavasFinal());
		assertNotNull(campeonatoRetornado.getQuartasFinal());
		assertNotNull(campeonatoRetornado.getSemiFinal());
		assertNotNull(campeonatoRetornado.getFinalCampeonato());
		assertNotNull(campeonatoRetornado.getTerceiroQuarto());
		assertNotNull(campeonatoRetornado.getPrimeiro());
		assertNotNull(campeonatoRetornado.getSegundo());
		assertNotNull(campeonatoRetornado.getTerceiro());
		assertNotNull(campeonatoRetornado.getQuarto());
		assertEquals(8, campeonatoRetornado.getOitavasFinal().size());
		assertEquals(4, campeonatoRetornado.getQuartasFinal().size());
		assertEquals(2, campeonatoRetornado.getSemiFinal().size());
		
		verify(campeonatoRepository, times(2)).save(Mockito.any(Campeonato.class));
	}
	
	@Test(expected = CampeonatoNaoGeradoException.class)
	public void novoCampeonatoNaoGeradoTest(){
		when(campeonatoRepository.save(Mockito.any(Campeonato.class))).thenReturn(campeonato);
		campeonatoService.novoCampeonato(selecoes);
	}
	
	@Test
	public void buscarCampeoesPorConfederacaoTest(){
		when(campeonatoRepository.findAllByOrderByIdAsc()).thenReturn(campeonatosCompletos);
		final List<GraficoValorDTO> campeoesRetornados = campeonatoService.buscarCampeoesPorConfederacao();
		assertNotNull(campeoesRetornados);
		assertEquals(1, campeoesRetornados.size());
		assertEquals(campeonatoCompleto.getPrimeiro().getConfederacao().toString(), campeoesRetornados.get(0).getIdentificador());
		verify(campeonatoRepository, times(1)).findAllByOrderByIdAsc();
	}
	
	@Test
	public void buscarCampeoesPorSelecaoTest(){
		when(campeonatoRepository.findAllByOrderByIdAsc()).thenReturn(campeonatosCompletos);
		final List<GraficoValorDTO> campeoesRetornados = campeonatoService.buscarCampeoesPorSelecao();
		assertNotNull(campeoesRetornados);
		assertEquals(1, campeoesRetornados.size());
		assertEquals(campeonatoCompleto.getPrimeiro().getNome(), campeoesRetornados.get(0).getIdentificador());
		verify(campeonatoRepository, times(1)).findAllByOrderByIdAsc();
	}
	
	@Test
	public void buscarCampeoesPorBandeiraTest(){
		when(campeonatoRepository.findAllByOrderByIdAsc()).thenReturn(campeonatosCompletos);
		final List<GraficoValorDTO> campeoesRetornados = campeonatoService.buscarCampeoesPorBandeira();
		assertNotNull(campeoesRetornados);
		assertEquals(1, campeoesRetornados.size());
		assertEquals(campeonatoCompleto.getPrimeiro().getBandeira().substring(0, 2), 
				campeoesRetornados.get(0).getIdentificador());
		verify(campeonatoRepository, times(1)).findAllByOrderByIdAsc();
	}
	
	@Test
	public void buscarParticipantesPorSelecaoTest(){
		when(campeonatoRepository.findAllByOrderByIdAsc()).thenReturn(campeonatosCompletos);
		final List<GraficoValorDTO> campeoesRetornados = campeonatoService.buscarParticipantesPorSelecao();
		assertNotNull(campeoesRetornados);
		assertEquals(1, campeoesRetornados.size());
		assertEquals(campeonatoCompleto.getPrimeiro().getNome(), campeoesRetornados.get(0).getIdentificador());
		verify(campeonatoRepository, times(1)).findAllByOrderByIdAsc();
	}
	
	@Test
	public void buscarParticipantesPorBandeiraTest(){
		when(campeonatoRepository.findAllByOrderByIdAsc()).thenReturn(campeonatosCompletos);
		final List<GraficoValorDTO> campeoesRetornados = campeonatoService.buscarParticipantesPorBandeira();
		assertNotNull(campeoesRetornados);
		assertEquals(1, campeoesRetornados.size());
		assertEquals(campeonatoCompleto.getPrimeiro().getBandeira().substring(0, 2), 
				campeoesRetornados.get(0).getIdentificador());
		verify(campeonatoRepository, times(1)).findAllByOrderByIdAsc();
	}
	
	@Test
	public void buscarSelecaoCampeaEmCampeonatoTest(){
		when(campeonatoRepository.findAllByOrderByIdAsc()).thenReturn(campeonatosCompletos);
		final GraficoListaValorDTO selecaoRetornadas = campeonatoService.buscarSelecaoEmCampeonato(selecoes.get(0));
		assertNotNull(selecaoRetornadas);
		assertEquals(1, selecaoRetornadas.getValores().size());
		assertEquals(selecoes.get(0).getNome(), selecaoRetornadas.getIdentificador());
		assertEquals(new Integer(7), selecaoRetornadas.getValores().get(0));
		verify(campeonatoRepository, times(1)).findAllByOrderByIdAsc();
	}
	
	@Test
	public void buscarSelecaoNaoParticipouEmCampeonatoTest(){
		Selecao novaSelecao = criaSelecao(10L, "Selecao10", "S10", "b10.png", new Integer(3));
		when(campeonatoRepository.findAllByOrderByIdAsc()).thenReturn(campeonatosCompletos);
		final GraficoListaValorDTO selecaoRetornadas = campeonatoService.buscarSelecaoEmCampeonato(novaSelecao);
		assertNotNull(selecaoRetornadas);
		assertEquals(1, selecaoRetornadas.getValores().size());
		assertEquals(novaSelecao.getNome(), selecaoRetornadas.getIdentificador());
		assertEquals(new Integer(0), selecaoRetornadas.getValores().get(0));
		verify(campeonatoRepository, times(1)).findAllByOrderByIdAsc();
	}
	
	@Test
	public void buscarPosicoesPorCampeonatoTest(){
		when(campeonatoRepository.findOne(CAMPEONATO_ID)).thenReturn(campeonatoCompleto);
		final List<GraficoValorDTO> posicoesRetornadas = campeonatoService.buscarPosicoesPorCampeonato(CAMPEONATO_ID);
		assertNotNull(posicoesRetornadas);
		assertEquals(32, posicoesRetornadas.size());
		assertEquals(campeonatoCompleto.getPrimeiro().getBandeira().substring(0, 2), 
				posicoesRetornadas.get(0).getIdentificador());
		verify(campeonatoRepository, times(1)).findOne(CAMPEONATO_ID);
	}

	private ArrayList<Selecao> criaListaSelecoes() {
		ArrayList<Selecao> selecoes = new ArrayList<>();
		selecoes.add(criaSelecao(1L, "Selecao1", "SE1", "bn1.png", new Integer(3)));
		return selecoes;
	}

	private Selecao criaSelecao(Long id, String nome, String abrev, String bandeira, Integer nivel) {
		final Selecao selecao = Selecao.builder()
				.id(id)
				.abrev(nome)
				.nome(abrev)
				.bandeira(bandeira)
				.nivel(new Integer(nivel))
				.confederacao(Confederacao.AFC)
				.build();
		return selecao;
	}
	
	private Campeonato criaCampeonato(Selecao selecao) {
		return Campeonato.builder()
				.id(CAMPEONATO_ID)
				.grupos(criaGrupos())
				.sede(selecao)
				.build();
	}
	
	private Campeonato criaCampeonatoCompleto(Selecao selecao) {
		Campeonato campeonato = Campeonato.builder()
				.id(101L)
				.grupos(criaGrupos())
				.sede(selecao)
				.finalCampeonato(jogo)
				.terceiroQuarto(jogo)
				.primeiro(selecao)
				.segundo(selecao)
				.terceiro(selecao)
				.quarto(selecao)
				.build();
		
		campeonato.setQuartasFinal(Arrays.asList(jogo, jogo, jogo, jogo));
		campeonato.setOitavasFinal(Arrays.asList(jogo, jogo, jogo, jogo, jogo, jogo, jogo, jogo));
		return campeonato;
	}
	
	private ArrayList<Grupo> criaGrupos() {
		ArrayList<Grupo> grupos = new ArrayList<>();
		String[] nomeGrupos = {"A", "B", "C", "D", "E", "F", "G", "H"};
		int num = 0;
		for (int i = 0; i < 8; i++){
			grupos.add(novoGrupo(campeonato, 
								 nomeGrupos[i], 
								 selecoes.get(0)));
			
			num = num + 4;
		}
		return grupos;
	}

	private Grupo novoGrupo(Campeonato campeonato2, String nome, Selecao selecao) {
		Grupo grupo = new Grupo(nome);
		InfoSelecaoNoGrupo info = InfoSelecaoNoGrupo.builder().selecao(selecao).build();
		grupo.setInfoSelecoes(Arrays.asList(info, info, info, info));
		grupo.setJogos(Arrays.asList(jogo, jogo));
		return grupo;
	}
	
	private Jogo criaJogo() {
		final Jogo jogo = Jogo.builder()
				.id(1L)
				.selecao1(criaSelecao(1L, "Seleção 1", "SE1", "b1.png", 3))
				.selecao2(criaSelecao(2L, "Seleção 2", "SE2", "b2.png", 4))
				.podeEmpatar(Boolean.TRUE)
				.gols1(new Integer(1))
				.gols2(new Integer(2))
				.build();
		return jogo;
	}

}
