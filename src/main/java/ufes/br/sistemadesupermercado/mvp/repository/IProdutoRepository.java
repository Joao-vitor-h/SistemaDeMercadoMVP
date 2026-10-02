package ufes.br.sistemadesupermercado.mvp.repository;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;

import java.util.Optional;

public interface IProdutoRepository {
    boolean verificarExistenciaProduto(String nome);

    Optional<ProdutoModel> buscarProduto(String nome);

    void adicionarProduto(String nome, double precoCusto, CategoriaModel categoria);

    void removerProduto(String nome);

    void editarProduto(String nome, double precoCusto);

    void editarProduto(String nome, CategoriaModel categoria);
}
