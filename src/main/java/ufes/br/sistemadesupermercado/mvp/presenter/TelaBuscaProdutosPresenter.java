package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.BuscarProdutosView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class TelaBuscaProdutosPresenter {
    
    private BuscarProdutosView view;
    private IProdutoRepository repository;

    public TelaBuscaProdutosPresenter(IProdutoRepository repository) {
        Objects.requireNonNull(repository, "O repositório é nulo.");
        this.repository = repository;
        this.view = new BuscarProdutosView();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        // Utilizado para alinhar as células das tabelas.
        DefaultTableCellRenderer centralizador = new DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);

        // Necessário para inserir os produtos na tabela com um for mais abaixo.
        DefaultTableModel tabalaProdutos = (DefaultTableModel) view.getTbProdutos().getModel();

        // Pega minha tabela para eu centralizar suas células.
        JTable tabelaProdutosParaCentralizar = view.getTbProdutos();

        for (int i = 0; i < tabelaProdutosParaCentralizar.getColumnCount(); i++) {
            tabelaProdutosParaCentralizar.getColumnModel().getColumn(i).setCellRenderer(centralizador);
        }

        for (ProdutoModel produto : repository.getProdutos()) {
            String nome = produto.getNome();
            double precoCusto = produto.getPrecoCusto();
            String categoria = produto.getCategoria().getNome();

            tabalaProdutos.addRow(new Object[]{nome, precoCusto, categoria});
        }

        view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String produto = view.getTxtBuscar().getText();
                String metodo = view.getCbBuscar().getSelectedItem().toString();

                try {
                    buscarProduto(produto, metodo);
                }
                catch (RuntimeException ex) {

                }
            }
        });

        view.setVisible(true);
    }

    private void buscarProduto(String nomeProduto, String metodo) {

    }
}
