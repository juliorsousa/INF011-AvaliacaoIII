package br.ifba.edu.inf011.command.impl;

import br.ifba.edu.inf011.command.AbstractDocumentCommand;
import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.decorator.SeloUrgenciaDecorator;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;

public class TornarUrgenteCommand extends AbstractDocumentCommand implements Command { // Command: ConcreteCommand

  public TornarUrgenteCommand(GerenciadorDocumentoModel manager) {
    super(manager);
  }

  @Override
  public void execute() {
    this.previous = this.getDocumentoAtual();

    this.current = new SeloUrgenciaDecorator(this.previous);

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
        "Documento '%s' se tornou urgente.",
        this.getDocumentoAtual().getNumero()
    );
  }

}
