package com.codigosandroid.gensyspdv.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.activity.DetalheVendaActivity;
import com.codigosandroid.gensyspdv.venda.PyDetalhe;
import com.codigosandroid.gensyspdv.venda.PyVenda;
import com.codigosandroid.gensyspdv.venda.ServicePyVenda;
import com.codigosandroid.gensyspdv.venda.VendasAdapter;
import com.codigosandroid.utils.utils.LogUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Tiago on 11/01/2018.
 */

public class VendasFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private VendasAdapter adapter;
    private TextView lb_total;
    private double total = 0;
    private NumberFormat format = NumberFormat.getCurrencyInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendas, container, false);
        inicializar(view);
        return view;
    }

    private void inicializar(View view) {
        lb_total = (TextView) view.findViewById(R.id.lbTotal);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleVendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerUp();
    }

    private void recyclerUp() {
        final List<PyVenda> pyVendas = ServicePyVenda.getAllInnerPyVenda(getActivity());

        if (!pyVendas.isEmpty()) {
            for (int i = 0; i < pyVendas.size(); i++) {

                total += pyVendas.get(i).getPyDetalhes().get(i).getTotal();

            }
            lb_total.setText(format.format(total));
        }

        adapter = new VendasAdapter(getActivity(), pyVendas, new VendasAdapter.OnClickRecyclerItem() {
            @Override
            public void onDetalheItem(RecyclerView.ViewHolder holder, int id) {
                PyVenda pyVenda = pyVendas.get(id);
                Intent intent = new Intent(getActivity(), DetalheVendaActivity.class);
                intent.putExtra("item", pyVenda);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
