package com.codigosandroid.gensyspdv.pagamento;

import android.content.Context;

import java.util.List;

/**
 * Created by Tiago on 08/01/2018.
 */

public class ServiceFormaPagamento {

    private static FormaPagamentoDAO formaPagamentoDAO;

    public static long insert(Context context, FormaPagamento formaPagamento) {
        formaPagamentoDAO = new FormaPagamentoDAO(context);
        return formaPagamentoDAO.insert(formaPagamento);
    }

    public static FormaPagamento getById(Context context, long id) {
        formaPagamentoDAO = new FormaPagamentoDAO(context);
        return formaPagamentoDAO.getById(id);
    }

    public static List<FormaPagamento> getAll(Context context) {
        formaPagamentoDAO = new FormaPagamentoDAO(context);
        return formaPagamentoDAO.getAll();
    }

}
