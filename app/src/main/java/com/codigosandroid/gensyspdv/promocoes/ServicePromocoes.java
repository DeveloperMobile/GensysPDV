package com.codigosandroid.gensyspdv.promocoes;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cliente.Cliente;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.gensyspdv.promocoes.Promocoes;
import com.codigosandroid.gensyspdv.promocoes.PromocoesDAO;
import com.codigosandroid.gensyspdv.promocoes.PromocoesExtDAO;
import com.codigosandroid.gensyspdv.utils.SharedUtils;
import com.codigosandroid.utils.utils.LogUtil;
import com.codigosandroid.utils.utils.PrefsUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 02/01/2018.
 */

public class ServicePromocoes {

    private static PromocoesDAO promocoesDAO;
    private static PromocoesExtDAO promocoesExtDAO;

    /**
     * Insere um registro na tabela promocoes
     * @param context contexto da classe que utiliza o método
     * @param promocoes objeto promocoes a ser inserido no db */
    public static long insert(Context context, Promocoes promocoes) {
        promocoesDAO = new PromocoesDAO(context);
        return promocoesDAO.insert(promocoes);
    }

    /**
     * Deleta todos os registros na tabela promocoes
     * @param context contexto da classe que utiliza o método */
    public static void deleteTab(Context context) {
        promocoesDAO = new PromocoesDAO(context);
        promocoesDAO.deleteTab();
    }

    /**
     * Deleta a tabela promocoes
     * @param context contexto da classe que utiliza o método */
    public static void dropTab(Context context) {
        promocoesDAO = new PromocoesDAO(context);
        promocoesDAO.dropTab();
    }

    /**
     * Cria a tabela promocoes
     * @param context contexto da classe que utiliza o método */
    public static void createTab(Context context) {
        promocoesDAO = new PromocoesDAO(context);
        promocoesDAO.createteTab();
    }

    /**
     * Busca todos os registros na tabela promocoes externa
     * @param context contexto da classe que utiliza o método */
    public static List<Promocoes> getAllExt(Context context) {
        promocoesExtDAO = new PromocoesExtDAO();
        if (SharedUtils.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return promocoesExtDAO.getAllByEmpresa(configuracoes.getHost(), configuracoes.getDb(),
                    configuracoes.getUserDb(), configuracoes.getPassDb(), configuracoes.getCompany());
        } else if (SharedUtils.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            try {
                Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
                return promocoesExtDAO.getAllByEmpresa(cloud.getHostWeb(), cloud.getMysqlDb(),
                        cloud.getMysqlUser(), cloud.getMysqlPass(),
                        SharedUtils.getString(context, context.getString(R.string.pref_company_cloud_key)));
            } catch (FileNotFoundException e) {
                LogUtil.error("ERROR: ", e.getMessage(), e);
                return new ArrayList<Promocoes>();
            }
        }
       return new ArrayList<Promocoes>();
    }

}
