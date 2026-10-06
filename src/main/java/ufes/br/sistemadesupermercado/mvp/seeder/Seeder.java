package ufes.br.sistemadesupermercado.mvp.seeder;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;

import java.util.ArrayList;
import java.util.List;

public class Seeder {

    private static CategoriaModel educacao = new CategoriaModel(
            1, "Educação", 25.00
    );
    private static CategoriaModel papelaria = new CategoriaModel(
            2, "Papelaria", 30.00
    );
    private static CategoriaModel alimentacao = new CategoriaModel(
            3, "Alimentação", 22.00
    );
    private static CategoriaModel lazer = new CategoriaModel(
            4, "Lazer", 35.00
    );
    private static CategoriaModel entretenimento = new CategoriaModel(
            5, "Entretenimento", 40.00
    );
    private static CategoriaModel higiene = new CategoriaModel(
            6, "Higiene", 28.00
    );
    private static CategoriaModel limpeza = new CategoriaModel(
            7, "Limpeza", 25.00
    );

    public static List<CategoriaModel> alimentarCategorias() {
        List<CategoriaModel> categorias = new ArrayList<>();

        categorias.add(educacao);
        categorias.add(papelaria);
        categorias.add(alimentacao);
        categorias.add(lazer);
        categorias.add(entretenimento);
        categorias.add(higiene);
        categorias.add(limpeza);

        return categorias;
    }

    public static List<ProdutoModel> alimentarProdutos() {

        List<ProdutoModel> produtos = new ArrayList<>();

        produtos.add(
                new ProdutoModel(1, "Livro didático", 45.00, educacao)
        );
        produtos.add(
                new ProdutoModel(2, "Livro paradidático", 30.00, educacao)
        );
        produtos.add(
                new ProdutoModel(3, "Mochila escolar", 70.00, educacao)
        );
        produtos.add(
                new ProdutoModel(4, "Caderno universitário", 16.00, papelaria)
        );
        produtos.add(
                new ProdutoModel(5, "Lápis grafite HB", 1.20, papelaria)
        );
        produtos.add(
                new ProdutoModel(6, "Caneta esferográfica azul", 2.20, papelaria)
        );
        produtos.add(
                new ProdutoModel(7, "Borracha branca", 1.00, papelaria)
        );
        produtos.add(
                new ProdutoModel(8, "Apontador com depósito", 3.50, papelaria)
        );
        produtos.add(
                new ProdutoModel(9, "Jogo de tabuleiro", 55.00, lazer)
        );
        produtos.add(
                new ProdutoModel(10, "Bola recreativa", 40.00, lazer)
        );
        produtos.add(
                new ProdutoModel(11, "Quebra-cabeça 500 peças", 35.00, lazer)
        );
        produtos.add(
                new ProdutoModel(12, "Fone de ouvido", 48.00, entretenimento)
        );
        produtos.add(
                new ProdutoModel(13, "Caixa de som portátil", 80.00, entretenimento)
        );
        produtos.add(
                new ProdutoModel(14, "Revista de passatempos", 12.00, entretenimento)
        );
        produtos.add(
                new ProdutoModel(15, "Biscoito integral", 5.50, alimentacao)
        );
        produtos.add(
                new ProdutoModel(16, "Suco de uva 1 L", 9.00, alimentacao)
        );
        produtos.add(
                new ProdutoModel(17, "Barra de cereal", 3.20, alimentacao)
        );
        produtos.add(
                new ProdutoModel(18, "Sabonete", 2.80, higiene)
        );
        produtos.add(
                new ProdutoModel(19, "Creme dental", 5.50, higiene)
        );
        produtos.add(
                new ProdutoModel(20, "Detergente líquido", 2.60, limpeza)
        );
        produtos.add(
                new ProdutoModel(21, "Esponja multiuso", 1.70, limpeza)
        );

        return produtos;
    }
}
