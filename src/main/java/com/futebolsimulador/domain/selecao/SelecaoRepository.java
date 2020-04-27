package com.futebolsimulador.domain.selecao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SelecaoRepository extends JpaRepository<Selecao,Long> {
	
	boolean existsByNome(String nome);
	
	boolean existsByAbrev(String abrev);
	
	boolean existsByBandeira(String bandeira);
	
	@Query("select s from Selecao s where s.nome = ?1 and s.id <> ?2")
	List<Selecao> existsByNome(String nome, Long id);
	
	@Query("select s from Selecao s where s.abrev = ?1 and s.id <> ?2")
	List<Selecao> existsByAbrev(String abrev, Long id);
	
	@Query("select s from Selecao s where s.bandeira = ?1 and s.id <> ?2")
	List<Selecao> existsByBandeira(String bandeira, Long id);

}
