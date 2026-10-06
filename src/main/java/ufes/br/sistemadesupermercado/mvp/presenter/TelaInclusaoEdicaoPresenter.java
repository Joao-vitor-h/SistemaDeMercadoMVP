package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;

import javax.swing.*;
import java.awt.event.*;
import java.util.Optional;

public class TelaInclusaoEdicaoPresenter {

    private InclusaoEdicaoVisualizacaoProdutoView view;
    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;

    public TelaInclusaoEdicaoPresenter(String nome, IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        this.view = new InclusaoEdicaoVisualizacaoProdutoView();
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;

        if (nome != null) {
            configuraView(nome);
        } else {
            configuraView();
        }
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.setTitle("Produto - Inclusão/Edição");
        view.getBtnVisualizarHistorico().setVisible(false);
        view.getTxtMargem().setEnabled(false);
        view.getTxtPrecoVenda().setEnabled(false);
        view.getBtnEditar().setText("Salvar");
        view.getBtnFechar().setText("Cancelar");

        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    java.lang.String nome = view.getTxtNomeProduto().getText();
                    double preco = Double.parseDouble(view.getTxtPrecoCusto().getText());
                    Object categoria = view.getCbCategoria().getSelectedItem();

                    criarNovoProduto(nome, preco, categoria.toString());
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
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

    private void configuraView(String nome) {
        view.setLocationRelativeTo(null);

        view.setTitle("Produto - Inclusão/Edição");
        view.getBtnVisualizarHistorico().setVisible(false);
        view.getTxtMargem().setEditable(false);
        view.getTxtPrecoVenda().setEditable(false);
        view.getBtnEditar().setText("Salvar");
        view.getBtnFechar().setText("Cancelar");

        Optional<ProdutoModel> produtoOpt = produtoRepository.buscarProduto(nome);

        ProdutoModel produto;

        try {
            produto = produtoOpt.get();

            view.getTxtNomeProduto().setText(produto.getNome());
            view.getTxtPrecoCusto().setText(String.valueOf(produto.getPrecoCusto()));
            view.getCbCategoria().setSelectedItem(produto.getCategoria().getNome());

            view.getBtnEditar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String novoNome = view.getTxtNomeProduto().getText();
                        double preco = Double.parseDouble(view.getTxtPrecoCusto().getText());
                        Object categoria = view.getCbCategoria().getSelectedItem();

                        Optional<CategoriaModel> categoriaOpt = categoriaRepository.buscarCategoria(categoria.toString());

                        produtoRepository.editarProduto(produto.getNome(), novoNome, preco, categoriaOpt.get());

                        fechar();
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(view, ex.getMessage());
                    }
                }
            });
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, "Erro ao encontrar o produto.");
        }

        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fechar();
            }
        });

        view.setVisible(true);
    }

    private void criarNovoProduto(java.lang.String nome, double precoCusto, java.lang.String categoria) {
        Optional<CategoriaModel> categoriaOpt = categoriaRepository.buscarCategoria(categoria);

        if (categoriaOpt.isPresent()) {
            produtoRepository.adicionarProduto(nome, precoCusto, categoriaOpt.get());
            TelaVisualizacaoProdutoPresenter visualizar = new TelaVisualizacaoProdutoPresenter(produtoRepository.buscarProduto(nome).get(), produtoRepository, categoriaRepository);
        }

        view.getTxtNomeProduto().setText("");
        view.getTxtPrecoCusto().setText("");
    }

    private void fechar() {
        view.dispose();
    }
}
