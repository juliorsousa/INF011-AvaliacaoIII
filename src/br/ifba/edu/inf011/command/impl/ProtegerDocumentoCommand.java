package br.ifba.edu.inf011.command.impl;

import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.proxy.DocumentoConfidencial;

public class ProtegerDocumentoCommand implements Command {

  private final GerenciadorDocumentoModel manager;

  protected Documento previous;
  protected Documento current;

  public ProtegerDocumentoCommand(GerenciadorDocumentoModel manager, Documento documento) {
    this.manager = manager;

    this.previous = documento;
    this.current = documento;
  }

  @Override
  public void execute() {
    this.current = new DocumentoConfidencial(this.previous);

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
        "Documento '%s' foi protegido como confidencial.",
        this.current.getNumero()
    );
  }

}
