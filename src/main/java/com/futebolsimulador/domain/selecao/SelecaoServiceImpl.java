package com.futebolsimulador.domain.selecao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebolsimulador.domain.jogo.JogoFacade;
import com.futebolsimulador.exception.ExceptionMessage;
import com.futebolsimulador.exception.ObjetoNaoEncontradoException;
import com.futebolsimulador.exception.SelecaoNaoValidadaException;
import com.futebolsimulador.exception.SelecaoParticipouCampeonatoException;
import com.futebolsimulador.infra.utils.MessageUtils;

@Service
public class SelecaoServiceImpl implements SelecaoService {
	
	@Autowired
	private SelecaoRepository selecaoRepository;
	
	@Autowired
	private JogoFacade jogoFacade;
	
	@Autowired
	protected MessageUtils messageUtil;
	
	public Selecao cadastrar(Selecao selecao) {
		validaSelecao(selecao, Boolean.FALSE);
		return selecaoRepository.save(selecao);
	}

	public List<Selecao> buscarTodos() {
		return selecaoRepository.findAll();
	}
	
	public Selecao buscarPorId(Long id) {
		Selecao selecao = selecaoRepository.findOne(id);
		if (selecao == null) {
			throw new ObjetoNaoEncontradoException(messageUtil.getMensagemErro(
					ExceptionMessage.SELECAO_ID_NAO_ENCONTRADA, Arrays.asList(id)));
		}
		return selecao;
	}
	
	public void excluir(Long idSelecao) {
		Selecao selecao = buscarPorId(idSelecao);
		if (podeExcluir(selecao)) {
			selecaoRepository.delete(selecao);
		}
	}
	
	public Selecao alterar(Selecao selecao, Long id) {
		validaSelecao(selecao, Boolean.TRUE);
		return selecaoRepository.save(alterarAtributos(selecao, id));
	}
	
	public void preCadastroSelecoes(){
		selecaoRepository.deleteAll();
		cadastraSelecoes();
	}
	
	private void validaSelecao(Selecao selecao, Boolean edicao) {
		List<String> erros = validaCamposSelecao(selecao);
		
		if (edicao) {
			erros.addAll(validaSelecoesExistentesEdicao(selecao));
		} else {
			erros.addAll(validaSelecoesExistentesCadastro(selecao));
		}
		
		if (erros.size() > 0) {
			throw new SelecaoNaoValidadaException(erros);
		}
	}
	
	private List<String> validaSelecoesExistentesCadastro(Selecao selecao) {
		List<String> erros = new ArrayList<>();
		
		if (selecaoRepository.existsByNome(selecao.getNome())) {
			erros.add(messageUtil.getMensagemErro(
					ExceptionMessage.SELECAO_NOME_CADASTRADA, Arrays.asList(selecao.getNome())));
		}
		if (selecaoRepository.existsByAbrev(selecao.getAbrev())) {
			erros.add(messageUtil.getMensagemErro(
					ExceptionMessage.SELECAO_SIGLA_CADASTRADA, Arrays.asList(selecao.getAbrev())));
		}
		if (selecaoRepository.existsByBandeira(selecao.getBandeira())) {
			erros.add(messageUtil.getMensagemErro(ExceptionMessage.SELECAO_BANDEIRA_CADASTRADA));
		}
			
		return erros;
	}
	
	private List<String> validaSelecoesExistentesEdicao(Selecao selecao) {
		List<String> erros = new ArrayList<>();
		
		if (!selecaoRepository.existsByNome(selecao.getNome(), selecao.getId()).isEmpty()) {
			erros.add(messageUtil.getMensagemErro(
					ExceptionMessage.SELECAO_NOME_CADASTRADA, Arrays.asList(selecao.getNome())));
		}
		if (!selecaoRepository.existsByAbrev(selecao.getAbrev(), selecao.getId()).isEmpty()) {
			erros.add(messageUtil.getMensagemErro(
					ExceptionMessage.SELECAO_SIGLA_CADASTRADA, Arrays.asList(selecao.getAbrev())));
		}
		if (!selecaoRepository.existsByBandeira(selecao.getBandeira(), selecao.getId()).isEmpty()) {
			erros.add(messageUtil.getMensagemErro(ExceptionMessage.SELECAO_BANDEIRA_CADASTRADA));
		}
			
		return erros;
	}
	
