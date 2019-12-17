package com.codigosandroid.gensyspdv.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.activity.ConfigCloudActivity;
import com.codigosandroid.gensyspdv.activity.ConfigDesktopActivity;
import com.codigosandroid.utils.utils.PrefsUtil;

/**
 * Created by Tiago on 02/01/2018.
 */

public class ConfigFragment extends BaseFragment {

    private CardView cardDesktop, cardCloud;
    private LinearLayout desktopView, cloudView;

    boolean desktop;
    boolean cloud;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        desktop = PrefsUtil.getBoolean(getActivity(), getActivity().getString(R.string.pref_desktop_key));
        cloud = PrefsUtil.getBoolean(getActivity(), getActivity().getString(R.string.pref_cloud_key));
        inicializar(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (desktop) {
            desktopView.setBackgroundResource(R.drawable.rounded_border_cardview_pressed);
        } else if (cloud) {
            cloudView.setBackgroundResource(R.drawable.rounded_border_cardview_pressed);
        } else {
            desktopView.setBackgroundResource(R.drawable.rounded_border_cardview);
            cloudView.setBackgroundResource(R.drawable.rounded_border_cardview);
        }
    }

    private void inicializar(View view) {

        desktopView = (LinearLayout) view.findViewById(R.id.desktop);
        cloudView = (LinearLayout) view.findViewById(R.id.cloud);

        cardDesktop = (CardView) view.findViewById(R.id.card_desktop);
        cardDesktop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefsUtil.setBoolean(getActivity(), getActivity().getString(R.string.pref_desktop_key), true);
                if (desktop) {
                    PrefsUtil.setBoolean(getActivity(), getActivity().getString(R.string.pref_cloud_key), false);
                    desktopView.setBackgroundResource(R.drawable.rounded_border_cardview_pressed);
                    cloudView.setBackgroundResource(R.drawable.rounded_border_cardview);
                }
                startActivity(new Intent(getActivity(), ConfigDesktopActivity.class));
            }
        });
        cardCloud = (CardView) view.findViewById(R.id.card_cloud);
        cardCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefsUtil.setBoolean(getActivity(), getActivity().getString(R.string.pref_cloud_key), true);
                if (cloud) {
                    PrefsUtil.setBoolean(getActivity(), getActivity().getString(R.string.pref_desktop_key), false);
                    cloudView.setBackgroundResource(R.drawable.rounded_border_cardview_pressed);
                    desktopView.setBackgroundResource(R.drawable.rounded_border_cardview);
                }
                startActivity(new Intent(getActivity(), ConfigCloudActivity.class));
            }
        });
    }

}
