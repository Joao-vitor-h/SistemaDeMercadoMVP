package ufes.br.sistemadesupermercado.mvp.presenter.stateCategoria;

import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.view.CategoriaProdutoView;

import java.util.Objects;

public abstract class CategoriaState {

    protected CategoriaProdutoView view;
    protected ICategoriaRepository categoriaRepository;

    public CategoriaState(CategoriaProdutoView view, ICategoriaRepository categoriaRepository) {
        Objects.requireNonNull(view, "A view está nula.");
        Objects.requireNonNull(categoriaRepository, "O repositório de categorias está nulo.");

        this.view = view;
        this.categoriaRepository = categoriaRepository;
    }

    public void visualizar() {
        throw new RuntimeException("Não é possível visualizar categorias neste estado: " + view.getEstado());
    }

    public void incluir() {
        throw new RuntimeException("Não é possível incluir categorias neste estado: " + view.getEstado());
    }

    public void editar() {
        throw new RuntimeException("Não é possível editar categorias neste estado: " + view.getEstado());
    }

    public void excluir() {
        throw new RuntimeException("Não é possível excluir categorias neste estado: " + view.getEstado());
    }
}
