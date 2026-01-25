package br.ifba.edu.inf011.command.macros;

import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.command.Macro;
import br.ifba.edu.inf011.command.impl.AssinarDocumentoCommand;
import br.ifba.edu.inf011.command.impl.TornarUrgenteCommand;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;

public class PriorizarMacro extends Macro implements Command {

  public PriorizarMacro(
      GerenciadorDocumentoModel manager,
      Documento documento,
      Operador operador
  ) {
    super(
        new TornarUrgenteCommand(manager, documento),
        new AssinarDocumentoCommand(manager, documento, operador)
    );
  }

  @Override
  public String toString() {
    return String.format(
        "Macro: Priorizar [%s]", super.toString()
    );
  }
}
