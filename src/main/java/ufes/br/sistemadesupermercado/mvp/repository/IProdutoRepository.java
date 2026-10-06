package ufes.br.sistemadesupermercado.mvp.repository;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;

import java.util.List;
import java.util.Optional;

public interface IProdutoRepository {
    boolean verificarExistenciaProduto(String nome);

    Optional<ProdutoModel> buscarProduto(String nome);

    void adicionarProduto(String nome, double precoCusto, CategoriaModel categoria);

    void removerProduto(String nome);

    public void editarProduto(String nome, String novoNome, double precoCusto, CategoriaModel categoria);

    List<ProdutoModel> getProdutos();
}
