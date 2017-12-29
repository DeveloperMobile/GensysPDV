package com.codigosandroid.gensyspdv.estoque;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 29/12/2017.
 */

public class EstoquePrecoDAO extends BaseDAO {

    private static final String TAG = EstoquePreco.class.getSimpleName();

    private static final String ID = "_ID";
    private static final String TABELA_CODIGO = "TABELA_CODIGO";
    private static final String ID_EMPRESA = "ID_EMPRESA";
    private static final String CODIGO = "CODIGO";
    private static final String DESCRICAO = "DESCRICAO";
    private static final String PRECO = "PRECO";
    private static final String EMPRESA = "EMPRESA";
    private static final String MENOR_PRECO = "MENOR_PRECO";
    private static final String AJUSTA_PERCENTUAL = "AJUSTE_PERCENTUAL";
    private static final String MARGEM_LUCRO = "MARGEM_LUCRO";
    private static final String ATUALIZADO = "ATUALIZADO";

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_ESTOQUE_PRECO + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "TABELA_CODIGO INTEGER, "
            + "ID_EMPRESA INTEGER, "
            + "CODIGO TEXT, "
            + "DESCRICAO TEXT, "
            + "PRECO REAL, "
            + "EMPRESA TEXT, "
            + "MENOR_PRECO REAL, "
            + "AJUSTE_PERCENTUAL REAL, "
            + "MARGEM_LUCRO REAL, "
            + "ATUALIZADO TEXT, "
            + "FOREIGN KEY (ID_EMPRESA) REFERENCES EMPRESA (_ID));";

    public EstoquePrecoDAO(Context context) {
        super(context);
    }

    public long insert(EstoquePreco estoquePreco) {
        try {
            open();
            ContentValues values = estoquePrecoToValues(estoquePreco);
            return db.insert(TABLE_ESTOQUE_PRECO, null, values);
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
            db.execSQL("DELETE FROM " + TABLE_ESTOQUE_PRECO);
        } catch (Exception e){
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void dropTab() {
        try {
            open();
            db.execSQL("DROP TABLE " + TABLE_ESTOQUE_PRECO);
        } catch (Exception e){
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void createTab() {
        try {
            open();
            db.execSQL(CREATE);
        } catch (Exception e){
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    private ContentValues estoquePrecoToValues(EstoquePreco estoquePreco) {
        ContentValues values = new ContentValues();
        values.put(TABELA_CODIGO, estoquePreco.getTabelaCodigo());
        values.put(ID_EMPRESA, estoquePreco.getEmp().getId());
        values.put(CODIGO, estoquePreco.getCodigo());
        values.put(DESCRICAO, estoquePreco.getDescricao());
        values.put(PRECO, estoquePreco.getPreco());
        values.put(EMPRESA, estoquePreco.getEmpresa());
        values.put(MENOR_PRECO, estoquePreco.getMenorPreco());
        values.put(AJUSTA_PERCENTUAL, estoquePreco.getTabelaCodigo());
        values.put(MARGEM_LUCRO, estoquePreco.getMargemLucro());
        values.put(ATUALIZADO, estoquePreco.getAtualizado());
        return values;
    }

    private EstoquePreco cursorToEstoquePreco(Cursor cursor) {
        EstoquePreco estoquePreco = new EstoquePreco();
        estoquePreco.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        estoquePreco.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(CODIGO)));
        estoquePreco.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DESCRICAO)));
        estoquePreco.setPreco(cursor.getDouble(cursor.getColumnIndexOrThrow(PRECO)));
        estoquePreco.setEmpresa(cursor.getString(cursor.getColumnIndexOrThrow(EMPRESA)));
        estoquePreco.setMenorPreco(cursor.getDouble(cursor.getColumnIndexOrThrow(MENOR_PRECO)));
        estoquePreco.setAjustePercentual(cursor.getDouble(cursor.getColumnIndexOrThrow(AJUSTA_PERCENTUAL)));
        estoquePreco.setMargemLucro(cursor.getDouble(cursor.getColumnIndexOrThrow(MARGEM_LUCRO)));
        estoquePreco.setAtualizado(cursor.getString(cursor.getColumnIndexOrThrow(ATUALIZADO)));
        return estoquePreco;
    }


}
