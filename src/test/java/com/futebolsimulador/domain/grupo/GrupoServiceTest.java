package com.futebolsimulador.domain.grupo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.futebolsimulador.domain.campeonato.Campeonato;
import com.futebolsimulador.domain.jogo.Jogo;
import com.futebolsimulador.domain.jogo.JogoFacade;
import com.futebolsimulador.domain.selecao.Confederacao;
import com.futebolsimulador.domain.selecao.Selecao;

@RunWith(MockitoJUnitRunner.class)
public class GrupoServiceTest {
	
	@Mock
	private GrupoRepository grupoRepository;
	
	@Mock
	private JogoFacade jogoFacade;
	
	@Mock
	private InfoSelecaoNoGrupoRepository infoSelecaoNoGrupoRepository;
	
	@InjectMocks
	private GrupoServiceImpl grupoService;
	
	private static final Long CAMPEONATO_ID = 1L;
	private Campeonato campeonato;
	private ArrayList<Selecao> selecoes;
	private InfoSelecaoNoGrupo info;
	
	@Before
	public void init() {
		selecoes = criaListaSelecoes();
		campeonato = criaCampeonato(selecoes.get(0));
		info = criaInfos();
	}
	
	@Test
	public void geraGruposSel1VenceTest(){
		Jogo jogo = criaJogo(2, 1);
		verificacoes(jogo);
	}
	
	@Test
	public void geraGruposSel2VenceTest(){
		Jogo jogo = criaJogo(1, 2);
		verificacoes(jogo);
	}
	
	@Test
	public void geraGruposEmpateVenceTest(){
		Jogo jogo = criaJogo(1, 1);
		verificacoes(jogo);
	}

	private void verificacoes(Jogo jogo) {
		when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(new Grupo());
		when(infoSelecaoNoGrupoRepository.save(Mockito.any(InfoSelecaoNoGrupo.class))).thenReturn(info);
		when(jogoFacade.salvaJogo(Mockito.any(Jogo.class))).thenReturn(jogo);
		when(jogoFacade.geraJogo(Mockito.any(Selecao.class), 
				Mockito.any(Selecao.class),Mockito.any(Boolean.class))).thenReturn(jogo);
		
		grupoService.geraGrupos(campeonato, selecoes);
		assertNotNull(campeonato.getGrupos());
		assertEquals(8, campeonato.getGrupos().size());
		verify(grupoRepository, times(16)).save(Mockito.any(Grupo.class));
	}

	private InfoSelecaoNoGrupo criaInfos() {
		return new InfoSelecaoNoGrupo(selecoes.get(0));
	}
	
	private Campeonato criaCampeonato(Selecao selecao) {
		Campeonato campeonato = new Campeonato(selecao);
		campeonato.setId(CAMPEONATO_ID);
		return campeonato;
	}
	
	private Jogo criaJogo(Integer gols1, Integer gols2) {
		final Jogo jogo = Jogo.builder()
				.id(1L)
				.selecao1(criaSelecao(1L, "Seleção 1", "SE1", "b1.png", 3))
				.selecao2(criaSelecao(2L, "Seleção 2", "SE2", "b2.png", 4))
				.podeEmpatar(Boolean.TRUE)
				.gols1(gols1)
				.gols2(gols2)
				.build();
		return jogo;
	}
	
	private ArrayList<Selecao> criaListaSelecoes() {
		ArrayList<Selecao> selecoes = new ArrayList<>();
		for (int i = 0; i < 32; i++) {
			selecoes.add(criaSelecao(1L, "Selecao"+i, "SE"+i, "bn1.png", new Integer(3)));
		}
		
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

}
