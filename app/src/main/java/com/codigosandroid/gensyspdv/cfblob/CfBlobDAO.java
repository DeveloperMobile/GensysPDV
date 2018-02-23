package com.codigosandroid.gensyspdv.cfblob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.gensyspdv.utils.DAO;
import com.codigosandroid.utils.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 02/01/2018.
 */

public class CfBlobDAO implements DAO<CfBlob> {

    private static final String TAG = CfBlobDAO.class.getSimpleName();

    private static final String ID = "_ID";
    private static final String ID_CFBLOB = "ID_CFBLOB";
    private static final String CONFIGURACAO = "CONFIGURACAO";
    private static final String PRINCIPAL = "PRINCIPAL";
    private static final String SECUNDARIO = "SECUNDARIO";
    private static final String TERCIARIO = "TERCIARIO";
    private static final String QUATERNARIO = "QUATERNARIO";
    private static final String MOBILE = "MOBILE";
    private static final String NOME = "NOME";
    private static final String STATUS = "STATUS";
    private static final String TESTADO = "TESTADO";
    private static final String DATA = "DATA";

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS " + BaseDAO.TABLE_CFBLOB + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "ID_CFBLOB INTEGER, "
            + "CONFIGURACAO TEXT, "
            + "PRINCIPAL TEXT, "
            + "SECUNDARIO TEXT, "
            + "TERCIARIO TEXT, "
            + "QUATERNARIO TEXTO, "
            + "MOBILE TEXT, "
            + "NOME TEXT, "
            + "STATUS TEXT, "
            + "TESTADO TEXT, "
            + "DATA TEXT);";

    private BaseDAO dao = null;

    public CfBlobDAO(Context context) {
        dao = new BaseDAO(context);
    }

    @Override
    public long insert(CfBlob cfBlob) {
        try {
            dao.open();
            ContentValues values = cfBlobToValues(cfBlob);
            return dao.db.insert(BaseDAO.TABLE_CFBLOB, null, values);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return 0;
        } finally {
            dao.close();
        }
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
    public List<CfBlob> getAll(String ip, String db, String user, String pass) {
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
        try {
            dao.open();
            dao.db.execSQL("DELETE FROM " + BaseDAO.TABLE_CFBLOB);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            dao.close();
        }
    }

    @Override
    public void dropTab() {
        try {
            dao.open();
            dao.db.execSQL("DROP TABLE " + BaseDAO.TABLE_CFBLOB);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            dao.close();
        }
    }

    @Override
    public void createTab() {
        try {
            dao.open();
            dao.db.execSQL(CREATE);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            dao.close();
        }
    }

    private ContentValues cfBlobToValues(CfBlob cfBlob) {
        ContentValues values = new ContentValues();
        values.put(ID_CFBLOB, cfBlob.getIdCfBlob());
        values.put(CONFIGURACAO, cfBlob.getConfiguracao());
        values.put(PRINCIPAL, cfBlob.getPrincipal());
        values.put(SECUNDARIO, cfBlob.getSecundario());
        values.put(TERCIARIO, cfBlob.getTerciario());
        values.put(QUATERNARIO, cfBlob.getQuaternario());
        values.put(MOBILE, cfBlob.getMobile());
        values.put(NOME, cfBlob.getNome());
        values.put(STATUS, cfBlob.getStatus());
        values.put(TESTADO, cfBlob.getTestado());
        values.put(DATA, cfBlob.getData());
        return values;
    }

}
