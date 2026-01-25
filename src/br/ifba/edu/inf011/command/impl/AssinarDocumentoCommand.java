package br.ifba.edu.inf011.command.impl;

import br.ifba.edu.inf011.command.AbstractDocumentCommand;
import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.decorator.AssinaturaDecorator;
import br.ifba.edu.inf011.model.Assinatura;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;
import java.time.LocalDateTime;

public class AssinarDocumentoCommand extends AbstractDocumentCommand implements Command { // Command: ConcreteCommand

  private final Operador operador;

  public AssinarDocumentoCommand(GerenciadorDocumentoModel manager, Operador operador) {
    super(manager);

    this.operador = operador;
  }

  @Override
  public void execute() {
    this.previous = this.getDocumentoAtual();

    System.out.println("[Assinatura] Classe do documento antes da assinatura: " + this.previous.getClass().getSimpleName());

    Assinatura assinatura = new Assinatura(operador, LocalDateTime.now());

    this.current = new AssinaturaDecorator(this.previous, assinatura);

    this.atualizarRepositorio(this.previous, this.current);
    this.setDocumentoAtual(this.current);

    System.out.println("[Assinatura] Classe do documento após a assinatura: " + this.current.getClass().getSimpleName());
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
        "Documento '%s' foi assinado por '%s'.",
        this.getDocumentoAtual().getNumero(),
        this.operador.getNome()
    );
  }

}
