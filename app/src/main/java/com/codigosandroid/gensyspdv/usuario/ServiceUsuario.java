package com.codigosandroid.gensyspdv.usuario;

import android.content.Context;

import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Tiago on 26/12/2017.
 */

public class ServiceUsuario {

    private static UsuarioExtDAO usuarioExtDAO;
    private static UsuarioDAO usuarioDAO;

    public static long insert(Context context, Usuario usuario) {
        usuarioDAO = new UsuarioDAO(context);
        return usuarioDAO.insert(usuario);
    }

    public static long insert(Context context, TipoUsuario tipoUsuario) {
        usuarioDAO = new UsuarioDAO(context);
        return usuarioDAO.insert(tipoUsuario);
    }

    public static Usuario getByNameExt(Context context, String nome) {
        usuarioExtDAO = new UsuarioExtDAO();
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return usuarioExtDAO.getByName(configuracoes.getHost(), configuracoes.getDb(), configuracoes.getUserDb(), configuracoes.getPassDb(), nome, configuracoes.getCompany());
    }

    public static List<Usuario> getAllExt(Context context) {
        usuarioExtDAO = new UsuarioExtDAO();
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return usuarioExtDAO.getAllByEmpresa(configuracoes.getHost(), configuracoes.getDb(), configuracoes.getUserDb(), configuracoes.getPassDb(), configuracoes.getCompany());
    }

}
