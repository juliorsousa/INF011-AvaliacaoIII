package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public abstract class AbstractDocumentCommand implements Command { // Command: AbstractCommand

  private final GerenciadorDocumentoModel manager;

  protected Documento previous;
  protected Documento current;

  public AbstractDocumentCommand(GerenciadorDocumentoModel manager) {
    this.manager = manager;
  }

  protected Documento getDocumentoAtual() {
    return this.manager.getDocumentoAtual();
  }

  protected void setDocumentoAtual(Documento documento) {
    this.manager.setDocumentoAtual(documento);
  }

  protected void atualizarRepositorio(
      Documento antigo,
      Documento novo
  ) {
    this.manager.atualizarRepositorio(antigo, novo);
  }

}
