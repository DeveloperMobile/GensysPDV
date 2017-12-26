package com.codigosandroid.gensyspdv.db;

import com.codigosandroid.utils.utils.LogUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Tiago on 26/12/2017.
 */

public class AcessoDB {

    public Connection getConnection(String host, String db, String user, String pass) {

        try {
            // Driver para conexão com MySQL
            Class.forName("com.mysql.jdbc.Driver");
            // Conecta no banco
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db + "?noAccessToProcedureBodies=true", user, pass);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtil.error("getConnection(): ", e.getMessage(), e);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
