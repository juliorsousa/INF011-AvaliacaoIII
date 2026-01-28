package br.ifba.edu.inf011.strategy.impl;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.strategy.AbstractNameGenerator;
import java.time.LocalDate;

public class CriminalNameGenerator extends AbstractNameGenerator { // Strategy: Concrete Strategy

  @Override
  public String generateName(Documento documento) {
    return "CRI-" + LocalDate.now().getYear() + "-" + documento.hashCode();
  }

  @Override
  public String getStrategyName() {
    return "Criminal";
  }

}
