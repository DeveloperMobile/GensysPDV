package com.codigosandroid.gensyspdv.cliente;

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

public class ClienteExtDAO {

    private static final String TAG = ClienteExtDAO.class.getSimpleName();

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    AcessoDB acessoDB = new AcessoDB();

    private static final String SELECT_ALL_BY_EMPRESA = "SELECT nome, fantasia, id_cliente, " +
            "coalesce(cpf, '') cpf, coalesce(cgc, '') cgc, coalesce(preco, '') preco, coalesce(tabelapreco, '') tabelapreco " +
            "FROM cliente WHERE UPPER(empresa)=?";

    private static final String SELECT_BY_EMPRESA = "SELECT nome, fantasia, " +
            "coalesce(cpf, '') cpf, coalesce(cgc, '') cgc, coalesce(preco, '') preco, id_cliente, coalesce(tabelapreco, '') tabelapreco " +
            "FROM cliente WHERE UPPER(empresa)=? AND fantasia=?";

    public List<Cliente> getAllByEmpresa(String host, String db, String user, String pass, String empresa) {

        List<Cliente> clientes = new ArrayList<>();
        try {
            conn = acessoDB.getConnection(host, db, user, pass);
            stmt = conn.prepareStatement(SELECT_ALL_BY_EMPRESA);
            stmt.setString(1, empresa.toUpperCase());
            rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = resultToCliente(rs);
                clientes.add(cliente);
            }
            rs.close();
            return clientes;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<Cliente>();
        } finally {
            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }
        }

    }

    private Cliente resultToCliente(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setNome(resultSet.getString("nome"));
        cliente.setFantasia(resultSet.getString("fantasia"));
        cliente.setCpf(resultSet.getString("cpf"));
        cliente.setCgc(resultSet.getString("cgc"));
        cliente.setPreco(resultSet.getString("preco"));
        cliente.setIdCliente(resultSet.getInt("id_cliente"));
        cliente.setTabelaPreco(resultSet.getInt("tabelapreco"));
        return cliente;
    }

}
