package com.futebolsimulador.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.futebolsimulador.infra.utils.ImagemUtil;

@RestController
public class ImagemController {
	
	@Autowired
	public ImagemUtil imagemUtil;
		
	@RequestMapping(method = RequestMethod.GET, value = "/bandeiras")
	public ResponseEntity<List<String>> buscarTodasSelecoes(){
		List<String> bandeiras = imagemUtil.getNomeArquivos();
		return new ResponseEntity<>(bandeiras, HttpStatus.OK);
	} 

}
