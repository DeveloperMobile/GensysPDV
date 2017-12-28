package com.codigosandroid.gensyspdv.empresa;

import android.content.Context;

import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;

import java.util.List;

/**
 * Created by Tiago on 28/12/2017.
 */

public class ServiceEmpresa {

    private static EmpresaDAO empresaDAO;
    private static EmpresaExcDAO empresaExcDAO;

    public static long insert(Context context, Empresa empresa) {
        empresaDAO = new EmpresaDAO(context);
        return empresaDAO.insert(empresa);
    }

    public static void deleteTab(Context context) {
        empresaDAO = new EmpresaDAO(context);
        empresaDAO.deleteTab();
    }

    public static List<Empresa> getAllExt(Context context) {
        empresaExcDAO = new EmpresaExcDAO();
        Configuracoes configuracoes = new Configuracoes();
        return empresaExcDAO.getAll(configuracoes.getHost(), configuracoes.getDb(),
                configuracoes.getUserDb(), configuracoes.getPassDb());
    }

}
