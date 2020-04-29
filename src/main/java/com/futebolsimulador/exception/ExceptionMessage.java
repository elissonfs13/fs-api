package com.futebolsimulador.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionMessage {
	
	CAMPO_NOME_OBG("campo.selecao.nome.obrigatorio"),
	CAMPO_SIGLA_OBG("campo.selecao.abrev.obrigatorio"),
	CAMPO_BANDEIRA_OBG("campo.selecao.bandeira.obrigatorio"),
	CAMPO_CONFEDERACAO_OBG("campo.selecao.confederacao.obrigatorio"),
	CAMPO_NIVEL_OBG("campo.selecao.nivel.obrigatorio"),
	
	SELECAO_NOME_CADASTRADA("selecao.nome.cadastrado"),
	SELECAO_SIGLA_CADASTRADA("selecao.sigla.cadastrado"),
	SELECAO_BANDEIRA_CADASTRADA("selecao.bandeira.cadastrado"),
	
	SELECAO_NAO_ENCONTRADA("selecao.naoencontrada"),
	SELECAO_ID_NAO_ENCONTRADA("selecao.id.naoencontrada"),
	SELECAO_VALIDA_DELECAO("selecao.valida.delecao"),
	
	CAMPEONATO_NAO_ENCONTRADO("campeonato.naoencontrado"),
	CAMPEONATO_ID_NAO_ENCONTRADO("campeonato.id.naoencontrado"),
	CAMPEONATO_ERRO_GERAR("campeonato.erro.gerar");

	@Getter
	private String value;
	
}