	private List<String> validaCamposSelecao(Selecao selecao) {
		List<String> erros = new ArrayList<>();
		
		if (selecao.getNome() == null || selecao.getNome().isEmpty()) 
			erros.add(messageUtil.getMensagemErro(ExceptionMessage.CAMPO_NOME_OBG));
		if (selecao.getAbrev() == null || selecao.getAbrev().isEmpty()) 
			erros.add(messageUtil.getMensagemErro(ExceptionMessage.CAMPO_SIGLA_OBG));
		if (selecao.getBandeira() == null || selecao.getBandeira().isEmpty()) 
			erros.add(messageUtil.getMensagemErro(ExceptionMessage.CAMPO_BANDEIRA_OBG));
		if (selecao.getConfederacao() == null) 
			erros.add(messageUtil.getMensagemErro(ExceptionMessage.CAMPO_CONFEDERACAO_OBG));
		if (selecao.getNivel() == null || selecao.getNivel() < 2 || selecao.getNivel() > 6) 
			erros.add(messageUtil.getMensagemErro(ExceptionMessage.CAMPO_NIVEL_OBG));
		
		return erros;
	}
	
	private boolean podeExcluir(Selecao selecao) {
		if (jogoFacade.selecaoParticipouCampeonato(selecao)) {
			throw new SelecaoParticipouCampeonatoException(messageUtil.getMensagemErro(
					ExceptionMessage.SELECAO_VALIDA_DELECAO, Arrays.asList(selecao.getNome())));
		}
		return true;
	}
	
	private Selecao alterarAtributos(Selecao selecaoEditada, Long id) {
		Selecao selecao = buscarPorId(id);
		selecao.setNome(selecaoEditada.getNome());
		selecao.setBandeira(selecaoEditada.getBandeira());
		selecao.setNivel(selecaoEditada.getNivel());
		selecao.setAbrev(selecaoEditada.getAbrev());
		selecao.setConfederacao(selecaoEditada.getConfederacao());
		return selecao;
	}

