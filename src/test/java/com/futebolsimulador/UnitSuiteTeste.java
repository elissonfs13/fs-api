package com.futebolsimulador;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.futebolsimulador.domain.campeonato.CampeonatoServiceTest;
import com.futebolsimulador.domain.grupo.GrupoServiceTest;
import com.futebolsimulador.domain.jogo.JogoServiceTest;
import com.futebolsimulador.domain.selecao.SelecaoServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	CampeonatoServiceTest.class,
	GrupoServiceTest.class,
	JogoServiceTest.class,
	SelecaoServiceTest.class
})
public class UnitSuiteTeste {
	
}
