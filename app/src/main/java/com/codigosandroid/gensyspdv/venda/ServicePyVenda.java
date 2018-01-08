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

    /** Busca todos os dados na tabela usuario */
    public static List<PyVenda> getAllInner(Context context) {
        pyVendaDAO = new PyVendaDAO(context);
        return pyVendaDAO.getAllInner();
    }

}
