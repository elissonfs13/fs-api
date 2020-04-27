package com.futebolsimulador.domain.grupo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.futebolsimulador.domain.selecao.Selecao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InfoSelecaoNoGrupo implements Serializable, Comparable<InfoSelecaoNoGrupo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "grupo_id")
	@JsonBackReference
	private Grupo grupo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Selecao selecao;
	
	private Integer pontos;
	
	private Integer golsMarcados;
	
	private Integer golsSofridos;
	
	private Integer saldoGols;
	
	@Setter
	private Integer classificacao;

	public InfoSelecaoNoGrupo(Selecao selecao) {
		super();
		this.selecao = selecao;
		this.pontos = new Integer(0);
		this.golsMarcados = new Integer(0);
		this.golsSofridos = new Integer(0);
		this.saldoGols = new Integer(0);
	}
	
	public void adicionaPontos(Integer novosPontos) {
		this.pontos += novosPontos;
	}
	
	public void adicionaGolsMarcados(Integer novosGols) {
		this.golsMarcados += novosGols;
	}
	
	public void adicionaGolsSofridos(Integer novosGols) {
		this.golsSofridos += novosGols;
	}
	
	public void calculaSaldoGols() {
		this.saldoGols = this.golsMarcados - this.golsSofridos;
	}

	@Override
	public int compareTo(InfoSelecaoNoGrupo outraInfo) {
		if (this.pontos > outraInfo.getPontos()){
			return -1;
		} else if (this.pontos < outraInfo.getPontos()){
			return 1;
		} else {
			if (this.saldoGols > outraInfo.getSaldoGols()){
				return -1;
			} else if (this.saldoGols < outraInfo.getSaldoGols()){
				return 1;
			} else {
				if (this.golsMarcados > outraInfo.getGolsMarcados()){
					return -1;
				} else if (this.golsMarcados < outraInfo.getGolsMarcados()){
					return 1;
				}
			}
		}
		return 0;
	}
	

}
