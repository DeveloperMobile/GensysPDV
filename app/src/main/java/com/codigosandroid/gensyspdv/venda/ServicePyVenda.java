package com.codigosandroid.gensyspdv.venda;

import android.content.Context;

import java.util.List;

/**
 * Created by Tiago on 05/01/2018.
 */

public class ServicePyVenda {

    private static PyVendaDAO pyVendaDAO;

    /**
     * Insere um registro nas tabelas pyvenda, pydetalhe e pyrecpag
     * @param context contexto da classe que utiliza o método
     * @param pyVenda objeto usuario a ser inserido no db */
    public static boolean insert(Context context, PyVenda pyVenda) {
        pyVendaDAO = new PyVendaDAO(context);
        return pyVendaDAO.insert(pyVenda);
    }

    /** Busca todos os dados na tabela pyvenda */
    public static List<PyVenda> getAllInnerPyVenda(Context context) {
        pyVendaDAO = new PyVendaDAO(context);
        return pyVendaDAO.getAllInnerPyVenda();
    }

    /** Busca todos os dados na tabela pydetalhe */
    public static List<PyDetalhe> getAllInnerDetalhe(Context context, long id) {
        pyVendaDAO = new PyVendaDAO(context);
        return pyVendaDAO.getAllInnerPyDetalhe(id);
    }

    /** Busca todos os dados na tabela pyrecpag */
    public static List<PyRecPag> getAllInnerRecPag(Context context, long id) {
        pyVendaDAO = new PyVendaDAO(context);
        return pyVendaDAO.getAllInnerPyRecPag(id);
    }

}
