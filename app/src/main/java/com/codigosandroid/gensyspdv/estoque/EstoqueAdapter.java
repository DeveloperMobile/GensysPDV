package com.codigosandroid.gensyspdv.estoque;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codigosandroid.gensyspdv.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago Vieira on 21/03/2017.
 */

public class EstoqueAdapter extends RecyclerView.Adapter<EstoqueAdapter.ProdutoVendaViewHolder> implements Filterable {

    private Context context;
    private List<Estoque> estoqueList;
    private List<Estoque> estoqueListFilter;
    private CustomFilter mFilter;
    private OnClickRecyclerItem onClickRecyclerItem;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

    public interface OnClickRecyclerItem {

        void onClickItem(RecyclerView.ViewHolder holder, int id);

    }

    public EstoqueAdapter() {

    }

    public EstoqueAdapter(Context context, List<Estoque> estoqueList, OnClickRecyclerItem onClickRecyclerItem) {

        this.context = context;
        this.estoqueList = estoqueList;
        this.estoqueListFilter = new ArrayList<>();
        this.estoqueListFilter.addAll(estoqueList);
        this.mFilter = new CustomFilter(EstoqueAdapter.this);
        this.onClickRecyclerItem = onClickRecyclerItem;

    }

    @Override
    public Filter getFilter() {

        return mFilter;

    }

    @Override
    public ProdutoVendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item, parent, false);
        return new ProdutoVendaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ProdutoVendaViewHolder holder, final int position) {

        Estoque estoque = estoqueListFilter.get(position);
        holder.label_descricao_valor.setText(estoque.getDescricao());
        holder.label_preco_valor.setText(numberFormat.format(estoque.getPrecoVenda()));

        if (onClickRecyclerItem != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    onClickRecyclerItem.onClickItem(holder, position);

                }

            });

        }

    }

    @Override
    public int getItemCount() {

        return estoqueListFilter != null ? estoqueListFilter.size() : 0;

    }

    public class ProdutoVendaViewHolder extends RecyclerView.ViewHolder {

        private TextView label_descricao_valor, label_preco_valor;

       public ProdutoVendaViewHolder(View itemView) {

           super(itemView);
           label_descricao_valor = (TextView) itemView.findViewById(R.id.label_descricao_valor);
           label_preco_valor = (TextView) itemView.findViewById(R.id.label_preco_valor);

       }

   }

   /* Filtro */
   public class CustomFilter extends Filter {

       private EstoqueAdapter produtoAdapter;

       public CustomFilter(EstoqueAdapter produtoAdapter) {

           super();
           this.produtoAdapter = produtoAdapter;

       }

       @Override
       protected FilterResults performFiltering(CharSequence constraint) {

           estoqueListFilter.clear();
           final FilterResults results = new FilterResults();

           if (constraint.length() == 0) {

               estoqueListFilter.addAll(estoqueList);

           } else {

               final String filterPattern = constraint.toString().toLowerCase().trim();

               for (final Estoque estoque : estoqueList) {

                   if (estoque.getDescricao().toLowerCase().startsWith(filterPattern)) {

                       estoqueListFilter.add(estoque);

                   }

               }

           }

           results.values = estoqueListFilter;
           results.count = estoqueListFilter.size();
           return results;

       }

       @Override
       protected void publishResults(CharSequence constraint, FilterResults results) {

           this.produtoAdapter.notifyDataSetChanged();

       }

   }

    public List<Estoque> getEstoqueListFilter() {
        return estoqueListFilter;
    }

    public void setEstoqueListFilter(List<Estoque> estoqueListFilter) {
        this.estoqueListFilter = estoqueListFilter;
    }

}
