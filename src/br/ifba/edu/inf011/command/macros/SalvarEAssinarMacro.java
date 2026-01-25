package br.ifba.edu.inf011.command.macros;

import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.command.Macro;
import br.ifba.edu.inf011.command.impl.AssinarDocumentoCommand;
import br.ifba.edu.inf011.command.impl.SalvarDocumentoCommand;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.operador.Operador;

public class SalvarEAssinarMacro extends Macro implements Command { // Command: Macro ConcreteCommand

  public SalvarEAssinarMacro(
      GerenciadorDocumentoModel manager,
      String conteudo, Operador operador
  ) throws FWDocumentException {
    super(
        new SalvarDocumentoCommand(manager, conteudo),
        new AssinarDocumentoCommand(manager, operador)
    );
  }

  @Override
  public String toString() {
    return String.format(
        "Macro: Salvar e Assinar Documento [%s]", super.toString()
    );
  }
}
