package com.futebolsimulador.domain.campeonato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato,Long> {
	
	public List<Campeonato> findAllByOrderByIdDesc();
	public List<Campeonato> findAllByOrderByIdAsc();

}
