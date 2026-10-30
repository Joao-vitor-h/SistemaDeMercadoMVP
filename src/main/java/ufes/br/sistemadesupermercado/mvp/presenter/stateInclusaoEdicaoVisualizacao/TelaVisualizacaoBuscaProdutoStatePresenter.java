package ufes.br.sistemadesupermercado.mvp.presenter.stateInclusaoEdicaoVisualizacao;

import ufes.br.sistemadesupermercado.mvp.model.CategoriaModel;
import ufes.br.sistemadesupermercado.mvp.model.HistoricoPrecoModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.presenter.TelaHistoricoPrecoPresenter;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IHistoricoPrecoRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.HistoricoPrecoView;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TelaVisualizacaoBuscaProdutoStatePresenter extends InclusaoEdicaoVisualizacaoViewState {

    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;
    private IHistoricoPrecoRepository historicoPrecoRepository;
    private List<ActionListener> monitores;
    private DefaultTableModel modelo;

    public TelaVisualizacaoBuscaProdutoStatePresenter(InclusaoEdicaoVisualizacaoProdutoView view, String nome, IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository, IHistoricoPrecoRepository historicoPrecoRepository, DefaultTableModel modelo) {
        super(view);
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
        this.modelo = modelo;
        this.monitores = new ArrayList<>();

        configuraView(nome);
    }

    private void configuraView(String nomeProduto) {
        view.setLocationRelativeTo(null);

        inserirCategorias(categoriaRepository);

        if (!historicoPrecoRepository.getHistoricos().isEmpty()) {
            LocalDate data = historicoPrecoRepository.getHistoricos().getLast().getDataCalculo();

            for (HistoricoPrecoModel registro : historicoPrecoRepository.getHistoricoPorData(data)) {
                if (registro.getProduto().getNome().equalsIgnoreCase(nomeProduto)) {
                    view.getTxtMargem().setText(registro.getPercentualLucro().toString());
                    view.getTxtPrecoVenda().setText(registro.getPrecoVenda().toString());
                }
            }
        }

        ProdutoModel produto = produtoRepository.buscarProduto(nomeProduto).get();

        view.setTitle("Produto - Visualização");
        view.getTxtNomeProduto().setText(produto.getNome());
        view.getTxtPrecoCusto().setText(String.valueOf(produto.getPrecoCusto()));
        view.getTxtNomeProduto().setEditable(false);
        view.getTxtPrecoCusto().setEditable(false);
        view.getCbCategoria().setEnabled(false);
        view.getTxtPrecoVenda().setEditable(false);
        view.getTxtMargem().setEditable(false);
        view.getCbCategoria().setSelectedIndex(produto.getCategoria().getId() - 1);
        view.getBtnEditar().setText("Editar");
        view.getBtnFechar().setText("Fechar");
        view.getBtnVisualizarHistorico().setVisible(true);

        ActionListener edita = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.editar(produto.getNome());
            }
        };

        ActionListener fecha = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        };

        ActionListener historico = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaHistoricoPrecoPresenter telaHistorico = new TelaHistoricoPrecoPresenter(historicoPrecoRepository, view.getTxtNomeProduto().getText());
            }
        };

        view.getBtnEditar().addActionListener(edita);
        view.getBtnFechar().addActionListener(fecha);
        view.getBtnVisualizarHistorico().addActionListener(historico);

        monitores.add(edita);
        monitores.add(fecha);
        monitores.add(historico);

        view.setVisible(true);
    }

    @Override
    public void editar(String nome) {

        view.getBtnEditar().removeActionListener(monitores.get(0));
        view.getBtnFechar().removeActionListener(monitores.get(1));
        view.getBtnVisualizarHistorico().removeActionListener(monitores.get(2));

        view.setEstado(new TelaEdicaoBuscaProdutoStatePresenter(view, nome, produtoRepository, categoriaRepository, historicoPrecoRepository, modelo));
    }
}
