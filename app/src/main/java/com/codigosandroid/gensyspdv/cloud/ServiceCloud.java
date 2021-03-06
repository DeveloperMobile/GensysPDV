package com.codigosandroid.gensyspdv.cloud;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.gensyspdv.utils.Utils;
import com.codigosandroid.gensyspdv.utils.Constantes;
import com.codigosandroid.utils.utils.AlertUtil;
import com.codigosandroid.utils.utils.PrefsUtil;
import com.codigosandroid.utils.utils.SharedUtil;

import java.io.FileNotFoundException;

/**
 * Created by Tiago on 02/01/2018.
 */

public class ServiceCloud {

    private static CloudExtDAO cloudExtDAO;

    public static Cloud getCloud(String loginEmail) {
        cloudExtDAO = new CloudExtDAO();
        if (Utils.localizaHost(Constantes.LOCAL, 3306)) {
            return cloudExtDAO.getCloud(Constantes.LOCAL, Constantes.FOLDER, Constantes.USER, Constantes.PASS, loginEmail);
        } else if (Utils.localizaHost(Constantes.EXTERNO, 3306)) {
            return cloudExtDAO.getCloud(Constantes.EXTERNO, Constantes.FOLDER, Constantes.USER, Constantes.PASS, loginEmail);
        }
        return new Cloud();
    }

    public static GeniusWeb getGeniusWeb(Context context) throws FileNotFoundException {
        cloudExtDAO = new CloudExtDAO();
        if (ServiceConfiguracoes.isPreferencesCloud(context)) {
            Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
            return cloudExtDAO.getGeniusWeb(cloud.getHostWeb(), cloud.getMysqlDb(), cloud.getMysqlUser(),
                    cloud.getMysqlPass(), SharedUtil.getString(context, context.getString(R.string.pref_company_cloud_key)));
        } else {
            AlertUtil.alert(context, "Aviso", "Não foi possível validar as configurações web!");
            return new GeniusWeb();
        }
    }

}
