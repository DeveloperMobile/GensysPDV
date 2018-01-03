package com.codigosandroid.gensyspdv.precohora;

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
 * Created by Tiago on 29/12/2017.
 */

public class ServicePrecoHora {

    private static PrecoHoraDAO precoHoraDAO;
    private static PrecoHoraExtDAO precoHoraExtDAO;

    /**
     * Insere um registro na tabela preco_hora
     * @param context contexto da classe que utiliza o método
     * @param precoHora objeto precoHora a ser inserido no db */
    public static long insert(Context context, PrecoHora precoHora) {
        precoHoraDAO = new PrecoHoraDAO(context);
        return precoHoraDAO.insert(precoHora);
    }

    /**
     * Deleta todos os registros na tabela preco_hora
     * @param context contexto da classe que utiliza o método */
    public static void deleteTab(Context context) {
        precoHoraDAO = new PrecoHoraDAO(context);
        precoHoraDAO.deleteTab();
    }

    /**
     * Deleta a tabela preco_hora
     * @param context contexto da classe que utiliza o método */
    public static void dropTab(Context context) {
        precoHoraDAO = new PrecoHoraDAO(context);
        precoHoraDAO.dropTab();
    }

    /**
     * Cria a tabela preco_hora
     * @param context contexto da classe que utiliza o método */
    public static void createTab(Context context) {
        precoHoraDAO = new PrecoHoraDAO(context);
        precoHoraDAO.createTab();
    }

    /**
     * Busca todos os registros na tabela preco_hora externa
     * @param context contexto da classe que utiliza o método */
    public static List<PrecoHora> getAllExt(Context context) {
        precoHoraExtDAO = new PrecoHoraExtDAO();
        if (SharedUtils.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return precoHoraExtDAO.getAll(configuracoes.getHost(), configuracoes.getDb(),
                    configuracoes.getUserDb(), configuracoes.getPassDb());
        } else if (SharedUtils.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            try {
                Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
                return precoHoraExtDAO.getAll(cloud.getHostWeb(), cloud.getMysqlDb(),
                        cloud.getMysqlUser(), cloud.getMysqlPass());
            } catch (FileNotFoundException e) {
                LogUtil.error("ERROR: ", e.getMessage(), e);
                return new ArrayList<PrecoHora>();
            }
        }
        return new ArrayList<PrecoHora>();
    }

}
