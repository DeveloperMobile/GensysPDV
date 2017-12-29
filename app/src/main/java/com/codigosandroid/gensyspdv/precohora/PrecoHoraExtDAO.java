package com.codigosandroid.gensyspdv.precohora;

import com.codigosandroid.gensyspdv.db.AcessoDB;
import com.codigosandroid.utils.utils.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 29/12/2017.
 */

public class PrecoHoraExtDAO {

    private static final String TAG = PrecoHoraExtDAO.class.getSimpleName();

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    AcessoDB acessoDB = new AcessoDB();

    private static final String GET_ALL = "SELECT * FROM precohora";

    public List<PrecoHora> getAll(String ip, String db, String user, String pass) {

        List<PrecoHora> precoHoras = new ArrayList<>();

        try {

            conn = acessoDB.getConnection(ip, db, user, pass);
            stmt = conn.prepareStatement(GET_ALL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                PrecoHora precoHora =  resultSetToPrecoHora(rs);
                precoHoras.add(precoHora);

            }

            rs.close();
            return precoHoras;

        } catch (Exception e){
            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<PrecoHora>();
        } finally {
            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }
        }
    }

    private PrecoHora resultSetToPrecoHora(ResultSet rs) throws SQLException {

        PrecoHora precoHora = new PrecoHora();
        precoHora.setIdEstoque(rs.getInt("id_estoque"));
        precoHora.setDiaSemana(rs.getString("diasemana"));
        precoHora.setHoraInicio(rs.getString("horainicio"));
        precoHora.setHoraFim(rs.getString("horafim"));
        precoHora.setValor(rs.getDouble("precovenda"));
        return precoHora;

    }

}
