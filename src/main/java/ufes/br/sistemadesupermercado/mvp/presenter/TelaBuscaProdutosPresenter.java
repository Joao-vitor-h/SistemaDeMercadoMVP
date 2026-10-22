package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.presenter.buscar.BuscaProdutoService;
import ufes.br.sistemadesupermercado.mvp.presenter.stateInclusaoEdicaoVisualizacao.TelaInclusaoProdutoStatePresenter;
import ufes.br.sistemadesupermercado.mvp.presenter.stateInclusaoEdicaoVisualizacao.TelaVisualizacaoBuscaProdutoStatePresenter;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.BuscarProdutosView;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
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

        inserirProdutos(modeloTabelaProdutos);

        // Criando índices para minha tabela.
        TableRowSorter<TableModel> ordenador = new TableRowSorter<>(modeloTabelaProdutos);
        tabelaProdutos.setRowSorter(ordenador);

        // Mudar aqui aplicando OCP.
        view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nome = view.getTxtBuscar().getText().trim();
                int metodo = view.getCbBuscar().getSelectedIndex();

                if (nome.isEmpty() || nome.isBlank()) {
                    ordenador.setRowFilter(null);
                }
                else {
                    try {
                        buscarProduto(nome, metodo, modeloTabelaProdutos, ordenador);
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(view, ex.getMessage());
                    }
                }
            }
        });

        List<Object> nomeProduto = new ArrayList<>();

        view.getBtnNovo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InclusaoEdicaoVisualizacaoProdutoView telaInclusao = new InclusaoEdicaoVisualizacaoProdutoView();
                telaInclusao.setEstado(new TelaInclusaoProdutoStatePresenter(telaInclusao, produtoRepository, categoriaRepository));
            }
        });

        tabelaProdutos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabelaProdutos.rowAtPoint(e.getPoint());
                if (nomeProduto.size() > 0) {
                    nomeProduto.clear();
                }
                nomeProduto.add(tabelaProdutos.getValueAt(linha, 0));
            }
        });

        view.getBtnVizualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeProduto.getFirst().toString();

                    nomeProduto.clear();

                    InclusaoEdicaoVisualizacaoProdutoView telaVisualiza = new InclusaoEdicaoVisualizacaoProdutoView();
                    telaVisualiza.setEstado(new TelaVisualizacaoBuscaProdutoStatePresenter(telaVisualiza, nome, produtoRepository, categoriaRepository));

                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(view, "Selecione um produto!");
                }

            }
        });

        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });

        view.setVisible(true);
    }

    // Futuramente será preciso aplicar o OCP aqui.
    private void buscarProduto(String nome, int metodo, TableModel modeloTabela, TableRowSorter<TableModel> ordenador) {

        BuscaProdutoService buscador = new BuscaProdutoService();

        buscador.buscar(metodo, nome, modeloTabela, ordenador);
    }

    private void inserirProdutos(DefaultTableModel modelo) {
        for (ProdutoModel produto : produtoRepository.getProdutos()) {
            String nome = produto.getNome();
            BigDecimal precoCusto = produto.getPrecoCusto();
            String categoria = produto.getCategoria().getNome();
            BigDecimal percentual = produto.getCategoria().getPercentualLucro();

            modelo.addRow(new Object[]{nome, precoCusto, categoria, percentual});
        }
    }
}
