package com.codigosandroid.gensyspdv.configuracoes;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.utils.SharedUtils;

/**
 * Created by Tiago on 27/12/2017.
 */

public class ServiceConfiguracoes {

    public static Configuracoes getConfiguracoes(Context context) {
        Configuracoes configuracoes = new Configuracoes();
        configuracoes.setHost(SharedUtils.getString(context, context.getString(R.string.pref_host_key)));
        configuracoes.setCompany(SharedUtils.getString(context, context.getString(R.string.pref_company_key)));
        configuracoes.setDb(SharedUtils.getString(context, context.getString(R.string.pref_db_key)));
        configuracoes.setUserDb(SharedUtils.getString(context, context.getString(R.string.pref_user_key)));
        configuracoes.setPassDb(SharedUtils.getString(context, context.getString(R.string.pref_pass_key)));
        configuracoes.setNfce(SharedUtils.isBoolean(context, context.getString(R.string.pref_print_nfce_key)));
        configuracoes.setNfceResume(SharedUtils.isBoolean(context, context.getString(R.string.pref_print_nfce_resume_key)));
        configuracoes.setNota(SharedUtils.isBoolean(context, context.getString(R.string.pref_print_note_key)));
        return configuracoes;
    }

    public static boolean isPreferences(Context context) {

        try {
            String home = SharedUtils.getString(context, context.getString(R.string.pref_host_key));
            String company = SharedUtils.getString(context, context.getString(R.string.pref_company_key));
            String user = SharedUtils.getString(context, context.getString(R.string.pref_user_key));
            String db = SharedUtils.getString(context, context.getString(R.string.pref_db_key));
            if (!home.equals("") && !company.equals("") && !user.equals("") && !db.equals("")) {
                return true;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
