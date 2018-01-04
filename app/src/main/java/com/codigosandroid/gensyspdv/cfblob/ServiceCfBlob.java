package com.codigosandroid.gensyspdv.cfblob;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.utils.utils.LogUtil;
import com.codigosandroid.utils.utils.PrefsUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 02/01/2018.
 */

public class ServiceCfBlob {

    private static CfBlobDAO cfBlobDAO;
    private static CfBlobExtDAO cfBlobExtDAO;

    /**
     * Insere um registro na tabela cfblob
     * @param context contexto da classe que utiliza o método
     * @param cfBlob objeto cfblob a ser inserido no db */
    public static long insert(Context context, CfBlob cfBlob) {
        cfBlobDAO = new CfBlobDAO(context);
        return cfBlobDAO.insert(cfBlob);
    }

    /**
     * Deleta todos os registros na tabela cfblob
     * @param context contexto da classe que utiliza o método */
    public static void deleteTab(Context context) {
        cfBlobDAO = new CfBlobDAO(context);
        cfBlobDAO.deleteTab();
    }

    /**
     * Deleta a tabela cfblob
     * @param context contexto da classe que utiliza o método */
    public static void dropTab(Context context) {
        cfBlobDAO = new CfBlobDAO(context);
        cfBlobDAO.dropTab();
    }

    /**
     * Cria a tabela cfblob
     * @param context contexto da classe que utiliza o método */
    public static void createTab(Context context) {
        cfBlobDAO = new CfBlobDAO(context);
        cfBlobDAO.createTab();
    }

    /**
     * Busca todos os registros na tabela cfblob externa
     * @param context contexto da classe que utiliza o método */
    public static List<CfBlob> getAllExt(Context context) {
        cfBlobExtDAO = new CfBlobExtDAO();
        if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return cfBlobExtDAO.getAll(configuracoes.getHost(), configuracoes.getDb(),
                    configuracoes.getUserDb(), configuracoes.getPassDb());
        } else if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            try {
                Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
                return cfBlobExtDAO.getAll(cloud.getHostWeb(), cloud.getMysqlDb(),
                        cloud.getMysqlUser(), cloud.getMysqlPass());
            } catch (FileNotFoundException e) {
                LogUtil.error("ERROR: ", e.getMessage(), e);
                return new ArrayList<CfBlob>();
            }
        }
        return new ArrayList<CfBlob>();
    }

}
