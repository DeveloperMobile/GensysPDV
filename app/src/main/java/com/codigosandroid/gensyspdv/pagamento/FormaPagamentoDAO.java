package com.codigosandroid.gensyspdv.pagamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 08/01/2018.
 */

public class FormaPagamentoDAO extends BaseDAO {

    private static final String TAG = FormaPagamentoDAO.class.getSimpleName();

    private static final String ID = "_ID";
    private static final String ID_TIPO_PAGAMENTO =  "ID_TIPO_PAGAMENTO";
    private static final String VALOR = "VALOR";
    private static final String PARCELA = "PARCELA";
    private static final String NUM_AUTORIZACAO = "NUM_AUTORIZACAO";

    public FormaPagamentoDAO(Context context) {
        super(context);
    }

    public long insert(FormaPagamento formaPagamento) {
        try {
            long id = 0;
            open();
            ContentValues values = formaPagamentoToValues(formaPagamento);
            db.insert(TABLE_FORMA_PAGAMENTO, null, values);
            Cursor cursor = db.rawQuery("SELECT last_insert_rowid() AS _ID;", null);
            if (cursor.moveToFirst()) {
                id = cursor.getLong(cursor.getColumnIndexOrThrow(ID));
            }
            cursor.close();
            return id;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return 0;
        } finally {
            close();
        }
    }

    private ContentValues formaPagamentoToValues(FormaPagamento formaPagamento) {
        ContentValues values = new ContentValues();
        values.put(ID_TIPO_PAGAMENTO, formaPagamento.getTipoPagamento().getId());
        values.put(VALOR, formaPagamento.getValor());
        values.put(PARCELA, formaPagamento.getParcela());
        values.put(NUM_AUTORIZACAO, formaPagamento.getNumAutorizacao());
        return values;
    }

}
