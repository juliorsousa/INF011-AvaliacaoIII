package br.ifba.edu.inf011.command.impl;

import br.ifba.edu.inf011.command.AbstractDocumentCommand;
import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.proxy.DocumentoConfidencial;

public class ProtegerDocumentoCommand extends AbstractDocumentCommand implements Command { // Command: ConcreteCommand

  public ProtegerDocumentoCommand(GerenciadorDocumentoModel manager) {
    super(manager);
  }

  @Override
  public void execute() {
    this.previous = this.getDocumentoAtual();
    this.current = new DocumentoConfidencial(this.previous);

    this.atualizarRepositorio(this.previous, this.current);
    this.setDocumentoAtual(this.current);
  }

  @Override
  public void undo() {
    this.atualizarRepositorio(this.current, this.previous);
    this.setDocumentoAtual(this.previous);
  }

  @Override
  public void redo() {
    this.execute();
  }

  @Override
  public String toString() {
    return String.format(
        "Documento '%s' foi protegido como confidencial.",
        this.getDocumentoAtual().getNumero()
    );
  }

}
