package ufes.br.sistemadesupermercado.mvp.presenter.buscar;

import ufes.br.sistemadesupermercado.mvp.presenter.TelaBuscaProdutosPresenter;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public interface IBuscaProduto {
    void buscar(String nome, TableModel modeloTabela, TableRowSorter<TableModel> ordenador);
}
