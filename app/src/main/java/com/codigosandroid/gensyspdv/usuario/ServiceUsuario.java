package com.codigosandroid.gensyspdv.usuario;

import android.content.Context;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.utils.utils.LogUtil;
import com.codigosandroid.utils.utils.PrefsUtil;
import com.codigosandroid.utils.utils.SharedUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
     * Busca um registro na tabela usuário
     * @param context contexto da classe que utiliza o método */
    public static Usuario getByApelido(Context context, String apelido) {
        usuarioDAO = new UsuarioDAO(context);
        return usuarioDAO.getByName(apelido);
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
        if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return tipoUsuarioDAO.getByName(configuracoes.getCompany().toUpperCase());
        } else if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            return tipoUsuarioDAO.getByName(SharedUtil.getString(context, context.getString(R.string.pref_company_cloud_key)));
        }
        return new TipoUsuario();
    }

    /**
     * Busca um registro na tabela usuário externa pelo nome do usuário
     * @param context contexto da classe que utiliza o método
     * @param nome nome do usuario a ser pesquisado no db */
    public static Usuario getByNameExt(Context context, String nome) {
        usuarioExtDAO = new UsuarioExtDAO();
        if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return usuarioExtDAO.getByName(configuracoes.getHost(), configuracoes.getDb(), configuracoes.getUserDb(),
                    configuracoes.getPassDb(), nome, configuracoes.getCompany());
        } else if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            try {
                Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
                return usuarioExtDAO.getByName(cloud.getHostWeb(), cloud.getMysqlDb(),
                        cloud.getMysqlUser(), cloud.getMysqlPass(), nome,
                        SharedUtil.getString(context, context.getString(R.string.pref_company_cloud_key)));
            } catch (FileNotFoundException e) {
                LogUtil.error("ERROR: ", e.getMessage(), e);
                return new Usuario();
            }
        }
       return new Usuario();
    }

    /**
     * Busca todos os registros na tabela usuário externa
     * @param context contexto da classe que utiliza o método */
    public static List<Usuario> getAllExt(Context context) {
        usuarioExtDAO = new UsuarioExtDAO();
        if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_desktop_key))) {
            Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
            return usuarioExtDAO.getAllByEmpresa(configuracoes.getHost(), configuracoes.getDb(),
                    configuracoes.getUserDb(), configuracoes.getPassDb(), configuracoes.getCompany());
        } else if (PrefsUtil.getBoolean(context, context.getString(R.string.pref_cloud_key))) {
            try {
                Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(context);
                return usuarioExtDAO.getAllByEmpresa(cloud.getHostWeb(), cloud.getMysqlDb(),
                        cloud.getMysqlUser(), cloud.getMysqlPass(),
                        SharedUtil.getString(context, context.getString(R.string.pref_company_cloud_key)));
            } catch (FileNotFoundException e) {
                LogUtil.error("ERROR: ", e.getMessage(), e);
                return new ArrayList<Usuario>();
            }
        }
        return new ArrayList<Usuario>();
    }

}
