package com.codigosandroid.gensyspdv.estoque;

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
 * Created by Tiago on 29/12/2017.
 */

public class ServiceEstoque {

    private static EstoqueDAO estoqueDAO;
    private static EstoqueExtDAO estoqueExtDAO;

    /**
     * Insere um registro na tabela estoque
     * @param context contexto da classe que utiliza o método
     * @param estoque objeto estoque a ser inserido no db */
    public static long insert(Context context, Estoque estoque) {
        estoqueDAO = new EstoqueDAO(context);
        return estoqueDAO.insert(estoque);
    }

    /**
     * Busca todos os registros na tabela estoque
     * @param context contexto da classe que utiliza o método */
    public static List<Estoque> getAll(Context context) {
        estoqueDAO = new EstoqueDAO(context);
        return estoqueDAO.getAll();
    }

    /**
     * Deleta todos os registros na tabela estoque
     * @param context contexto da classe que utiliza o método */
    public static void deleteTab(Context context) {
        estoqueDAO = new EstoqueDAO(context);
        estoqueDAO.deleteTab();
    }

    /**
     * Deleta a tabela estoque
     * @param context contexto da classe que utiliza o método */
    public static void dropTab(Context context) {
        estoqueDAO = new EstoqueDAO(context);
        estoqueDAO.dropTab();
    }

    /**
     * Cria a tabela estoque
     * @param context contexto da classe que utiliza o método */
    public static void createTab(Context context) {
        estoqueDAO = new EstoqueDAO(context);
        estoqueDAO.createTab();
    }

    /**
     * Busca registro na tabela estoque pelo recno
     * @param context contexto da classe que utiliza o método */
    public static Estoque getByRecno(Context context, int recno) {
        estoqueDAO = new EstoqueDAO(context);
        return estoqueDAO.getByRecno(recno);
    }

    /**
     * Busca registro na tabela estoque pelo codigo do produto
     * @param context contexto da classe que utiliza o método */
    public static Estoque getByCode(Context context, String codigo) {
        estoqueDAO = new EstoqueDAO(context);
        return estoqueDAO.getByCode(codigo);
    }

    /**
     * Busca todos os registros na tabela estoque externa
     * @param context contexto da classe que utiliza o método */
    public static List<Estoque> getAllExt(Context context) {
        estoqueExtDAO = new EstoqueExtDAO();
        if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return estoqueExtDAO.getByEmpresa(configuracoes.getHost(), configuracoes.getDb(), configuracoes.getUserDb(),
                    configuracoes.getPassDb(), configuracoes.getCompany());
        } else if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            try {
                Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
                return estoqueExtDAO.getByEmpresa(cloud.getHostWeb(), cloud.getMysqlDb(),
                        cloud.getMysqlUser(), cloud.getMysqlPass(),
                        SharedUtil.getString(context, context.getString(R.string.pref_company_cloud_key)));
            } catch (FileNotFoundException e) {
                LogUtil.error("ERROR: ", e.getMessage(), e);
                return new ArrayList<Estoque>();
            }
        }
        return new ArrayList<Estoque>();
    }

}
