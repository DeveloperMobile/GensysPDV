package com.codigosandroid.gensyspdv.fragment.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.utils.utils.AndroidUtil;

/**
 * Created by Tiago on 06/09/2017.
 */

public class DialogSyncFragment extends DialogFragment {

    private static final String TAG = DialogSyncFragment.class.getSimpleName();

    public static TextView lbStatus;
    private ProgressBar progress;

    public static void showDialog(FragmentManager fm) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("sincronizar");

        if (prev != null) {

            ft.remove(prev);

        }

        ft.addToBackStack(null);
        DialogSyncFragment dsf = new DialogSyncFragment();
        dsf.show(ft, "sincronizar");

    }

    public static void closeDialog(FragmentManager fm) {

        FragmentTransaction ft = fm.beginTransaction();
        DialogSyncFragment dsf = (DialogSyncFragment) fm.findFragmentByTag("sincronizar");

        if (dsf != null) {

            dsf.dismiss();
            ft.remove(dsf);

        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (!AndroidUtil.isAndroid5Lollipop()) {

            setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Holo_Dialog);

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_progress_sync, container, false);
        inicializar(view);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void inicializar(View view) {

        lbStatus = (TextView) view.findViewById(R.id.lbSync);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        progress.setIndeterminate(true);

    }

}
