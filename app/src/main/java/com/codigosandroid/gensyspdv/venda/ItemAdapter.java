package com.codigosandroid.gensyspdv.venda;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.estoque.Estoque;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago Vieira on 21/03/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ProdutoVendaViewHolder> {

    private Context context;
    private List<PyDetalhe> pyDetalhes;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

    public ItemAdapter() {

    }

    public ItemAdapter(Context context, List<PyDetalhe> pyDetalhes) {
        this.context = context;
        this.pyDetalhes = pyDetalhes;
    }

    @Override
    public ProdutoVendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item, parent, false);
        return new ProdutoVendaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ProdutoVendaViewHolder holder, final int position) {
        PyDetalhe pyDetalhe = pyDetalhes.get(position);
        holder.label_descricao_valor.setText(pyDetalhe.getEstoque().getDescricao());
        holder.label_preco_valor.setText(numberFormat.format(pyDetalhe.getEstoque().getPrecoVenda()));
    }

    @Override
    public int getItemCount() {

        return pyDetalhes != null ? pyDetalhes.size() : 0;

    }

    public class ProdutoVendaViewHolder extends RecyclerView.ViewHolder {

        private TextView label_descricao_valor, label_preco_valor;

       public ProdutoVendaViewHolder(View itemView) {

           super(itemView);
           label_descricao_valor = (TextView) itemView.findViewById(R.id.label_descricao_valor);
           label_preco_valor = (TextView) itemView.findViewById(R.id.label_preco_valor);

       }

   }

}
