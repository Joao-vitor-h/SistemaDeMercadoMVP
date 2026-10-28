package ufes.br.sistemadesupermercado.mvp.presenter.stateCategoria;

import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.CategoriaProdutoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaExclusaoStatePresenter extends CategoriaState {

    private List<MouseListener> monitoresMouse;
    private List<ActionListener> monitoresAcao;

    public CategoriaExclusaoStatePresenter(CategoriaProdutoView view, ICategoriaRepository categoriaRepository, IProdutoRepository produtoRepository) {
        super(view, categoriaRepository, produtoRepository);
        this.monitoresMouse = new ArrayList<>();
        this.monitoresAcao = new ArrayList<>();
        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.getTxtCategoria().setText("");
        view.getTxtPercentual().setText("");

        view.getLblModo().setText("Modo: Exclusão");

        view.getTxtCategoria().setEditable(false);
        view.getTxtPercentual().setEditable(false);

        view.getBtnSalvar().setEnabled(false);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnNovo().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(true);
        view.getBtnFechar().setEnabled(false);

        JTable tabela = view.getTbCategoria();

        MouseListener clique = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.rowAtPoint(e.getPoint());
                String categoria = tabela.getValueAt(linha, 0).toString();
                String valor = tabela.getValueAt(linha, 1).toString();
                view.getTxtCategoria().setText(categoria);
                view.getTxtPercentual().setText(valor);
            }
        };

        ActionListener exclui = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    confirmar(view.getTxtCategoria().getText());
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }

            }
        };

        ActionListener cancela = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.visualizar();
            }
        };

        view.getTbCategoria().addMouseListener(clique);
        view.getBtnExcluir().addActionListener(exclui);
        view.getBtnCancelar().addActionListener(cancela);

        monitoresMouse.add(clique);
        monitoresAcao.add(exclui);
        monitoresAcao.add(cancela);

        view.setVisible(true);
    }

    @Override
    public void visualizar() {
        view.getTbCategoria().removeMouseListener(monitoresMouse.get(0));
        view.getBtnExcluir().removeActionListener(monitoresAcao.get(0));
        view.getBtnCancelar().removeActionListener(monitoresAcao.get(1));

        view.setEstado(new CategoriaVisualizacaoStatePresenter(view, categoriaRepository, produtoRepository));
    }

    @Override
    public String toString() {
        return "Exclusão";
    }

    private void confirmar(String nomeCategoria) {
        if (nomeCategoria.isBlank()) {
            throw new RuntimeException("Selecione uma categoria para excluir.");
        }

        for (ProdutoModel produto : produtoRepository.getProdutos()) {
            if (produto.getCategoria().getNome().equalsIgnoreCase(nomeCategoria)) {
                throw new RuntimeException("A categoria não pode ser excluída, pois está relacionada a pelo menos um produto.");
            }
        }

        Object[] alternativas = {"Sim", "Não"};

        int resposta = JOptionPane.showOptionDialog(
                view,
                "Tem certeza que deseja excluir essa categoria: " + categoriaRepository.buscarCategoria(nomeCategoria).get().getNome(),
                "Excluir?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, alternativas, alternativas[0]
        );

        if (resposta == JOptionPane.YES_OPTION) {
            categoriaRepository.removerCategoria(nomeCategoria);

            DefaultTableModel modelo = (DefaultTableModel) view.getTbCategoria().getModel();

            for (int i = 0; i < modelo.getRowCount(); i++) {
                if (modelo.getValueAt(i, 0).equals(nomeCategoria)) {
                    modelo.removeRow(i);
                }
            }

            JOptionPane.showMessageDialog(view, "A categoria " + nomeCategoria + " foi excluída com sucesso.", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
        }

        view.getTxtCategoria().setText("");
        view.getTxtPercentual().setText("");

        view.visualizar();
    }
}
