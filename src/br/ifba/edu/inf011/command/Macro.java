package br.ifba.edu.inf011.command;

import java.util.ArrayList;
import java.util.List;

public class Macro implements Command { // Command: Macro AbstractCommand | Composite: Composite

  private final List<Command> commands;

  public Macro(Command... commands) {
    this.commands = new ArrayList<>(List.of(commands));
  }

  @Override
  public void execute() {
    List<Command> executedCommands = new ArrayList<>();

    try {
      for (Command command : commands) {
        command.execute();
        executedCommands.add(command);
      }
    } catch (Exception exception) {
      for (int i = executedCommands.size() - 1; i >= 0; i--) {
        executedCommands.get(i).undo();
      }

      throw exception;
    }
  }

  @Override
  public void undo() {
    for (int i = commands.size() - 1; i >= 0; i--) {
      commands.get(i).undo();
    }
  }

  @Override
  public void redo() {
    List<Command> redone = new ArrayList<>();

    try {
      for (Command command : commands) {
        command.redo();
        redone.add(command);
      }
    } catch (Exception exception) {
      for (int i = redone.size() - 1; i >= 0; i--) {
        redone.get(i).undo();
      }

      throw exception;
    }
  }

  @Override
  public String toString() {
    String joinedCommands = String.join("\n",
        this.getCommands().stream()
            .map(command ->
                "\t - %s".formatted(command.toString())
            )
            .toArray(String[]::new)
    );

    return String.format("%n%s%n", joinedCommands);
  }

  protected List<Command> getCommands() {
    return this.commands;
  }

}
