package com.codigosandroid.gensyspdv.cliente;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.utils.utils.LogUtil;
import com.codigosandroid.utils.utils.PrefsUtil;
import com.codigosandroid.utils.utils.SharedUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
     * Busca todos os registros na tabela cliente
     * @param context contexto da classe que utiliza o método */
    public static List<Cliente> getAll(Context context) {
        clienteDAO = new ClienteDAO(context);
        return clienteDAO.getAll();
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
        if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return clienteExtDAO.getAllByEmpresa(configuracoes.getHost(), configuracoes.getDb(),
                    configuracoes.getUserDb(), configuracoes.getPassDb(), configuracoes.getCompany());
        } else if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            try {
                Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
                return clienteExtDAO.getAllByEmpresa(cloud.getHostWeb(), cloud.getMysqlDb(),
                        cloud.getMysqlUser(), cloud.getMysqlPass(),
                        SharedUtil.getString(context, context.getString(R.string.pref_company_cloud_key)));
            } catch (FileNotFoundException e) {
                LogUtil.error("ERROR: ", e.getMessage(), e);
                return new ArrayList<Cliente>();
            }
        }
        return new ArrayList<Cliente>();
    }

}
