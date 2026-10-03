package ufes.br.sistemadesupermercado.mvp.repository;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.seeder.Seeder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProdutoRepository implements IProdutoRepository {

    private static int contador;
    private List<ProdutoModel> produtos;


    public ProdutoRepository() {
        produtos = Seeder.alimentarProdutos();
        contador = produtos.size();
    }

    public static int getContador() { return contador; }

    @Override
    public boolean verificarExistenciaProduto(String nome) {

        Optional<ProdutoModel> produtoOpt = buscarProduto(nome);

        return produtoOpt.isPresent();
    }

    @Override
    public Optional<ProdutoModel> buscarProduto(String nome) {
        Objects.requireNonNull(nome, "O nome é nulo.");

        if (nome.isBlank() || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome passado é inválido para buscar um produto.");
        }

        for (ProdutoModel produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return Optional.of(produto);
            }
        }

        return Optional.empty();
    }

    @Override
    public void adicionarProduto(String nome, double precoCusto, CategoriaModel categoria) {

        Objects.requireNonNull(nome, "O nome é nulo.");
        Objects.requireNonNull(categoria, "A categoria é nula.");

        if (nome.isBlank() || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome passado é inválido para o produto.");
        }

        if (precoCusto <= 0) {
            throw new IllegalArgumentException("O preço de custa é inválido.");
        }

        produtos.add(new ProdutoModel(++contador, nome.trim(), precoCusto, categoria));
    }

    @Override
    public void removerProduto(String nome) {
        Optional<ProdutoModel> produtoOpt = buscarProduto(nome);

        if (produtoOpt.isPresent()) {
            produtos.remove(produtoOpt.get());
        }
    }

    @Override
    public void editarProduto(String nome, double precoCusto) {
        if(precoCusto <= 0) {
            throw new IllegalArgumentException("O preço está inválido.");
        }

        if (verificarExistenciaProduto(nome)) {
            Optional<ProdutoModel> produtoOpt = buscarProduto(nome);
            produtoOpt.get().setPrecoCusto(precoCusto);
        }
    }

    @Override
    public void editarProduto(String nome, CategoriaModel categoria) {
        Objects.requireNonNull(categoria, "A categoria está nula.");

        if (verificarExistenciaProduto(nome)) {
            Optional<ProdutoModel> produtoOpt = buscarProduto(nome);
            produtoOpt.get().setCategoria(categoria);
        }
    }

    @Override
    public List<ProdutoModel> getProdutos() { return produtos; }
}
