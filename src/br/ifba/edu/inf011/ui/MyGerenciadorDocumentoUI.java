package br.ifba.edu.inf011.ui;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.strategy.NameGeneratingStrategy;
import javax.swing.JOptionPane;

public class MyGerenciadorDocumentoUI extends AbstractGerenciadorDocumentosUI {

  public MyGerenciadorDocumentoUI(DocumentOperatorFactory factory) {
    super(factory);
  }

  protected JPanelOperacoes montarMenuOperacoes() {
    JPanelOperacoes comandos = new JPanelOperacoes();
    comandos.addOperacao("➕ Criar Publico", e -> this.criarDocumentoPublico());
    comandos.addOperacao("➕ Criar Privado", e -> this.criarDocumentoPrivado());

    comandos.addOperacao("💾 Salvar", e -> this.salvarConteudo());
    comandos.addOperacao("🔑 Proteger", e -> this.protegerDocumento());
    comandos.addOperacao("✍️ Assinar", e -> this.assinarDocumento());
    comandos.addOperacao("⏰ Urgente", e -> this.tornarUrgente());

    comandos.addOperacao("⚡ Priorizar", e -> this.priorizar());
    comandos.addOperacao("💾✍️ Salvar e Assinar", e -> this.salvarEAssinar());

    comandos.addOperacao("↩️ Desfazer", e -> this.undo());
    comandos.addOperacao("↪️ Refazer", e -> this.redo());

    // CONSOLIDAR DOCUMENTO
    comandos.addOperacao("📜 Consolidar", e -> this.consolidate());

    return comandos;
  }

  private void undo() {
    this.controller.undo();
    this.atual = this.controller.getDocumentoAtual();
    this.refreshUI();
  }

  private void redo() {
    this.controller.redo();
    this.atual = this.controller.getDocumentoAtual();
    this.refreshUI();
  }

  protected void criarDocumentoPublico() {
    this.criarDocumento(Privacidade.PUBLICO);
  }

  protected void criarDocumentoPrivado() {
    this.criarDocumento(Privacidade.SIGILOSO);
  }

  protected void salvarConteudo() {
    try {
      this.controller.salvarDocumento(this.atual, this.areaEdicao.getConteudo());
      this.atual = this.controller.getDocumentoAtual();
      this.refreshUI();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Erro ao Salvar: " + e.getMessage());
    }
  }

  protected void protegerDocumento() {
    try {
      this.controller.protegerDocumento(this.atual);
      this.atual = this.controller.getDocumentoAtual();
      this.refreshUI();
    } catch (FWDocumentException e) {
      JOptionPane.showMessageDialog(this, "Erro ao proteger: " + e.getMessage());
    }
  }

  protected void assinarDocumento() {
    try {
      this.controller.assinarDocumento(this.atual);
      this.atual = this.controller.getDocumentoAtual();
      this.refreshUI();
    } catch (FWDocumentException e) {
      JOptionPane.showMessageDialog(this, "Erro ao assinar: " + e.getMessage());
    }
  }

  protected void tornarUrgente() {
    try {
      this.controller.tornarUrgente(this.atual);
      this.atual = this.controller.getDocumentoAtual();
      this.refreshUI();
    } catch (FWDocumentException e) {
      JOptionPane.showMessageDialog(this, "Erro ao tornar urgente: " + e.getMessage());
    }
  }

  protected void consolidate() {
    try {
      this.controller.consolidarAlteracoes(this.atual);
      this.atual = this.controller.getDocumentoAtual();
      this.refreshUI();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Erro ao consolidar: " + e.getMessage());
    }
  }

  protected void priorizar() {
    try {
      this.controller.priorizar(this.atual);
      this.atual = this.controller.getDocumentoAtual();
      this.refreshUI();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Erro ao priorizar: " + e.getMessage());
    }
  }

  protected void salvarEAssinar() {
    try {
      this.controller.salvarEAssinar(this.atual, this.areaEdicao.getConteudo());
      this.atual = this.controller.getDocumentoAtual();
      this.refreshUI();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Erro ao salvar e assinar: " + e.getMessage());
    }
  }

  private void criarDocumento(Privacidade privacidade) {
    try {
      NameGeneratingStrategy tipo = this.barraSuperior.getSelected();
      this.atual = this.controller.criarDocumento(tipo, privacidade);
      this.barraDocs.addDoc("[" + atual.getNumero() + "]");
      this.refreshUI();
    } catch (FWDocumentException e) {
      JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
    }
  }


}
