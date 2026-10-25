package ufes.br.sistemadesupermercado.mvp.presenter.stateCategoria;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
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

    public CategoriaVisualizacaoStatePresenter(CategoriaProdutoView view, ICategoriaRepository categoriaRepository) {
        super(view, categoriaRepository);
        monitoresAcao = new ArrayList<>();
        monitoresMouse = new ArrayList<>();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.getBtnSalvar().setEnabled(false);
        view.getBtnCancelar().setEnabled(false);

        inserirCategorias();

        DefaultTableCellRenderer centralizador = new DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);

        JTable tabela = view.getTbCategoria();

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centralizador);
        }

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        List<String> nomeCategoria = new ArrayList<>();

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
                view.editar();
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
        view.getTbCategoria().removeMouseListener(monitoresMouse.getFirst());

        view.getBtnNovo().removeActionListener(monitoresAcao.get(0));
        view.getBtnEditar().removeActionListener(monitoresAcao.get(1));
        view.getBtnExcluir().removeActionListener(monitoresAcao.get(2));
        view.getBtnFechar().removeActionListener(monitoresAcao.get(3));

        view.setEstado(new CategoriaInclusaoStatePresenter(view, categoriaRepository));
    }

    @Override
    public void editar() {

    }

    @Override
    public void excluir() {

    }

    private void fechar() {
        view.dispose();
    }

}
