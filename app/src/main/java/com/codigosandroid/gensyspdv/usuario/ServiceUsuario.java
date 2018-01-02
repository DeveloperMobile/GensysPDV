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
    private static TipoUsuarioDAO tipoUsuarioDAO;

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
        tipoUsuarioDAO = new TipoUsuarioDAO(context);
        return tipoUsuarioDAO.insert(tipoUsuario);
    }

    /**
     * Busca todos os registros na tabela usuário
     * @param context contexto da classe que utiliza o método */
    public static List<Usuario> getAll(Context context) {
        usuarioDAO = new UsuarioDAO(context);
        return usuarioDAO.getAll();
    }

    /**
     * Busca todos os registros na tabela usuário vinculados a tipo_usuario
     * @param context contexto da classe que utiliza o método */
    public static List<Usuario> getAllInner(Context context) {
        usuarioDAO = new UsuarioDAO(context);
        return usuarioDAO.getAllInner();
    }

    /**
     * Deleta todos os registros na tabela usuário
     * @param context contexto da classe que utiliza o método */
    public static void deleteTabUsuario(Context context) {
        usuarioDAO = new UsuarioDAO(context);
        usuarioDAO.deleteTabUsuario();
    }

    /**
     * Deleta a tabela usuario
     * @param context contexto da classe que utiliza o método */
    public static void dropTabUsuario(Context context) {
        usuarioDAO = new UsuarioDAO(context);
        usuarioDAO.dropTabUsuario();
    }

    /**
     * Cria a tabela usuario
     * @param context contexto da classe que utiliza o método */
    public static void createTabUsuario(Context context) {
        usuarioDAO = new UsuarioDAO(context);
        usuarioDAO.createTabUsuario();
    }

    /**
     * Deleta todos os registros na tabela tipo_usuario
     * @param context contexto da classe que utiliza o método */
    public static void deleteTabTipoUsuario(Context context) {
        tipoUsuarioDAO = new TipoUsuarioDAO(context);
        tipoUsuarioDAO.deleteTabTipoUsuario();
    }

    /**
     * Deleta a tabela tipo_usuario
     * @param context contexto da classe que utiliza o método */
    public static void dropTabTipoUsuario(Context context) {
        tipoUsuarioDAO = new TipoUsuarioDAO(context);
        tipoUsuarioDAO.dropTabTipoUsuario();
    }

    /**
     * Cria a tabela tipo_usuario
     * @param context contexto da classe que utiliza o método */
    public static void createTabTipoUsuario(Context context) {
        tipoUsuarioDAO = new TipoUsuarioDAO(context);
        tipoUsuarioDAO.createTabTipoUsuario();
    }

    /**
     * Busca um registro na tabela tipo_usuario  pela descricao
     * @param context contexto da classe que utiliza o método
     * @param nome nome do usuario a ser pesquisado no db */
    public static TipoUsuario getByName(Context context, String nome) {
        tipoUsuarioDAO = new TipoUsuarioDAO(context);
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return tipoUsuarioDAO.getByName(configuracoes.getCompany().toUpperCase());
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
