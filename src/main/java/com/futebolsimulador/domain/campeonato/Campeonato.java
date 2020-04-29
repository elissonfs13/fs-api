package com.futebolsimulador.domain.campeonato;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.futebolsimulador.domain.grupo.Grupo;
import com.futebolsimulador.domain.jogo.Jogo;
import com.futebolsimulador.domain.selecao.Selecao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Campeonato implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Selecao sede;
	
	@OneToMany(mappedBy = "campeonato", fetch=FetchType.EAGER)
	private List<Grupo> grupos;
	
	@OneToMany(mappedBy = "oitavas", fetch=FetchType.EAGER)
	@OrderBy(value = "id")
	private List<Jogo> oitavasFinal;
	
	@OneToMany(mappedBy = "quartas", fetch=FetchType.EAGER)
	@OrderBy(value = "id")
	private List<Jogo> quartasFinal;
	
	@OneToMany(mappedBy = "semis", fetch=FetchType.EAGER)
	@OrderBy(value = "id")
	private List<Jogo> semiFinal;
	
	@ManyToOne
	private Jogo terceiroQuarto;
	
	@ManyToOne
	private Jogo finalCampeonato;
	
	@ManyToOne
	private Selecao primeiro;
	
	@ManyToOne
	private Selecao segundo;
	
	@ManyToOne
	private Selecao terceiro;
	
	@ManyToOne
	private Selecao quarto;

	public Campeonato(Selecao sede) {
		super();
		this.sede = sede;
	}
	
	public void geraClassificacao() {
		if (this.finalCampeonato != null) {
			this.primeiro = this.finalCampeonato.getVencedor();
			this.segundo = this.finalCampeonato.getPerdedor();
		}
		if (this.terceiroQuarto != null) {
			this.terceiro = this.terceiroQuarto.getVencedor();
			this.quarto = this.terceiroQuarto.getPerdedor();
		}
	}

	
}
