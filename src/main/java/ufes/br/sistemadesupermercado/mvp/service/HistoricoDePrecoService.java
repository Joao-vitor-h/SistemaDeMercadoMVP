package ufes.br.sistemadesupermercado.mvp.service;

import ufes.br.sistemadesupermercado.mvp.model.HistoricoPrecoModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.IHistoricoPrecoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;

public class HistoricoDePrecoService {

    private IHistoricoPrecoRepository repository;

    public HistoricoDePrecoService(IHistoricoPrecoRepository repository) {
        Objects.requireNonNull(repository, "O repositório de históricos está nulo.");

        this.repository = repository;
    }

    public void adicionarRegistros(LocalDate data, double percentual, double precoVenda, ProdutoModel produto) {
        repository.criarRegistro(data, percentual, precoVenda, produto);
    }
}
