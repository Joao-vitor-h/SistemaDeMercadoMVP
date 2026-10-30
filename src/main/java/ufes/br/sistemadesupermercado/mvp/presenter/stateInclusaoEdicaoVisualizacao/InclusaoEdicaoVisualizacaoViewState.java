package ufes.br.sistemadesupermercado.mvp.presenter.stateInclusaoEdicaoVisualizacao;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public abstract class InclusaoEdicaoVisualizacaoViewState {

    protected InclusaoEdicaoVisualizacaoProdutoView view;

    public InclusaoEdicaoVisualizacaoViewState(InclusaoEdicaoVisualizacaoProdutoView view) {
        Objects.requireNonNull(view, "A instância da tela está nula.");
        this.view = view;
    }

    public void incluir() {
        throw new RuntimeException("Não é possível incluir produtos neste estado.");
    }

    public void visualizar(String nome) {
        throw new RuntimeException("Não é possível visualizar produtos neste estado.");
    }

    public void editar(String nome) {
        throw new RuntimeException("Não é possível editar produtos neste estado.");
    }

    public void inserirCategorias(ICategoriaRepository categoriaRepository) {
        DefaultComboBoxModel modelo = (DefaultComboBoxModel) view.getCbCategoria().getModel();

        for (CategoriaModel categoria : categoriaRepository.getCategorias()) {
            modelo.addElement(categoria.getNome());
        }
    }

    public void removerCategorias(ICategoriaRepository categoriaRepository) {
        DefaultComboBoxModel modelo = (DefaultComboBoxModel) view.getCbCategoria().getModel();

        for (CategoriaModel categoria : categoriaRepository.getCategorias()) {
            modelo.removeElement(categoria.getNome());
        }
    }
}
