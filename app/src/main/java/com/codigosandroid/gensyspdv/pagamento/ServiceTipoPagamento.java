package com.codigosandroid.gensyspdv.pagamento;

import android.content.Context;

import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;

import java.util.List;

/**
 * Created by Tiago on 29/12/2017.
 */

public class ServiceTipoPagamento {

    private static TipoPagamentoDAO tipoPagamentoDAO;
    private static TipoPagamentoExtDAO tipoPagamentoExtDAO;

    /**
     * Insere um registro na tabela usuário
     * @param context contexto da classe que utiliza o método
     * @param tipoPagamento objeto tipoPagamento a ser inserido no db */
    public static long insert(Context context, TipoPagamento tipoPagamento) {
        tipoPagamentoDAO = new TipoPagamentoDAO(context);
        return tipoPagamentoDAO.insert(tipoPagamento);
    }

    /**
     * Deleta todos os registros na tabela tipo_pagamento
     * @param context contexto da classe que utiliza o método */
    public static void deleteTab(Context context) {
        tipoPagamentoDAO = new TipoPagamentoDAO(context);
        tipoPagamentoDAO.deleteTab();
    }

    /**
     * Deleta a tabela tipo_pagamento
     * @param context contexto da classe que utiliza o método */
    public static void dropTab(Context context) {
        tipoPagamentoDAO = new TipoPagamentoDAO(context);
        tipoPagamentoDAO.dropTab();
    }

    /**
     * Cria a tabela tipo_pagamento
     * @param context contexto da classe que utiliza o método */
    public static void createTab(Context context) {
        tipoPagamentoDAO = new TipoPagamentoDAO(context);
        tipoPagamentoDAO.createTab();
    }

    /**
     * Busca todos os registros na tabela tipo_pagamento(formapag) externa
     * @param context contexto da classe que utiliza o método */
    public static List<TipoPagamento> getAllExt(Context context) {
        tipoPagamentoExtDAO = new TipoPagamentoExtDAO();
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return tipoPagamentoExtDAO.getAll(configuracoes.getHost(), configuracoes.getDb(),
                configuracoes.getUserDb(), configuracoes.getPassDb());
    }

}
