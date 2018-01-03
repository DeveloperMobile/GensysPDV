package com.codigosandroid.gensyspdv.venda;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago Vieira on 21/03/2017.
 */

public class ItemVendaAdapter extends RecyclerView.Adapter<ItemVendaAdapter.ProdutoVendaViewHolder> {

    private Context context;
    private List<PyDetalhe> pyDetalhes;
    private OnClickRecyclerItem onClickRecyclerItem;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

    public interface OnClickRecyclerItem {

        void onDeleteItem(RecyclerView.ViewHolder holder, int id);
        void onSelectedItem(RecyclerView.ViewHolder holder, int id);

    }

    public ItemVendaAdapter(Context context, List<PyDetalhe> pyDetalhes, OnClickRecyclerItem onClickRecyclerItem) {

        this.context = context;
        this.pyDetalhes = pyDetalhes;
        this.onClickRecyclerItem = onClickRecyclerItem;

    }

    @Override
    public ProdutoVendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_venda, parent, false);
        return new ProdutoVendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProdutoVendaViewHolder holder, final int position) {

        PyDetalhe pyDetalhe = pyDetalhes.get(position);
        holder.label_descricao_valor.setText(pyDetalhe.getEstoque().getDescricao());
        holder.lb_qtde_valor.setText("x" + pyDetalhe.getQuantidade());
        holder.label_preco_valor.setText(numberFormat.format((pyDetalhe.getQuantidade() * pyDetalhe.getEstoque().getPrecoVenda())));

        if (onClickRecyclerItem != null) {

            holder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    onClickRecyclerItem.onDeleteItem(holder, position);

                }

            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    onClickRecyclerItem.onSelectedItem(holder, position);

                }

            });

        }

    }

    @Override
    public int getItemCount() {

        return pyDetalhes != null ? pyDetalhes.size() : 0;

    }

    public class ProdutoVendaViewHolder extends RecyclerView.ViewHolder {

        private TextView label_descricao_valor, label_preco_valor, lb_qtde_valor;
        private ImageButton btnDeleteItem;

       public ProdutoVendaViewHolder(View itemView) {

           super(itemView);
           label_descricao_valor = (TextView) itemView.findViewById(R.id.lb_descricao);
           label_preco_valor = (TextView) itemView.findViewById(R.id.lb_preco);
           lb_qtde_valor = (TextView) itemView.findViewById(R.id.lb_qtde);
           btnDeleteItem = (ImageButton) itemView.findViewById(R.id.btnDeleteItem);

       }

   }

}
