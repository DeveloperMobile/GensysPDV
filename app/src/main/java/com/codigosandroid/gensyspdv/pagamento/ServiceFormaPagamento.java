package com.codigosandroid.gensyspdv.pagamento;

import android.content.Context;

/**
 * Created by Tiago on 08/01/2018.
 */

public class ServiceFormaPagamento {

    private static FormaPagamentoDAO formaPagamentoDAO;

    public static long insert(Context context, FormaPagamento formaPagamento) {
        formaPagamentoDAO = new FormaPagamentoDAO(context);
        return formaPagamentoDAO.insert(formaPagamento);
    }

}
