package ufes.br.sistemadesupermercado.mvp.presenter.stateInclusaoEdicaoVisualizacao;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TelaEdicaoBuscaProdutoStatePresenter extends InclusaoEdicaoVisualizacaoViewState {

    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;
    private List<ActionListener> monitores;


    public TelaEdicaoBuscaProdutoStatePresenter(InclusaoEdicaoVisualizacaoProdutoView view, String nome, IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        super(view);
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.monitores = new ArrayList<>();

        configuraView(nome);
    }

    private void configuraView(String nome) {
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

        Optional<ProdutoModel> produtoOpt = produtoRepository.buscarProduto(nome);

        ProdutoModel produto;

        ActionListener salva = null;
        ActionListener fecha = null;

        try {
            produto = produtoOpt.get();

            view.getTxtNomeProduto().setText(produto.getNome());
            view.getTxtPrecoCusto().setText(String.valueOf(produto.getPrecoCusto()));
            view.getCbCategoria().setSelectedItem(produto.getCategoria().getNome());

            salva = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String novoNome = view.getTxtNomeProduto().getText();
                        double preco = Double.parseDouble(view.getTxtPrecoCusto().getText());
                        Object categoria = view.getCbCategoria().getSelectedItem();

                        Optional<CategoriaModel> categoriaOpt = categoriaRepository.buscarCategoria(categoria.toString());

                        produtoRepository.editarProduto(produto.getNome(), novoNome, preco, categoriaOpt.get());

                        view.visualizar(novoNome);
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(view, ex.getMessage());
                    }
                }
            };
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, "Erro ao encontrar o produto.");
        }

        fecha = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.visualizar(nome);
            }
        };

        view.getBtnEditar().addActionListener(salva);
        view.getBtnFechar().addActionListener(fecha);

        monitores.add(salva);
        monitores.add(fecha);

        view.setVisible(true);
    }

    @Override
    public void visualizar(String nome) {

        view.getBtnEditar().removeActionListener(monitores.get(0));
        view.getBtnFechar().removeActionListener(monitores.get(1));

        view.setEstado(new TelaVisualizacaoBuscaProdutoStatePresenter(view, nome, produtoRepository, categoriaRepository));
    }
}
