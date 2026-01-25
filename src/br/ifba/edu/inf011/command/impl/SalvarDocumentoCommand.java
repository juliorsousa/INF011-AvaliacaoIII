package br.ifba.edu.inf011.command.impl;

import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class SalvarDocumentoCommand implements Command {

  protected final GerenciadorDocumentoModel manager;

  protected Documento documento;

  protected String newContent;
  protected String previousContent;

  public SalvarDocumentoCommand(GerenciadorDocumentoModel manager, Documento documento, String currentContent) throws FWDocumentException {
    this.manager = manager;

    this.documento = documento;

    this.previousContent = documento.getConteudo();
    this.newContent = currentContent;
  }

  @Override
  public void execute() {
//    if (this.documento  instanceof AssinaturaDecorator) { TODO: ESPERAR VALIDAÇÃO DE REQUISITO
//      throw new IllegalStateException("Não é possível editar um documento assinado.");
//    }

    this.documento.setConteudo(newContent);
    this.manager.setDocumentoAtual(this.documento);
  }

  @Override
  public void undo() {
    this.documento.setConteudo(previousContent);
    this.manager.setDocumentoAtual(this.documento);
  }

  @Override
  public void redo() {
    this.execute();
  }

  @Override
  public String toString() {
    return String.format(
        "Documento '%s' teve seu conteúdo alterado. (%d -> %d caracteres)",
        this.documento.getNumero(),
        this.previousContent != null ? this.previousContent.length() : 0,
        this.newContent.length()
    );
  }

}
