package com.codigosandroid.gensyspdv.sobre;

import android.app.Activity;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.utils.AndroidUtils;
import com.codigosandroid.utils.utils.AndroidUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 12/09/2017.
 */

public class Sobre implements Serializable {

    private String descricao;
    private String valor;

    public Sobre(String descricao, String valor) {

        setDescricao(descricao);
        setValor(valor);

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public static List<Sobre> getSobre(Activity activity) {

        List<Sobre> sobreList = new ArrayList<>();
        sobreList.add(new Sobre(activity.getString(R.string.sobre_versao), AndroidUtil.getVersionName(activity)));
        sobreList.add(new Sobre(activity.getString(R.string.sobre_serial), AndroidUtils.getSerial(activity)));
        sobreList.add(new Sobre(activity.getString(R.string.sobre_email), activity.getString(R.string.sobre_email_value)));
        sobreList.add(new Sobre(activity.getString(R.string.sobre_site), activity.getString(R.string.sobre_site_value)));
        sobreList.add(new Sobre(activity.getString(R.string.sobre_politica_privacidade), activity.getString(R.string.sobre_politica_privacidade_value)));
        sobreList.add(new Sobre(activity.getString(R.string.sobre_fone), activity.getString(R.string.sobre_fone_value)));
        return sobreList;

    }

}
