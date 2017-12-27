package com.codigosandroid.gensyspdv.usuario;

import android.content.Context;

import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;

/**
 * Created by Tiago on 26/12/2017.
 */

public class ServiceUsuario {

    private static UsuarioExtDAO usuarioExtDAO;

    public static Usuario getByNameExt(Context context, String nome) {
        usuarioExtDAO = new UsuarioExtDAO();
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return usuarioExtDAO.getByName(configuracoes.getHost(), configuracoes.getDb(), configuracoes.getUserDb(), configuracoes.getPassDb(), nome);
    }

}
