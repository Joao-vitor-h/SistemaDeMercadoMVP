package ufes.br.sistemadesupermercado.mvp.presenter.buscar;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BuscaProdutoPorNome implements IBuscaProduto {

    @Override
    public void buscar(String nome, TableModel modeloTabela, TableRowSorter<TableModel> ordenador) {

        Objects.requireNonNull(modeloTabela, "O modelo de tabela está nulo.");
        Objects.requireNonNull(ordenador, "A forma de organizar a tabela está nula.");

        List<Integer> ocultos = new ArrayList<>();
        int linhas = modeloTabela.getRowCount();

        for (int i = 0; i < linhas; i++) {
            Object nomeProduto = modeloTabela.getValueAt(i, 0);

            if (!(nome.equalsIgnoreCase(nomeProduto.toString()))) {
                ocultos.add(i);
            }
        }

        ordenador.setRowFilter(new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                return !ocultos.contains(entry.getIdentifier());
            }
        });
    }
}
