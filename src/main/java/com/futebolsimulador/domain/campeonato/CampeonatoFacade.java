package com.futebolsimulador.domain.campeonato;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.futebolsimulador.controllers.dto.GraficoListaValorDTO;
import com.futebolsimulador.controllers.dto.GraficoValorDTO;
import com.futebolsimulador.domain.selecao.Selecao;

@Component
public class CampeonatoFacade {
	
	@Autowired
	private CampeonatoService campeonatoService;
	
	public Campeonato novoCampeonato(ArrayList<Selecao> selecoes) {
		return campeonatoService.novoCampeonato(selecoes);
	}
	
	public List<Campeonato> buscarTodos() {
		return campeonatoService.buscarTodos();
	}
	
	public Campeonato buscarPorId(Long id) {
		return campeonatoService.buscarPorId(id);
	}

	public List<GraficoValorDTO> buscarCampeoesPorConfederacao() {
		return campeonatoService.buscarCampeoesPorConfederacao();
	}

	public List<GraficoValorDTO> buscarCampeoesPorSelecao() {
		return campeonatoService.buscarCampeoesPorSelecao();
	}
	
	public List<GraficoValorDTO> buscarCampeoesPorBandeira() {
		return campeonatoService.buscarCampeoesPorBandeira();
	}

	public GraficoListaValorDTO buscarSelecaoEmCampeonato(Selecao selecao) {
		return campeonatoService.buscarSelecaoEmCampeonato(selecao);
	}
	
	public List<GraficoValorDTO> buscarPosicoesPorCampeonato(Long campeonatoId) {
		return campeonatoService.buscarPosicoesPorCampeonato(campeonatoId);
	}

	public List<GraficoValorDTO> buscarParticipantesPorSelecao() {
		return campeonatoService.buscarParticipantesPorSelecao();
	}

	public List<GraficoValorDTO> buscarParticipantesPorBandeira() {
		return campeonatoService.buscarParticipantesPorBandeira();
	}

}
