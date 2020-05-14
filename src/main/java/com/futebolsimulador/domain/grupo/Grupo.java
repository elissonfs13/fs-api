package com.futebolsimulador.domain.grupo;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.futebolsimulador.domain.campeonato.Campeonato;
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
public class Grupo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@OneToMany(mappedBy = "grupo")
	@OrderBy(value = "classificacao")
	private List<InfoSelecaoNoGrupo> infoSelecoes;
	
	@OneToMany(mappedBy = "grupo")
	@OrderBy(value = "id")
	private List<Jogo> jogos;
	
	@ManyToOne
    @JoinColumn(name="campeonato_id")
    @JsonBackReference
	private Campeonato campeonato;

	public Grupo(String nome) {
		super();
		this.nome = nome;
	}
	
	public Selecao getPrimeiro() {
		return this.infoSelecoes.get(0).getSelecao();
	}
	
	public Selecao getSegundo() {
		return this.infoSelecoes.get(1).getSelecao();
	}
	
	public Selecao getTerceiro() {
		return this.infoSelecoes.get(2).getSelecao();
	}
	
	public Selecao getQuarto() {
		return this.infoSelecoes.get(3).getSelecao();
	}
	
	public boolean selecaoEstaPresente(Selecao selecao) {
		for (Jogo jogo : this.jogos) {
			if (jogo.selecaoEstaPresente(selecao)) {
				return true;
			}
		}
		return false;
	}
	
	public List<Selecao> getSelecoes() {
		return this.infoSelecoes.stream()
				.map(infoSelecao -> infoSelecao.getSelecao())
				.collect(Collectors.toList());
	}

}
