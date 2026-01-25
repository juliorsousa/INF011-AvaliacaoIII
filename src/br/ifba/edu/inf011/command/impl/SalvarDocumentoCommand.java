package br.ifba.edu.inf011.command.impl;

import br.ifba.edu.inf011.command.AbstractDocumentCommand;
import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.decorator.AssinaturaDecorator;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;

public class SalvarDocumentoCommand extends AbstractDocumentCommand implements Command { // Command: ConcreteCommand

  protected String previousContent;
  protected String newContent;

  public SalvarDocumentoCommand(GerenciadorDocumentoModel manager, String newContent) {
    super(manager);

    this.newContent = newContent;
  }

  @Override
  public void execute() {
    try {
      this.previous = this.getDocumentoAtual();
      this.previousContent = this.previous.getConteudo();

      this.current = this.previous;

      if (this.previous instanceof AssinaturaDecorator) { // TODO: ESPERAR VALIDAÇÃO DE REQUISITO
        throw new IllegalStateException("Não é possível editar um documento assinado.");
      }

      this.previous.setConteudo(newContent);
    } catch (FWDocumentException e) {
      throw new RuntimeException("Erro ao salvar o documento: " + e.getMessage(), e);
    }
  }

  @Override
  public void undo() {
    this.current.setConteudo(previousContent);
    this.setDocumentoAtual(this.current);
  }

  @Override
  public void redo() {
    this.execute();
  }

  @Override
  public String toString() {
    Integer previousContentLength =
        this.previousContent != null ? this.previousContent.length() : 0;

    return String.format(
        "Documento '%s' teve seu conteúdo alterado. (%d -> %d caracteres)",
        this.getDocumentoAtual().getNumero(),
        previousContentLength,
        this.newContent.length()
    );
  }

}
