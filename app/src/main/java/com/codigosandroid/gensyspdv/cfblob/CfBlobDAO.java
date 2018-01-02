package com.codigosandroid.gensyspdv.cfblob;

import android.content.ContentValues;
import android.content.Context;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 02/01/2018.
 */

public class CfBlobDAO extends BaseDAO {

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

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_CFBLOB + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
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

    public CfBlobDAO(Context context) {
        super(context);
    }

    public long insert(CfBlob cfBlob) {
        try {
            open();
            ContentValues values = cfBlobToValues(cfBlob);
            return db.insert(TABLE_CFBLOB, null, values);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return 0;
        } finally {
            close();
        }
    }

    public void deleteTab() {
        try {
            open();
            db.execSQL("DELETE FROM " + TABLE_CFBLOB);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void dropTab() {
        try {
            open();
            db.execSQL("DROP TABLE " + TABLE_CFBLOB);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void createTab() {
        try {
            open();
            db.execSQL(CREATE);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
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
