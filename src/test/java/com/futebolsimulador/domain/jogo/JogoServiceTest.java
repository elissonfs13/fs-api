package com.futebolsimulador.domain.jogo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.futebolsimulador.domain.selecao.Confederacao;
import com.futebolsimulador.domain.selecao.Selecao;
import com.futebolsimulador.infra.utils.GeradorAleatorioUtil;

@RunWith(MockitoJUnitRunner.class)
public class JogoServiceTest {
	
	@Mock
	private JogoRepository jogoRepository;
	
	@Mock
	private GeradorAleatorioUtil gerador;
	
	@InjectMocks
	private JogoServiceImpl jogoService;
	
	private static final Long JOGO_ID = 1L;
	
	private Jogo jogo;
	private List<Jogo> jogos;
	
	@Before
	public void init() {
		jogo = criarJogo();
		jogos = new ArrayList<>();
		jogos.add(jogo);
	}
	
	@Test
	public void geraJogoTest() {
		Selecao selecao1 = jogo.getSelecao1();
		Selecao selecao2 = jogo.getSelecao2();
		
		when(jogoRepository.save(Mockito.any(Jogo.class))).thenReturn(jogo);
		when(gerador.getNumRandom(3)).thenReturn(1);
		when(gerador.getNumRandom(4)).thenReturn(2);
		
		final Jogo jogoRetornado = jogoService.geraJogo(selecao1, selecao2, Boolean.TRUE);
		assertNotNull(jogoRetornado);
		assertEquals(selecao1, jogoRetornado.getSelecao1());
		assertEquals(selecao2, jogoRetornado.getSelecao2());
		assertEquals(selecao2, jogoRetornado.getVencedor());
		assertEquals(new Integer(1), jogoRetornado.getGols1());
		assertEquals(new Integer(2), jogoRetornado.getGols2());
		verify(jogoRepository, times(1)).save(Mockito.any(Jogo.class));
	}
	
	@Test
	public void salvaJogoTest() {
		when(jogoRepository.save(jogo)).thenReturn(jogo);
		
		final Jogo jogoRetornado = jogoService.salvaJogo(jogo);
		assertNotNull(jogoRetornado);
		assertEquals(jogo, jogoRetornado);
		verify(jogoRepository, times(1)).save(jogo);
	}
	
	@Test
	public void selecaoParticipouCampeonatoTest() {
		Selecao selecao = criaSelecao(1L, "Seleção 1", "SE1", "b1.png", 3);
		when(jogoRepository.findBySelecao1(selecao)).thenReturn(jogos);
		
		final boolean selJogou = jogoService.selecaoParticipouCampeonato(selecao);
		assertNotNull(selJogou);
		assertEquals(Boolean.TRUE, selJogou);
		verify(jogoRepository, times(1)).findBySelecao1(selecao);
	}
	
	@Test
	public void selecaoNaoParticipouCampeonatoTest() {
		Selecao selecao = criaSelecao(1L, "Seleção 1", "SE1", "b1.png", 3);
		when(jogoRepository.findBySelecao1(selecao)).thenReturn(new ArrayList<Jogo>());
		
		final boolean selJogou = jogoService.selecaoParticipouCampeonato(selecao);
		assertNotNull(selJogou);
		assertEquals(Boolean.FALSE, selJogou);
		verify(jogoRepository, times(1)).findBySelecao1(selecao);
	}
	
	private Jogo criarJogo() {
		final Jogo jogo = Jogo.builder()
				.id(JOGO_ID)
				.selecao1(criaSelecao(1L, "Seleção 1", "SE1", "b1.png", 3))
				.selecao2(criaSelecao(2L, "Seleção 2", "SE2", "b2.png", 4))
				.podeEmpatar(Boolean.TRUE)
				.gols1(new Integer(1))
				.gols2(new Integer(2))
				.build();
		return jogo;
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

}
