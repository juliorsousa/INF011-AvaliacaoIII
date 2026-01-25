package br.ifba.edu.inf011.command.impl;

import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.decorator.AssinaturaDecorator;
import br.ifba.edu.inf011.model.Assinatura;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;
import java.time.LocalDateTime;

public class AssinarDocumentoCommand implements Command {

  private final Operador operador;
  private final GerenciadorDocumentoModel manager;

  protected Documento previous;
  protected Documento current;

  public AssinarDocumentoCommand(GerenciadorDocumentoModel manager, Documento documento, Operador operador) {
    this.operador = operador;
    this.manager = manager;

    this.previous = documento;
    this.current = documento;
  }

  @Override
  public void execute() {
    Assinatura assinatura = new Assinatura(operador, LocalDateTime.now());

    this.current = new AssinaturaDecorator(this.previous, assinatura);

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
    this.execute();
  }

  @Override
  public String toString() {
    return String.format(
        "Documento '%s' foi assinado por '%s'.",
        this.current.getNumero(),
        this.operador.getNome()
    );
  }

}
