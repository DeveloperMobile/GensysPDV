package com.codigosandroid.gensyspdv.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.venda.PyVenda;

import java.text.NumberFormat;

/**
 * Created by Tiago on 12/01/2018.
 */

public class DetalheVendaFragment extends BaseFragment {

    private TextView data_emissao, cliente, vendedor,
            valor_bruto, desconto, valor_liquido, troco;

    private RecyclerView recyclerItensVenda, recyclerPagamento;

    private PyVenda pyVenda;

    private NumberFormat format = NumberFormat.getCurrencyInstance();
    private double desc = 0, total = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_venda, container, false);
        //pyVenda = (PyVenda) getArguments().getSerializable("item");
        inicializar(view);
        return view;
    }

    private void inicializar(View view) {
        data_emissao = (TextView) view.findViewById(R.id.data_emissao);
        //data_emissao.setText(pyVenda.getDataEmissao());

        cliente = (TextView) view.findViewById(R.id.cliente);
        //cliente.setText(pyVenda.getCliente().getFantasia());

        vendedor = (TextView) view.findViewById(R.id.vendedor);
        //vendedor.setText(pyVenda.getUsuario().getApelido());

        valor_bruto = (TextView) view.findViewById(R.id.valor_bruto);
        //valor_bruto.setText(format.format(pyVenda.getTotal()));

        desconto = (TextView) view.findViewById(R.id.desconto);
        //desconto.setText(format.format(desconto()));

        valor_liquido = (TextView) view.findViewById(R.id.valor_liquido);
        //valor_liquido.setText(format.format(valorLiquido()));

        recyclerItensVenda = (RecyclerView) view.findViewById(R.id.recyclerItensVenda);
        recyclerPagamento = (RecyclerView) view.findViewById(R.id.recyclerPagamento);
    }

    private double desconto() {
        for (int i = 0; i < pyVenda.getPyDetalhes().size(); i++) {
            desc += pyVenda.getPyDetalhes().get(i).getDesconto();
        }
        return desc;
    }

    private double valorLiquido() {
        for (int i = 0; i < pyVenda.getPyDetalhes().size(); i++) {
            total += pyVenda.getPyDetalhes().get(i).getTotal() - pyVenda.getPyDetalhes().get(i).getVlDesconto();
        }
        return total;
    }


}
