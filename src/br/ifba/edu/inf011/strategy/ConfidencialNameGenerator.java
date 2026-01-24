package br.ifba.edu.inf011.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;

public class ConfidencialNameGenerator extends AbstractNameGeneratingStrategy { // Strategy: Concrete Strategy

  @Override
  public String generateName(Documento documento) {
    return "DOC-" + System.currentTimeMillis();
  }

  @Override
  public String getStrategyName() {
    return "Confidencial";
  }
}
