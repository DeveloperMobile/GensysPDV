package com.codigosandroid.gensyspdv.pagamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

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

    private static final String INNER_FORMA_PAGAMENTO = "SELECT FP.*, TP.* FROM FORMA_PAGAMENTO FP " +
            "INNER JOIN TIPO_PAGAMENTO TP ON FP.ID_TIPO_PAGAMENTO = TP._ID";

    private static final String INNER_FORMA_PAGAMENTO_ID = "SELECT FP.*, TP.* FROM FORMA_PAGAMENTO FP " +
            "INNER JOIN TIPO_PAGAMENTO TP ON FP.ID_TIPO_PAGAMENTO = TP._ID WHERE FP._ID=?";

    public FormaPagamentoDAO(Context context) {
        super(context);
    }

    public long insert(FormaPagamento formaPagamento) {
        try {
            long id = 0;
            open();
            ContentValues values = formaPagamentoToValues(formaPagamento);
            return db.insert(TABLE_FORMA_PAGAMENTO, null, values);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return 0;
        } finally {
            close();
        }
    }

    public List<FormaPagamento> getAll() {
        List<FormaPagamento> formaPagamentos = new ArrayList<>();
        try {
            open();
            Cursor cursor = db.rawQuery(INNER_FORMA_PAGAMENTO, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                FormaPagamento formaPagamento = cursorToFormaPagamento(cursor);
                formaPagamentos.add(formaPagamento);
                cursor.moveToNext();
            }
            cursor.close();
            return formaPagamentos;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<FormaPagamento>();
        } finally {
            close();
        }
    }

    public FormaPagamento getById(long id) {
        FormaPagamento formaPagamento = new FormaPagamento();
        try {
            open();
            Cursor cursor = db.rawQuery(INNER_FORMA_PAGAMENTO_ID, new String[]{ String.valueOf(id) });
            if (cursor.moveToFirst()) {
                formaPagamento = cursorToFormaPagamento(cursor);
            }
            cursor.close();
            return formaPagamento;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new FormaPagamento();
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

    private FormaPagamento cursorToFormaPagamento(Cursor cursor) {
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(cursor.getLong(cursor.getColumnIndexOrThrow("FP."+ID)));
        formaPagamento.setTipoPagamento(cursorToTipoPagamento(cursor));
        formaPagamento.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("FP."+VALOR)));
        formaPagamento.setParcela(cursor.getInt(cursor.getColumnIndexOrThrow("FP."+PARCELA)));
        formaPagamento.setNumAutorizacao(cursor.getString(cursor.getColumnIndexOrThrow("FP."+NUM_AUTORIZACAO)));
        return formaPagamento;
    }

    private TipoPagamento cursorToTipoPagamento(Cursor cursor) {
        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setId(cursor.getLong(cursor.getColumnIndexOrThrow("TP."+ID)));
        tipoPagamento.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("TP.DESCRICAO")));
        tipoPagamento.setTipoPagamento(cursor.getString(cursor.getColumnIndexOrThrow("TP.TIPO_PAGAMENTO")));
        tipoPagamento.setIdFormapag(cursor.getInt(cursor.getColumnIndexOrThrow("TP.ID_FORMAPAG")));
        return tipoPagamento;
    }

}
