package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.presenter.stateInclusaoEdicaoVisualizacao.TelaInclusaoProdutoStatePresenter;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IHistoricoPrecoRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.service.HistoricoDePrecoServiceObserver;
import ufes.br.sistemadesupermercado.mvp.view.CategoriaProdutoView;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;
import ufes.br.sistemadesupermercado.mvp.view.TelaPrincipalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class TelaPrincipalPresenter {

    private TelaPrincipalView view;
    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;
    private IHistoricoPrecoRepository historicoPrecoRepository;

    public TelaPrincipalPresenter(IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository, IHistoricoPrecoRepository historicoPrecoRepository) {
        Objects.requireNonNull(produtoRepository, "O repositório de produtos está nulo.");
        Objects.requireNonNull(categoriaRepository, "O repositório de categorias está nulo.");
        Objects.requireNonNull(historicoPrecoRepository, "O repositório de históricos está nulo.");

        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
        this.view = new TelaPrincipalView();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);
        view.setExtendedState(JFrame.MAXIMIZED_BOTH);

        HistoricoDePrecoServiceObserver registro = new HistoricoDePrecoServiceObserver(historicoPrecoRepository);

        InclusaoEdicaoVisualizacaoProdutoView inclusao = new InclusaoEdicaoVisualizacaoProdutoView();
        inclusao.setEstado(new TelaInclusaoProdutoStatePresenter(inclusao, produtoRepository, categoriaRepository));
        inclusao.setVisible(false);

        view.getMenuItemIncluirDados().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inclusao.setVisible(true);
            }
        });

        TelaBuscaProdutosPresenter buscaProduto = new TelaBuscaProdutosPresenter(produtoRepository, categoriaRepository, historicoPrecoRepository);
        buscaProduto.getView().setVisible(false);

        view.getMenuItemBuscarProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscaProduto.getView().setVisible(true);
            }
        });

        CategoriaProdutoView telaCategoria = new CategoriaProdutoView(categoriaRepository, produtoRepository);
        telaCategoria.setVisible(false);

        view.getMenuItemCategoria().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaCategoria.setVisible(true);
            }
        });

        TelaCalculoMargemLucroPresenter calculoMargemLucro = new TelaCalculoMargemLucroPresenter(produtoRepository, historicoPrecoRepository, registro);
        calculoMargemLucro.getView().setVisible(false);

        view.getMenuItemCalcularMargemDeLucro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculoMargemLucro.getView().setVisible(true);
            }
        });

        registro.adicionarTela(buscaProduto);

        view.setVisible(true);
    }
}