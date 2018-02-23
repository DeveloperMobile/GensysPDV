package com.codigosandroid.gensyspdv.pagamento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.pagamento.FormaPagamento;
import com.codigosandroid.gensyspdv.pagamento.FormaPagamentoAdapter;
import com.codigosandroid.gensyspdv.pagamento.ServiceFormaPagamento;
import com.codigosandroid.gensyspdv.pagamento.ServiceTipoPagamento;
import com.codigosandroid.gensyspdv.pagamento.TipoPagamento;
import com.codigosandroid.gensyspdv.pagamento.TipoPagamentoAdapter;
import com.codigosandroid.gensyspdv.utils.Constantes;
import com.codigosandroid.gensyspdv.utils.DialogCallback;
import com.codigosandroid.gensyspdv.utils.OnClickRecyclerItem;
import com.codigosandroid.gensyspdv.utils.Utils;
import com.codigosandroid.utils.utils.AndroidUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Tiago on 30/11/2017.
 */

public class DialogFormaPagamento extends DialogFragment {

    private static final String TAG = DialogFormaPagamento.class.getSimpleName();

    List<FormaPagamento> formaPagamentos = new ArrayList<>();
    private RecyclerView recyclerPag, recyclerFormaPags;
    private Button btnFinalizarVenda;
    private TextView lbMsgPags, lbTotalVenda;
    private DialogCallback<List<FormaPagamento>> dialogCallBack;

    private DecimalFormat formatTotal, formatTroco;
    private double total, totalAux, totalValor, subTotal, troco;
    private String auto;
    private Integer parcelas;
    private boolean flag = false;

    FormaPagamento p = null;

    /**
     * Mostra o dialog
     *
     * @param fm Objeto FragmentManager
     * @param dialogCallBack interface de retorno.
     */
    public static void showDialog(FragmentManager fm, double total, final DialogCallback<List<FormaPagamento>> dialogCallBack) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("formapagamento");

        if (prev != null) {

            ft.remove(prev);

        }

