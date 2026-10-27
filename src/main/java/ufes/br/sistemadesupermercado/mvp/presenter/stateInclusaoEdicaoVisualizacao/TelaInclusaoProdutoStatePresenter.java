package ufes.br.sistemadesupermercado.mvp.presenter.stateInclusaoEdicaoVisualizacao;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TelaInclusaoProdutoStatePresenter extends InclusaoEdicaoVisualizacaoViewState {

    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;
    private List<ActionListener> monitores;

    public TelaInclusaoProdutoStatePresenter(InclusaoEdicaoVisualizacaoProdutoView view, IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        super(view);
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.monitores = new ArrayList<>();

        configuraView();
    }

    // Aplicar padrão Observer sempre que ocorrer uma inserção na tela de busca dos produtos.
    private void configuraView() {
        view.setLocationRelativeTo(null);

        view.setTitle("Produto - Inclusão/Edição");
        view.getBtnVisualizarHistorico().setVisible(false);
        view.getTxtNomeProduto().setEnabled(true);
        view.getTxtPrecoCusto().setEnabled(true);
        view.getCbCategoria().setEnabled(true);
        view.getTxtMargem().setEditable(false);
        view.getTxtPrecoVenda().setEditable(false);
        view.getBtnEditar().setText("Salvar");
        view.getBtnFechar().setText("Cancelar");
        view.getTxtNomeProduto().setText("");
        view.getTxtPrecoCusto().setText("");

        ActionListener salva = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String nome = view.getTxtNomeProduto().getText();
                        double preco = Double.parseDouble(view.getTxtPrecoCusto().getText());
                        Object categoria = view.getCbCategoria().getSelectedItem();

                        criarNovoProduto(nome, preco, categoria.toString());
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(view, ex.getMessage());
                    }
                }
        };

        ActionListener fecha = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        };

        view.getBtnEditar().addActionListener(salva);
        view.getBtnFechar().addActionListener(fecha);

        monitores.add(salva);
        monitores.add(fecha);

        view.setVisible(true);
    }

    private void criarNovoProduto(java.lang.String nome, double precoCusto, String categoria) {
        Optional<CategoriaModel> categoriaOpt = categoriaRepository.buscarCategoria(categoria);

        if (categoriaOpt.isPresent()) {
            produtoRepository.adicionarProduto(nome, precoCusto, categoriaOpt.get());
        }

        view.getTxtNomeProduto().setText("");
        view.getTxtPrecoCusto().setText("");

        view.visualizar(nome);
    }

    private void cancelar() {
        view.dispose();
    }

    @Override
    public void visualizar(String nome) {

        view.getBtnEditar().removeActionListener(monitores.get(0));
        view.getBtnFechar().removeActionListener(monitores.get(1));

        view.setEstado(new TelaVisualizacaoProdutoStatePresenter(view, nome, produtoRepository, categoriaRepository));
    }
}
