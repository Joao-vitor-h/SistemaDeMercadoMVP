package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.VisualizacaoProdutoView;

import javax.management.RuntimeMBeanException;
import java.util.Objects;
import java.util.Optional;

public class TelaVisualizacaoProdutoPresenter {

    private VisualizacaoProdutoView view;
    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;


    public TelaVisualizacaoProdutoPresenter(ProdutoModel produto, IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        this.view = new VisualizacaoProdutoView();
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;

        configuraView(produto);
    }

    private void configuraView(ProdutoModel produto) {
        view.setLocationRelativeTo(null);

        view.getTxtNomeProduto().setText(produto.getNome());
        view.getTxtPrecoCusto().setText(String.valueOf(produto.getPrecoCusto()));
        view.getCbCategoria().setEnabled(false);
        view.getTxtPrecoVenda().setEnabled(false);
        view.getTxtMargem().setEnabled(false);
        view.getCbCategoria().setSelectedIndex(produto.getCategoria().getId() - 1);

        view.setVisible(true);
    }
}
