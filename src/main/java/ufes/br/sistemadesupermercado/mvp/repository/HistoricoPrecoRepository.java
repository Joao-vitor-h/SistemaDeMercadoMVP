package ufes.br.sistemadesupermercado.mvp.repository;

import ufes.br.sistemadesupermercado.mvp.model.HistoricoPrecoModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoricoPrecoRepository implements IHistoricoPrecoRepository {

    private List<HistoricoPrecoModel> historicos;

    public HistoricoPrecoRepository() {
        historicos = new ArrayList<>();
    }

    @Override
    public void criarRegistro(LocalDate data, double percentual, double precoVenda, ProdutoModel produto) {

        Objects.requireNonNull(data, "Está data está nula");
        Objects.requireNonNull(produto, "O produto está nulo.");

        if (percentual <= 0) {
            throw new IllegalArgumentException("O percentual não está válido.");
        }

        if (precoVenda <= 0) {
            throw new IllegalArgumentException("O preço de venda não está válido.");
        }

        historicos.add(
                new HistoricoPrecoModel(data, percentual, precoVenda, produto)
        );
    }

    @Override
    public List<HistoricoPrecoModel> getHistoricos() {
        return historicos;
    }

    @Override
    public List<HistoricoPrecoModel> getHistoricoPorData(LocalDate data) {
        Objects.requireNonNull(data, "A data está nula.");

        List<HistoricoPrecoModel> registros = new ArrayList<>();

        for (HistoricoPrecoModel historico : historicos) {
            if (historico.getDataCalculo().equals(data)) {
                registros.add(historico);
            }
        }

        return registros;
    }
}
