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

    /**
     * Insere um registro na tabela usuário
     * @param context contexto da classe que utiliza o método
     * @param usuario objeto usuario a ser inserido no db */
    public static long insert(Context context, Usuario usuario) {
        usuarioDAO = new UsuarioDAO(context);
        return usuarioDAO.insert(usuario);
    }

    /**
     * Insere um registro na tabela tipo_usuario
     * @param context contexto da classe que utiliza o método
     * @param tipoUsuario objeto tipo_usuario a ser inserido no db */
    public static long insert(Context context, TipoUsuario tipoUsuario) {
        usuarioDAO = new UsuarioDAO(context);
        return usuarioDAO.insert(tipoUsuario);
    }

    /**
     * Busca todos os registros na tabela usuário
     * @param context contexto da classe que utiliza o método */
    public static List<Usuario> getAll(Context context) {
        usuarioDAO = new UsuarioDAO(context);
        return usuarioDAO.getAll();
    }

    /**
     * Deleta todos os registros na tabela usuário
     * @param context contexto da classe que utiliza o método */
    public static void deleteTab(Context context) {
        usuarioDAO = new UsuarioDAO(context);
        usuarioDAO.deleteTabUsuario();
    }

    /**
     * Deleta todos os registros na tabela tipo_usuario
     * @param context contexto da classe que utiliza o método */
    public static void deleteTabTipoUsuario(Context context) {
        usuarioDAO = new UsuarioDAO(context);
        usuarioDAO.deleteTabTipoUsuario();
    }

    /**
     * Busca um registro na tabela usuário externa pelo nome do usuário
     * @param context contexto da classe que utiliza o método
     * @param nome nome do usuario a ser pesquisado no db */
    public static Usuario getByNameExt(Context context, String nome) {
        usuarioExtDAO = new UsuarioExtDAO();
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return usuarioExtDAO.getByName(configuracoes.getHost(), configuracoes.getDb(), configuracoes.getUserDb(), configuracoes.getPassDb(), nome, configuracoes.getCompany());
    }

    /**
     * Busca todos os registros na tabela usuário externa
     * @param context contexto da classe que utiliza o método */
    public static List<Usuario> getAllExt(Context context) {
        usuarioExtDAO = new UsuarioExtDAO();
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return usuarioExtDAO.getAllByEmpresa(configuracoes.getHost(), configuracoes.getDb(), configuracoes.getUserDb(), configuracoes.getPassDb(), configuracoes.getCompany());
    }

}
