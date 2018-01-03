package com.codigosandroid.gensyspdv.cliente;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago Vieira on 21/03/2017.
 */

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> implements Filterable {

    private Context context;
    private List<Cliente> clienteList;
    private List<Cliente> clienteListFilter;
    private CustomFilter mFilter;
    private OnClickRecyclerItem onClickRecyclerItem;

    public interface OnClickRecyclerItem {

        void onClickItem(RecyclerView.ViewHolder holder, int id);

    }

    public ClienteAdapter() {

    }

    public ClienteAdapter(Context context, List<Cliente> clienteList, OnClickRecyclerItem onClickRecyclerItem) {

        this.context = context;
        this.clienteList = clienteList;
        this.clienteListFilter = new ArrayList<>();
        this.clienteListFilter.addAll(clienteList);
        this.mFilter = new CustomFilter(ClienteAdapter.this);
        this.onClickRecyclerItem = onClickRecyclerItem;

    }

    @Override
    public Filter getFilter() {

        return mFilter;

    }

    @Override
    public ClienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_cliente, parent, false);
        ClienteViewHolder holder = new ClienteViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ClienteViewHolder holder, final int position) {

        Cliente cliente = clienteListFilter.get(position);
        holder.label_fantasia_valor.setText(cliente.getFantasia());
        holder.label_nome_valor.setText(cliente.getNome());
        holder.label_cpf_valor.setText(cliente.getCpf());
        holder.label_cgc_valor.setText(cliente.getCgc());

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

        return clienteListFilter != null ? clienteListFilter.size() : 0;

    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder {

        private TextView label_nome_valor, label_fantasia_valor, label_cpf_valor, label_cgc_valor, label_telefone_valor;

       public ClienteViewHolder(View itemView) {

           super(itemView);
           label_nome_valor = (TextView) itemView.findViewById(R.id.label_nome_valor);
           label_fantasia_valor = (TextView) itemView.findViewById(R.id.label_fantasia_valor);
           label_cpf_valor = (TextView) itemView.findViewById(R.id.label_cpf_valor);
           label_cgc_valor = (TextView) itemView.findViewById(R.id.label_cgc_valor);

       }

   }

   /* Filtro */
   public class CustomFilter extends Filter {

       private ClienteAdapter clienteAdapter;

       public CustomFilter(ClienteAdapter clienteAdapter) {

           super();
           this.clienteAdapter = clienteAdapter;

       }

       @Override
       protected FilterResults performFiltering(CharSequence constraint) {

           clienteListFilter.clear();
           final FilterResults results = new FilterResults();

           if (constraint.length() == 0) {

               clienteListFilter.addAll(clienteList);

           } else {

               final String filterPattern = constraint.toString().toLowerCase().trim();

               for (final Cliente cliente : clienteList) {

                   if (cliente.getFantasia().toLowerCase().startsWith(filterPattern)) {

                       clienteListFilter.add(cliente);

                   } else if (cliente.getCpf().startsWith(filterPattern)) {

                       clienteListFilter.add(cliente);

                   } else if (cliente.getCgc().startsWith(filterPattern)) {

                       clienteListFilter.add(cliente);

                   }

               }

           }

           results.values = clienteListFilter;
           results.count = clienteListFilter.size();
           return results;

       }

       @Override
       protected void publishResults(CharSequence constraint, FilterResults results) {

           this.clienteAdapter.notifyDataSetChanged();

       }

   }

    public List<Cliente> getClienteListFilter() {
        return clienteListFilter;
    }

    public void setClienteListFilter(List<Cliente> clienteListFilter) {
        this.clienteListFilter = clienteListFilter;
    }

}
