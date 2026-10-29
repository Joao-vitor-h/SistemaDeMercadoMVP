package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.model.HistoricoPrecoModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.IHistoricoPrecoRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.service.CalculoPrecoVendaService;
import ufes.br.sistemadesupermercado.mvp.service.HistoricoDePrecoServiceObserver;
import ufes.br.sistemadesupermercado.mvp.service.IntervaloEntreCalculosService;
import ufes.br.sistemadesupermercado.mvp.view.CalculoMargemLucroView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Objects;

public class TelaCalculoMargemLucroPresenter {

    private CalculoMargemLucroView view;
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository historicoPrecoRepository;
    private HistoricoDePrecoServiceObserver registro;

    public TelaCalculoMargemLucroPresenter(IProdutoRepository produtoRepository, IHistoricoPrecoRepository historicoPrecoRepository, HistoricoDePrecoServiceObserver registro) {
        Objects.requireNonNull(produtoRepository, "O repositório de produtos está nulo");
        Objects.requireNonNull(historicoPrecoRepository, "O repositório de históricos está nulo.");
        Objects.requireNonNull(registro, "O histórico preço service está nulo.");

        view = new CalculoMargemLucroView();
        this.produtoRepository = produtoRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
        this.registro = registro;

        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        if (!historicoPrecoRepository.getHistoricos().isEmpty()) {
            inserirResgistros(
                    historicoPrecoRepository.getHistoricos().getLast().getDataCalculo()
            );
        }

        DefaultTableCellRenderer centralizador = new DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);

        JTable tabela = view.getTbProduto();

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centralizador);
        }

        view.getBtnCalcular().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String data = view.getTxtData().getText().trim();

                    if (data.isEmpty()) {
                        throw new IllegalArgumentException("O campo data está vazio.");
                    }

                    if (data.length() != 10) {
                        throw new IllegalArgumentException("A data está incompleta!");
                    }

                    String dia = data.substring(0, 2);
                    String mes = data.substring(3, 5);
                    String ano = data.substring(6, 10);

                    LocalDate dataCalculo = LocalDate.of(Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia));

                    IntervaloEntreCalculosService intervalo = new IntervaloEntreCalculosService();

                    if (intervalo.verificarIntervaloData(dataCalculo)) {
                        criarRegistros(dataCalculo);
                        inserirResgistros(dataCalculo);
                    } else {
                        throw new RuntimeException("Não é possível calcular preço de venda, pois a data inserida não atende ao intervalo de 10 dias.");
                    }
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }
            }
        });

        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fechar();
            }
        });

        view.setVisible(true);
    }

    private void criarRegistros(LocalDate data) {
        CalculoPrecoVendaService calculoPrecoVenda = new CalculoPrecoVendaService();

        for (ProdutoModel produto : produtoRepository.getProdutos()) {
            registro.adicionarRegistro(
                    data,
                    produto.getCategoria().getPercentualLucro().doubleValue(),
                    calculoPrecoVenda.calcularPrecoVenda(produto),
                    produto
            );
        }
    }

    private void inserirResgistros(LocalDate data) {
        DefaultTableModel modelo = (DefaultTableModel) view.getTbProduto().getModel();

        if (!historicoPrecoRepository.getHistoricos().isEmpty()) {
            for (HistoricoPrecoModel registro : historicoPrecoRepository.getHistoricoPorData(data)) {
                modelo.addRow(new Object[]{
                    registro.getProduto().getNome(),
                    registro.getProduto().getPrecoCusto(),
                    registro.getProduto().getCategoria().getNome(),
                    registro.getPercentualLucro(),
                    registro.getPrecoVenda()
                });
            }
        }
    }

    private void fechar() {
        view.dispose();
    }

    public CalculoMargemLucroView getView() {
        return view;
    }
}
