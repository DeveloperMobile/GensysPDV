package com.codigosandroid.gensyspdv.usuario;

import com.codigosandroid.gensyspdv.db.AcessoDB;
import com.codigosandroid.utils.utils.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Tiago on 26/12/2017.
 */

public class UsuarioExtDAO {

    private static final String TAG = UsuarioExtDAO.class.getSimpleName();

    private static final String GET_USER = "SELECT * FROM vendedor WHERE UPPER(apelido)=?;";

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    AcessoDB acessoDB = new AcessoDB();

    public Usuario getByName(String ip, String db, String user, String pass, String name) {

        try {

            conn = acessoDB.getConnection(ip, db, user, pass);
            stmt = conn.prepareStatement(GET_USER);
            stmt.setString(1, name.toUpperCase());
            rs = stmt.executeQuery();

            while (rs.next()) {

                 return resultToUsuario(rs);


            }

            rs.close();

        } catch (Exception e) {

            LogUtil.error(TAG, e.getMessage(), e);
            return null;
            
        }

        return null;

    }

    private Usuario resultToUsuario(ResultSet rs) throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setIdVendedor(rs.getInt("id_vendedor"));
        usuario.setNome(rs.getString("nome"));
        usuario.setApelido(rs.getString("apelido"));
        usuario.setSenha(rs.getString("tabletsenha"));
        usuario.setEmail(rs.getString("email"));
        usuario.setTipo(rs.getString("tipo"));
        return usuario;

    }

}
