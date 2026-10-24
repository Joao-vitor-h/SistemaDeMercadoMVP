package ufes.br.sistemadesupermercado.mvp.repository;

import ufes.br.sistemadesupermercado.mvp.model.HistoricoPrecoModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;

import java.time.LocalDate;
import java.util.List;

public interface IHistoricoPrecoRepository {
    void criarRegistro(LocalDate data, double percentual, double precoVenda, ProdutoModel produto);

    List<HistoricoPrecoModel> getHistoricos();

    List<HistoricoPrecoModel> getHistoricoPorData(LocalDate data);
}
