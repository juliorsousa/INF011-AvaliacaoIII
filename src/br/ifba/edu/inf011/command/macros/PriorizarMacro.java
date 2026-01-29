package br.ifba.edu.inf011.command.macros;

import br.ifba.edu.inf011.command.Macro;
import br.ifba.edu.inf011.command.impl.AssinarDocumentoCommand;
import br.ifba.edu.inf011.command.impl.TornarUrgenteCommand;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.operador.Operador;

public class PriorizarMacro extends Macro { // Command: Macro ConcreteCommand

  public PriorizarMacro(
      GerenciadorDocumentoModel manager,
      Operador operador
  ) {
    super(
        new TornarUrgenteCommand(manager),
        new AssinarDocumentoCommand(manager, operador)
    );
  }

  @Override
  public String toString() {
    return String.format(
        "Macro: Priorizar [%s]", super.toString()
    );
  }

}
