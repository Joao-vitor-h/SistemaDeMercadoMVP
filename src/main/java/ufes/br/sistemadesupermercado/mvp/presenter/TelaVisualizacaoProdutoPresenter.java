package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.view.VisualizacaoProdutoView;

public class TelaVisualizacaoProdutoPresenter {

    private VisualizacaoProdutoView view;


    public TelaVisualizacaoProdutoPresenter() {
        view = new VisualizacaoProdutoView();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.setVisible(true);
    }
}
