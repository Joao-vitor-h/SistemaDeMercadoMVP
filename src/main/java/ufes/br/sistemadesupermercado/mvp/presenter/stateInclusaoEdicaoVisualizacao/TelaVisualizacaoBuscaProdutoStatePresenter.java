package ufes.br.sistemadesupermercado.mvp.presenter.stateInclusaoEdicaoVisualizacao;

import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TelaVisualizacaoBuscaProdutoStatePresenter extends InclusaoEdicaoVisualizacaoViewState {

    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;
    private List<ActionListener> monitores;

    public TelaVisualizacaoBuscaProdutoStatePresenter(InclusaoEdicaoVisualizacaoProdutoView view, String nome, IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        super(view);
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.monitores = new ArrayList<>();

        configuraView(nome);
    }

    private void configuraView(String nomeProduto) {
        view.setLocationRelativeTo(null);

        ProdutoModel produto = produtoRepository.buscarProduto(nomeProduto).get();

        view.setTitle("Produto - Visualização");
        view.getTxtNomeProduto().setText(produto.getNome());
        view.getTxtPrecoCusto().setText(String.valueOf(produto.getPrecoCusto()));
        view.getTxtNomeProduto().setEditable(false);
        view.getTxtPrecoCusto().setEditable(false);
        view.getCbCategoria().setEditable(false);
        view.getTxtPrecoVenda().setEditable(false);
        view.getTxtMargem().setEditable(false);
        view.getCbCategoria().setSelectedIndex(produto.getCategoria().getId() - 1);
        view.getBtnEditar().setText("Editar");
        view.getBtnFechar().setText("Fechar");

        ActionListener salva = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.editar(produto.getNome());
            }
        };

        ActionListener fecha = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        };

        view.getBtnEditar().addActionListener(salva);
        view.getBtnFechar().addActionListener(fecha);

        monitores.add(salva);
        monitores.add(fecha);

        view.setVisible(true);
    }

    @Override
    public void editar(String nome) {

        view.getBtnEditar().removeActionListener(monitores.get(0));
        view.getBtnFechar().removeActionListener(monitores.get(1));

        view.setEstado(new TelaEdicaoBuscaProdutoStatePresenter(view, nome, produtoRepository, categoriaRepository));
    }
}
