package ufes.br.sistemadesupermercado;

import ufes.br.sistemadesupermercado.mvp.presenter.TelaPrincipalPresenter;
import ufes.br.sistemadesupermercado.mvp.repository.*;

public class SistemaDeSupermercadoMVP {

    public static void main(String[] args) {
        IProdutoRepository produtoRepository = new ProdutoRepository();
        ICategoriaRepository categoriaRepository = new CategoriaRepository();
        IHistoricoPrecoRepository historicoPrecoRepository = new HistoricoPrecoRepository();

        TelaPrincipalPresenter tela1 = new TelaPrincipalPresenter(produtoRepository, categoriaRepository, historicoPrecoRepository);
    }
}
