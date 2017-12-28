package com.codigosandroid.gensyspdv.sobre;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;

import java.util.List;

/**
 * Created by Tiago Vieira on 16/01/2017.
 */

public class SobreAdapter extends RecyclerView.Adapter<SobreAdapter.SobreViewHolder> {

    private Context context;
    private List<Sobre> sobreList;
    private OnClickHyperlinkItem onClickRecyclerItem;

    public interface OnClickHyperlinkItem {

        void onClickItem(SobreViewHolder holder, int id);

    }

    public SobreAdapter(Context context, List<Sobre> sobreList, OnClickHyperlinkItem onClickRecyclerItem) {

        this.context = context;
        this.sobreList = sobreList;
        this.onClickRecyclerItem = onClickRecyclerItem;

    }

    @Override
    public SobreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_sobre, parent, false);
        SobreViewHolder holder = new SobreViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final SobreViewHolder holder, final int position) {

        Sobre sobre = sobreList.get(position);
        holder.descricao.setText(sobre.getDescricao());
        holder.valor.setText(sobre.getValor());

        if (onClickRecyclerItem != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    onClickRecyclerItem.onClickItem(holder, position);

                }

            });

        }

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public int getItemCount() {

        return sobreList != null ? sobreList.size() : 0;

    }

    public class SobreViewHolder extends RecyclerView.ViewHolder {

        private TextView descricao;
        private TextView valor;

        public SobreViewHolder(View itemView) {

            super(itemView);
            descricao = (TextView) itemView.findViewById(R.id.descricao);
            valor = (TextView) itemView.findViewById(R.id.valor);

        }

    }

}
