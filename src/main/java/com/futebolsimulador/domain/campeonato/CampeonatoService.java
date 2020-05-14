package com.futebolsimulador.domain.campeonato;

import java.util.ArrayList;
import java.util.List;

import com.futebolsimulador.controllers.dto.GraficoListaValorDTO;
import com.futebolsimulador.controllers.dto.GraficoValorDTO;
import com.futebolsimulador.domain.selecao.Selecao;

public interface CampeonatoService {
	
	Campeonato novoCampeonato(ArrayList<Selecao> selecoes);
	List<Campeonato> buscarTodos();
	Campeonato buscarPorId(Long id);
	List<GraficoValorDTO> buscarCampeoesPorConfederacao();
	List<GraficoValorDTO> buscarCampeoesPorSelecao();
	List<GraficoValorDTO> buscarCampeoesPorBandeira();
	GraficoListaValorDTO buscarSelecaoEmCampeonato(Selecao selecao);
	List<GraficoValorDTO> buscarPosicoesPorCampeonato(Long campeonatoId);
	List<GraficoValorDTO> buscarParticipantesPorSelecao();
	List<GraficoValorDTO> buscarParticipantesPorBandeira();
	
}
