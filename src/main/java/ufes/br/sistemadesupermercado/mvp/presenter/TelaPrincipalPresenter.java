package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.TelaPrincipalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class TelaPrincipalPresenter {

    private TelaPrincipalView view;
    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;

    public TelaPrincipalPresenter(IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        Objects.requireNonNull(produtoRepository, "O repositório de produtos está nulo.");
        Objects.requireNonNull(categoriaRepository, "O repositório de categorias está nulo.");
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.view = new TelaPrincipalView();
        configuraView();
    }

    private void configuraView() {

        view.setLocationRelativeTo(null);
        view.setExtendedState(JFrame.MAXIMIZED_BOTH);

        view.getMenuItemIncluirDados().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaInclusaoEdicaoPresenter inclusaoEdicaoProduto = new TelaInclusaoEdicaoPresenter();
            }
        });

        view.getMenuItemBuscarProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaBuscaProdutosPresenter buscaProduto = new TelaBuscaProdutosPresenter(produtoRepository, categoriaRepository);
            }
        });

        view.getMenuItemCategoria().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCategoriaProdutoPresenter categoria = new TelaCategoriaProdutoPresenter();
            }
        });

        view.getMenuItemCalcularMargemDeLucro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCalculoMargemLucroPresenter calculoMargemLucro = new TelaCalculoMargemLucroPresenter();
            }
        });

        view.setVisible(true);
    }
}