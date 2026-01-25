package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.command.impl.AssinarDocumentoCommand;
import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.command.CommandContext;
import br.ifba.edu.inf011.command.impl.ProtegerDocumentoCommand;
import br.ifba.edu.inf011.command.impl.SalvarDocumentoCommand;
import br.ifba.edu.inf011.command.impl.TornarUrgenteCommand;
import br.ifba.edu.inf011.command.macros.PriorizarMacro;
import br.ifba.edu.inf011.command.macros.SalvarEAssinarMacro;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.strategy.NameGeneratingStrategy;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDocumentoModel {

  private final DocumentHistory history = DocumentHistory.getInstance();
  private final CommandContext commandContext = CommandContext.getInstance();

  private List<Documento> repositorio;
  private DocumentOperatorFactory factory;
  private Autenticador autenticador;
  private Documento atual;

  public GerenciadorDocumentoModel(DocumentOperatorFactory factory) {
    this.repositorio = new ArrayList<>();
    this.factory = factory;
    this.autenticador = new Autenticador();
    this.atual = null;
  }

  public void undo() {
    commandContext.undo(this.atual);
  }

  public void redo() {
    commandContext.redo(this.atual);
  }

  public Documento criarDocumento(NameGeneratingStrategy tipoAutenticador, Privacidade privacidade) throws FWDocumentException {
    Operador operador = factory.getOperador();
    Documento documento = factory.getDocumento();

    operador.inicializar("jdc", "João das Couves");
    documento.inicializar(operador, privacidade);

    this.autenticador.autenticar(tipoAutenticador, documento);
    this.repositorio.add(documento);
    this.atual = documento;

    this.history.logDocumentCreated(documento);

    return documento;
  }

  public void salvarDocumento(Documento doc, String conteudo) throws Exception {
    commandContext.addCommand(doc, new SalvarDocumentoCommand(this, doc, conteudo));
  }

  public void assinarDocumento(Documento doc) throws FWDocumentException {
    if (doc == null) return;

    Operador operador = factory.getOperador();
    operador.inicializar("jdc", "João das Couves");

    Command assinarCommand = new AssinarDocumentoCommand(this, doc, operador);

    commandContext.addCommand(doc, assinarCommand);
  }

  public void protegerDocumento(Documento doc) throws FWDocumentException {
    if (doc == null) return;

    Command protegerCommand = new ProtegerDocumentoCommand(this, doc);

    commandContext.addCommand(doc, protegerCommand);
  }

  public void tornarUrgente(Documento doc) throws FWDocumentException {
    if (doc == null) return;

    Command tornarUrgenteCommand = new TornarUrgenteCommand(this, doc);

    commandContext.addCommand(doc, tornarUrgenteCommand);
  }

  public void priorizar(Documento doc) throws FWDocumentException {
    if (doc == null) return;

    Operador operador = factory.getOperador();
    operador.inicializar("jdc", "João das Couves");

    Command macro = new PriorizarMacro(this, doc, operador);
    commandContext.addCommand(doc, macro);
  }

  public void salvarEAssinar(Documento doc, String conteudo) throws FWDocumentException {
    if (doc == null) return;

    Operador operador = factory.getOperador();
    operador.inicializar("jdc", "João das Couves");

    Command macro = new SalvarEAssinarMacro(this, doc, conteudo, operador);
    commandContext.addCommand(doc, macro);
  }

  public void consolidarAlteracoes(Documento doc) {
    this.commandContext.consolidateDocument(doc);
  }

  public List<Documento> getRepositorio() {
    return repositorio;
  }

  public void atualizarRepositorio(Documento antigo, Documento novo) {
    int index = repositorio.indexOf(antigo);
    if (index != -1) {
      repositorio.set(index, novo);
    }
  }

  public Documento getDocumentoAtual() {
    return this.atual;
  }

  public void setDocumentoAtual(Documento doc) {
    this.atual = doc;
  }

}
