package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.DocumentHistory;
import br.ifba.edu.inf011.model.documentos.Documento;
import java.util.HashMap;
import java.util.Stack;

public class CommandContext {

  protected static CommandContext INSTANCE = null;

  private CommandContext() {
  }

  private final DocumentHistory history = DocumentHistory.getInstance();
  private final HashMap<String, Stack<Command>> undoMappings = new HashMap<>();
  private final HashMap<String, Stack<Command>> redoMappings = new HashMap<>();

  public void addCommand(Documento documento, Command command) {
    this.history.logCommand(command);

    command.execute();

    this.history.logCommandExecuted(command);

    this.getUndoStack(documento).push(command);
    this.getRedoStack(documento).clear();
  }

  public void consolidateDocument(Documento documento) {
    this.history.logDocumentConsolidated(documento);

    this.getUndoStack(documento).clear();
    this.getRedoStack(documento).clear();

    this.undoMappings.remove(documento.getNumero());
    this.redoMappings.remove(documento.getNumero());
  }

  public void undo(Documento documento) {
    Stack<Command> undoStack = this.getUndoStack(documento);

    if (undoStack.isEmpty()) {
      this.history.logNothingToUndo(documento);
      return;
    }

    Command command = undoStack.pop();
    command.undo();

    this.history.logCommandUndo(command);

    this.getRedoStack(documento).push(command);
  }

  public void redo(Documento documento) {
    Stack<Command> redoStack = this.getRedoStack(documento);

    if (redoStack.isEmpty()) {
      this.history.logNothingToRedo(documento);
      return;
    }

    Command command = redoStack.pop();
    command.redo();

    this.history.logCommandRedo(command);

    this.getUndoStack(documento).push(command);
  }

  private Stack<Command> getUndoStack(Documento documento) {
    return this.undoMappings.computeIfAbsent(
        documento.getNumero(),
        k -> new Stack<>()
    );
  }

  private Stack<Command> getRedoStack(Documento documento) {
    return this.redoMappings.computeIfAbsent(
        documento.getNumero(),
        k -> new Stack<>()
    );
  }

  public static CommandContext getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CommandContext();
    }

    return INSTANCE;
  }

}
