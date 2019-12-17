package com.codigosandroid.gensyspdv.venda.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.utils.DialogCallback;
import com.codigosandroid.gensyspdv.venda.PyDetalhe;
import com.codigosandroid.utils.utils.PrefsUtil;

import java.text.DecimalFormat;


/**
 * Created by Tiago on 25/09/2017.
 */

public class DialogDesconto extends DialogFragment {

    private static final String TAG = DialogDesconto.class.getSimpleName();
    private static final String DESCONTO = "desconto";
    private static final String VALOR = "valor";

    /* Componentes */
    private Button[] btNumericos = new Button[10];
    private TextView total, valor, tipo;
    private RadioGroup radioGroup;
    private RadioButton rbValor, rbDesc;
    private DecimalFormat format;
    private boolean flag = false;
    private PyDetalhe pyDetalhe;

    private DialogCallback<PyDetalhe> dialogCallback;

    /* Abre o dialog */
    public static void showItem(FragmentManager fm, PyDetalhe pyDetalhe, DialogCallback<PyDetalhe> dialogCallback) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_desconto");

        if (prev != null) {

            ft.remove(prev);

        }

        ft.addToBackStack(null);
        DialogDesconto dialog = new DialogDesconto();
        dialog.pyDetalhe = pyDetalhe;
        dialog.dialogCallback = dialogCallback;
        dialog.show(ft, "dialog_desconto");

    }

    /* Fecha o dialog */
    public static void closeDialog(FragmentManager fm) {

        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
        DialogDesconto cd = (DialogDesconto) fm.findFragmentByTag("dialog_desconto");

        if (cd != null) {

            cd.dismiss();
            ft.remove(cd);

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_desconto, container, false);
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
        /* Decimal Format Config */
        format = new DecimalFormat("#,###.##");
        format.setMinimumFractionDigits(2);
        format.setParseBigDecimal(true);

        rbValor = (RadioButton) view.findViewById(R.id.rbValor);
        rbValor.setChecked(PrefsUtil.getBoolean(getActivity(), VALOR) ? PrefsUtil.getBoolean(getActivity(), VALOR) : rbValor.isChecked());
        rbValor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoPagamento();
            }
        });
        rbDesc = (RadioButton) view.findViewById(R.id.rbDesc);
        rbDesc.setChecked(PrefsUtil.getBoolean(getActivity(), DESCONTO));
        rbDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoPagamento();
            }
        });
        valor = (TextView) view.findViewById(R.id.valor);
        tipo = (TextView) view.findViewById(R.id.lbTipo);

        if (rbValor.isChecked()) {
            tipo.setText(R.string.lb_tipo_desc);
            valor.setText(format.format(pyDetalhe.getVlDesconto()));
        } else {
            tipo.setText(R.string.lb_desc);
            valor.setText(format.format(pyDetalhe.getDesconto()));
        }

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

                    teclas(num);

                }
            });

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
                closeDialog(getActivity().getSupportFragmentManager());
            }
        });

        view.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dialogCallback != null) {
                    pyDetalhe.setVlDesconto(desconto(pyDetalhe.getEstoque().getPrecoVenda() * pyDetalhe.getQuantidade()));
                    pyDetalhe.setDesconto(percentual(pyDetalhe.getEstoque().getPrecoVenda() * pyDetalhe.getQuantidade()));
                    dialogCallback.getObject(pyDetalhe);
                }
                closeDialog(getActivity().getSupportFragmentManager());

            }
        });

        /* Tipo Desconto */
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbValor:
                        PrefsUtil.setBoolean(getActivity(), VALOR, rbValor.isChecked());
                        PrefsUtil.setBoolean(getActivity(), DESCONTO, rbDesc.isChecked());
                        break;
                    case R.id.rbDesc:
                        PrefsUtil.setBoolean(getActivity(), VALOR, rbValor.isChecked());
                        PrefsUtil.setBoolean(getActivity(), DESCONTO, rbDesc.isChecked());
                        break;
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
            String numVisor = valor.getText().toString().trim().replace(",", "").replace(".", "")
                    .replace("R$", "").replace("%", "");
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
            String numVisor = valor.getText().toString().trim().replace(",", "").replace(".", "")
                    .replace("R$", "").replace("%", "");
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

    private void tipoPagamento() {

        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rbValor:
                tipo.setText(R.string.lb_tipo_desc);
                valor.setText(format.format(pyDetalhe.getVlDesconto()));
                break;
            case R.id.rbDesc:
                tipo.setText(R.string.lb_desc);
                valor.setText(format.format(pyDetalhe.getDesconto()));
                break;
        }

    }

    private double desconto(double valorTotal) {
        double total = 0;
        if (rbDesc.isChecked()) {
            total = (valorTotal * Double.parseDouble(valor.getText().toString().replace(",", "."))) / 100;
        } else {
            total = (valorTotal * percentual(valorTotal)) / 100;
        }
        return total;
    }

    private double percentual(double valorTotal) {
        double total = 0;
        if (rbValor.isChecked()) {
            total = (Double.parseDouble(valor.getText().toString().replace(",", ".")) / valorTotal) * 100;
        } else {
            total = (desconto(valorTotal) / valorTotal) * 100;
        }
        return total;
    }

}
