package com.futebolsimulador.domain.jogo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.futebolsimulador.domain.selecao.Selecao;

@Repository
public interface JogoRepository extends JpaRepository<Jogo,Long> {
	
	public List<Jogo> findBySelecao1(Selecao selecao);

}
