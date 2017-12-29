package com.codigosandroid.gensyspdv.cliente;

import android.content.Context;

import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;

import java.util.List;

/**
 * Created by Tiago on 28/12/2017.
 */

public class ServiceCliente {

    private static ClienteDAO clienteDAO;
    private static ClienteExtDAO clienteExtDAO;

    /**
     * Insere um registro na tabela usuário
     * @param context contexto da classe que utiliza o método
     * @param cliente objeto cliente a ser inserido no db */
    public static long insert(Context context, Cliente cliente) {
        clienteDAO = new ClienteDAO(context);
        return clienteDAO.insert(cliente);
    }

    /**
     * Deleta todos os registros na tabela cliente
     * @param context contexto da classe que utiliza o método */
    public static void deleteTab(Context context) {
        clienteDAO = new ClienteDAO(context);
        clienteDAO.deleteTab();
    }

    /**
     * Deleta a tabela cliente
     * @param context contexto da classe que utiliza o método */
    public static void dropTab(Context context) {
        clienteDAO = new ClienteDAO(context);
        clienteDAO.dropTab();
    }

    /**
     * Cria a tabela cliente
     * @param context contexto da classe que utiliza o método */
    public static void createTab(Context context) {
        clienteDAO = new ClienteDAO(context);
        clienteDAO.createTab();
    }

    /**
     * Busca todos os registros na tabela cliente externa
     * @param context contexto da classe que utiliza o método */
    public static List<Cliente> getAllExt(Context context) {
        clienteExtDAO = new ClienteExtDAO();
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return clienteExtDAO.getAllByEmpresa(configuracoes.getHost(), configuracoes.getDb(),
                configuracoes.getUserDb(), configuracoes.getPassDb(), configuracoes.getCompany());
    }

}
