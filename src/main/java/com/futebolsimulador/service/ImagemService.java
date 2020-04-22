package com.futebolsimulador.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ImagemService {
	
	public List<String> getNomeArquivos(){
		List<String> nomeArquivos = new ArrayList<>();
		File pasta = new File("src/main/resources/static/img/bandeiras");
		File[] arquivos = pasta.listFiles();
		for (File arquivo : arquivos) {
			if (arquivo.isFile()) {
				nomeArquivos.add(arquivo.getName());
			}
		}
		return nomeArquivos;
	}

}
