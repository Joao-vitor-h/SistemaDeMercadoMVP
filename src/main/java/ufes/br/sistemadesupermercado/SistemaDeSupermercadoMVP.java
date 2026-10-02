package ufes.br.sistemadesupermercado;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.repository.CategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;

import java.util.Optional;

/**
 *
 * @author ludico
 */
public class SistemaDeSupermercadoMVP {

    public static void main(String[] args) {

        ICategoriaRepository categorias = new CategoriaRepository();

        Optional<CategoriaModel> categoriaOpt = categorias.buscarCategoria("Entretenimento");

        System.out.println(categoriaOpt.get());

        categorias.adicionarCategoria("Teste", 50.00);

        categoriaOpt = categorias.buscarCategoria("Teste");

        System.out.println(categoriaOpt.get());

        System.out.println(CategoriaRepository.getContador());

        categorias.editarCategoria("TesTe", 60.00);

        System.out.println(categoriaOpt.get());

        categorias.removerCategoria("TESTE");

        categoriaOpt = categorias.buscarCategoria("Teste");

        System.out.println(categoriaOpt.isPresent() ? "SIM" : "NÃO");
    }
}
