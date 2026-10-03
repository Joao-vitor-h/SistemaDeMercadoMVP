package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.view.CalculoMargemLucroView;

public class TelaCalculoMargemLucroPresenter {

    private CalculoMargemLucroView view;

    public TelaCalculoMargemLucroPresenter() {
        view = new CalculoMargemLucroView();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.setVisible(true);


    }
}
