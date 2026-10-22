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
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

        view.getBtnCalcular().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = view.getTxtData().getText();

                int dia = Integer.parseInt(data.substring(0, 2));
                int mes = Integer.parseInt(data.substring(3, 5));
                int ano = Integer.parseInt(data.substring(6, 10));

                atualizarPrecoVenda(LocalDate.of(ano, mes, dia));

                inserirResgistros();
            }
        });

        view.setVisible(true);
    }

    private void inserirResgistros() {
        DefaultTableModel modelo = (DefaultTableModel) view.getTbProduto().getModel();

        for (HistoricoPrecoModel registro : historicoPrecoRepository.getHistorico()) {
            modelo.addRow(new Object[]{
                            registro.getProduto().getNome(),
                            registro.getProduto().getPrecoCusto(),
                            registro.getProduto().getCategoria().getNome(),
                            registro.getPercentualLucro(),
                            registro.getPrecoVenda()
                    }
            );
        }
    }

    private void atualizarPrecoVenda(LocalDate data) {
        CalculoPrecoVendaService calculoPrecoVenda = new CalculoPrecoVendaService();
        HistoricoDePrecoService historicoDePrecoService = new HistoricoDePrecoService(historicoPrecoRepository);
        IntervaloEntreCalculosService intervalo = new IntervaloEntreCalculosService();

        try {
            if (intervalo.verificarIntervaloData(data)) {
                for (ProdutoModel produto : produtoRepository.getProdutos()) {
                    historicoDePrecoService.adicionarRegistros(
                            data,
                            produto.getCategoria().getPercentualLucro().doubleValue(),
                            calculoPrecoVenda.calcularPrecoVenda(produto),
                            produto
                    );
                }



            } else {
                throw new RuntimeException("O intervalo de dias ainda não foi atingido!");
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }
    }
}
