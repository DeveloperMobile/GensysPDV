package com.codigosandroid.gensyspdv.venda.dialog;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.utils.DialogCallback;
import com.codigosandroid.gensyspdv.venda.PyDetalhe;

/**
 * Created by Tiago on 05/10/2017.
 */

public class QuantidadeDialog extends DialogFragment {

    private PyDetalhe pyDetalhe;

    private TextView lbProduto;
    private EditText etQtde, etObservacao;

    private int quantidade = 1;

    private DialogCallback<PyDetalhe> dialogCallback;

    /* Abre o dialog com a lista de usuários */
    public static void showDialog(FragmentManager fm, PyDetalhe pyDetalhe, DialogCallback<PyDetalhe> dialogCallback) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = fm.findFragmentByTag("quantidade_dialog");

        if (frag != null) {

            ft.remove(frag);

        }

        ft.addToBackStack(null);
        QuantidadeDialog quantidadeDialog = new QuantidadeDialog();
        quantidadeDialog.dialogCallback = dialogCallback;
        quantidadeDialog.pyDetalhe = pyDetalhe;
        quantidadeDialog.show(ft, "quantidade_dialog");

    }

    /* Fecha o dialog */
    public static void closeDialog(FragmentManager fm) {

        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
        QuantidadeDialog ud = (QuantidadeDialog) fm.findFragmentByTag("quantidade_dialog");

        if (ud != null) {

            ud.dismiss();
            ft.remove(ud);

        }

    }

    private void inicializar(View view) {

        /* Descrição do produto */
        lbProduto = (TextView) view.findViewById(R.id.lbProduto);
        lbProduto.setText(pyDetalhe.getEstoque().getDescricao());
        /* Quantidade */
        quantidade = pyDetalhe != null ? pyDetalhe.getQuantidade() : 1;
        etQtde = (EditText) view.findViewById(R.id.etQtde);
        etQtde.setText(String.valueOf(quantidade));
        /* Observação */
        //etObservacao = (EditText) view.findViewById(R.id.etObservacao);
        /* Fecha dialog */
        view.findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                closeDialog(getActivity().getSupportFragmentManager());

            }

        });
        /* Confirma ações do dialog */
        view.findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (dialogCallback != null) {

                    if (etQtde.getText().toString().equals("0") || etQtde == null || etQtde.getText().toString().equals("")) {

                        closeDialog(getActivity().getSupportFragmentManager());
                        Toast.makeText(getActivity(), "Quantidade inválida", Toast.LENGTH_SHORT).show();

                    } else {

                        pyDetalhe.setQuantidade(Integer.parseInt(etQtde.getText().toString().trim()));
                        //pyDetalhe.setObservacao(etObservacao.getText().toString());
                        dialogCallback.getObject(pyDetalhe);
                        closeDialog(getActivity().getSupportFragmentManager());

                    }

                }

            }

        });
        /* Aumentar Quantidade */
        view.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                quantidade++;
                etQtde.setText(String.valueOf(quantidade));

            }

        });
        /* Diminuir Quantidade */
        view.findViewById(R.id.btnRemove).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (quantidade > 1) {
                    quantidade--;
                    etQtde.setText(String.valueOf(quantidade));
                }

            }

        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
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

        View view = inflater.inflate(R.layout.dialog_quantidade, container, false);
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

}
