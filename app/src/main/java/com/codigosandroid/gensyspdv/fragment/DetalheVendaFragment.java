package com.codigosandroid.gensyspdv.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.venda.ItemAdapter;
import com.codigosandroid.gensyspdv.venda.PyDetalhe;
import com.codigosandroid.gensyspdv.venda.PyVenda;
import com.codigosandroid.gensyspdv.venda.ServicePyVenda;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tiago on 12/01/2018.
 */

public class DetalheVendaFragment extends BaseFragment {

    private TextView data_emissao, cliente, vendedor,
            valor_bruto, desconto, valor_liquido, troco;

    private RecyclerView recyclerItensVenda, recyclerPagamento;

    private PyVenda pyVenda;
    private List<PyDetalhe> pyDetalhes = new ArrayList<>();

    private NumberFormat format = NumberFormat.getCurrencyInstance();
    private double desc = 0, totalLiq = 0, totalBru = 0;
    private ItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_venda, container, false);
        pyVenda = (PyVenda) getArguments().getSerializable("item");
        pyDetalhes = ServicePyVenda.getAllInnerDetalhe(getActivity(), pyVenda.getId());
        inicializar(view);
        return view;
    }

    private void inicializar(View view) {
        data_emissao = (TextView) view.findViewById(R.id.data_emissao);
        data_emissao.setText(pyVenda.getDataEmissao());

        cliente = (TextView) view.findViewById(R.id.cliente);
        cliente.setText(pyVenda.getCliente().getFantasia());

        vendedor = (TextView) view.findViewById(R.id.vendedor);
        vendedor.setText(pyVenda.getUsuario().getApelido());

        valor_bruto = (TextView) view.findViewById(R.id.valor_bruto);
        valor_bruto.setText(format.format(valorBruto()));

        desconto = (TextView) view.findViewById(R.id.desconto);
        desconto.setText(format.format(desconto()));

        valor_liquido = (TextView) view.findViewById(R.id.valor_liquido);
        valor_liquido.setText(format.format(valorLiquido()));

        recyclerItensVenda = (RecyclerView) view.findViewById(R.id.recyclerItensVenda);
        recyclerItensVenda.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerItensVenda.setItemAnimator(new DefaultItemAnimator());
        recyclerUpItensVenda();

        recyclerPagamento = (RecyclerView) view.findViewById(R.id.recyclerPagamento);
        recyclerPagamento.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerPagamento.setItemAnimator(new DefaultItemAnimator());
    }

    private double desconto() {
        for (int i = 0; i < pyDetalhes.size(); i++) {
            desc += pyDetalhes.get(i).getVlDesconto();
        }
        return desc;
    }

    private double valorLiquido() {
        for (int i = 0; i < pyDetalhes.size(); i++) {
            totalLiq += pyDetalhes.get(i).getTotal() - pyDetalhes.get(i).getVlDesconto();
        }
        return totalLiq;
    }

    public double valorBruto() {
        for (int i = 0; i < pyDetalhes.size(); i++) {
            totalBru += pyDetalhes.get(i).getTotal();
        }
        return totalBru;
    }

    private void recyclerUpItensVenda() {
        adapter = new ItemAdapter(getActivity(), pyDetalhes);
        recyclerItensVenda.setAdapter(adapter);
    }


}
