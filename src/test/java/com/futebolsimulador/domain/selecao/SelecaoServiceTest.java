package com.futebolsimulador.domain.selecao;

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

import com.futebolsimulador.domain.jogo.JogoFacade;
import com.futebolsimulador.exception.ObjetoNaoEncontradoException;
import com.futebolsimulador.exception.SelecaoNaoValidadaException;
import com.futebolsimulador.exception.SelecaoParticipouCampeonatoException;
import com.futebolsimulador.infra.utils.MessageUtils;

@RunWith(MockitoJUnitRunner.class)
public class SelecaoServiceTest {
	
	@Mock
	private SelecaoRepository selecaoRepository;
	
	@Mock
	private JogoFacade jogoFacade;
	
	@Mock
	protected MessageUtils messageUtil;
	
	@InjectMocks
	private SelecaoServiceImpl selecaoService;
	
	private static final Long SELECAO_ID = 1L;
	private Selecao selecao;
	private List<Selecao> selecoes;
	
	@Before
	public void init() {
		selecao = criaSelecao();
		selecoes = new ArrayList<>();
		selecoes.add(selecao);
	}
	
	@Test
	public void buscarPorIdTest(){
		when(selecaoRepository.findOne(SELECAO_ID)).thenReturn(selecao);
		final Selecao selecaoRetornada = selecaoService.buscarPorId(SELECAO_ID);
		assertNotNull(selecaoRetornada);
		assertEquals(selecao, selecaoRetornada);
		verify(selecaoRepository, times(1)).findOne(SELECAO_ID);
	}
	
	@Test(expected = ObjetoNaoEncontradoException.class)
	public void buscarPorIdSelecaoNaoEncontradaTest(){
		when(selecaoRepository.findOne(SELECAO_ID)).thenReturn(null);
		selecaoService.buscarPorId(SELECAO_ID);
	}
	
	@Test
	public void buscarTodosTest(){
		when(selecaoRepository.findAll()).thenReturn(selecoes);
		final List<Selecao> selecoesRetornadas = selecaoService.buscarTodos();
		assertNotNull(selecoesRetornadas);
		assertEquals(1, selecoesRetornadas.size());
		assertEquals(selecoes, selecoesRetornadas);
		assertEquals(selecao, selecoesRetornadas.get(0));
		verify(selecaoRepository, times(1)).findAll();
	}
	
	@Test
	public void cadastrarTest() {
		when(selecaoRepository.save(selecao)).thenReturn(selecao);
		when(selecaoRepository.existsByNome(selecao.getNome())).thenReturn(false);
		when(selecaoRepository.existsByAbrev(selecao.getAbrev())).thenReturn(false);
		when(selecaoRepository.existsByBandeira(selecao.getBandeira())).thenReturn(false);
		
		final Selecao selecaoRetornada = selecaoService.cadastrar(selecao);
		assertNotNull(selecaoRetornada);
		assertEquals(selecao, selecaoRetornada);
		verify(selecaoRepository, times(1)).save(selecao);
	}
	
	@Test(expected = SelecaoNaoValidadaException.class)
	public void cadastrarSelecaoExistenteTest() {
		when(selecaoRepository.existsByNome(selecao.getNome())).thenReturn(true);
		when(selecaoRepository.existsByAbrev(selecao.getAbrev())).thenReturn(true);
		when(selecaoRepository.existsByBandeira(selecao.getBandeira())).thenReturn(true);
		
		selecaoService.cadastrar(selecao);
	}
	
	@Test(expected = SelecaoNaoValidadaException.class)
	public void cadastrarSelecaoCamposNulosTest() {
		when(selecaoRepository.existsByNome(selecao.getNome())).thenReturn(false);
		when(selecaoRepository.existsByAbrev(selecao.getAbrev())).thenReturn(false);
		when(selecaoRepository.existsByBandeira(selecao.getBandeira())).thenReturn(false);
		
		selecaoService.cadastrar(new Selecao());
	}
	
