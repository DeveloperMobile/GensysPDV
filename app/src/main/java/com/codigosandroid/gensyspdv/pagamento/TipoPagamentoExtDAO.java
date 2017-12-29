package com.codigosandroid.gensyspdv.pagamento;

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

public class TipoPagamentoExtDAO {

    private static final String TAG = TipoPagamentoExtDAO.class.getSimpleName();

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    AcessoDB acessoDB = new AcessoDB();

    private static final String GET_FORMAPAG = "SELECT coalesce(descricao, '') descricao, coalesce(tipopagamento, '') tipopagamento, id_formapag FROM formapag WHERE " +
            "tipopagamento='DINHEIRO' OR tipopagamento='CRÉDITO' OR tipopagamento='CRÉDITO_PARCELA' " +
            "OR tipopagamento='DÉBITO' OR tipopagamento='VOUCHER';";


    public List<TipoPagamento> getAll(String ip, String db, String user, String pass) {

        List<TipoPagamento> tipoPagamentos = new ArrayList<>();

        try {

            conn = acessoDB.getConnection(ip, db, user, pass);
            stmt = conn.prepareStatement(GET_FORMAPAG);
            rs = stmt.executeQuery();

            while (rs.next()) {

                TipoPagamento tipoPagamento = resultSetToTipoPagamento(rs);
                tipoPagamentos.add(tipoPagamento);

            }

            rs.close();
            return tipoPagamentos;

        } catch (Exception e){

            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<TipoPagamento>();

        } finally {
            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }
        }

    }

    private TipoPagamento resultSetToTipoPagamento(ResultSet rs) throws SQLException {

        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setDescricao(rs.getString("descricao"));
        tipoPagamento.setTipoPagamento(rs.getString("tipopagamento"));
        tipoPagamento.setIdFormapag(rs.getInt("id_formapag"));
        return tipoPagamento;

    }

}
