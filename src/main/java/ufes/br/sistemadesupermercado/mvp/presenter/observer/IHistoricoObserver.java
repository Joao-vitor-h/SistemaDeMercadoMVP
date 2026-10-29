package ufes.br.sistemadesupermercado.mvp.presenter.observer;

import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;

import java.time.LocalDate;

public interface IHistoricoObserver {
    void atualizar(LocalDate dataDoUltimoRegistro);
}
