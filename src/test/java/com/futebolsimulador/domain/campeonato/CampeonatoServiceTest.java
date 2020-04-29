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
	private List<Campeonato> campeonatos;
	private ArrayList<Selecao> selecoes;
	private Jogo jogo;
	
	@Before
	public void init() {
		selecoes = criaListaSelecoes();
		campeonato = criaCampeonato(selecoes.get(0));
		campeonatos = new ArrayList<>();
		campeonatos.add(campeonato);
		jogo = criaJogo();
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
		when(campeonatoRepository.findAll()).thenReturn(campeonatos);
		final List<Campeonato> campeonatosRetornados = campeonatoService.buscarTodos();
		assertNotNull(campeonatosRetornados);
		assertEquals(1, campeonatosRetornados.size());
		assertEquals(campeonatos, campeonatosRetornados);
		assertEquals(campeonato, campeonatosRetornados.get(0));
		verify(campeonatoRepository, times(1)).findAll();
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
		grupo.setInfoSelecoes(Arrays.asList(info, info));
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
