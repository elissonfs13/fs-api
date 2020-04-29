package com.futebolsimulador.domain.jogo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.futebolsimulador.domain.campeonato.Campeonato;
import com.futebolsimulador.domain.grupo.Grupo;
import com.futebolsimulador.domain.selecao.Selecao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Jogo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Selecao selecao1;
	
	private Integer gols1;
	
	@ManyToOne
	private Selecao selecao2;
	
	private Integer gols2;
	
	private Boolean podeEmpatar;
	
	private Resultado resultado;
	
	@ManyToOne
	@JoinColumn(name = "grupo_id")
	@JsonBackReference
	@Setter
	private Grupo grupo;
	
	@ManyToOne
    @JoinColumn(name="oitavas_campeonato_id")
    @JsonBackReference
    @Setter
	private Campeonato oitavas;
	
	@ManyToOne
    @JoinColumn(name="quartas_campeonato_id")
    @JsonBackReference
    @Setter
	private Campeonato quartas;
	
	@ManyToOne
    @JoinColumn(name="semis_campeonato_id")
    @JsonBackReference
    @Setter
	private Campeonato semis;
	
	public Resultado verificarResultado() {
		if (this.gols1 > this.gols2){
			return Resultado.TIME1;
		} else if (this.gols1 < this.gols2){
			return Resultado.TIME2;
		} else {
			return Resultado.EMPATE;
		}
	}
	
	public Selecao getVencedor() {
		if (Resultado.TIME1.equals(this.verificarResultado())){
			return this.selecao1;
		} else {
			return this.selecao2;
		}
	}
	
	public Selecao getPerdedor() {
		if (Resultado.TIME2.equals(this.verificarResultado())){
			return this.selecao1;
		} else {
			return this.selecao2;
		}
	}

}
