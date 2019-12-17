package com.codigosandroid.gensyspdv.pagamento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.utils.Constantes;
import com.codigosandroid.gensyspdv.utils.OnClickRecyclerItem;

import java.util.List;

/**
 * Created by Tiago on 30/11/2017.
 */

public class TipoPagamentoAdapter extends RecyclerView.Adapter<TipoPagamentoAdapter.TipoPagamentoViewHolder> {

    private Context context;
    private List<TipoPagamento> tipoPagamentos;
    private OnClickRecyclerItem onClickRecyclerItem;

    public TipoPagamentoAdapter(Context context, List<TipoPagamento> tipoPagamentos, OnClickRecyclerItem onClickRecyclerItem) {
        this.tipoPagamentos = tipoPagamentos;
        this.context = context;
        this.onClickRecyclerItem = onClickRecyclerItem;
    }

    @Override
    public TipoPagamentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_forma_pagamento, parent, false);
        return new TipoPagamentoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final TipoPagamentoViewHolder holder, final int position) {
        TipoPagamento tipoPagamento = tipoPagamentos.get(position);
        holder.descricao.setText(tipoPagamento.getDescricao());

        switch (tipoPagamento.getTipoPagamento()) {
            case Constantes.DINHEIRO: holder.icon.setImageResource(R.drawable.ic_money); break;
            case Constantes.CREDITO: holder.icon.setImageResource(R.drawable.ic_card); break;
            case Constantes.CREDITO_PARCELA: holder.icon.setImageResource(R.drawable.ic_card); break;
            case Constantes.DEBITO: holder.icon.setImageResource(R.drawable.ic_card); break;
            case Constantes.VOUCHER: holder.icon.setImageResource(R.drawable.ic_card); break;
        }

        holder.cardPag.setOnClickListener(v -> {
            if (onClickRecyclerItem != null) {
                onClickRecyclerItem.onClickItem(holder, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tipoPagamentos.isEmpty() ? 0 : tipoPagamentos.size();
    }

    public class TipoPagamentoViewHolder extends RecyclerView.ViewHolder {

        private CardView cardPag;
        private TextView descricao;
        private ImageView icon;

        public TipoPagamentoViewHolder(View itemView) {

            super(itemView);
            descricao = (TextView) itemView.findViewById(R.id.descricao);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            cardPag = (CardView) itemView.findViewById(R.id.cardPag);

        }

    }

}
