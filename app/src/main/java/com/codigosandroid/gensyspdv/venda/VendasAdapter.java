package com.codigosandroid.gensyspdv.venda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.utils.Utils;

import java.text.NumberFormat;
import java.util.List;


/**
 * Created by Tiago on 11/01/2018.
 */

public class VendasAdapter extends RecyclerView.Adapter<VendasAdapter.VendasViewHolder> {

    private Context context;
    private List<PyVenda> pyVendas;
    private OnClickRecyclerItem onClickRecyclerItem;
    private NumberFormat format = NumberFormat.getCurrencyInstance();
    private double total = 0;

    public interface OnClickRecyclerItem {

        void onDetalheItem(RecyclerView.ViewHolder holder, int id);

    }

    public VendasAdapter(Context context, List<PyVenda> pyVendas, OnClickRecyclerItem onClickRecyclerItem) {
        this.context = context;
        this.pyVendas = pyVendas;
        this.onClickRecyclerItem = onClickRecyclerItem;
    }

    @Override
    public VendasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_venda, parent, false);
        return new VendasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VendasViewHolder holder, final int position) {
        PyVenda pyVenda = pyVendas.get(position);
        holder.lb_venda_numero.setText(Utils.getDocumento(pyVenda.getId()));
        holder.lb_data_hora.setText(pyVenda.getDataEmissao());
        holder.lb_cliente_nome.setText(pyVenda.getCliente().getFantasia());
        holder.lb_vendedor_nome.setText(pyVenda.getUsuario().getApelido());
        holder.lb_valor_total.setText(format.format(pyVendas.get(position).getPyDetalhes().get(position).getTotal()));
        if (onClickRecyclerItem != null) {
            holder.card_venda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRecyclerItem.onDetalheItem(holder, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return pyVendas != null ? pyVendas.size() : 0;
    }

    public class VendasViewHolder extends RecyclerView.ViewHolder {

        private TextView lb_venda_numero, lb_data_hora, lb_cliente_nome, lb_vendedor_nome, lb_valor_total;
        private CardView card_venda;

        public VendasViewHolder(View itemView) {

            super(itemView);
            lb_venda_numero = (TextView) itemView.findViewById(R.id.lb_venda_numero);
            lb_data_hora = (TextView) itemView.findViewById(R.id.lb_data_hora);
            lb_cliente_nome = (TextView) itemView.findViewById(R.id.lb_cliente_nome);
            lb_vendedor_nome = (TextView) itemView.findViewById(R.id.lb_vendedor_nome);
            lb_valor_total = (TextView) itemView.findViewById(R.id.lb_valor_total);
            card_venda = (CardView) itemView.findViewById(R.id.card_venda);

        }

    }

}
