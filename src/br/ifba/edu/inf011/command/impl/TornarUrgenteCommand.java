package br.ifba.edu.inf011.command.impl;

import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.decorator.SeloUrgenciaDecorator;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class TornarUrgenteCommand implements Command {

  private final GerenciadorDocumentoModel manager;

  protected Documento previous;
  protected Documento current;

  public TornarUrgenteCommand(GerenciadorDocumentoModel manager, Documento documento) {
    this.manager = manager;

    this.previous = documento;
    this.current = documento;
  }

  @Override
  public void execute() {
    this.current = new SeloUrgenciaDecorator(this.previous);

    this.manager.atualizarRepositorio(this.previous, this.current);
    this.manager.setDocumentoAtual(this.current);
  }

  @Override
  public void undo() {
    this.manager.atualizarRepositorio(this.current, this.previous);
    this.manager.setDocumentoAtual(this.previous);
  }

  @Override
  public void redo() {
    this.manager.atualizarRepositorio(this.previous, this.current);
    this.manager.setDocumentoAtual(this.current);
  }

  @Override
  public String toString() {
    return String.format(
        "Documento '%s' se tornou urgente.",
        this.current.getNumero()
    );
  }

}
