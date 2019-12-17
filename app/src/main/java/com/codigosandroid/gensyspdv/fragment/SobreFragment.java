package com.codigosandroid.gensyspdv.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.activity.WebActivity;
import com.codigosandroid.gensyspdv.sobre.Sobre;
import com.codigosandroid.gensyspdv.sobre.SobreAdapter;
import com.codigosandroid.gensyspdv.utils.OnClickRecyclerItem;

import java.util.List;

/**
 * Created by Tiago on 28/12/2017.
 */

public class SobreFragment extends BaseFragment implements OnClickRecyclerItem {

    private RecyclerView recyclerView;
    private List<Sobre> sobreList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sobre, container, false);
        inicializar(view);
        return view;
    }

    private void inicializar(View view) {

        sobreList = Sobre.getSobre(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.listaSobre);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new SobreAdapter(getActivity(), sobreList, this));

    }

    @Override
    public void onClickItem(RecyclerView.ViewHolder holder, int id) {
        Sobre sobre = sobreList.get(id);
        Intent intent;
        switch (id) {
            case 3:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("site", sobre.getValor());
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("privacidade", sobre.getValor());
                startActivity(intent);
                break;

        }
    }

}
