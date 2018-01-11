package com.codigosandroid.gensyspdv.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.venda.PyDetalhe;
import com.codigosandroid.gensyspdv.venda.PyVenda;
import com.codigosandroid.gensyspdv.venda.ServicePyVenda;
import com.codigosandroid.gensyspdv.venda.VendasAdapter;

import java.util.List;

/**
 * Created by Tiago on 11/01/2018.
 */

public class VendasFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private VendasAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendas, container, false);
        inicializar(view);
        return view;
    }

    private void inicializar(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleVendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerUp();
    }

    private void recyclerUp() {
        List<PyVenda> pyVendas = ServicePyVenda.getAllInner(getActivity());
        adapter = new VendasAdapter(getActivity(), pyVendas, new VendasAdapter.OnClickRecyclerItem() {
            @Override
            public void onDetalheItem(RecyclerView.ViewHolder holder, int id) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

}
