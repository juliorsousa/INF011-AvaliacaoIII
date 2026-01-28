package br.ifba.edu.inf011.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;

public interface NameGenerator { // Strategy: Strategy Interface

  String generateName(Documento documento);
  String getStrategyName();

}
