package com.codigosandroid.gensyspdv.empresa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

import java.util.List;

/**
 * Created by Tiago on 28/12/2017.
 */

public class EmpresaDAO extends BaseDAO {

    private static final String TAG = EmpresaDAO.class.getSimpleName();

    private static final String ID = "_ID";
    private static final String DESCRICAO = "DESCRICAO";
    private static final String NFCE_TOKEN = "NFCE_TOKEN";
    private static final String NFCE_CSC = "NFCE_CSC";
    private static final String ID_EMPRESA = "ID_EMPRESA";

    private static final String CREATE =  "CREATE TABLE IF NOT EXISTS " + TABLE_EMPRESA + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "DESCRICAO TEXT, "
            + "NFCE_TOKEN TEXT, "
            + "NFCE_CSC TEXT, "
            + "ID_EMPRESA INTEGER);";

    public EmpresaDAO(Context context) {
        super(context);
    }

    public long insert(Empresa empresa) {
        try {
            open();
            ContentValues values = empresaToValues(empresa);
            return db.insert(TABLE_EMPRESA, null, values);
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
            db.execSQL("DELETE FROM " + TABLE_EMPRESA);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void dropTab() {
        try {
            open();
            db.execSQL("DROP TABLE " + TABLE_EMPRESA);
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

    public Empresa getByName(String descricao) {

        Empresa empresa = new Empresa();

        try {

            open();
            Cursor cursor = db.query(TABLE_EMPRESA, null, "UPPER(" + DESCRICAO + ")=?" , new String[]{ descricao.toUpperCase() },
                    null, null, null);
            if (cursor.moveToFirst()) {
                empresa = cursorToEmpresa(cursor);
            }

            cursor.close();
            return empresa;

        } catch (Exception e){
            LogUtil.error(TAG, e.getMessage(), e);
            return new Empresa();
        } finally {
            close();
        }

    }

    private ContentValues empresaToValues(Empresa empresa) {
        ContentValues values = new ContentValues();
        values.put(DESCRICAO, empresa.getDescricao());
        values.put(NFCE_TOKEN, empresa.getNfceToken());
        values.put(NFCE_CSC, empresa.getNfceCsc());
        values.put(ID_EMPRESA, empresa.getIdEmpresa());
        return values;
    }

    private Empresa cursorToEmpresa(Cursor cursor) {
        Empresa empresa = new Empresa();
        empresa.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        empresa.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DESCRICAO)));
        empresa.setNfceToken(cursor.getString(cursor.getColumnIndexOrThrow(NFCE_TOKEN)));
        empresa.setNfceCsc(cursor.getString(cursor.getColumnIndexOrThrow(NFCE_CSC)));
        empresa.setIdEmpresa(cursor.getInt(cursor.getColumnIndexOrThrow(ID_EMPRESA)));
        return empresa;
    }

}
