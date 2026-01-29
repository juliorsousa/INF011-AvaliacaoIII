package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.strategy.NameGenerator;

import br.ifba.edu.inf011.model.documentos.Documento;

public class Autenticador { // Strategy: Context
	
	public void autenticar(NameGenerator strategy, Documento documento) {
    documento.setNumero(strategy.generateName(documento));
	}

}
