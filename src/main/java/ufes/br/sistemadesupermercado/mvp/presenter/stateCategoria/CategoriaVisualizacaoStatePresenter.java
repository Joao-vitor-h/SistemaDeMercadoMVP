package ufes.br.sistemadesupermercado.mvp.presenter.stateCategoria;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.CategoriaProdutoView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaVisualizacaoStatePresenter extends CategoriaState {

    private List<ActionListener> monitoresAcao;
    private List<MouseListener> monitoresMouse;

    public CategoriaVisualizacaoStatePresenter(CategoriaProdutoView view, ICategoriaRepository categoriaRepository, IProdutoRepository produtoRepository) {
        super(view, categoriaRepository, produtoRepository);
        monitoresAcao = new ArrayList<>();
        monitoresMouse = new ArrayList<>();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        JTable tabela = view.getTbCategoria();

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        DefaultTableCellRenderer centralizador = new DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);

        view.getLblModo().setText("Modo: Visualização");

        view.getTxtCategoria().setEditable(false);
        view.getTxtPercentual().setEditable(false);

        view.getBtnSalvar().setEnabled(false);
        view.getBtnCancelar().setEnabled(false);
        view.getBtnNovo().setEnabled(true);
        view.getBtnEditar().setEnabled(true);
        view.getBtnExcluir().setEnabled(true);
        view.getBtnFechar().setEnabled(true);

        if (modelo.getRowCount() == 0) {
            inserirCategorias();
        }

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centralizador);
        }

        MouseListener selecionaRegistro = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.rowAtPoint(e.getPoint());
                String categoria = tabela.getValueAt(linha, 0).toString();
                String valor = tabela.getValueAt(linha, 1).toString();
                view.getTxtCategoria().setText(categoria);
                view.getTxtPercentual().setText(valor);
            }
        };

        ActionListener novo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.incluir();
            }
        };

        ActionListener edita = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nomeCategoria = view.getTxtCategoria().getText();
                    String percentual = view.getTxtPercentual().getText();

                    if (nomeCategoria.isBlank() || percentual.isBlank()) {
                        throw new RuntimeException("Selecione uma categoria para editar.");
                    }

                    if (!categoriaRepository.verificarExistenciaCategoria(nomeCategoria)) {
                        throw new RuntimeException("Essa categoria não existe! Selecione uma categoria.");
                    }

                    view.editar();
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }

            }
        };

        ActionListener deleta = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.excluir();
            }
        };

        ActionListener fecha = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fechar();
            }
        };

        view.getTbCategoria().addMouseListener(selecionaRegistro);
        view.getBtnNovo().addActionListener(novo);
        view.getBtnEditar().addActionListener(edita);
        view.getBtnExcluir().addActionListener(deleta);
        view.getBtnFechar().addActionListener(fecha);

        monitoresMouse.add(selecionaRegistro);

        monitoresAcao.add(novo);
        monitoresAcao.add(edita);
        monitoresAcao.add(deleta);
        monitoresAcao.add(fecha);

        view.setVisible(true);
    }

    private void inserirCategorias() {
        DefaultTableModel modelo = (DefaultTableModel) view.getTbCategoria().getModel();

        for (CategoriaModel categoria : categoriaRepository.getCategorias()) {
            modelo.addRow(new Object[]{categoria.getNome(), categoria.getPercentualLucro()});
        }
    }

    @Override
    public void incluir() {
        removerMonitores();
        view.setEstado(new CategoriaInclusaoStatePresenter(view, categoriaRepository, produtoRepository));
    }

    @Override
    public void editar() {
        removerMonitores();
        view.setEstado(new CategoriaEdicaoStatePresenter(view, categoriaRepository, produtoRepository));
    }

    @Override
    public void excluir() {
        removerMonitores();
        view.setEstado(new CategoriaExclusaoStatePresenter(view, categoriaRepository, produtoRepository));
    }

    private void fechar() {
        view.dispose();
    }

    private void removerMonitores() {
        view.getTbCategoria().removeMouseListener(monitoresMouse.getFirst());

        view.getBtnNovo().removeActionListener(monitoresAcao.get(0));
        view.getBtnEditar().removeActionListener(monitoresAcao.get(1));
        view.getBtnExcluir().removeActionListener(monitoresAcao.get(2));
        view.getBtnFechar().removeActionListener(monitoresAcao.get(3));
    }

    @Override
    public String toString() {
        return "Visualizar";
    }

}
