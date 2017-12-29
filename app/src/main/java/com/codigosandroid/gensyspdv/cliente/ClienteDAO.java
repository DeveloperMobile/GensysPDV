package com.codigosandroid.gensyspdv.cliente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 28/12/2017.
 */

public class ClienteDAO extends BaseDAO {

    private static final String TAG = ClienteDAO.class.getSimpleName();

    private static final String ID = "_ID";
    private static final String NOME = "NOME";
    private static final String FANTASIA = "FANTASIA";
    private static final String CPF = "CPF";
    private static final String CGC = "CGC";
    private static final String PRECO = "PRECO";
    private static final String ID_CLIENTE = "ID_CLIENTE";
    private static final String TABELA_PRECO = "TABELA_PRECO";

    private static final String CREATE_CLIENTE = "CREATE TABLE IF NOT EXISTS " + TABLE_CLIENTE + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "NOME TEXT, "
            + "FANTASIA TEXT, "
            + "CPF TEXT, "
            + "CGC TEXT, "
            + "PRECO TEXT, "
            + "ID_CLIENTE INTEGER, "
            + "TABELA_PRECO INTEGER);";

    public ClienteDAO(Context context) {
        super(context);
    }

    public long insert(Cliente cliente) {
        try {
            open();
            ContentValues values = valuesToCliente(cliente);
            return db.insert(TABLE_CLIENTE, null, values);
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
            db.execSQL("DELETE FROM "+ TABLE_CLIENTE);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void dropTab() {
        try {
            open();
            db.execSQL("DROP TABLE "+ TABLE_CLIENTE);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void createTab() {
        try {
            open();
            db.execSQL(CREATE_CLIENTE);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    private ContentValues valuesToCliente(Cliente cliente) {
        ContentValues values = new ContentValues();
        values.put(NOME, cliente.getNome());
        values.put(FANTASIA, cliente.getFantasia());
        values.put(CPF, cliente.getCpf());
        values.put(CGC, cliente.getCgc());
        values.put(PRECO, cliente.getPreco());
        values.put(ID_CLIENTE, cliente.getIdCliente());
        values.put(TABELA_PRECO, cliente.getTabelaPreco());
        return values;
    }

    private Cliente cursorToCliente(Cursor cursor) {
        Cliente cliente = new Cliente();
        cliente.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        cliente.setNome(cursor.getString(cursor.getColumnIndexOrThrow(NOME)));
        cliente.setFantasia(cursor.getString(cursor.getColumnIndexOrThrow(FANTASIA)));
        cliente.setCpf(cursor.getString(cursor.getColumnIndexOrThrow(CPF)));
        cliente.setCgc(cursor.getString(cursor.getColumnIndexOrThrow(CGC)));
        cliente.setPreco(cursor.getString(cursor.getColumnIndexOrThrow(PRECO)));
        cliente.setIdCliente(cursor.getInt(cursor.getColumnIndexOrThrow(ID_CLIENTE)));
        cliente.setTabelaPreco(cursor.getInt(cursor.getColumnIndexOrThrow(TABELA_PRECO)));
        return cliente;
    }

}

