package com.codigosandroid.gensyspdv.configuracoes;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.utils.utils.PrefsUtil;

/**
 * Created by Tiago on 27/12/2017.
 */

public class ServiceConfiguracoes {

    public static Configuracoes getConfiguracoes(Context context) {
        Configuracoes configuracoes = new Configuracoes();
        configuracoes.setHost(PrefsUtil.getString(context, context.getString(R.string.pref_host_key)));
        configuracoes.setCompany(PrefsUtil.getString(context, context.getString(R.string.pref_company_key)));
        configuracoes.setDb(PrefsUtil.getString(context, context.getString(R.string.pref_db_key)));
        configuracoes.setUserDb(PrefsUtil.getString(context, context.getString(R.string.pref_user_key)));
        configuracoes.setPassDb(PrefsUtil.getString(context, context.getString(R.string.pref_pass_key)));
        return configuracoes;
    }

}
