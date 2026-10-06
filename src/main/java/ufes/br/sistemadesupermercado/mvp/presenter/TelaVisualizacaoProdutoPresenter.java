package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaVisualizacaoProdutoPresenter {

    private InclusaoEdicaoVisualizacaoProdutoView view;
    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;


    public TelaVisualizacaoProdutoPresenter(ProdutoModel produto, IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        this.view = new InclusaoEdicaoVisualizacaoProdutoView();
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;

        configuraView(produto);
    }

    private void configuraView(ProdutoModel produto) {
        view.setLocationRelativeTo(null);

        view.getTxtNomeProduto().setText(produto.getNome());
        view.getTxtPrecoCusto().setText(java.lang.String.valueOf(produto.getPrecoCusto()));
        view.getTxtNomeProduto().setEnabled(false);
        view.getTxtPrecoCusto().setEnabled(false);
        view.getCbCategoria().setEnabled(false);
        view.getTxtPrecoVenda().setEnabled(false);
        view.getTxtMargem().setEnabled(false);
        view.getCbCategoria().setSelectedIndex(produto.getCategoria().getId() - 1);

        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaInclusaoEdicaoPresenter telaEditar = new TelaInclusaoEdicaoPresenter(produto.getNome(), produtoRepository, categoriaRepository);
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
}
