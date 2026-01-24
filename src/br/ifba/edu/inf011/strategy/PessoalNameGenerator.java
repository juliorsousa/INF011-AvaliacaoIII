package br.ifba.edu.inf011.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;
import java.time.LocalDate;

public class PessoalNameGenerator extends AbstractNameGeneratingStrategy { // Strategy: Concrete Strategy

  @Override
  public String generateName(Documento documento) {
    return "PES-" + LocalDate.now().getDayOfYear() + "-" + documento.getProprietario().hashCode();
  }

  @Override
  public String getStrategyName() {
    return "Pessoal";
  }
}
