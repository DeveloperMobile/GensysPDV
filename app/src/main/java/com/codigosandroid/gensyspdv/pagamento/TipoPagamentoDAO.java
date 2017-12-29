package com.codigosandroid.gensyspdv.pagamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 29/12/2017.
 */

public class TipoPagamentoDAO extends BaseDAO {

    private static final String TAG = TipoPagamento.class.getSimpleName();

    private static final String ID = "_ID";
    private static final String DESCRICAO = "DESCRICAO";
    private static final String TIPO_PAGAMENTO = "TIPO_PAGAMENTO";
    private static final String ID_FORMAPAG = "ID_FORMAPAG";

    private static final String CREATE =  "CREATE TABLE IF NOT EXISTS " + TABLE_TIPO_PAGAMENTO + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "DESCRICAO TEXT, "
            + "TIPO_PAGAMENTO TEXT, "
            + "ID_FORMAPAG INTEGER);";


    public TipoPagamentoDAO(Context context) {
        super(context);
    }

    public long insert(TipoPagamento tipoPagamento) {
        try {
            open();
            ContentValues values = tipoPagamentoToValues(tipoPagamento);
            return db.insert(TABLE_TIPO_PAGAMENTO, null, values);
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
            db.execSQL("DELETE FROM " + TABLE_TIPO_PAGAMENTO);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void dropTab() {
        try {
            open();
            db.execSQL("DROP TABLE " + TABLE_TIPO_PAGAMENTO);
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

    private ContentValues tipoPagamentoToValues(TipoPagamento tipoPagamento) {
        ContentValues values = new ContentValues();
        values.put(DESCRICAO, tipoPagamento.getDescricao());
        values.put(TIPO_PAGAMENTO, tipoPagamento.getTipoPagamento());
        values.put(ID_FORMAPAG, tipoPagamento.getIdFormapag());
        return values;
    }

    private TipoPagamento cursorToTipoPagamento(Cursor cursor) {
        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        tipoPagamento.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DESCRICAO)));
        tipoPagamento.setTipoPagamento(cursor.getString(cursor.getColumnIndexOrThrow(TIPO_PAGAMENTO)));
        tipoPagamento.setIdFormapag(cursor.getInt(cursor.getColumnIndexOrThrow(ID_FORMAPAG)));
        return tipoPagamento;
    }

}
