package com.codigosandroid.gensyspdv.pagamento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.pagamento.FormaPagamento;
import com.codigosandroid.gensyspdv.pagamento.TipoPagamento;
import com.codigosandroid.gensyspdv.utils.Constantes;
import com.codigosandroid.gensyspdv.utils.DialogCallback;
import com.codigosandroid.utils.utils.AlertUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by Tiago on 25/09/2017.
 */

public class DialogPagamento extends DialogFragment {

    private static final String TAG = DialogPagamento.class.getSimpleName();

    private Button[] btNumericos = new Button[10];
    private TextView total, valor, lbTipoPagamento;
    private ImageView imgPag;
    private DecimalFormat format;
    private double totalVenda, valorVisor;
    private String tipoPagamento;
    private boolean flag = false;
    private DialogCallback<FormaPagamento> dialogCallBack;

    public static void showItem(FragmentManager fm, double totalVenda, String tipoPagamento, DialogCallback<FormaPagamento> dialogCallBack) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_pagamento");

        if (prev != null) {

            ft.remove(prev);

        }

        ft.addToBackStack(null);
        DialogPagamento dialog = new DialogPagamento();
        dialog.dialogCallBack = dialogCallBack;
        dialog.totalVenda = totalVenda;
        dialog.tipoPagamento = tipoPagamento;
        dialog.show(ft, "dialog_pagamento");

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_pagamento, container, false);
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

        // Format Total
        format = new DecimalFormat("R$#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
        format.setMinimumFractionDigits(2);
        format.setParseBigDecimal(true);

        valorVisor = totalVenda;

        total = (TextView) view.findViewById(R.id.total);
        valor = (TextView) view.findViewById(R.id.valor);
        total.setText(format.format(totalVenda));
        valor.setText(format.format(valorVisor));

        btNumericos[0] = (Button) view.findViewById(R.id.btn0);
        btNumericos[1] = (Button) view.findViewById(R.id.btn1);
        btNumericos[2] = (Button) view.findViewById(R.id.btn2);
        btNumericos[3] = (Button) view.findViewById(R.id.btn3);
        btNumericos[4] = (Button) view.findViewById(R.id.btn4);
        btNumericos[5] = (Button) view.findViewById(R.id.btn5);
        btNumericos[6] = (Button) view.findViewById(R.id.btn6);
        btNumericos[7] = (Button) view.findViewById(R.id.btn7);
        btNumericos[8] = (Button) view.findViewById(R.id.btn8);
        btNumericos[9] = (Button) view.findViewById(R.id.btn9);

        for (int i = 0; i < btNumericos.length; i++) {

            final int num = i;
            btNumericos[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    teclas((num));

                }
            });

        }

        imgPag = (ImageView) view.findViewById(R.id.imgPag);
        lbTipoPagamento = (TextView) view.findViewById(R.id.lbTipoPagamento);

        if (tipoPagamento.equals(Constantes.DINHEIRO)) {
            imgPag.setImageResource(R.drawable.ic_money);
            lbTipoPagamento.setText(tipoPagamento);
        } else {
            imgPag.setImageResource(R.drawable.ic_card);
            lbTipoPagamento.setText(tipoPagamento);
        }

        view.findViewById(R.id.btnC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        view.findViewById(R.id.btnDel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remover();
            }
        });


        view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        view.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tipoPagamento.equals(Constantes.CREDITO)) {
                    DialogParcelas.showItem(getActivity().getSupportFragmentManager(), tipoPagamento, new DialogCallback<Integer>() {
                        @Override
                        public void getObject(Integer integer) {
                            FormaPagamento pagamento = new FormaPagamento();
                            pagamento.setValor(Double.parseDouble(valor.getText().toString().replace(",", ".").replace("R$", "")));
                            pagamento.setParcela(integer);
                            if (pagamento.getValor() > totalVenda) {
                                AlertUtil.alert(getContext(), "Aviso", "Pagamento em cartão não retorna troco!", 0, android.R.drawable.ic_dialog_alert);
                                dismiss();
                            } else {
                                if (dialogCallBack != null) {
                                    dialogCallBack.getObject(pagamento);
                                }
                            }
                            dismiss();
                        }
                    });
                } else {
                    FormaPagamento pagamento = new FormaPagamento();
                    pagamento.setValor(Double.parseDouble(valor.getText().toString().replace(",", ".").replace("R$", "")));
                    if (dialogCallBack != null) {
                        dialogCallBack.getObject(pagamento);
                    }
                    dismiss();
                }

            }
        });

    }

    private void teclas(int le_numero) {

        try {

            if (!flag) {
               clear();
               flag = true;
            }

            String numero = "";
            String numVisor = valor.getText().toString().trim().replace(",", "").replace(".", "").replace("R$", "");
            numero = numVisor + String.valueOf(le_numero);
            valor.setText(format.format((Double.valueOf(numero) / 100)));

        } catch (NumberFormatException e) {

            Log.d(TAG, e.getMessage(), e);

        }

    }

    private void remover() {

        try {

            String numero = "";
            int i = 0;
            String numVisor = valor.getText().toString().trim().replace(",", "").replace(".", "").replace("R$", "");
            i = i + 1;
            numero = numero + numVisor.substring(0, numVisor.length() - i);
            Log.d(TAG, numero);
            valor.setText(format.format((Double.valueOf(numero) / 100)));

        } catch (NumberFormatException e) {

            Log.d(TAG, e.getMessage(), e);

        }

    }

    private void clear() {
        valor.setText(format.format(0.0));
    }

}
