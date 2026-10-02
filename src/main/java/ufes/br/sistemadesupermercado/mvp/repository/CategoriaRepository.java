package ufes.br.sistemadesupermercado.mvp.repository;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.seeder.Seeder;

import java.util.*;

public class CategoriaRepository implements ICategoriaRepository {

    private static int contador;
    private List<CategoriaModel> categorias;

    public CategoriaRepository() {
        categorias = Seeder.alimentarCategorias();
        contador = categorias.size();
    }

    public static int getContador() { return contador; }

    @Override
    public boolean verificarExistenciaCategoria(String nome) {

        Optional<CategoriaModel> categoriaOpt = buscarCategoria(nome);

        return categoriaOpt.isPresent();
    }

    @Override
    public void adicionarCategoria(String nome, double percentual) {
        Objects.requireNonNull(nome, "O nome é nulo.");

        if (nome.isBlank() || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome é inválido para a categoria.");
        }

        if (verificarExistenciaCategoria(nome)) {
            throw new RuntimeException("A categoria já existe.");
        }

        if (percentual <= 0) {
            throw new IllegalArgumentException("O percentual do produto é inválido.");
        }

        categorias.add(new CategoriaModel(++contador, nome.trim(), percentual));
    }

    @Override
    public Optional<CategoriaModel> buscarCategoria(String nome) {

        Objects.requireNonNull(nome, "O nome é nulo.");

        if (nome.isBlank() || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome passado é inválido para buscar uma categoria.");
        }

        for (CategoriaModel categoria : categorias) {
            if (categoria.getNome().equalsIgnoreCase(nome)) {
               return Optional.of(categoria);
            }
        }

        return Optional.empty();
    }

    @Override
    public void removerCategoria(String nome) {
        Optional<CategoriaModel> categorioOpt = buscarCategoria(nome);

        if (categorioOpt.isPresent()) {
            categorias.remove(categorioOpt.get());
        }
    }

    @Override
    public void editarCategoria(String nome, double percentual) {
        if (verificarExistenciaCategoria(nome)) {
            Optional<CategoriaModel> categorioOpt = buscarCategoria(nome);
            categorioOpt.get().setPercentualLucro(percentual);
        }
    }
}
