package com.codigosandroid.gensyspdv.cfblob;

import com.codigosandroid.gensyspdv.db.AcessoDB;
import com.codigosandroid.gensyspdv.utils.DAO;
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

public class CfBlobExtDAO implements DAO<CfBlob> {

    private static final String TAG = CfBlobExtDAO.class.getSimpleName();

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    AcessoDB acessoDB = new AcessoDB();

    private static final String GET_ALL = "SELECT * FROM cfblob WHERE mobile='VM'";

    @Override
    public List<CfBlob> getAll(String ip, String db, String user, String pass) {

        List<CfBlob> cfBlobList = new ArrayList<>();

        try {
            conn = acessoDB.getConnection(ip, db, user, pass);
            stmt = conn.prepareStatement(GET_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CfBlob cfBlob = resultToCfBlob(rs);
                cfBlobList.add(cfBlob);
            }

            rs.close();
            return cfBlobList;

        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<CfBlob>();
        } finally {
            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                LogUtil.error(TAG, e.getMessage(), e);
            }
        }
    }

    private CfBlob resultToCfBlob(ResultSet resultSet) throws SQLException {
        CfBlob cfBlob = new CfBlob();
        cfBlob.setIdCfBlob(resultSet.getInt("id_cfblob"));
        cfBlob.setConfiguracao(resultSet.getString("configuracao"));
        cfBlob.setPrincipal(resultSet.getString("principal"));
        cfBlob.setSecundario(resultSet.getString("secundario"));
        cfBlob.setTerciario(resultSet.getString("terciario"));
        cfBlob.setQuaternario(resultSet.getString("quaternario"));
        cfBlob.setMobile(resultSet.getString("mobile"));
        cfBlob.setNome(resultSet.getString("nome"));
        cfBlob.setStatus(resultSet.getString("status"));
        cfBlob.setTestado(resultSet.getString("testado"));
        cfBlob.setData(resultSet.getDate("data").toString());
        return cfBlob;
    }

    @Override
    public long insert(CfBlob cfBlob) {
        return 0;
    }

    @Override
    public long update(CfBlob cfBlob) {
        return 0;
    }

    @Override
    public long delete(CfBlob cfBlob) {
        return 0;
    }

    @Override
    public List<CfBlob> getAll() {
        return null;
    }

    @Override
    public List<CfBlob> getById(long id) {
        return null;
    }

    @Override
    public List<CfBlob> betByName(String name) {
        return null;
    }

    @Override
    public void deleteTab() {

    }

    @Override
    public void dropTab() {

    }

    @Override
    public void createTab() {

    }

}
