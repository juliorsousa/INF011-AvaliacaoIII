package br.ifba.edu.inf011.strategy.impl;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.strategy.AbstractNameGenerator;
import java.time.LocalDate;

public class PessoalNameGenerator extends AbstractNameGenerator { // Strategy: Concrete Strategy

  @Override
  public String generateName(Documento documento) {
    return "PES-" + LocalDate.now().getDayOfYear() + "-" + documento.getProprietario().hashCode();
  }

  @Override
  public String getStrategyName() {
    return "Pessoal";
  }

}
