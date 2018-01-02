package com.codigosandroid.gensyspdv.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.activity.ConfigDesktopActivity;

/**
 * Created by Tiago on 02/01/2018.
 */

public class ConfigFragment extends BaseFragment {

    private CardView cardDesktop, cardCloud;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        inicializar(view);
        return view;
    }

    private void inicializar(View view) {
        cardDesktop = (CardView) view.findViewById(R.id.card_desktop);
        cardDesktop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ConfigDesktopActivity.class));
            }
        });
        cardCloud = (CardView) view.findViewById(R.id.card_cloud);
    }

}
