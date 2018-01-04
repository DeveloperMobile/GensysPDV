package com.codigosandroid.gensyspdv.configuracoes;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.cloud.GeniusWeb;
import com.codigosandroid.gensyspdv.usuario.Usuario;
import com.codigosandroid.gensyspdv.utils.Constantes;
import com.codigosandroid.utils.utils.FileUtil;
import com.codigosandroid.utils.utils.IOUtil;
import com.codigosandroid.utils.utils.SharedUtil;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Tiago on 27/12/2017.
 */

public class ServiceConfiguracoes {

    private static final String TAG = ServiceConfiguracoes.class.getSimpleName();

    public static Configuracoes getConfiguracoes(Context context) {
        Configuracoes configuracoes = new Configuracoes();
        configuracoes.setHost(SharedUtil.getString(context, context.getString(R.string.pref_host_key)));
        configuracoes.setCompany(SharedUtil.getString(context, context.getString(R.string.pref_company_key)));
        configuracoes.setDb(SharedUtil.getString(context, context.getString(R.string.pref_db_key)));
        configuracoes.setUserDb(SharedUtil.getString(context, context.getString(R.string.pref_user_key)));
        configuracoes.setPassDb(SharedUtil.getString(context, context.getString(R.string.pref_pass_key)));
        configuracoes.setNfce(SharedUtil.isBoolean(context, context.getString(R.string.pref_print_nfce_key)));
        configuracoes.setNfceResume(SharedUtil.isBoolean(context, context.getString(R.string.pref_print_nfce_resume_key)));
        configuracoes.setNota(SharedUtil.isBoolean(context, context.getString(R.string.pref_print_note_key)));
        return configuracoes;
    }

    public static boolean isPreferencesDesktop(Context context) {

        try {
            String home = SharedUtil.getString(context, context.getString(R.string.pref_host_key));
            String company = SharedUtil.getString(context, context.getString(R.string.pref_company_key));
            String user = SharedUtil.getString(context, context.getString(R.string.pref_user_key));
            String db = SharedUtil.getString(context, context.getString(R.string.pref_db_key));
            if (!home.equals("") && !company.equals("") && !user.equals("") && !db.equals("")) {
                return true;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean isPreferencesGw(Context context) {

        File file = FileUtil.getFile(context, Constantes.FILE_GW_JSON);

        if (file.exists()) {
            return true;
        }

        return false;

    }

    public static boolean isPreferencesCloud(Context context) {

        File file = FileUtil.getFile(context, Constantes.FILE_CLOUD_JSON);

        if (file.exists()) {
            return true;
        }

        return false;

    }

    public static Cloud loadCloudFromJSON(Context context) throws FileNotFoundException {
        File file = FileUtil.getFile(context, Constantes.FILE_CLOUD_JSON);
        if (file.exists()) {
            Gson gson = new Gson();
            BufferedReader br = IOUtil.readJson(file);
            return gson.fromJson(br, Cloud.class);
        }
        return new Cloud();
    }

    public static Usuario loadOperadorFromJSON(Context context) throws FileNotFoundException {
        File file = FileUtil.getFile(context, Constantes.FILE_OPERADOR_JSON);
        if (file.exists()) {
            Gson gson = new Gson();
            BufferedReader br = IOUtil.readJson(file);
            return gson.fromJson(br, Usuario.class);
        }
        return new Usuario();
    }

    public static GeniusWeb loadGeniusWebFromJSON(Context context) throws FileNotFoundException {
       File file = FileUtil.getFile(context, Constantes.FILE_GW_JSON);
       if (file.exists()) {
           Gson gson = new Gson();
           BufferedReader br = IOUtil.readJson(file);
           return gson.fromJson(br, GeniusWeb.class);
       }
       return new GeniusWeb();
    }

    public static String toJson(GeniusWeb geniusWeb) {
        Gson gson = new Gson();
        return gson.toJson(geniusWeb);
    }

    public static String toJson(Cloud cloud) {
        Gson gson = new Gson();
        return gson.toJson(cloud);
    }

    public static String toJson(Usuario operador) {
        Gson gson = new Gson();
        return gson.toJson(operador);
    }

    public static void saveJson(Context context, String fileName, GeniusWeb geniusWeb) {
        File file = FileUtil.getFile(context, fileName);
        if (!file.exists()) {
            IOUtil.writeString(file, toJson(geniusWeb));
        }
    }

    public static void saveJson(Context context, String fileName, Cloud cloud) {
        File file = FileUtil.getFile(context, fileName);
        if (!file.exists()) {
            IOUtil.writeString(file, toJson(cloud));
        }
    }

    public static void saveJson(Context context, String fileName, Usuario operador) {
        File file = FileUtil.getFile(context, fileName);
        if (!file.exists()) {
            IOUtil.writeString(file, toJson(operador));
        }
    }

}
