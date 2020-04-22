package com.futebolsimulador.domain.selecao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelecaoRepository extends JpaRepository<Selecao,Long> {

}
