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
    public boolean verificarExistenciaProduto(java.lang.String nome) {

        Optional<ProdutoModel> produtoOpt = buscarProduto(nome);

        return produtoOpt.isPresent();
    }

    @Override
    public Optional<ProdutoModel> buscarProduto(java.lang.String nome) {
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

        if (verificarExistenciaProduto(nome)) {
            throw new IllegalArgumentException("O produto já existe!");
        }

        if (precoCusto <= 0) {
            throw new IllegalArgumentException("O preço de custa é inválido.");
        }

        produtos.add(new ProdutoModel(++contador, nome.trim(), precoCusto, categoria));
    }

    @Override
    public void removerProduto(java.lang.String nome) {
        Optional<ProdutoModel> produtoOpt = buscarProduto(nome);

        if (produtoOpt.isPresent()) {
            produtos.remove(produtoOpt.get());
        }
    }

    private void editarProduto(String nome, double precoCusto) {
        if(precoCusto <= 0) {
            throw new IllegalArgumentException("O preço está inválido.");
        }
        Optional<ProdutoModel> produtoOpt = buscarProduto(nome);
        produtoOpt.get().setPrecoCusto(precoCusto);
    }

    private void editarProduto(String nome, CategoriaModel categoria) {
        Optional<ProdutoModel> produtoOpt = buscarProduto(nome);
        produtoOpt.get().setCategoria(categoria);
    }

    private void editarProduto(String nome, String novoNome) {
        Optional<ProdutoModel> produtoOpt = buscarProduto(nome);
        produtoOpt.get().setNome(novoNome);
    }

    @Override
    public void editarProduto(String nome, String novoNome, double precoCusto, CategoriaModel categoria) {
        editarProduto(nome, novoNome);
        editarProduto(nome, precoCusto);
        editarProduto(nome, categoria);
    }

    @Override
    public List<ProdutoModel> getProdutos() { return produtos; }
}
