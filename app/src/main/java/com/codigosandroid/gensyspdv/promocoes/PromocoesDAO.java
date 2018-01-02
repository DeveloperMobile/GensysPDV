package com.codigosandroid.gensyspdv.promocoes;

import android.content.ContentValues;
import android.content.Context;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 02/01/2018.
 */

public class PromocoesDAO extends BaseDAO {

    private static final String TAG = PromocoesDAO.class.getSimpleName();

    private static final String ID = "_ID";
    private static final String ID_EMPRESA = "ID_EMPRESA";
    private static final String ID_ESTOQUE = "ID_ESTOQUE";
    private static final String DATA_INICIO = "DATA_INICIO";
    private static final String DATA_FIM = "DATA_FIM";
    private static final String NOME_PROMOCAO = "NOME_PROMOCAO";
    private static final String OPERADOR = "OPERADOR";
    private static final String DATA_CRIACAO = "DATA_CRIACAO";
    private static final String OPERADOR_ALTERACAO = "OPERADOR_ALTERACAO";
    private static final String DATA_ALTERACAO = "DATA_ALTERACAO";
    private static final String VALOR = "VALOR";
    private static final String HORA_INICIO = "HORA_INICIO";
    private static final String HORA_FIM = "HORA_FIM";

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROMOCOES + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "ID_EMPRESA INTEGER, "
            + "ID_ESTOQUE INTEGER,  "
            + "DATA_INICIO TEXT, "
            + "DATA_FIM TEXT, "
            + "NOME_PROMOCAO TEXT, "
            + "OPERADOR TEXT, "
            + "DATA_CRIACAO TEXT, "
            + "OPERADOR_ALTERACAO TEXT, "
            + "DATA_ALTERACAO TEXT, "
            + "VALOR REAL, "
            + "HORA_INICIO TEXT, "
            + "HORA_FIM TEXT, "
            + "FOREIGN KEY (ID_EMPRESA) REFERENCES EMPRESA (_ID), "
            + "FOREIGN KEY (ID_ESTOQUE) REFERENCES ESTOQUE (_ID));";

    public PromocoesDAO(Context context) {
        super(context);
    }

    public long insert(Promocoes promocoes) {
        try {
            open();
            ContentValues values = promocoesToValues(promocoes);
            return db.insert(TABLE_PROMOCOES, null, values);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return  0;
        } finally {
            close();
        }
    }

    public void deleteTab() {
        try {
            open();
            db.execSQL("DELETE FROM " + TABLE_PROMOCOES);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void dropTab() {
        try {
            open();
            db.execSQL("DROP TABLE " + TABLE_PROMOCOES);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void createteTab() {
        try {
            open();
            db.execSQL(CREATE);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    private ContentValues promocoesToValues(Promocoes promocoes) {
        ContentValues values = new ContentValues();
        values.put(ID_EMPRESA, promocoes.getEmpresa().getId());
        values.put(ID_ESTOQUE, promocoes.getEstoque().getId());
        values.put(DATA_INICIO, promocoes.getDataInicio());
        values.put(DATA_FIM, promocoes.getDataFim());
        values.put(NOME_PROMOCAO, promocoes.getNomePromocao());
        values.put(OPERADOR, promocoes.getOperador());
        values.put(DATA_CRIACAO, promocoes.getDataCriacao());
        values.put(OPERADOR_ALTERACAO, promocoes.getOperadorAlteracao());
        values.put(DATA_ALTERACAO, promocoes.getDataAlteracao());
        values.put(VALOR, promocoes.getValor());
        values.put(HORA_INICIO, promocoes.getHoraFim());
        values.put(HORA_FIM, promocoes.getHoraFim());
        return values;
    }

}
