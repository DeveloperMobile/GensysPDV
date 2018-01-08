package com.codigosandroid.gensyspdv.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cliente.Cliente;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.gensyspdv.empresa.ServiceEmpresa;
import com.codigosandroid.gensyspdv.estoque.Estoque;
import com.codigosandroid.gensyspdv.pagamento.FormaPagamento;
import com.codigosandroid.gensyspdv.pagamento.ServiceFormaPagamento;
import com.codigosandroid.gensyspdv.pagamento.dialog.DialogFormaPagamento;
import com.codigosandroid.gensyspdv.usuario.Usuario;
import com.codigosandroid.gensyspdv.utils.DialogCallback;
import com.codigosandroid.gensyspdv.utils.Utils;
import com.codigosandroid.gensyspdv.venda.ItemVendaAdapter;
import com.codigosandroid.gensyspdv.venda.PyDetalhe;
import com.codigosandroid.gensyspdv.venda.PyRecPag;
import com.codigosandroid.gensyspdv.venda.PyVenda;
import com.codigosandroid.gensyspdv.venda.ServicePyVenda;
import com.codigosandroid.gensyspdv.venda.dialog.ClienteDialog;
import com.codigosandroid.gensyspdv.venda.dialog.DialogDesconto;
import com.codigosandroid.gensyspdv.venda.dialog.EstoqueDialog;
import com.codigosandroid.gensyspdv.venda.dialog.QuantidadeDialog;
import com.codigosandroid.gensyspdv.venda.dialog.UsuarioDialog;
import com.codigosandroid.utils.utils.AlertUtil;
import com.codigosandroid.utils.utils.AndroidUtil;
import com.codigosandroid.utils.utils.LogUtil;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 01/09/2017.
 */

public class VendaFragment extends BaseFragment implements View.OnClickListener {

