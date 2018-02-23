package com.codigosandroid.gensyspdv.cfblob;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.gensyspdv.db.ServiceGeneric;
import com.codigosandroid.gensyspdv.utils.DAO;
import com.codigosandroid.utils.utils.PrefsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by analise on 23/02/18.
 */

public class ServiceCfBlobExt extends ServiceGeneric<CfBlob> {

    private DAO<CfBlob> dao;

    @Override
    public DAO<CfBlob> getDAO(Context context) {
        if (dao == null) {
            dao = new CfBlobExtDAO();
        }
        return dao;
    }


    /**
     * Busca todos os registros na tabela cfblob externa
     * @param context contexto da classe que utiliza o método */
    public List<CfBlob> getAllExt(Context context) {
        if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return getDAO(context).getAll(configuracoes.getHost(), configuracoes.getDb(),
                    configuracoes.getUserDb(), configuracoes.getPassDb());
        } else if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
            return getDAO(context).getAll(cloud.getHostWeb(), cloud.getMysqlDb(),
                    cloud.getMysqlUser(), cloud.getMysqlPass());
        }
        return new ArrayList<CfBlob>();
    }

}
