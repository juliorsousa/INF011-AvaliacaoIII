package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.strategy.NameGeneratingStrategy;

import br.ifba.edu.inf011.model.documentos.Documento;

public class Autenticador {
	
	public void autenticar(NameGeneratingStrategy strategy, Documento documento) { // Strategy: Context
    documento.setNumero(strategy.generateName(documento));
	}

}
