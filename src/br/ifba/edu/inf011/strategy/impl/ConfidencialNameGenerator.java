package br.ifba.edu.inf011.strategy.impl;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.strategy.AbstractNameGenerator;

public class ConfidencialNameGenerator extends AbstractNameGenerator { // Strategy: Concrete Strategy

  @Override
  public String generateName(Documento documento) {
    return "DOC-" + System.currentTimeMillis();
  }

  @Override
  public String getStrategyName() {
    return "Confidencial";
  }

}
