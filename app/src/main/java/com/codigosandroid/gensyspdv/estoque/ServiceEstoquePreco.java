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

public class ServiceEstoquePreco {

    private static EstoquePrecoDAO estoquePrecoDAO;
    private static EstoquePrecoExcDAO estoquePrecoExcDAO;

    /**
     * Insere um registro na tabela estoque_preco
     * @param context contexto da classe que utiliza o método
     * @param estoquePreco objeto estoque_preco a ser inserido no db */
    public static long insert(Context context, EstoquePreco estoquePreco) {
        estoquePrecoDAO = new EstoquePrecoDAO(context);
        return estoquePrecoDAO.insert(estoquePreco);
    }

    /**
     * Deleta todos os registros na tabela estoque_preco
     * @param context contexto da classe que utiliza o método */
    public static void deleteTab(Context context) {
        estoquePrecoDAO = new EstoquePrecoDAO(context);
        estoquePrecoDAO.deleteTab();
    }

    /**
     * Deleta a tabela estoque_preco
     * @param context contexto da classe que utiliza o método */
    public static void dropTab(Context context) {
        estoquePrecoDAO = new EstoquePrecoDAO(context);
        estoquePrecoDAO.dropTab();
    }

    /**
     * Cria a tabela estoque_preco
     * @param context contexto da classe que utiliza o método */
    public static void createTab(Context context) {
        estoquePrecoDAO = new EstoquePrecoDAO(context);
        estoquePrecoDAO.createTab();
    }

    /**
     * Busca todos os registros na tabela estoque_preco externa
     * @param context contexto da classe que utiliza o método */
    public static List<EstoquePreco> getAllExt(Context context) {
        estoquePrecoExcDAO = new EstoquePrecoExcDAO();
        if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return estoquePrecoExcDAO.getByEmpresa(configuracoes.getHost(), configuracoes.getDb(),
                    configuracoes.getUserDb(), configuracoes.getPassDb(), configuracoes.getCompany());
        } else if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
            return estoquePrecoExcDAO.getByEmpresa(cloud.getHostWeb(), cloud.getMysqlDb(),
                    cloud.getMysqlUser(), cloud.getMysqlPass(),
                    SharedUtil.getString(context, context.getString(R.string.pref_company_cloud_key)));

        }
       return new ArrayList<EstoquePreco>();
    }

}