        ft.addToBackStack(null);
        DialogFormaPagamento dfp = new DialogFormaPagamento();
        dfp.dialogCallBack = dialogCallBack;
        dfp.total = total;
        dfp.show(ft, "formapagamento");

    }

    /**
     * Fecha o dialog
     * @param fm Objeto FragmentManager*/
    public static void closeDialog(FragmentManager fm) {

        FragmentTransaction ft = fm.beginTransaction();
        DialogFormaPagamento dfp = (DialogFormaPagamento) fm.findFragmentByTag("formapagamento");

        if (dfp != null) {

            dfp.dismiss();
            ft.remove(dfp);

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        setCancelable(false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_forma_pagamento, container, false);
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

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "REQUEST_CODE: " + requestCode);
        switch (requestCode) {
            case PaymentUtils.COLLECT_PAYMENT_REQUEST:
                PaymentUtils.onResultPaymentCard(getActivity(), requestCode, resultCode, data, new PaymentCallback() {

                    @Override
                    public void getReceipt(Receipt receipt) {

                        try {

                            auto = receipt.getAUTO();
                            parcelas = receipt.getInstallments();
                            Log.d(TAG, "Autorização: " + auto);
                            Log.d(TAG, "No.Parcelas: " + parcelas);

                            p.setParcela(parcelas);
                            p.setNumAutorizacao(auto);
                            addPagamento(p, null);

                        } catch (NullPointerException e) {

                            Log.d(TAG, e.getMessage());

                        }

                    }

                    @Override
                    public void getFail() {

                        flag = false;

                    }

                });
                break;

        }
    }*/

    private void inicializar(View view) {

        // Format Total
        formatTotal = new DecimalFormat("Restante R$#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatTotal.setMinimumFractionDigits(2);
        formatTotal.setParseBigDecimal(true);
        // Format Troco
        formatTroco = new DecimalFormat("Troco R$#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatTroco.setMinimumFractionDigits(2);
        formatTroco.setParseBigDecimal(true);
        // Componentes
        lbTotalVenda = (TextView) view.findViewById(R.id.lbTotalVenda);
        lbTotalVenda.setText(formatTotal.format(total));
        lbMsgPags = (TextView) view.findViewById(R.id.lbMsgPags);
        recyclerPag = (RecyclerView) view.findViewById(R.id.recyclerPags);
        recyclerFormaPagamentoUp();
        recyclerFormaPags = (RecyclerView) view.findViewById(R.id.recyclerFormaPags);
        recyclerTipoPagamentoUp();
        view.findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnFinalizarVenda = (Button) view.findViewById(R.id.btnFinalizarVenda);
        btnFinalizarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCallBack != null) {
                    dialogCallBack.getObject(ServiceFormaPagamento.getAll(getActivity()));
                }
            }
        });

    }

    private void recyclerFormaPagamentoUp() {

        recyclerPag.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerPag.setItemAnimator(new DefaultItemAnimator());
        if (!formaPagamentos.isEmpty()) {
            lbMsgPags.setVisibility(View.GONE);
            recyclerPag.setVisibility(View.VISIBLE);
            recyclerPag.setAdapter(new FormaPagamentoAdapter(getActivity(), formaPagamentos, new OnClickRecyclerItem() {

                @Override
                public void onClickItem(RecyclerView.ViewHolder formaPagsViewHolder, int id) {
                    formaPagamentos.remove(id);
                    calculoPagamentoExcluirItem(formaPagamentos);
                }


            }));
        } else {
            recyclerPag.setVisibility(View.GONE);
            lbMsgPags.setVisibility(View.VISIBLE);
        }

    }

    private void recyclerTipoPagamentoUp() {
        final List<TipoPagamento> tipoPagamentos = ServiceTipoPagamento.getAll(getActivity());
        recyclerFormaPags.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerFormaPags.setItemAnimator(new DefaultItemAnimator());
        recyclerFormaPags.setAdapter(new TipoPagamentoAdapter(getActivity(), tipoPagamentos, new OnClickRecyclerItem() {

            @Override
            public void onClickItem(RecyclerView.ViewHolder formaPagsViewHolder, int id) {
                final TipoPagamento tipoPagamento = tipoPagamentos.get(id);

                totalValor = totalValor > 0 ? totalValor : total;
                DialogPagamento.showItem(getActivity().getSupportFragmentManager(), totalValor, tipoPagamento.getTipoPagamento(), new DialogCallback<FormaPagamento>() {
                    @Override
                    public void getObject(FormaPagamento formaPagamento) {

                        String valor = formatTotal.format(formaPagamento.getValor())
                                .replace("Restante", "")
                                .replace("R$", "")
                                .replace(",", "")
                                .replace(".", "").trim();
                        formaPagamento.setValorLong(valor);

                        boolean serviceRunning = Utils.isServiceRunning(getActivity(), "co.poynt.services.PoyntProductService");

                        if (serviceRunning) {

                            switch (tipoPagamento.getTipoPagamento()) {
                                case Constantes.CREDITO:
                                    p = new FormaPagamento();
                                    p.setTipoPagamento(tipoPagamento);
                                    p.setValor(formaPagamento.getValor());
                                    if (formaPagamento.getValor() > total) {
                                        double dif = formaPagamento.getValor() - total;
                                        p.setTotal(formaPagamento.getValor() - dif);
                                    } else {
                                        p.setTotal(formaPagamento.getValor());
                                    }
                                    break;
                                case Constantes.DEBITO:
                                    p = new FormaPagamento();
                                    p.setTipoPagamento(tipoPagamento);
                                    p.setValor(formaPagamento.getValor());
                                    if (tipoPagamento.getTipoPagamento().equals(Constantes.DINHEIRO) && formaPagamento.getValor() > total) {
                                        double dif = formaPagamento.getValor() - total;
                                        p.setTotal(formaPagamento.getValor() - dif);
                                    } else {
                                        p.setTotal(formaPagamento.getValor());
                                    }
                                    break;
                                case Constantes.VOUCHER:
                                    p = new FormaPagamento();
                                    p.setTipoPagamento(tipoPagamento);
                                    p.setValor(formaPagamento.getValor());
                                    if (tipoPagamento.getTipoPagamento().equals(Constantes.DINHEIRO) && formaPagamento.getValor() > total) {
                                        double dif = formaPagamento.getValor() - total;
                                        p.setTotal(formaPagamento.getValor() - dif);
                                    } else {
                                        p.setTotal(formaPagamento.getValor());
                                    }
                                    break;
                                case Constantes.DINHEIRO:
                                    p = new FormaPagamento();
                                    p.setTipoPagamento(tipoPagamento);
                                    p.setValor(formaPagamento.getValor());
                                    if (tipoPagamento.getTipoPagamento().equals(Constantes.DINHEIRO) && formaPagamento.getValor() > total) {
                                        double dif = formaPagamento.getValor() - total;
                                        p.setTotal(formaPagamento.getValor() - dif);
                                    } else {
                                        p.setTotal(formaPagamento.getValor());
                                    }
                                    p.setValor(formaPagamento.getValor());
                                    addPagamento(p, formaPagamento);
                                    break;
                            }

                            switch (tipoPagamento.getTipoPagamento()) {
                                case Constantes.CREDITO:
                                    //PaymentUtils.openActivityPaymentCreditInInstallMent(DialogFormaPagamento.this, formaPagamento.getParcela(), Long.parseLong(formaPag.getValorLong().replace(".", "")));
                                    break;
                                case Constantes.DEBITO:
                                    //PaymentUtils.openActivityPaymentDebit(DialogFormaPagamento.this, Long.parseLong(formaPag.getValorLong().replace(".", "")));
                                    break;
                                case Constantes.VOUCHER:
                                    //PaymentUtils.openActivityPaymentVoucher(DialogFormaPagamento.this, Long.parseLong(formaPag.getValorLong().replace(".", "")));
                                    break;
                            }

                        } else {
                            FormaPagamento p = new FormaPagamento();
                            p.setTipoPagamento(tipoPagamento);
                            if (tipoPagamento.getTipoPagamento().equals(Constantes.DINHEIRO) && formaPagamento.getValor() > total) {
                                double dif = formaPagamento.getValor() - total;
                                p.setTotal(formaPagamento.getValor() - dif);
                            } else {
                                p.setTotal(formaPagamento.getValor());
                            }
                            p.setValor(formaPagamento.getValor());
                            p.setParcela(formaPagamento.getParcela());
                            p.setNumAutorizacao(auto);
                            addPagamento(p, formaPagamento);
                        }

                    }
                });
            }

        }));
    }

    private void addPagamento(FormaPagamento p, FormaPagamento formaPagamento) {

        try {

            if (!formaPagamentos.isEmpty()) {
                if (formaPagamentos.contains(p)) {
                    for (int i = 0; i < formaPagamentos.size(); i++) {
                        if (formaPagamentos.get(i).getTipoPagamento().equals(Constantes.DINHEIRO)) {
                            formaPagamentos.get(i).setValor(formaPagamentos.get(i).getValor() + formaPagamento.getValor());
                            ServiceFormaPagamento.insert(getActivity(), formaPagamentos.get(i));
                        }
                    }
                } else {
                    formaPagamentos.add(p);
                    for (FormaPagamento formaPagamento1 : formaPagamentos) {
                        ServiceFormaPagamento.insert(getActivity(), formaPagamento1);
                    }
                }
            } else {
                formaPagamentos.add(p);
                for (FormaPagamento formaPagamento1 : formaPagamentos) {
                    ServiceFormaPagamento.insert(getActivity(), formaPagamento1);
                }
            }
            calculoPagamento(formaPagamentos);
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "ERRO: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void calculoPagamento(List<FormaPagamento> pagamentos) {
        for (int i = 0; i < pagamentos.size(); i++) {

            Log.d(TAG, "TOTAL: " + pagamentos.get(i).getValor());
            subTotal += pagamentos.get(i).getValor();
            if (subTotal < total) {
                totalAux = total - subTotal;
                totalValor = totalAux;
                lbTotalVenda.setText(formatTotal.format(totalAux));
                //break;
            } else if (subTotal > total) {
                troco = subTotal - total;
                lbTotalVenda.setText(formatTroco.format(troco));
                btnFinalizarVenda.setVisibility(View.VISIBLE);
                recyclerFormaPags.setVisibility(View.GONE);
                //break;
            } else if (subTotal == total) {
                lbTotalVenda.setText("Pagamento Concluído");
                btnFinalizarVenda.setVisibility(View.VISIBLE);
                recyclerFormaPags.setVisibility(View.GONE);
                //break;
            }

        }

        totalAux = 0;
        troco = 0;
        subTotal = 0;
        recyclerFormaPagamentoUp();
    }

    private void calculoPagamentoExcluirItem(List<FormaPagamento> pagamentos) {
        if (pagamentos.isEmpty()) {
            subTotal = 0;
            troco = 0;
            totalAux = 0;
            totalValor = 0;
            lbTotalVenda.setText(formatTotal.format(total));
            btnFinalizarVenda.setVisibility(View.GONE);
            recyclerFormaPags.setVisibility(View.VISIBLE);
        }
        for (FormaPagamento pagamento : pagamentos) {
            subTotal += pagamento.getValor();
            if (subTotal < total) {
                totalAux = total - subTotal;
                totalValor = totalAux;
                lbTotalVenda.setText(formatTotal.format(totalAux));
                btnFinalizarVenda.setVisibility(View.GONE);
                recyclerFormaPags.setVisibility(View.VISIBLE);
                //break;
            } else if (subTotal > total) {
                troco = subTotal - total;
                lbTotalVenda.setText(formatTroco.format(troco));
                //break;
            } else if (subTotal == total) {
                lbTotalVenda.setText("Pagamento Concluído");
            }
        }
        troco = 0;
        totalAux = 0;
        subTotal = 0;
        recyclerFormaPagamentoUp();
    }

}
