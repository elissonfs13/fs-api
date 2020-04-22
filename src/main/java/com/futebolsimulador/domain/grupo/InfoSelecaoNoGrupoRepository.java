package com.futebolsimulador.domain.grupo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoSelecaoNoGrupoRepository extends JpaRepository<InfoSelecaoNoGrupo,Long> {

}
