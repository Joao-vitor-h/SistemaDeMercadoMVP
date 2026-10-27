package ufes.br.sistemadesupermercado.mvp.presenter.stateCategoria;

import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.view.CategoriaProdutoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CategoriaInclusaoStatePresenter extends CategoriaState {

    private List<ActionListener> monitores;

    public CategoriaInclusaoStatePresenter(CategoriaProdutoView view, ICategoriaRepository categoriaRepository) {
        super(view, categoriaRepository);
        monitores = new ArrayList<>();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.getLblModo().setText("Modo: Inclusão");

        view.getTxtCategoria().setEditable(true);
        view.getTxtPercentual().setEditable(true);

        view.getTxtCategoria().setText("");
        view.getTxtPercentual().setText("");

        view.getBtnNovo().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnFechar().setEnabled(false);

        ActionListener cadastra = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoria = view.getTxtCategoria().getText();
                String percentual = view.getTxtPercentual().getText();
                double valor = Double.parseDouble(percentual);

                BigDecimal percentualLucro = new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP);

                try {
                    categoriaRepository.adicionarCategoria(
                            categoria,
                            percentualLucro.doubleValue()
                    );

                    DefaultTableModel modelo = (DefaultTableModel) view.getTbCategoria().getModel();

                    modelo.addRow(new Object[]{categoria, percentualLucro});
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }

                view.getTxtCategoria().setText("");
                view.getTxtPercentual().setText("");
            }
        };

        ActionListener cancela = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.visualizar();
            }
        };

        view.getBtnSalvar().addActionListener(cadastra);
        view.getBtnCancelar().addActionListener(cancela);

        monitores.add(cadastra);
        monitores.add(cancela);

        view.setVisible(true);
    }

    @Override
    public void visualizar() {
        view.getBtnSalvar().removeActionListener(monitores.get(0));
        view.getBtnCancelar().removeActionListener(monitores.get(1));

        view.setEstado(new CategoriaVisualizacaoStatePresenter(view, categoriaRepository));
    }

    @Override
    public String toString() {
        return "Inclusão";
    }
}
