package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.view.CategoriaProdutoView;

public class TelaCategoriaProdutoPresenter {

    private CategoriaProdutoView view;


    public TelaCategoriaProdutoPresenter() {
        view = new CategoriaProdutoView();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.setVisible(true);
    }
}
