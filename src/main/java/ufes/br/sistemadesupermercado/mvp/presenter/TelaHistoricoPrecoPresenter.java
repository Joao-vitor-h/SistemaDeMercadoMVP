package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.model.HistoricoPrecoModel;
import ufes.br.sistemadesupermercado.mvp.presenter.observer.IHistoricoObserver;
import ufes.br.sistemadesupermercado.mvp.repository.IHistoricoPrecoRepository;
import ufes.br.sistemadesupermercado.mvp.view.HistoricoPrecoView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class TelaHistoricoPrecoPresenter {

    private HistoricoPrecoView view;
    private IHistoricoPrecoRepository historicoPrecoRepository;

    public TelaHistoricoPrecoPresenter(IHistoricoPrecoRepository historicoPrecoRepository, String nome) {
        Objects.requireNonNull(historicoPrecoRepository, "O repositório de históricos está nulo.");
        this.view = new HistoricoPrecoView();
        this.historicoPrecoRepository = historicoPrecoRepository;

        configuraView(nome);
    }

    private void configuraView(String nome) {
        view.setLocationRelativeTo(null);

        view.getTxtProduto().setEditable(false);
        view.getTxtCategoria().setEditable(false);

        DefaultTableCellRenderer centralizador = new DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < view.getTblHistorico().getColumnCount(); i++) {
            view.getTblHistorico().getColumnModel().getColumn(i).setCellRenderer(centralizador);
        }

        inserirRegistro(nome);

        for (HistoricoPrecoModel registro : historicoPrecoRepository.getHistoricos()) {
            if (registro.getProduto().getNome().equalsIgnoreCase(nome)) {
                view.getTxtProduto().setText(registro.getProduto().getNome());
                view.getTxtCategoria().setText(registro.getProduto().getCategoria().getNome());
            }
        }

        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });

        view.setVisible(true);
    }

    private void inserirRegistro(String nome) {
        DefaultTableModel modelo = (DefaultTableModel) view.getTblHistorico().getModel();

        for (HistoricoPrecoModel registro : historicoPrecoRepository.getHistoricos()) {
            if (registro.getProduto().getNome().equalsIgnoreCase(nome)) {
                String dia = String.valueOf(registro.getDataCalculo().getDayOfMonth());
                String mes = String.valueOf(registro.getDataCalculo().getMonthValue());
                String ano = String.valueOf(registro.getDataCalculo().getYear());

                modelo.addRow(new Object[]{
                        dia + "/" + mes + "/" + ano,
                        registro.getPercentualLucro(),
                        registro.getPrecoVenda()
                });
            }
        }
    }
}
