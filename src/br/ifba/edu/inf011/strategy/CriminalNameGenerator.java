package br.ifba.edu.inf011.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;
import java.time.LocalDate;

public class CriminalNameGenerator extends AbstractNameGeneratingStrategy { // Strategy: Concrete Strategy

  @Override
  public String generateName(Documento documento) {
    return "CRI-" + LocalDate.now().getYear() + "-" + documento.hashCode();
  }

  @Override
  public String getStrategyName() {
    return "Criminal";
  }
}
