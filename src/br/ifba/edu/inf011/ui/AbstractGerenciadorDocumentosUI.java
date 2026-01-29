package br.ifba.edu.inf011.ui;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.strategy.impl.ConfidencialNameGenerator;
import br.ifba.edu.inf011.strategy.impl.CriminalNameGenerator;
import br.ifba.edu.inf011.strategy.impl.ExportacaoNameGenerator;
import br.ifba.edu.inf011.strategy.NameGenerator;
import br.ifba.edu.inf011.strategy.impl.PessoalNameGenerator;
import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class AbstractGerenciadorDocumentosUI extends JFrame implements ListSelectionListener { // Strategy: Client, Command: Client

  protected GerenciadorDocumentoModel controller;
  protected JPanelBarraSuperior<NameGenerator> barraSuperior;
  protected JPanelAreaEdicao areaEdicao;
  protected JPanelListaDocumentos<String> barraDocs;

  protected NameGenerator[] tipos = {new CriminalNameGenerator(), new PessoalNameGenerator(), new ExportacaoNameGenerator(), new ConfidencialNameGenerator()};

  protected Documento atual;
  protected DefaultListModel<String> listDocs;


  public AbstractGerenciadorDocumentosUI(DocumentOperatorFactory factory) {
    this.controller = new GerenciadorDocumentoModel(factory);
    this.listDocs = new DefaultListModel<String>();
    this.barraSuperior = new JPanelBarraSuperior<NameGenerator>(tipos);
    this.areaEdicao = new JPanelAreaEdicao();
    this.barraDocs = new JPanelListaDocumentos<String>(this.listDocs, this);
    this.montarAparencia();
  }


  protected abstract JPanelOperacoes montarMenuOperacoes();


  public void montarAparencia() {
    // Configuração da Janela
    this.setTitle("Sistema de Gestão de Documentos - INF011");
    this.setSize(800, 500);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    //Layout
    this.add(this.barraSuperior, BorderLayout.NORTH);
    this.add(this.areaEdicao, BorderLayout.CENTER);
    this.add(this.barraDocs, BorderLayout.WEST);
    this.add(this.montarMenuOperacoes(), BorderLayout.EAST);

  }


  protected void refreshUI() {
    try {
      this.areaEdicao.atualizar(this.atual.getConteudo());
    } catch (Exception e) {
      this.areaEdicao.atualizar("");
      JOptionPane.showMessageDialog(this, "Erro ao Carregar : " + e.getMessage());
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      int index = this.barraDocs.getIndiceDocSelecionado();
      if (index != -1) {
        this.atual = controller.getRepositorio().get(index);
        this.controller.setDocumentoAtual(this.atual);
        this.refreshUI();
      }
    }
  }

}