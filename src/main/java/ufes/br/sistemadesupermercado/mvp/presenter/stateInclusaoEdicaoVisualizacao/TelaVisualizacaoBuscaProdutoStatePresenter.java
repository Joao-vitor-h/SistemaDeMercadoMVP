package ufes.br.sistemadesupermercado.mvp.presenter.stateInclusaoEdicaoVisualizacao;

import ufes.br.sistemadesupermercado.mvp.model.HistoricoPrecoModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.ICategoriaRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IHistoricoPrecoRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.view.InclusaoEdicaoVisualizacaoProdutoView;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.AsyncBoxView;
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

    public TelaVisualizacaoBuscaProdutoStatePresenter(InclusaoEdicaoVisualizacaoProdutoView view, String nome, IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository, IHistoricoPrecoRepository historicoPrecoRepository) {
        super(view);
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
        this.monitores = new ArrayList<>();

        configuraView(nome);
    }

    private void configuraView(String nomeProduto) {
        view.setLocationRelativeTo(null);

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

        view.getBtnEditar().addActionListener(edita);
        view.getBtnFechar().addActionListener(fecha);

        monitores.add(edita);
        monitores.add(fecha);

        view.setVisible(true);
    }

    @Override
    public void editar(String nome) {

        view.getBtnEditar().removeActionListener(monitores.get(0));
        view.getBtnFechar().removeActionListener(monitores.get(1));

        view.setEstado(new TelaEdicaoBuscaProdutoStatePresenter(view, nome, produtoRepository, categoriaRepository, historicoPrecoRepository));
    }
}