	private void cadastraSelecoes() {
		cadastraSelecao("Argentina", "ARG", "AR.png", Confederacao.CONMEBOL, new Integer(6));
		cadastraSelecao("Bolívia", "BOL", "BO.png", Confederacao.CONMEBOL, new Integer(2));
		cadastraSelecao("Brasil", "BRA", "BR.png", Confederacao.CONMEBOL, new Integer(6));
		cadastraSelecao("Chile", "CHI", "CL.png", Confederacao.CONMEBOL, new Integer(4));
		cadastraSelecao("Colômbia", "COL", "CO.png", Confederacao.CONMEBOL, new Integer(4));
		cadastraSelecao("Equador", "EQU", "EC.png", Confederacao.CONMEBOL, new Integer(3));
		cadastraSelecao("Paraguai", "PAR", "PY.png", Confederacao.CONMEBOL, new Integer(3));
		cadastraSelecao("Peru", "PER", "PE.png", Confederacao.CONMEBOL, new Integer(3));
		cadastraSelecao("Uruguai", "URU", "UY.png", Confederacao.CONMEBOL, new Integer(5));
		cadastraSelecao("Venezuela", "VEN", "VE.png", Confederacao.CONMEBOL, new Integer(2));
		
		cadastraSelecao("Canadá", "CAN", "CA.png", Confederacao.CONCACAF, new Integer(2));
		cadastraSelecao("Costa Rica", "CSR", "CR.png", Confederacao.CONCACAF, new Integer(3));
		cadastraSelecao("Estados Unidos", "EUA", "US.png", Confederacao.CONCACAF, new Integer(4));
		cadastraSelecao("Honduras", "HON", "HN.png", Confederacao.CONCACAF, new Integer(2));
		cadastraSelecao("México", "MEX", "MX.png", Confederacao.CONCACAF, new Integer(5));
		cadastraSelecao("Panamá", "PAN", "PA.png", Confederacao.CONCACAF, new Integer(2));
		
		cadastraSelecao("Alemanha", "ALE", "DE.png", Confederacao.UEFA, new Integer(6));
		cadastraSelecao("Áustria", "AUT", "AT.png", Confederacao.UEFA, new Integer(2));
		cadastraSelecao("Bélgica", "BEL", "BE.png", Confederacao.UEFA, new Integer(4));
		cadastraSelecao("Bósnia", "BOS", "BA.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Bulgária", "BUL", "BG.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Croácia", "CRO", "CT.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Dinamarca", "DIN", "DK.png", Confederacao.UEFA, new Integer(4));
		cadastraSelecao("Escócia", "ESC", "SC.png", Confederacao.UEFA, new Integer(2));
		cadastraSelecao("Eslováquia", "ESK", "SK.png", Confederacao.UEFA, new Integer(2));
		cadastraSelecao("Eslovênia", "ESN", "SI.png", Confederacao.UEFA, new Integer(2));
		cadastraSelecao("Espanha", "ESP", "ES.png", Confederacao.UEFA, new Integer(6));
		cadastraSelecao("França", "FRA", "FR.png", Confederacao.UEFA, new Integer(5));
		cadastraSelecao("Grécia", "GRE", "GR.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Holanda", "HOL", "NL.png", Confederacao.UEFA, new Integer(5));
		cadastraSelecao("Hungria", "HUN", "HU.png", Confederacao.UEFA, new Integer(2));
		cadastraSelecao("Inglaterra", "ING", "EN.png", Confederacao.UEFA, new Integer(5));
		cadastraSelecao("Irlanda", "IRL", "IE.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Irlanda do Norte", "IRN", "JE.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Islândia", "ISL", "IS.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Itália", "ITA", "IT.png", Confederacao.UEFA, new Integer(6));
		cadastraSelecao("Noruega", "NOR", "NO.png", Confederacao.UEFA, new Integer(2));
		cadastraSelecao("País de Gales", "PGA", "WA.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Polônia", "POL", "PL.png", Confederacao.UEFA, new Integer(4));
		cadastraSelecao("Portugal", "POR", "PT.png", Confederacao.UEFA, new Integer(5));
		cadastraSelecao("Rep. Tcheca", "RTC", "CZ.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Romênia", "ROM", "RO.png", Confederacao.UEFA, new Integer(2));
		cadastraSelecao("Rússia", "RUS", "RU.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Sérvia", "SER", "RS.png", Confederacao.UEFA, new Integer(4));
		cadastraSelecao("Suécia", "SUE", "SE.png", Confederacao.UEFA, new Integer(4));
		cadastraSelecao("Suiça", "SUI", "CH.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Turquia", "TUR", "TR.png", Confederacao.UEFA, new Integer(3));
		cadastraSelecao("Ucrânia", "UCR", "UA.png", Confederacao.UEFA, new Integer(3));
		
		cadastraSelecao("Arábia Saudita", "SAU", "SA.png", Confederacao.AFC, new Integer(2));
		cadastraSelecao("Austrália", "AUS", "AU.png", Confederacao.AFC, new Integer(3));
		cadastraSelecao("China", "CHN", "CN.png", Confederacao.AFC, new Integer(2));
		cadastraSelecao("Coreia do Sul", "COR", "KR.png", Confederacao.AFC, new Integer(3));
		cadastraSelecao("Irã", "IRA", "IR.png", Confederacao.AFC, new Integer(2));
		cadastraSelecao("Japão", "JAP", "JP.png", Confederacao.AFC, new Integer(3));
		cadastraSelecao("Qatar", "QAT", "QA.png", Confederacao.AFC, new Integer(2));
		cadastraSelecao("Uzbequistão", "UZB", "UZ.png", Confederacao.AFC, new Integer(2));
		
		cadastraSelecao("Nova Zelândia", "NZL", "NZ.png", Confederacao.OFC, new Integer(2));
		
		cadastraSelecao("África do Sul", "AFS", "ZA.png", Confederacao.CAF, new Integer(2));
		cadastraSelecao("Argélia", "AGL", "DZ.png", Confederacao.CAF, new Integer(2));
		cadastraSelecao("Camarões", "CAM", "CM.png", Confederacao.CAF, new Integer(3));
		cadastraSelecao("Costa do Marfim", "CMF", "CI.png", Confederacao.CAF, new Integer(4));
		cadastraSelecao("Egito", "EGI", "EG.png", Confederacao.CAF, new Integer(3));
		cadastraSelecao("Gana", "GAN", "GH.png", Confederacao.CAF, new Integer(4));
		cadastraSelecao("Marrocos", "MAR", "MA.png", Confederacao.CAF, new Integer(3));
		cadastraSelecao("Nigéria", "NIG", "NG.png", Confederacao.CAF, new Integer(4));
		cadastraSelecao("Senegal", "SEN", "SN.png", Confederacao.CAF, new Integer(3));
		cadastraSelecao("Tunísia", "TUN", "TN.png", Confederacao.CAF, new Integer(2));
	}
	
	private void cadastraSelecao(String nome, String abrev, String bandeira, Confederacao confederacao, Integer nivel){
		Selecao selecao = Selecao.builder() 
				.nome(nome)
				.abrev(abrev)
				.bandeira(bandeira)
				.nivel(nivel)
				.confederacao(confederacao)
				.build();;
		
		selecaoRepository.save(selecao);
	}

}
