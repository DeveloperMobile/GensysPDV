package com.codigosandroid.gensyspdv.pagamento;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.utils.utils.LogUtil;
import com.codigosandroid.utils.utils.PrefsUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return tipoPagamentoExtDAO.getAll(configuracoes.getHost(), configuracoes.getDb(),
                    configuracoes.getUserDb(), configuracoes.getPassDb());
        } else if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            try {
                Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
                return tipoPagamentoExtDAO.getAll(cloud.getHostWeb(), cloud.getMysqlDb(),
                        cloud.getMysqlUser(), cloud.getMysqlPass());
            } catch (FileNotFoundException e) {
                LogUtil.error("ERROR: ", e.getMessage(), e);
                return new ArrayList<TipoPagamento>();
            }
        }
        return new ArrayList<TipoPagamento>();
    }

}