    /* Componentes */
    private Button btnSearchCliente, btnSearchSalesman;
    private ImageButton btnCancelaCliente, btnCancelaUsuario, btnSearchProdut, btnDesconto;
    private RecyclerView recyclerView;
    private TextView lbDescricaoProduto, lbValorProduto, lbTotalProdutos, lbTotal;
    /* Objetos */
    private static Cliente cliente = null;
    private static Usuario usuario = null;
    private static Estoque estoque = null;
    /* Lista de produdtos */
    private List<Estoque> estoqueList = new ArrayList<>();
    private List<PyDetalhe> detalhes = new ArrayList<>();
    private List<PyRecPag> recPags = new ArrayList<>();
    /* Adapter */
    private ItemVendaAdapter adapter;
    /* Formato Numérico */
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    private DecimalFormat decimalFormat;
    /* Total */
    private double total = 0.0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_venda, container, false);
        inicializar(view);
        return view;

    }

    private void inicializar(View view) {

        /* Decimal Format Config */
        decimalFormat = new DecimalFormat("#,###.##");
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setParseBigDecimal(true);
        /* Seleciona cliente */
        btnSearchCliente = (Button) view.findViewById(R.id.btnSearchClient);
        btnSearchCliente.setOnClickListener(this);
        /* Seleciona vendedor */
        btnSearchSalesman = (Button) view.findViewById(R.id.btnSearchSalesman);
        btnSearchSalesman.setOnClickListener(this);
        /* Seleciona Produto */
        btnSearchProdut = (ImageButton) view.findViewById(R.id.btnSearchProd);
        btnSearchProdut.setOnClickListener(this);
        /* Cancela Cliente */
        btnCancelaCliente = (ImageButton) view.findViewById(R.id.btnCancelClient);
        btnCancelaCliente.setOnClickListener(this);
        /* Cancela Vendedor */
        btnCancelaUsuario = (ImageButton) view.findViewById(R.id.btnCancelSalesMan);
        btnCancelaUsuario.setOnClickListener(this);
        /* Finalizar Venda */
        view.findViewById(R.id.btnFinalizar).setOnClickListener(this);
        /* Lista de Produtos */
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        /* Descrição, valor e total de itens */
        lbDescricaoProduto = (TextView) view.findViewById(R.id.lbDescricaoProduto);
        lbValorProduto = (TextView) view.findViewById(R.id.lbValorProduto);
        lbTotalProdutos = (TextView) view.findViewById(R.id.lbTotalProdutos);
        lbTotal = (TextView) view.findViewById(R.id.lbTotal);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSearchClient:
                ClienteDialog.showDialog(getActivity().getSupportFragmentManager(), new DialogCallback<Cliente>() {

                    @Override
                    public void getObject(Cliente cliente) {
                        VendaFragment.cliente = cliente;
                        btnSearchCliente.setText(cliente.getFantasia());
                        ClienteDialog.closeDialog(getActivity().getSupportFragmentManager());
                        btnCancelaCliente.setVisibility(View.VISIBLE);
                    }

                });
                break;
            case R.id.btnCancelClient:
                cancelClient();
                break;
            case R.id.btnSearchSalesman:
                UsuarioDialog.showDialog(getActivity().getSupportFragmentManager(), new DialogCallback<Usuario>() {

                    @Override
                    public void getObject(Usuario usuario) {
                        VendaFragment.usuario = usuario;
                        btnSearchSalesman.setText(usuario.getApelido());
                        UsuarioDialog.closeDialog(getActivity().getSupportFragmentManager());
                        btnCancelaUsuario.setVisibility(View.VISIBLE);
                    }

                });
                break;
            case R.id.btnCancelSalesMan:
                cancelSalesMan();
                break;
            case R.id.btnSearchProd:
                EstoqueDialog.showDialog(getActivity().getSupportFragmentManager(), new DialogCallback<Estoque>() {

                    @Override
                    public void getObject(Estoque estoque) {

                        VendaFragment.estoque = estoque;
                        PyDetalhe pyDetalhe = new PyDetalhe();
                        pyDetalhe.setEstoque(estoque);
                        pyDetalhe.setQuantidade(1);
                        pyDetalhe.setVlDesconto(0);
                        pyDetalhe.setDesconto(0);
                        pyDetalhe.setValor(estoque.getPrecoVenda());
                        pyDetalhe.setTotal(pyDetalhe.getValor() * pyDetalhe.getQuantidade());
                        detalhes.add(pyDetalhe);
                        lbDescricaoProduto.setText(pyDetalhe.getEstoque().getDescricao());
                        lbValorProduto.setText(numberFormat.format(pyDetalhe.getEstoque().getPrecoVenda() * pyDetalhe.getQuantidade()));
                        lbTotalProdutos.setText(String.valueOf(detalhes.size()));

                        total += (pyDetalhe.getEstoque().getPrecoVenda() * pyDetalhe.getQuantidade());

                        lbTotal.setText(decimalFormat.format(total));
                        recyclerUp();
                        EstoqueDialog.closeDialog(getActivity().getSupportFragmentManager());


                    }

                });
                break;
            case R.id.btnFinalizar:
                if (detalhes.isEmpty()) {
                    AlertUtil.alert(getActivity(), "Aviso", "Inclua pelo menos um item na venda!");
                } else {
                    DialogFormaPagamento.showDialog(getActivity().getSupportFragmentManager(),
                            total, new DialogCallback<List<FormaPagamento>>() {
                                @Override
                                public void getObject(List<FormaPagamento> formaPagamentos) {
                                    try {
                                        Usuario operador = ServiceConfiguracoes.loadOperadorFromJSON(getActivity());
                                        PyVenda pyVenda = new PyVenda();
                                        pyVenda.setIdentificador(AndroidUtil.getSerial(getActivity()));
                                        pyVenda.setTipo("V");
                                        pyVenda.setDataEmissao(Utils.getDataAtual());
                                        pyVenda.setCliente(cliente);
                                        pyVenda.setUsuario(usuario);
                                        pyVenda.setIdOperador(operador.getId());
                                        pyVenda.setEmpresa(ServiceEmpresa.getByName(getActivity()));
                                        pyVenda.setTotal(total);
                                        pyVenda.setPyDetalhes(detalhes);
                                        for (int i = 0; i < formaPagamentos.size(); i++) {
                                            formaPagamentos.get(i).setId(ServiceFormaPagamento.insert(getActivity(), formaPagamentos.get(i)));
                                            PyRecPag pyRecPag = new PyRecPag();
                                            pyRecPag.setPyVenda(pyVenda);
                                            pyRecPag.setFormaPagamentoPagamento(formaPagamentos.get(i));
                                            pyRecPag.setValor(formaPagamentos.get(i).getValor());
                                            recPags.add(pyRecPag);
                                        }
                                        pyVenda.setPyRecPags(recPags);
                                        if (ServicePyVenda.insert(getActivity(), pyVenda)) {
                                            AlertUtil.alert(getActivity(), "Venda", "Venda realizada com sucesso!", 0, new Runnable() {
                                                @Override
                                                public void run() {
                                                    clear();
                                                }
                                            });
                                        } else {
                                            AlertUtil.alert(getActivity(), "Erro", "Erro ao gerar venda");
                                        }
                                        DialogFormaPagamento.closeDialog(getActivity().getSupportFragmentManager());
                                    } catch (Exception e) {
                                        LogUtil.error(TAG, e.getMessage(), e);
                                    }

                                }
                            });
                }
                break;
        }

    }

    private void recyclerUp() {

        adapter = new ItemVendaAdapter(getActivity(), detalhes, new ItemVendaAdapter.OnClickRecyclerItem() {

            @Override
            public void onDeleteItem(RecyclerView.ViewHolder holder, final int id) {

                AlertUtil.alert(getActivity(), "Aviso", "Deseja realmente remover o item "
                        + detalhes.get(id).getEstoque().getDescricao() + "?", R.string.btn_yes, R.string.btn_no, new Runnable() {

                    @Override
                    public void run() {

                        PyDetalhe pyDetalhe = detalhes.get(id);
                        detalhes.remove(id);

                        if (detalhes.isEmpty()) {
                            lbDescricaoProduto.setText(R.string.description_produt);
                            lbValorProduto.setText(numberFormat.format(0));
                            lbTotalProdutos.setText(String.valueOf(detalhes.size()));
                        } else {
                            lbDescricaoProduto.setText(pyDetalhe.getEstoque().getDescricao());
                            lbValorProduto.setText(numberFormat.format(pyDetalhe.getEstoque().getPrecoVenda()));
                            lbTotalProdutos.setText(String.valueOf(detalhes.size()));
                        }

                        total -= (pyDetalhe.getEstoque().getPrecoVenda() * pyDetalhe.getQuantidade()) - pyDetalhe.getVlDesconto();
                        lbTotal.setText(decimalFormat.format(total));
                        recyclerUp();

                    }

                });

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onDescontoItem(RecyclerView.ViewHolder holder, final int id) {
                PyDetalhe pyDetalhe = detalhes.get(id);
                DialogDesconto.showItem(getActivity().getSupportFragmentManager(), pyDetalhe, new DialogCallback<PyDetalhe>() {
                    @Override
                    public void getObject(PyDetalhe pyDetalhe) {
                        detalhes.get(id).setVlDesconto(pyDetalhe.getVlDesconto());
                        detalhes.get(id).setDesconto(pyDetalhe.getDesconto());
                        total = 0;
                        for (int i = 0; i < detalhes.size(); i++) {
                            total += (detalhes.get(i).getEstoque().getPrecoVenda() * detalhes.get(i).getQuantidade()) - detalhes.get(i).getVlDesconto();
                        }
                        lbTotal.setText(decimalFormat.format(total));
                        recyclerUp();
                    }
                });
            }

            @Override
            public void onSelectedItem(RecyclerView.ViewHolder holder, final int id) {

                PyDetalhe detalhe = detalhes.get(id);
                QuantidadeDialog.showDialog(getActivity().getSupportFragmentManager(), detalhe, new DialogCallback<PyDetalhe>() {

                    @Override
                    public void getObject(PyDetalhe pyDetalhe) {

                        if (pyDetalhe != null) {
                            detalhes.get(id).setQuantidade(pyDetalhe.getQuantidade());
                            total = 0;
                            for (int i = 0; i < detalhes.size(); i++) {
                                total += (detalhes.get(i).getEstoque().getPrecoVenda() * detalhes.get(i).getQuantidade()) - detalhes.get(i).getVlDesconto();
                            }
                            lbTotal.setText(decimalFormat.format(total));
                            recyclerUp();

                        }

                    }

                });

            }

        });
        recyclerView.setAdapter(adapter);

    }

    private void clear() {
        detalhes.clear();
        lbDescricaoProduto.setText(getString(R.string.description_produt));
        lbValorProduto.setText(numberFormat.format(0.0));
        lbTotalProdutos.setText(String.valueOf(detalhes.size()));
        cancelSalesMan();
        cancelClient();
        total = 0;
        lbTotal.setText(decimalFormat.format(total));
        recyclerUp();
    }

    private void cancelSalesMan() {
        usuario = null;
        btnSearchSalesman.setText(R.string.search_item_salesman);
        btnCancelaUsuario.setVisibility(View.GONE);
    }

    private void cancelClient() {
        cliente = null;
        btnSearchCliente.setText(R.string.search_item_client);
        btnCancelaCliente.setVisibility(View.GONE);
    }

}
