package com.futebolsimulador.domain.campeonato;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.futebolsimulador.domain.grupo.Grupo;
import com.futebolsimulador.domain.jogo.Jogo;
import com.futebolsimulador.domain.selecao.Selecao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Campeonato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Selecao sede;
	
	@OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL)
	private List<Grupo> grupos;
	
	@JoinTable(name = "oitavas_final", joinColumns = {
			@JoinColumn(name = "campeonato_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "jogo_id", referencedColumnName = "id", nullable = false) })
	@ManyToMany(cascade = CascadeType.ALL)
	@OrderBy("id")
	private List<Jogo> oitavasFinal;
	
	@JoinTable(name = "quartas_final", joinColumns = {
			@JoinColumn(name = "campeonato_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "jogo_id", referencedColumnName = "id", nullable = false) })
	@ManyToMany(cascade = CascadeType.ALL)
	@OrderBy("id")
	private List<Jogo> quartasFinal;
	
	@JoinTable(name = "semi_final", joinColumns = {
			@JoinColumn(name = "campeonato_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "jogo_id", referencedColumnName = "id", nullable = false) })
	@ManyToMany(cascade = CascadeType.ALL)
	@OrderBy("id")
	private List<Jogo> semiFinal;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Jogo terceiroQuarto;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Jogo finalCampeonato;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Selecao primeiro;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Selecao segundo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Selecao terceiro;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Selecao quarto;

	public Campeonato(Selecao sede) {
		super();
		this.sede = sede;
	}
	
	public void geraClassificacao() {
		this.primeiro = this.finalCampeonato.getVencedor();
		this.segundo = this.finalCampeonato.getPerdedor();
		this.terceiro = this.terceiroQuarto.getVencedor();
		this.quarto = this.terceiroQuarto.getPerdedor();
	}

	
}
