package ufes.br.sistemadesupermercado.mvp.service;

import ufes.br.sistemadesupermercado.mvp.model.HistoricoPrecoModel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class IntervaloEntreCalculosService {

    private static LocalDate dataDoUltimoCalculo;

    public boolean verificarIntervaloData(LocalDate data) {

        if (dataDoUltimoCalculo == null) {
            dataDoUltimoCalculo = data;
            return true;
        }

        long dias = dataDoUltimoCalculo.until(data, ChronoUnit.DAYS);

        if (dias >= 10) {
            dataDoUltimoCalculo = data;
            return true;
        }

        return false;
    }
}
