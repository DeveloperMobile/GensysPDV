package com.codigosandroid.gensyspdv.estoque;

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

public class EstoquePrecoExcDAO {

    private static final String TAG = EstoquePrecoExcDAO.class.getSimpleName();

    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    AcessoDB acessoDB = new AcessoDB();

    private static final String GET_ALL = "SELECT * FROM estoquepreco WHERE UPPER(empresa)=?";

    public List<EstoquePreco> getByEmpresa(String ip, String db, String user, String pass, String empresa) {

        List<EstoquePreco> estoquePrecos = new ArrayList<>();

        try {

            conn = acessoDB.getConnection(ip, db, user, pass);
            stmt = conn.prepareStatement(GET_ALL);
            stmt.setString(1, empresa.toUpperCase());
            rs = stmt.executeQuery();

            while (rs.next()) {
                EstoquePreco estoquePreco = resultSetToEstoquePreco(rs);
                estoquePrecos.add(estoquePreco);
            }

            rs.close();
            return estoquePrecos;

        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<EstoquePreco>();
        } finally {
            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }
        }
    }

    private EstoquePreco resultSetToEstoquePreco(ResultSet rs) throws SQLException {

        EstoquePreco estoquePreco = new EstoquePreco();
        estoquePreco.setTabelaCodigo(rs.getInt("tabelacodigo"));
        estoquePreco.setCodigo(rs.getString("codigo"));
        estoquePreco.setDescricao(rs.getString("descricao"));
        estoquePreco.setPreco(rs.getDouble("preco"));
        estoquePreco.setEmpresa(rs.getString("empresa"));
        estoquePreco.setMenorPreco(rs.getDouble("menorpreco"));
        estoquePreco.setAjustePercentual(rs.getDouble("ajustepercentual"));
        estoquePreco.setMargemLucro(rs.getDouble("margemlucro"));
        estoquePreco.setAtualizado(rs.getTimestamp("atualizado").toString());
        return estoquePreco;

    }

}
