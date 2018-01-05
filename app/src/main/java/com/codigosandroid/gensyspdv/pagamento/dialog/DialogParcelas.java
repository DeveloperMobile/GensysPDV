package com.codigosandroid.gensyspdv.pagamento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.utils.DialogCallback;

/**
 * Created by Tiago on 05/12/2017.
 */

public class DialogParcelas extends DialogFragment {

    private TextView lbParcela, lbTipoFormaPag;
    private int parcela = 1;
    private String tipoPagamento;
    private DialogCallback<Integer> dialogCallBack;

    public static void showItem(FragmentManager fm, String tipoPagamento, DialogCallback<Integer> dialogCallBack) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_parcela");

        if (prev != null) {

            ft.remove(prev);

        }

        ft.addToBackStack(null);
        DialogParcelas dialog = new DialogParcelas();
        dialog.dialogCallBack = dialogCallBack;
        dialog.tipoPagamento = tipoPagamento;
        dialog.show(ft, "dialog_parcela");

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_parcela, container, false);
        inicializar(view);
        return view;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    private void inicializar(View view) {
        lbTipoFormaPag = (TextView) view.findViewById(R.id.lbTipoFormaPag);
        lbTipoFormaPag.setText(tipoPagamento);
        lbParcela = (TextView) view.findViewById(R.id.lbParcela);
        lbParcela.setText(String.valueOf(parcela));
        view.findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.btnAdicionar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parcela++;
                lbParcela.setText(String.valueOf(parcela));
            }
        });
        view.findViewById(R.id.btnRemover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parcela > 1) {
                    parcela--;
                    lbParcela.setText(String.valueOf(parcela));
                }
            }
        });
        view.findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCallBack != null) {
                    dialogCallBack.getObject(parcela);
                }
                dismiss();
            }
        });
    }

}
