package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class TelaEdicaoProdutoPresenter {

    private InclusaoEdicaoVisualizacaoProdutoView view;
    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;

    public TelaEdicaoProdutoPresenter(String nome, IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        this.view = new InclusaoEdicaoVisualizacaoProdutoView();
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;

        configuraView(nome);
    }

    // Aplicar padrão Observer para atualizar a lista de produtos sempre que ocorrer uma edição.
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

    private void fechar() {
        view.dispose();
    }
}
