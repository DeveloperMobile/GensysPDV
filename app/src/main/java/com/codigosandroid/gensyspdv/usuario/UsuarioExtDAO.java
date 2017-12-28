package com.codigosandroid.gensyspdv.usuario;

import com.codigosandroid.gensyspdv.db.AcessoDB;
import com.codigosandroid.utils.utils.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 26/12/2017.
 */

public class UsuarioExtDAO {

    private static final String TAG = UsuarioExtDAO.class.getSimpleName();

    private static final String GET_ALL_BY_EMPRESA = "SELECT * FROM vendedor " +
            "WHERE UPPER(empresa)=? AND (tipo='FUNCIONÁRIO' OR tipo='VENDEDOR')";

    private static final String GET_USER_BY_EMPRESA = "SELECT * FROM vendedor WHERE UPPER(apelido)=? AND UPPER(empresa)=?;";

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    AcessoDB acessoDB = new AcessoDB();

    public List<Usuario> getAllByEmpresa(String ip, String db, String user, String pass, String empresa) {

        List<Usuario> usuarioList = new ArrayList<>();

        try {

            conn = acessoDB.getConnection(ip, db, user, pass);
            stmt = conn.prepareStatement(GET_ALL_BY_EMPRESA);
            stmt.setString(1, empresa.toUpperCase());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = resultToUsuario(rs);
                usuarioList.add(usuario);
            }

            rs.close();
            return usuarioList;

        } catch (Exception e) {

            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<Usuario>();

        } finally {

            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }
        }

    }

    public Usuario getByName(String ip, String db, String user, String pass, String name, String empresa) {

        try {

            conn = acessoDB.getConnection(ip, db, user, pass);
            stmt = conn.prepareStatement(GET_USER_BY_EMPRESA);
            stmt.setString(1, name.toUpperCase());
            stmt.setString(2, empresa.toUpperCase());
            rs = stmt.executeQuery();

            while (rs.next()) {

                 return resultToUsuario(rs);


            }

            rs.close();

        } catch (Exception e) {

            LogUtil.error(TAG, e.getMessage(), e);
            return null;
            
        } finally {
            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }
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
