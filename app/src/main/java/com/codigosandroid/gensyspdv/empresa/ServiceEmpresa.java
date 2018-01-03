package com.codigosandroid.gensyspdv.empresa;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cliente.Cliente;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.gensyspdv.utils.SharedUtils;
import com.codigosandroid.utils.utils.LogUtil;
import com.codigosandroid.utils.utils.PrefsUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 28/12/2017.
 */

public class ServiceEmpresa {

    private static EmpresaDAO empresaDAO;
    private static EmpresaExtDAO empresaExcDAO;

    /**
     * Insere um registro na tabela empresa
     * @param context contexto da classe que utiliza o método
     * @param empresa objeto empresa a ser inserido no db */
    public static long insert(Context context, Empresa empresa) {
        empresaDAO = new EmpresaDAO(context);
        return empresaDAO.insert(empresa);
    }

    /**
     * Deleta todos os registros na tabela empresa
     * @param context contexto da classe que utiliza o método */
    public static void deleteTab(Context context) {
        empresaDAO = new EmpresaDAO(context);
        empresaDAO.deleteTab();
    }

    /**
     * Deleta a tabela empresa
     * @param context contexto da classe que utiliza o método */
    public static void dropTab(Context context) {
        empresaDAO = new EmpresaDAO(context);
        empresaDAO.dropTab();
    }

    /**
     * Cria a tabela empresa
     * @param context contexto da classe que utiliza o método */
    public static void createTab(Context context) {
        empresaDAO = new EmpresaDAO(context);
        empresaDAO.createTab();
    }

    /**
     * Busca registro na tabela empresa pela descrição
     * @param context contexto da classe que utiliza o método */
    public static Empresa getByName(Context context) {
        empresaDAO = new EmpresaDAO(context);
        if (SharedUtils.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return empresaDAO.getByName(configuracoes.getCompany().toUpperCase());
        } else if (SharedUtils.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            return empresaDAO.getByName(SharedUtils.getString(context, context.getString(R.string.pref_company_cloud_key)));
        }
        return new Empresa();
    }

    /**
     * Busca todos os registros na tabela empresa externa
     * @param context contexto da classe que utiliza o método */
    public static List<Empresa> getAllExt(Context context) {
        empresaExcDAO = new EmpresaExtDAO();
        if (SharedUtils.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return empresaExcDAO.getAll(configuracoes.getHost(), configuracoes.getDb(),
                    configuracoes.getUserDb(), configuracoes.getPassDb());
        } else if (SharedUtils.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            try {
                Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
                return empresaExcDAO.getAll(cloud.getHostWeb(), cloud.getMysqlDb(),
                        cloud.getMysqlUser(), cloud.getMysqlPass());
            } catch (FileNotFoundException e) {
                LogUtil.error("ERROR: ", e.getMessage(), e);
                return new ArrayList<Empresa>();
            }
        }
       return new ArrayList<Empresa>();
    }

}
