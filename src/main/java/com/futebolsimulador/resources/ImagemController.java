package com.futebolsimulador.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.futebolsimulador.service.ImagemService;

@RestController
public class ImagemController {
	
	@Autowired
	public ImagemService imagemService;
	
	@CrossOrigin	
	@RequestMapping(method = RequestMethod.GET, value = "/bandeiras")
	public ResponseEntity<List<String>> buscarTodasSelecoes(){
		List<String> bandeiras = imagemService.getNomeArquivos();
		return new ResponseEntity<>(bandeiras, HttpStatus.OK);
	} 

}
