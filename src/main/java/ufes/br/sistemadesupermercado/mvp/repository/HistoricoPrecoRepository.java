package ufes.br.sistemadesupermercado.mvp.repository;

import ufes.br.sistemadesupermercado.mvp.model.HistoricoPrecoModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoricoPrecoRepository implements IHistoricoPrecoRepository {

    private List<HistoricoPrecoModel> historico;


    public HistoricoPrecoRepository() {
        historico = new ArrayList<>();
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

        try {
            if (verificarIntervaloData(data)) {
                historico.add(
                        new HistoricoPrecoModel(data, percentual, precoVenda, produto)
                );
            }
        } catch (RuntimeException e) {
            historico.add(
                    new HistoricoPrecoModel(data, percentual, precoVenda, produto)
            );
        }
    }

    @Override
    public boolean verificarIntervaloData(LocalDate data) throws RuntimeException {

        HistoricoPrecoModel ultimoRegistro = historico.getLast();

        LocalDate dataUltimoRegistro = ultimoRegistro.getDataCalculo();

        long dias = dataUltimoRegistro.until(data, ChronoUnit.DAYS);

        return dias >= 10;
    }
}
