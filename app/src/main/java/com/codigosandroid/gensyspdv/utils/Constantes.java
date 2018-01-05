package com.codigosandroid.gensyspdv.utils;

import android.Manifest;

/**
 * Created by Tiago on 28/12/2017.
 */

public class Constantes {

    // Permissões especiais
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };
    // Tipos de sincronização
    public static final String SYNC = "sync";
    public static final String RESUME = "resume";
    // Tipos de usuario
    public static final String VENDEDOR = "VENDEDOR";
    public static final String FUNCIONARIO = "FUNCIONÁRIO";
    // Dados sincronizados
    public static final String CLIENTE = "cliente";
    public static final String EMPRESA = "empresa";
    public static final String ESTOQUE = "estoque";
    public static final String ESTOQUE_PRECO = "estoque_preco";
    public static final String FORMAPAG = "formapag";
    public static final String PRECO_HORA = "preco_hora";
    public static final String PROMOCOES = "promocoes";
    public static final String CFBLOB = "cfblob";
    public static final String USUARIO = "usuario";
    public static final String CLOUD = "cloud";
    public static final String G_WEB = "gweb";
    // Acesso Sevidor Genius
    public static final String LOCAL = "192.168.0.254";
    public static final String EXTERNO = "177.135.200.98";
    public static final String USER = "usersite";
    public static final String PASS = "site6431";
    public static final String FOLDER = "gensys";
    // Nome dos arquivos Json
    public static final String FILE_CLOUD_JSON = "cloud.json";
    public static final String FILE_GW_JSON = "genius_web.json";
    public static final String FILE_OPERADOR_JSON = "operador.json";
    // Preferences values
    public static final String DESKTOP = "desktop";
    // Formas de pagamento
    public static final String DINHEIRO = "DINHEIRO";
    public static final String CREDITO = "CRÉDITO";
    public static final String CREDITO_PARCELA = "CRÉDITO_PARCELA";
    public static final String DEBITO = "DÉBITO";
    public static final String VOUCHER = "VOUCHER";

}
