package com.codigosandroid.gensyspdv.usuario;

/**
 * Created by Tiago on 26/12/2017.
 */

public class ServiceUsuario {

    private static UsuarioExtDAO usuarioExtDAO;

    public static Usuario getByNameExt(String nome, String tabletsenha) {
        usuarioExtDAO = new UsuarioExtDAO();
        return usuarioExtDAO.getByName("192.168.0.83", "geniusnfe", "root", "", nome, tabletsenha);
    }

}
