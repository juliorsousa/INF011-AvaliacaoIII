package br.ifba.edu.inf011.ui;

import br.ifba.edu.inf011.strategy.ConfidencialNameGenerator;
import br.ifba.edu.inf011.strategy.CriminalNameGenerator;
import br.ifba.edu.inf011.strategy.ExportacaoNameGenerator;
import br.ifba.edu.inf011.strategy.NameGeneratingStrategy;
import br.ifba.edu.inf011.strategy.PessoalNameGenerator;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public abstract class AbstractGerenciadorDocumentosUI extends JFrame implements ListSelectionListener{ // Strategy: Client
    
	protected GerenciadorDocumentoModel controller;
	protected JPanelBarraSuperior<NameGeneratingStrategy> barraSuperior;
	protected JPanelAreaEdicao areaEdicao;
	protected JPanelListaDocumentos<String> barraDocs;

  	protected NameGeneratingStrategy[] tipos = {new CriminalNameGenerator(), new PessoalNameGenerator(), new ExportacaoNameGenerator(), new ConfidencialNameGenerator()};

  protected Documento atual;
    protected DefaultListModel<String> listDocs;


    public AbstractGerenciadorDocumentosUI(DocumentOperatorFactory factory) {
        this.controller = new GerenciadorDocumentoModel(factory);
    	this.listDocs = new DefaultListModel<String>();
    	this.barraSuperior = new JPanelBarraSuperior<NameGeneratingStrategy>(tipos);
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
	            this.refreshUI();
	        }
        }
	}

	


}