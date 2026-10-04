package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.BuscarProdutosView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class TelaBuscaProdutosPresenter {
    
    private BuscarProdutosView view;
    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;

    public TelaBuscaProdutosPresenter(IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        Objects.requireNonNull(produtoRepository, "O repositório de produtos está nulo.");
        Objects.requireNonNull(categoriaRepository, "O repositório de categorias está nulo.");
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.view = new BuscarProdutosView();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        // Utilizado para alinhar as células das tabelas.
        DefaultTableCellRenderer centralizador = new DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);

        // Necessário para inserir os produtos na tabela com um for mais abaixo.
        DefaultTableModel modeloTabelaProdutos = (DefaultTableModel) view.getTbProdutos().getModel();

        // Pega minha tabela para eu centralizar suas células.
        JTable tabelaProdutos = view.getTbProdutos();

        for (int i = 0; i < tabelaProdutos.getColumnCount(); i++) {
            tabelaProdutos.getColumnModel().getColumn(i).setCellRenderer(centralizador);
        }

        for (ProdutoModel produto : produtoRepository.getProdutos()) {
            String nome = produto.getNome();
            double precoCusto = produto.getPrecoCusto();
            String categoria = produto.getCategoria().getNome();

            modeloTabelaProdutos.addRow(new Object[]{nome, precoCusto, categoria});
        }

        // Criando índices para minha tabela.
        TableRowSorter<TableModel> ordenador = new TableRowSorter<>(modeloTabelaProdutos);
        tabelaProdutos.setRowSorter(ordenador);

        // Mudar aqui aplicando OCP.
        view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nome = view.getTxtBuscar().getText().trim();
                String metodo = view.getCbBuscar().getSelectedItem().toString();

                if (nome.isEmpty() || nome.isBlank()) {
                    ordenador.setRowFilter(null);
                }
                else if (metodo.equals("Nome do Produto")) {
                    if (produtoRepository.verificarExistenciaProduto(nome)) {
                        buscarProduto(nome, metodo);
                    } else {
                        JOptionPane.showMessageDialog(view, "FALHA: O produto " + nome.toUpperCase() + " não existe!");
                    }
                }
                else {
                    if (categoriaRepository.verificarExistenciaCategoria(nome)) {
                        buscarProduto(nome, metodo);
                    } else {
                        JOptionPane.showMessageDialog(view, "FALHA: A categoria " + nome.toUpperCase() + " não existe!");
                    }
                }
            }
        });

        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });

        view.getBtnVizualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaVisualizacaoProdutoPresenter vizualizacaoProduto = new TelaVisualizacaoProdutoPresenter();
            }
        });

        view.setVisible(true);
    }

    // Futuramente será preciso aplicar o OCP aqui.
    private void buscarProduto(String nome, String metodo) {

        TableModel modeloTabela = view.getTbProdutos().getModel();
        TableRowSorter<TableModel> ordenador = (TableRowSorter<TableModel>) view.getTbProdutos().getRowSorter();
        // Precisa ser final para não ser alterado pela classe interna.
        final List<Integer> ocultos = new ArrayList<>();
        int linhas = modeloTabela.getRowCount();

        if (metodo.equals("Nome do Produto")) {
            for (int i = 0; i < linhas; i++) {
                Object nomeProduto = modeloTabela.getValueAt(i, 0);

                if (!(nome.equalsIgnoreCase(nomeProduto.toString()))) {
                    ocultos.add(i);
                }
            }

            ordenador.setRowFilter(new RowFilter<TableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                    return !ocultos.contains(entry.getIdentifier());
                }
            });
        }
        else {
            for (int i = 0; i < linhas; i++) {
                Object nomeCategoria = modeloTabela.getValueAt(i, 2);

                if (!(nome.equalsIgnoreCase(nomeCategoria.toString()))) {
                    ocultos.add(i);
                }
            }

            ordenador.setRowFilter(new RowFilter<TableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                    return !ocultos.contains(entry.getIdentifier());
                }
            });
        }
    }
}
