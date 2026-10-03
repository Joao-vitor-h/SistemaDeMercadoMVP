package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoProdutosView;

public class TelaInclusaoEdicaoPresenter {

    private InclusaoEdicaoProdutosView view;

    public TelaInclusaoEdicaoPresenter() {
        view = new InclusaoEdicaoProdutosView();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.setVisible(true);
    }
}
