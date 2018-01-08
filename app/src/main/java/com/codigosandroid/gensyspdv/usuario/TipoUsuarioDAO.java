package com.codigosandroid.gensyspdv.usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

import java.util.List;

/**
 * Created by Tiago on 29/12/2017.
 */

public class TipoUsuarioDAO extends BaseDAO {

    private static final String TAG = TipoUsuarioDAO.class.getSimpleName();

    private static final String ID = "_ID";
    private static final String DESCRICAO = "DESCRICAO";

    private static final String CREATE_TIPO_USUARIO =  "CREATE TABLE IF NOT EXISTS " + TABLE_TIPO_USUARIO + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "DESCRICAO TEXT UNIQUE);";

    public TipoUsuarioDAO(Context context) {
        super(context);
    }

    /** Insere dados na tabela tipo_usuario
     * @param tipoUsuario objeto a ser inserido na tabela tipo_usuario */
    public long insert(TipoUsuario tipoUsuario) {
        long id = 0;
        try {
            open();
            ContentValues values = tipoUsuarioToValues(tipoUsuario);
            db.insert(TABLE_TIPO_USUARIO, null, values);
            Cursor cursor = db.rawQuery("SELECT last_insert_rowid() AS _ID;", null);
            if (cursor.moveToFirst()) {
                id = cursor.getLong(cursor.getColumnIndexOrThrow(ID));
            }
            cursor.close();
            return id;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return id;
        } finally {
            close();
        }
    }

    /** Busca os tipos de usuário pela escricao
     * @param descricao descricao do tipo de usuário (ex.: VENDEDOR, FUNCIONÁRIO, etc.) */
    public TipoUsuario getByName(String descricao) {
        TipoUsuario tipoUsuario = new TipoUsuario();
        try {
            open();
            Cursor cursor = db.query(TABLE_TIPO_USUARIO, null, DESCRICAO + "=?", new String[]{ descricao },
                    null, null, null);
            if (cursor.moveToFirst()) {
                tipoUsuario = cursorToTipoUsuario(cursor);
            }
            cursor.close();
            return tipoUsuario;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new TipoUsuario();
        } finally {
            close();
        }
    }

    /** Deleta todos os dados da tabela tipo_usuario */
    public void deleteTabTipoUsuario() {
        try {
            open();
            db.execSQL("DELETE FROM " + TABLE_TIPO_USUARIO);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    /** Deleta a tabela tipo_usuario */
    public void dropTabTipoUsuario() {
        try {
            open();
            db.execSQL("DROP TABLE " + TABLE_TIPO_USUARIO);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    /** Cria a tabela tipo_usuario */
    public void createTabTipoUsuario() {
        try {
            open();
            db.execSQL(CREATE_TIPO_USUARIO);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    private ContentValues tipoUsuarioToValues(TipoUsuario tipoUsuario) {
        ContentValues values = new ContentValues();
        values.put(DESCRICAO, tipoUsuario.getDescricao());
        return values;
    }

    private TipoUsuario cursorToTipoUsuario(Cursor cursor) {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        tipoUsuario.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DESCRICAO)));
        return tipoUsuario;
    }

}