	@Test(expected = SelecaoNaoValidadaException.class)
	public void cadastrarSelecaoCamposVaziosTest() {
		when(selecaoRepository.existsByNome(selecao.getNome())).thenReturn(false);
		when(selecaoRepository.existsByAbrev(selecao.getAbrev())).thenReturn(false);
		when(selecaoRepository.existsByBandeira(selecao.getBandeira())).thenReturn(false);
		
		selecaoService.cadastrar(criaSelecaoCamposVazios());
	}
	
	@Test
	public void excluirTest() {
		when(selecaoRepository.findOne(SELECAO_ID)).thenReturn(selecao);
		when(jogoFacade.selecaoParticipouCampeonato(selecao)).thenReturn(false);
		selecaoService.excluir(SELECAO_ID);
		verify(selecaoRepository, times(1)).delete(selecao);
	}
	
	@Test(expected = SelecaoParticipouCampeonatoException.class)
	public void excluirSelecaoParticipouCampeonatoTest() {
		when(selecaoRepository.findOne(SELECAO_ID)).thenReturn(selecao);
		when(jogoFacade.selecaoParticipouCampeonato(selecao)).thenReturn(true);
		selecaoService.excluir(SELECAO_ID);
	}
	
	@Test
	public void alterarTest() {
		final String nomeEditado = "Selecao Editada";
		Selecao selecaoEditada = criaSelecao();
		selecaoEditada.setNome(nomeEditado);
		
		when(selecaoRepository.findOne(SELECAO_ID)).thenReturn(selecao);
		when(selecaoRepository.save(selecao)).thenReturn(selecaoEditada);
		when(selecaoRepository.existsByNome(selecao.getNome())).thenReturn(false);
		when(selecaoRepository.existsByAbrev(selecao.getAbrev())).thenReturn(false);
		when(selecaoRepository.existsByBandeira(selecao.getBandeira())).thenReturn(false);
		
		final Selecao selecaoRetornada = selecaoService.alterar(selecaoEditada, SELECAO_ID);
		assertNotNull(selecaoRetornada);
		assertEquals(nomeEditado, selecaoRetornada.getNome());
		verify(selecaoRepository, times(1)).save(selecaoEditada);
	}
	
	@Test(expected = SelecaoNaoValidadaException.class)
	public void alterarSelecaoExistenteTest() {
		final String nomeEditado = "Selecao Editada";
		Selecao selecaoEditada = criaSelecao();
		selecaoEditada.setNome(nomeEditado);
		
		when(selecaoRepository.findOne(SELECAO_ID)).thenReturn(selecao);
		when(selecaoRepository.save(selecao)).thenReturn(selecaoEditada);
		when(selecaoRepository.existsByNome(selecaoEditada.getNome(), selecaoEditada.getId())).thenReturn(selecoes);
		when(selecaoRepository.existsByAbrev(selecaoEditada.getAbrev(), selecaoEditada.getId())).thenReturn(selecoes);
		when(selecaoRepository.existsByBandeira(selecaoEditada.getBandeira(), selecaoEditada.getId())).thenReturn(selecoes);
		
		selecaoService.alterar(selecaoEditada, SELECAO_ID);
	}
	
	@Test
	public void preCadastroSelecoesTest() {
		when(selecaoRepository.save(selecao)).thenReturn(selecao);
		
		selecaoService.preCadastroSelecoes();
		verify(selecaoRepository, times(1)).deleteAll();
		verify(selecaoRepository, times(67)).save(Mockito.any(Selecao.class));
	}

	private Selecao criaSelecao() {
		final Selecao selecao = Selecao.builder()
				.id(SELECAO_ID)
				.abrev("SEL")
				.nome("Selecao")
				.bandeira("bn.png")
				.nivel(new Integer(2))
				.confederacao(Confederacao.AFC)
				.build();
		return selecao;
	}
	
	private Selecao criaSelecaoCamposVazios() {
		final Selecao selecao = Selecao.builder()
				.id(SELECAO_ID)
				.abrev("")
				.nome("")
				.bandeira("")
				.nivel(new Integer(1))
				.confederacao(Confederacao.AFC)
				.build();
		return selecao;
	}
	
	

}
