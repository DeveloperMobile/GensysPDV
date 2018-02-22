package com.codigosandroid.gensyspdv.pagamento;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.utils.OnClickRecyclerItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

/**
 * Created by Tiago on 30/11/2017.
 */

public class FormaPagamentoAdapter extends RecyclerView.Adapter<FormaPagamentoAdapter.FormaPagamentoViewHolder> {

    private Context context;
    private List<FormaPagamento> formaPagamentos;
    private OnClickRecyclerItem onClickRecyclerItem;
    private DecimalFormat format;

    public FormaPagamentoAdapter(Context context, List<FormaPagamento> formaPagamentos, OnClickRecyclerItem onClickRecyclerItem) {
        this.formaPagamentos = formaPagamentos;
        this.context = context;
        this.onClickRecyclerItem = onClickRecyclerItem;
        /* Decimal Format */
        format = new DecimalFormat("R$#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
        format.setMinimumFractionDigits(2);
        format.setParseBigDecimal(true);
    }

    @Override
    public FormaPagamentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_pagamento, parent, false);
        return new FormaPagamentoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final FormaPagamentoViewHolder holder, final int position) {
        FormaPagamento formaPagamento = formaPagamentos.get(position);
        holder.desc_pag.setText(formaPagamento.getTipoPagamento().getDescricao());
        holder.valor_pag.setText(format.format(formaPagamento.getValor()));
        holder.btnDelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickRecyclerItem != null) {
                    onClickRecyclerItem.onClickItem(holder, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return formaPagamentos.isEmpty() ? 0 : formaPagamentos.size();
    }

    public class FormaPagamentoViewHolder extends RecyclerView.ViewHolder {

        private TextView desc_pag;
        private TextView valor_pag;
        private ImageView btnDelItem;

        public FormaPagamentoViewHolder(View itemView) {

            super(itemView);
            desc_pag = (TextView) itemView.findViewById(R.id.desc_pag);
            valor_pag = (TextView) itemView.findViewById(R.id.valor_pag);
            btnDelItem = (ImageView) itemView.findViewById(R.id.btnDelItem);

        }

    }

}
