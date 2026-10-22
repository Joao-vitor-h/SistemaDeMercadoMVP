package ufes.br.sistemadesupermercado.mvp.service;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;


public class CalculoPrecoVendaService {

    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;

    public double calcularPrecoVenda(ProdutoModel produto) {
        CategoriaModel categoria = produto.getCategoria();

        return produto.getPrecoCusto().doubleValue() * (1 + categoria.getPercentualLucro().doubleValue() / 100);
    }
}
