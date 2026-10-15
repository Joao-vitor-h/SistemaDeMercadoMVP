package ufes.br.sistemadesupermercado.mvp.presenter.buscar;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

public class BuscaProdutoService {

    final private List<IBuscaProduto> buscador;

    public BuscaProdutoService() {
        buscador = new ArrayList<>();
        buscador.add(new BuscaProdutoPorNome());
        buscador.add(new BuscaProdutoPorCategoria());
    }

    public void buscar(int indice, String nome, TableModel modeloTabela, TableRowSorter<TableModel> ordenador) {
        buscador.get(indice).buscar(nome, modeloTabela, ordenador);
    }
}
