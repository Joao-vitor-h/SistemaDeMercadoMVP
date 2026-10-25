package ufes.br.sistemadesupermercado.mvp.presenter.stateCategoria;

import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.view.CategoriaProdutoView;

public class CategoriaInclusaoStatePresenter extends CategoriaState {

    public CategoriaInclusaoStatePresenter(CategoriaProdutoView view, ICategoriaRepository categoriaRepository) {
        super(view, categoriaRepository);
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.getLblModo().setText("Modo: Inclusão");

        view.getTxtCategoria().setText("");
        view.getTxtPercentual().setText("");

        view.getBtnNovo().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnFechar().setEnabled(false);

        view.setVisible(true);
    }
}
