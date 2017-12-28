package com.codigosandroid.gensyspdv.empresa;

import com.codigosandroid.gensyspdv.db.AcessoDB;
import com.codigosandroid.utils.utils.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 28/12/2017.
 */

public class EmpresaExcDAO {

    private static final String TAG = EmpresaExcDAO.class.getSimpleName();

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    AcessoDB acessoDB = new AcessoDB();

    private static final String GET_EMPRESA = "SELECT descricao, coalesce(nfcetoken, '') nfcetoken, coalesce(nfcecsc, '') nfcecsc, id_empresa FROM empresa;";

    public List<Empresa> getAll(String host, String db, String user, String pass) {

        List<Empresa> empresas = new ArrayList<>();

        try {

            conn = acessoDB.getConnection(host, db, user, pass);
            stmt = conn.prepareStatement(GET_EMPRESA);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Empresa empresa = resultToEmpresa(rs);
                empresas.add(empresa);
            }

            rs.close();
            return empresas;

        } catch (Exception e) {

            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<Empresa>();

        } finally {
            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }
        }

    }

    private Empresa resultToEmpresa(ResultSet rs) throws SQLException {

        Empresa empresa = new Empresa();
        empresa.setDescricao(rs.getString("descricao"));
        empresa.setNfceToken(rs.getString("nfcetoken"));
        empresa.setNfceCsc(rs.getString("nfcecsc"));
        empresa.setIdEmpresa(rs.getInt("id_empresa"));
        return empresa;
    }

}
