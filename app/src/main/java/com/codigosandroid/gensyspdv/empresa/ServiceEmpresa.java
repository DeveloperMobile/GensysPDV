package com.codigosandroid.gensyspdv.empresa;

import android.content.Context;

import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;

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
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return empresaDAO.getByName(configuracoes.getCompany().toUpperCase());
    }

    /**
     * Busca todos os registros na tabela empresa externa
     * @param context contexto da classe que utiliza o método */
    public static List<Empresa> getAllExt(Context context) {
        empresaExcDAO = new EmpresaExtDAO();
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return empresaExcDAO.getAll(configuracoes.getHost(), configuracoes.getDb(),
                configuracoes.getUserDb(), configuracoes.getPassDb());
    }

}
