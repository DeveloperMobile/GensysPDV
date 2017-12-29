package com.codigosandroid.gensyspdv.precohora;

import android.content.ContentValues;
import android.content.Context;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 29/12/2017.
 */

public class PrecoHoraDAO extends BaseDAO {

    private static final String TAG = PrecoHoraDAO.class.getSimpleName();

    private static final String ID = "_ID";
    private static final String ID_ESTOQUE = "ID_ESTOQUE";
    private static final String DIA_SEMANA = "DIA_SEMANA";
    private static final String HORA_INICIO = "HORA_INICIO";
    private static final String HORA_FIM = "HORA_FIM";
    private static final String VALOR = "VALOR";

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRECO_HORA + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "ID_ESTOQUE INTEGER, "
            + "DIA_SEMANA TEXT, "
            + "HORA_INICIO TEXT, "
            + "HORA_FIM TEXT, "
            + "VALOR REAL, "
            + "FOREIGN KEY (ID_ESTOQUE) REFERENCES ESTOQUE (_ID));";

    public PrecoHoraDAO(Context context) {
        super(context);
    }

    public long insert(PrecoHora precoHora) {
        try {
            open();
            ContentValues values = precoHoraToValues(precoHora);
            return db.insert(TABLE_PRECO_HORA, null, values);
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
            db.execSQL("DELETE FROM " + TABLE_PRECO_HORA);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void dropTab() {
        try {
            open();
            db.execSQL("DROP TABLE " + TABLE_PRECO_HORA);
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

    private ContentValues precoHoraToValues(PrecoHora precoHora) {
        ContentValues values = new ContentValues();
        values.put(ID_ESTOQUE, precoHora.getEstoque().getId());
        values.put(DIA_SEMANA, precoHora.getDiaSemana());
        values.put(HORA_INICIO, precoHora.getHoraInicio());
        values.put(HORA_FIM, precoHora.getHoraFim());
        values.put(VALOR, precoHora.getValor());
        return values;
    }

}
