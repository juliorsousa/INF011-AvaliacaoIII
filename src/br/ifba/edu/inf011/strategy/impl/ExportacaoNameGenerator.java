package br.ifba.edu.inf011.strategy.impl;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.strategy.AbstractNameGenerator;

public class ExportacaoNameGenerator extends AbstractNameGenerator { // Strategy: Concrete Strategy

  @Override
  public String generateName(Documento documento) {
    if (documento.getPrivacidade() == Privacidade.SIGILOSO) {
      return "SECURE-" + documento.hashCode();
    } else
      return "PUB-" + documento.hashCode();
  }

  @Override
  public String getStrategyName() {
    return "Exportação";
  }

}
