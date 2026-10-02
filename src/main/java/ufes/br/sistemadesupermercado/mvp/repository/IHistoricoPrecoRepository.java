package ufes.br.sistemadesupermercado.mvp.repository;

import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;

import java.time.LocalDate;

public interface IHistoricoPrecoRepository {
    void criarRegistro(LocalDate data, double percentual, double precoVenda, ProdutoModel produto);

    boolean verificarIntervaloData(LocalDate data) throws RuntimeException;
}
