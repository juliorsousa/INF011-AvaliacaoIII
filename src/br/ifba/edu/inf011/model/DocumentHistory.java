package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.model.documentos.Documento;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DocumentHistory {

  protected static DocumentHistory INSTANCE = null;

  private DocumentHistory() {}

  public void logDocumentCreated(Documento documento) {
    String content = String.format(
        "Documento '%s' criado. (Privacidade: %s)%n",
        documento.getNumero(),
        documento.getPrivacidade().toString()
    );

    composedRawLog(content);
  }

  public void logDocumentConsolidated(Documento documento) {
    String content = String.format(
        "Documento '%s' consolidado.%n",
        documento.getNumero()
    );

    composedRawLog(content);
  }

  public void logNothingToUndo(Documento documento) {
    String content = String.format(
        "Nada a desfazer para o documento '%s'.%n",
        documento.getNumero()
    );

    composedRawLog(content);
  }

  public void logNothingToRedo(Documento documento) {
    String content = String.format(
        "Nada a refazer para o documento '%s'.%n",
        documento.getNumero()
    );

    composedRawLog(content);
  }

  public void logCommandUndo(Command command) {
    String content = String.format(
        "Desfeito: %s%n",
        command
    );

    composedRawLog(content);
  }

  public void logCommandRedo(Command command) {
    String content = String.format(
        "Refeito: %s%n", command
    );

    composedRawLog(content);
  }

  public void logCommand(Command command) {
    String content = String.format(
        "%s%n",
        command
    );

    composedRawLog(content);
  }

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

  private void composedRawLog(String content) {
    try (FileWriter writer = new FileWriter("document_history.log", true)) {
      String formatted = String.format(
          "%s [%s] %s",
          Thread.currentThread().getName(),
          LocalDateTime.now().format(formatter),
          content
      );

      writer.write(formatted);

      System.out.print(formatted);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static DocumentHistory getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new DocumentHistory();
    }

    return INSTANCE;
  }

}
