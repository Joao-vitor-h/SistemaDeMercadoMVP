package ufes.br.sistemadesupermercado.mvp.model;

import java.time.LocalDate;
import java.util.Objects;

public class HistoricoPrecoModel {

    private LocalDate dataCalculo;
    private double percentualLucro;
    private double precoVenda;

    public HistoricoPrecoModel(LocalDate dataCalculo, double percentualLucro, double precoVenda) {
        Objects.requireNonNull(dataCalculo, "A data está nula.");

        this.dataCalculo = dataCalculo;
        this.percentualLucro = percentualLucro;
        this.precoVenda = precoVenda;
    }

    public LocalDate getDataCalculo() {
        return dataCalculo;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setDataCalculo(LocalDate dataCalculo) {
        this.dataCalculo = dataCalculo;
    }

    public void setPercentualLucro(double percentualLucro) {
        this.percentualLucro = percentualLucro;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }
}
