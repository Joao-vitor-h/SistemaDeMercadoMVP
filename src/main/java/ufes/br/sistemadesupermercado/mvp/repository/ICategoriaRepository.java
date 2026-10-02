package ufes.br.sistemadesupermercado.mvp.repository;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;

import java.util.Optional;

public interface ICategoriaRepository {
    boolean verificarExistenciaCategoria(String nome);

    void adicionarCategoria(String nome, double percentual);

    Optional<CategoriaModel> buscarCategoria(String nome);

    void removerCategoria(String nome);

    void editarCategoria(String nome, double percentual);
}
