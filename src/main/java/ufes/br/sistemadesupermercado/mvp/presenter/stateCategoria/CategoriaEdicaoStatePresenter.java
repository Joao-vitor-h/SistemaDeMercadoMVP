package ufes.br.sistemadesupermercado.mvp.presenter.stateCategoria;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.CategoriaProdutoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaEdicaoStatePresenter extends CategoriaState {

    private List<ActionListener> monitores;

    public CategoriaEdicaoStatePresenter(CategoriaProdutoView view, ICategoriaRepository categoriaRepository, IProdutoRepository produtoRepository) {
        super(view, categoriaRepository, produtoRepository);
        this.monitores = new ArrayList<>();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.getLblModo().setText("Modo: Edição");

        view.getTxtCategoria().setEditable(true);
        view.getTxtPercentual().setEditable(true);

        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnNovo().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnFechar().setEnabled(false);

        DefaultTableModel modelo = (DefaultTableModel) view.getTbCategoria().getModel();

        String nomeCategoria = view.getTxtCategoria().getText();
        Optional<CategoriaModel> categoriaOpt = categoriaRepository.buscarCategoria(nomeCategoria);

        ActionListener salvaEdicao = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novoNome = view.getTxtCategoria().getText();
                double percentual = Double.parseDouble(view.getTxtPercentual().getText());

                String nomeAntigo = categoriaOpt.get().getNome();

                if (categoriaOpt.isPresent()) {
                    categoriaRepository.editarCategoria(nomeAntigo, novoNome, percentual);
                }

                for (int i = 0; i < modelo.getRowCount(); i++) {
                    String registro = modelo.getValueAt(i, 0).toString();
                    if (nomeAntigo.equalsIgnoreCase(registro)) {
                        modelo.setValueAt(novoNome, i, 0);
                        modelo.setValueAt(new BigDecimal(percentual).setScale(2, RoundingMode.HALF_UP), i, 1);
                    }
                }

                view.visualizar();
            }
        };

        ActionListener canecela = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getTxtCategoria().setText("");
                view.getTxtPercentual().setText("");

                view.visualizar();
            }
        };

        view.getBtnSalvar().addActionListener(salvaEdicao);
        view.getBtnCancelar().addActionListener(canecela);

        monitores.add(salvaEdicao);
        monitores.add(canecela);

        view.setVisible(true);
    }

    @Override
    public void visualizar() {
        view.getBtnSalvar().removeActionListener(monitores.get(0));
        view.getBtnCancelar().removeActionListener(monitores.get(1));

        view.setEstado(new CategoriaVisualizacaoStatePresenter(view, categoriaRepository, produtoRepository));
    }

    @Override
    public String toString() {
        return "Edição";
    }
}
