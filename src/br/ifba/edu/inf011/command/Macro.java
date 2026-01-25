package br.ifba.edu.inf011.command;

import java.util.List;

public class Macro implements Command {

  private final List<Command> commands;

  public Macro(Command... commands) {
    this.commands = List.of(commands);
  }

  @Override
  public void execute() {
    for (Command command : commands){
      command.execute();
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
    for (Command command : commands) command.execute();
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
