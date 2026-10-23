package ufes.br.sistemadesupermercado.mvp.presenter;

import ufes.br.sistemadesupermercado.mvp.model.HistoricoPrecoModel;
import ufes.br.sistemadesupermercado.mvp.model.ProdutoModel;
import ufes.br.sistemadesupermercado.mvp.repository.IHistoricoPrecoRepository;
import ufes.br.sistemadesupermercado.mvp.repository.IProdutoRepository;
import ufes.br.sistemadesupermercado.mvp.service.CalculoPrecoVendaService;
import ufes.br.sistemadesupermercado.mvp.service.HistoricoDePrecoService;
import ufes.br.sistemadesupermercado.mvp.service.IntervaloEntreCalculosService;
import ufes.br.sistemadesupermercado.mvp.view.CalculoMargemLucroView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.BitSet;
import java.util.Objects;

public class TelaCalculoMargemLucroPresenter {

    private CalculoMargemLucroView view;
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository historicoPrecoRepository;

    public TelaCalculoMargemLucroPresenter(IProdutoRepository produtoRepository, IHistoricoPrecoRepository historicoPrecoRepository) {
        Objects.requireNonNull(produtoRepository, "O repositório de produtos está nulo");
        Objects.requireNonNull(historicoPrecoRepository, "O repositório de históricos está nulo.");

        view = new CalculoMargemLucroView();
        this.produtoRepository = produtoRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;

        configuraView();
    }

    private void configuraView() {
        view.setLocationRelativeTo(null);

        inserirResgistros();

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
                    // Resolver erro da data.
                    if (!data.isEmpty()) {
                        int dia = Integer.parseInt(data.substring(0, 2));
                        int mes = Integer.parseInt(data.substring(3, 5));
                        int ano = Integer.parseInt(data.substring(6, 10));

                        atualizarPrecoVenda(LocalDate.of(ano, mes, dia));
                    } else {
                        throw new RuntimeException("O campo data está vazio.");
                    }

                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }
            }
        });

        view.setVisible(true);
    }

    private void criarRegistros(LocalDate data) {
        CalculoPrecoVendaService calculoPrecoVenda = new CalculoPrecoVendaService();
        HistoricoDePrecoService historicoDePrecoService = new HistoricoDePrecoService(historicoPrecoRepository);

        for (ProdutoModel produto : produtoRepository.getProdutos()) {
            historicoDePrecoService.adicionarRegistro(
                    data,
                    produto.getCategoria().getPercentualLucro().doubleValue(),
                    calculoPrecoVenda.calcularPrecoVenda(produto),
                    produto
            );
        }
    }

    private void inserirResgistros() {
        DefaultTableModel modelo = (DefaultTableModel) view.getTbProduto().getModel();

        for (ProdutoModel produto : produtoRepository.getProdutos()) {
            modelo.addRow(new Object[]{
                    produto.getNome(),
                    produto.getPrecoCusto(),
                    produto.getCategoria().getNome(),
                    produto.getCategoria().getPercentualLucro()
                }
            );
        }
    }

    private void atualizarPrecoVenda(LocalDate data) {
        DefaultTableModel modelo = (DefaultTableModel) view.getTbProduto().getModel();
        IntervaloEntreCalculosService intervalo = new IntervaloEntreCalculosService();
        int i = 0;

        if (intervalo.verificarIntervaloData(data)) {
            criarRegistros(data);
            for (HistoricoPrecoModel registro : historicoPrecoRepository.getHistorico()) {
                modelo.setValueAt(registro.getPrecoVenda(), i++, 4);
            }
        }
    }
}
