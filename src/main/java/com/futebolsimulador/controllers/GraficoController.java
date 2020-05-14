package com.futebolsimulador.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.futebolsimulador.controllers.dto.GraficoListaValorDTO;
import com.futebolsimulador.controllers.dto.GraficoValorDTO;
import com.futebolsimulador.domain.campeonato.CampeonatoFacade;
import com.futebolsimulador.domain.selecao.SelecaoFacade;

@RestController
@RequestMapping("/grafico")
public class GraficoController {
	
	@Autowired
	private CampeonatoFacade campeonatoFacade;
	
	@Autowired
	private SelecaoFacade selecaoFacade;
	
	@RequestMapping(method = RequestMethod.GET, value = "/campeoes/confederacao")
	public ResponseEntity<List<GraficoValorDTO>> buscarCampeoesPorConfederacao(){
		List<GraficoValorDTO> campeonatosBuscados = campeonatoFacade.buscarCampeoesPorConfederacao();
		return ResponseEntity.ok(campeonatosBuscados);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/campeoes/selecao")
	public ResponseEntity<List<GraficoValorDTO>> buscarCampeoesPorSelecao(){
		List<GraficoValorDTO> campeonatosBuscados = campeonatoFacade.buscarCampeoesPorSelecao();
		return ResponseEntity.ok(campeonatosBuscados);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/campeoes/bandeira")
	public ResponseEntity<List<GraficoValorDTO>> buscarCampeoesPorBandeira(){
		List<GraficoValorDTO> campeonatosBuscados = campeonatoFacade.buscarCampeoesPorBandeira();
		return ResponseEntity.ok(campeonatosBuscados);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/participantes/selecao")
	public ResponseEntity<List<GraficoValorDTO>> buscarParticipantesPorSelecao(){
		List<GraficoValorDTO> campeonatosBuscados = campeonatoFacade.buscarParticipantesPorSelecao();
		return ResponseEntity.ok(campeonatosBuscados);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/participantes/bandeira")
	public ResponseEntity<List<GraficoValorDTO>> buscarParticipantesPorBandeira(){
		List<GraficoValorDTO> campeonatosBuscados = campeonatoFacade.buscarParticipantesPorBandeira();
		return ResponseEntity.ok(campeonatosBuscados);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/selecao/{id}/colocacoes")
	public ResponseEntity<GraficoListaValorDTO> buscarCampeonatoPorId(@PathVariable Long id) {
		GraficoListaValorDTO graficoLinhaDTO = selecaoFacade.buscarPosicoesSelecao(id);
		return ResponseEntity.ok(graficoLinhaDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/campeonato/{id}/colocacoes")
	public ResponseEntity<List<GraficoValorDTO>> buscarCampeoesPorBandeira(@PathVariable Long id){
		List<GraficoValorDTO> posicoesBuscados = campeonatoFacade.buscarPosicoesPorCampeonato(id);
		return ResponseEntity.ok(posicoesBuscados);
	}

}
