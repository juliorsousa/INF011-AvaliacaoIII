package br.ifba.edu.inf011.command;

public interface Command { // Command: Command Interface | Composite: Leaf

  void execute();

  void undo();
  void redo();

}
