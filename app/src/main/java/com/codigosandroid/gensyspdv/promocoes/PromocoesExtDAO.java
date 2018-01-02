package com.codigosandroid.gensyspdv.promocoes;

import com.codigosandroid.gensyspdv.db.AcessoDB;
import com.codigosandroid.utils.utils.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 02/01/2018.
 */

public class PromocoesExtDAO {

    private static final String TAG = PromocoesExtDAO.class.getSimpleName();

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    AcessoDB acessoDB = new AcessoDB();

    private static final String GET_ALL = "SELECT coalesce(empresa, '') empresa, coalesce(codigo, '') codigo, " +
            "coalesce(datainicio, '') datainicio, coalesce(datafim, '') datafim, " +
            "coalesce(nomepromocao, '') nomepromocao, coalesce(operador, '') operador, coalesce(datacriacao, '') datacriacao, " +
            "coalesce(operadoralteracao, '') operadoralteracao, coalesce(dataalteracao, '') dataalteracao, " +
            "coalesce(precovenda, '') precovenda, coalesce(horainicio, '') horainicio, coalesce(horafim, '') horafim " +
            "FROM promocoes WHERE UPPER(empresa)=?";

    public List<Promocoes> getAllByEmpresa(String ip, String db, String user, String pass, String empresa) {

        List<Promocoes> promocoesList = new ArrayList<>();

        try {

            conn = acessoDB.getConnection(ip, db, user, pass);
            stmt = conn.prepareStatement(GET_ALL);
            stmt.setString(1, empresa.toUpperCase());
            rs = stmt.executeQuery();

            while (rs.next()) {

                Promocoes promocoes = resultSetToPromocoes(rs);
                promocoesList.add(promocoes);

            }

            rs.close();
            return promocoesList;

        }  catch (Exception e){
            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<Promocoes>();
        } finally {

            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (Exception e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }

        }

    }

    /* Mapeia o ResultSet num objeto Promocoes */
    private Promocoes resultSetToPromocoes(ResultSet rs) throws SQLException {

        Promocoes promocoes = new Promocoes();
        promocoes.setCodigoEstoque(rs.getString("codigo"));
        promocoes.setDescricaoEmpresa(rs.getString("empresa"));
        promocoes.setDataInicio(rs.getString("datainicio"));
        promocoes.setDataFim(rs.getString("datafim"));
        promocoes.setNomePromocao(rs.getString("nomepromocao"));
        promocoes.setOperador(rs.getString("operador"));
        promocoes.setDataCriacao(rs.getString("datacriacao"));
        promocoes.setOperadorAlteracao(rs.getString("operadoralteracao"));
        promocoes.setDataAlteracao(rs.getString("dataalteracao"));
        promocoes.setValor(rs.getDouble("precovenda"));
        promocoes.setHoraInicio(rs.getString("horainicio"));
        promocoes.setHoraFim(rs.getString("horafim"));
        return promocoes;

    }


}
