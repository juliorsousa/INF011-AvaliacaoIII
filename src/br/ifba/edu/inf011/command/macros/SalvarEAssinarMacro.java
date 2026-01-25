package br.ifba.edu.inf011.command.macros;

import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.command.Macro;
import br.ifba.edu.inf011.command.impl.AssinarDocumentoCommand;
import br.ifba.edu.inf011.command.impl.SalvarDocumentoCommand;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;

public class SalvarEAssinarMacro extends Macro implements Command {

  public SalvarEAssinarMacro(
      GerenciadorDocumentoModel manager,
      Documento documento,
      String conteudo, Operador operador
  ) throws FWDocumentException {
    super(
        new SalvarDocumentoCommand(manager, documento, conteudo),
        new AssinarDocumentoCommand(manager, documento, operador)
    );
  }

  @Override
  public String toString() {
    return String.format(
        "Macro: Salvar e Assinar Documento [%s]", super.toString()
    );
  }
}
