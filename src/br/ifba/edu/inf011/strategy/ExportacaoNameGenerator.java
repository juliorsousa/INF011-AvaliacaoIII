package br.ifba.edu.inf011.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;

public class ExportacaoNameGenerator extends AbstractNameGeneratingStrategy { // Strategy: Concrete Strategy

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
