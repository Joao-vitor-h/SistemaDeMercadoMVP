package ufes.br.sistemadesupermercado.mvp.service;

import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.presenter.observer.IHistoricoObserver;
import ufes.br.sistemadesupermercado.mvp.repository.IHistoricoPrecoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoricoDePrecoServiceObserver {

    private IHistoricoPrecoRepository repository;
    private List<IHistoricoObserver> telas;

    public HistoricoDePrecoServiceObserver(IHistoricoPrecoRepository repository) {
        Objects.requireNonNull(repository, "O repositório de históricos está nulo.");

        this.repository = repository;
        this.telas = new ArrayList<>();
    }

    public void adicionarTela(IHistoricoObserver tela) {
        Objects.requireNonNull(tela, "O objeto tela está nulo.");
        this.telas.add(tela);
    }

    public void removerTela(IHistoricoObserver tela) {
        Objects.requireNonNull(tela, "O objeto tela está nulo.");
        this.telas.remove(tela);
    }

    public void adicionarRegistro(LocalDate data, double percentual, double precoVenda, ProdutoModel produto) {
        repository.criarRegistro(data, percentual, precoVenda, produto);
        notificarTelas(data);
    }

    private void notificarTelas(LocalDate data) {
        for (IHistoricoObserver tela : telas) {
            tela.atualizar(data);
        }
    }
}
