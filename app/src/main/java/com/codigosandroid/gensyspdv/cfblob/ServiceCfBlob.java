package com.codigosandroid.gensyspdv.cfblob;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.gensyspdv.db.ServiceGeneric;
import com.codigosandroid.gensyspdv.utils.DAO;
import com.codigosandroid.utils.utils.LogUtil;
import com.codigosandroid.utils.utils.PrefsUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 02/01/2018.
 */

public class ServiceCfBlob extends ServiceGeneric<CfBlob> {

    private static DAO<CfBlob> dao;

    @Override
    public DAO<CfBlob> getDAO(Context context) {
        if (dao == null) {
            dao = new CfBlobDAO(context);
        }
        return dao;
    }

    /**
     * Insere um registro na tabela cfblob
     * @param context contexto da classe que utiliza o método
     * @param cfBlob objeto cfblob a ser inserido no db */
    public long insert(Context context, CfBlob cfBlob) {
        return getDAO(context).insert(cfBlob);
    }

    /**
     * Deleta todos os registros na tabela cfblob
     * @param context contexto da classe que utiliza o método */
    public void deleteTab(Context context) {
        getDAO(context).deleteTab();
    }

    /**
     * Deleta a tabela cfblob
     * @param context contexto da classe que utiliza o método */
    public void dropTab(Context context) {
        getDAO(context).dropTab();
    }

    /**
     * Cria a tabela cfblob
     * @param context contexto da classe que utiliza o método */
    public void createTab(Context context) {
        getDAO(context).createTab();
    }

}
