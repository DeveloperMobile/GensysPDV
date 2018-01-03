package com.codigosandroid.gensyspdv.cloud;

import com.codigosandroid.gensyspdv.db.AcessoDB;
import com.codigosandroid.utils.utils.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Tiago on 02/01/2018.
 */

public class CloudExtDAO {

    private static final String TAG = CloudExtDAO.class.getSimpleName();

    private static final String CONFIG_WEB = "SELECT loginemail, mysqlpasta, mysqllogin, mysqlsenha, hostweb FROM webcliente WHERE loginemail=?;";
    private static final String CONFIG_WEB_BY_EMAIL = "SELECT apelido, tabletsenha, empresa FROM vendedor WHERE UPPER(empresa)=?";

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    AcessoDB acessoDB = new AcessoDB();

    public Cloud getCloud(String host, String db, String user, String pass, String loginEmail) {

        Cloud cloud = new Cloud();

        try {

            conn = acessoDB.getConnection(host, db, user, pass);
            stmt = conn.prepareStatement(CONFIG_WEB);
            stmt.setString(1, loginEmail);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cloud =  resultToCloud(rs);
            }

            rs.close();
            return cloud;

        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new Cloud();
        } finally {

            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }
        }
    }

    public GeniusWeb getGeniusWeb(String host, String db, String login, String senha, String empresa) {

        GeniusWeb geniusWeb = new GeniusWeb();

        try {
            conn = acessoDB.getConnection(host, db, login, senha);
            stmt = conn.prepareStatement(CONFIG_WEB_BY_EMAIL);
            stmt.setString(1, empresa.toUpperCase());
            rs = stmt.executeQuery();

            if (rs.next()) {
                geniusWeb = resultToGeniusWeb(rs);
            }

            rs.close();
            return geniusWeb;

        } catch (Exception e) {

            LogUtil.error(TAG, e.getMessage(), e);
            return new GeniusWeb();

        } finally {
            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }
        }

    }

    private Cloud resultToCloud(ResultSet rs) throws SQLException {
        Cloud cloud = new Cloud();
        cloud.setLoginEmail(rs.getString("loginemail"));
        cloud.setHostWeb(rs.getString("hostweb"));
        cloud.setMysqlDb(rs.getString("mysqlpasta"));
        cloud.setMysqlUser(rs.getString("mysqllogin"));
        cloud.setMysqlPass(rs.getString("mysqlsenha"));
        return cloud;
    }

    private GeniusWeb resultToGeniusWeb(ResultSet rs) throws SQLException {
        GeniusWeb geniusWeb = new GeniusWeb();
        geniusWeb.setApelido(rs.getString("apelido"));
        geniusWeb.setTabletSenha(rs.getString("tabletsenha"));
        geniusWeb.setEmpresa(rs.getString("empresa"));
        return geniusWeb;
    }

}
