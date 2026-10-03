package ufes.br.sistemadesupermercado;

import ufes.br.sistemadesupermercado.mvp.presenter.TelaPrincipalPresenter;
import ufes.br.sistemadesupermercado.mvp.repository.CategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.repository.ProdutoRepository;

/**
 *
 * @author ludico
 */
public class SistemaDeSupermercadoMVP {

    public static void main(String[] args) {
        IProdutoRepository produtoRepository = new ProdutoRepository();
        ICategoriaRepository categoriaRepository = new CategoriaRepository();

        TelaPrincipalPresenter tela1 = new TelaPrincipalPresenter(produtoRepository, categoriaRepository);
    }
}
