package com.codigosandroid.gensyspdv.usuario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.utils.OnClickRecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago Vieira on 21/03/2017.
 */

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> implements Filterable {

    private Context context;
    private List<Usuario> usuarioList;
    private List<Usuario> usuarioListFilter;
    private CustomFilter mFilter;
    private OnClickRecyclerItem onClickRecyclerItem;

    public UsuarioAdapter() {

    }

    public UsuarioAdapter(Context context, List<Usuario> usuarioList, OnClickRecyclerItem onClickRecyclerItem) {

        this.context = context;
        this.usuarioList = usuarioList;
        this.usuarioListFilter = new ArrayList<>();
        this.usuarioListFilter.addAll(usuarioList);
        this.mFilter = new CustomFilter(UsuarioAdapter.this);
        this.onClickRecyclerItem = onClickRecyclerItem;

    }

    @Override
    public Filter getFilter() {

        return mFilter;

    }

    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_usuario, parent, false);
        UsuarioViewHolder holder = new UsuarioViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final UsuarioViewHolder holder, final int position) {

        Usuario usuario = usuarioListFilter.get(position);
        holder.label_nome_valor.setText(usuario.getNome());

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

        return usuarioListFilter != null ? usuarioListFilter.size() : 0;

    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        private TextView label_nome_valor;

       public UsuarioViewHolder(View itemView) {

           super(itemView);
           label_nome_valor = (TextView) itemView.findViewById(R.id.label_nome_valor);

       }

   }

   /* Filtro */
   public class CustomFilter extends Filter {

       private UsuarioAdapter usuarioAdapter;

       public CustomFilter(UsuarioAdapter usuarioAdapter) {

           super();
           this.usuarioAdapter = usuarioAdapter;

       }

       @Override
       protected FilterResults performFiltering(CharSequence constraint) {

           usuarioListFilter.clear();
           final FilterResults results = new FilterResults();

           if (constraint.length() == 0) {

               usuarioListFilter.addAll(usuarioList);

           } else {

               final String filterPattern = constraint.toString().toLowerCase().trim();

               for (final Usuario usuario : usuarioList) {

                   if (usuario.getApelido().toLowerCase().startsWith(filterPattern)) {

                       usuarioListFilter.add(usuario);

                   }

               }

           }

           results.values = usuarioListFilter;
           results.count = usuarioListFilter.size();
           return results;

       }

       @Override
       protected void publishResults(CharSequence constraint, FilterResults results) {

           this.usuarioAdapter.notifyDataSetChanged();

       }

   }

    public List<Usuario> getUsuarioListFilter() {
        return usuarioListFilter;
    }

    public void setUsuarioListFilter(List<Usuario> usuarioListFilter) {
        this.usuarioListFilter = usuarioListFilter;
    }

}
