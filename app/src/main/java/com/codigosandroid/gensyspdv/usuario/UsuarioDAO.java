package com.codigosandroid.gensyspdv.usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 27/12/2017.
 */

public class UsuarioDAO extends BaseDAO {

    private static final String TAG = UsuarioDAO.class.getSimpleName();

    // TIPO_USUARIO
    private static final String DESCRICAO = "DESCRICAO";
    // USUARIO
    private static final String ID = "_ID";
    private static final String ID_TIPO_USUARIO = "ID_TIPO_USUARIO";
    private static final String NOME = "NOME";
    private static final String APELIDO = "APELIDO";
    private static final String SENHA = "SENHA";
    private static final String EMAIL = "EMAIL";
    private static final String TIPO = "TIPO";
    private static final String ID_VENDEDOR = "ID_VENDEDOR";

    public UsuarioDAO(Context context) {
        super(context);
    }

    public long insert(Usuario usuario) {
        try {
            open();
            ContentValues values = usuarioToValues(usuario);
            return db.insert(TABLE_USUARIO, null, values);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return 0;
        } finally {
            close();
        }
    }

    public long insert(TipoUsuario tipoUsuario) {long id = 0;
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

    public List<Usuario> getAll() {

        List<Usuario> usuarioList = new ArrayList<>();

        try {

            open();
            Cursor cursor = db.query(TABLE_USUARIO, null, null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Usuario usuario = cursorToUsuario(cursor);
                usuarioList.add(usuario);
                cursor.moveToNext();
            }

            cursor.close();
            return usuarioList;

        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<Usuario>();
        } finally {
            close();
        }
    }

    public void deleteTabUsuario() {
        try {
            open();
            db.execSQL("DELETE FROM " + TABLE_USUARIO);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

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

    private ContentValues usuarioToValues(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(ID_TIPO_USUARIO, usuario.getTipoUsuario().getId());
        values.put(NOME, usuario.getNome());
        values.put(APELIDO, usuario.getApelido());
        values.put(SENHA, usuario.getSenha());
        values.put(EMAIL, usuario.getEmail());
        values.put(TIPO, usuario.getTipo());
        values.put(ID_VENDEDOR, usuario.getIdVendedor());
        return values;
    }

    private ContentValues tipoUsuarioToValues(TipoUsuario tipoUsuario) {
        ContentValues values = new ContentValues();
        values.put(DESCRICAO, tipoUsuario.getDescricao());
        return values;
    }

    private Usuario cursorToUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        usuario.setNome(cursor.getString(cursor.getColumnIndexOrThrow(NOME)));
        usuario.setApelido(cursor.getString(cursor.getColumnIndexOrThrow(APELIDO)));
        usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow(SENHA)));
        usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(EMAIL)));
        usuario.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(TIPO)));
        usuario.setIdVendedor(cursor.getInt(cursor.getColumnIndexOrThrow(ID_VENDEDOR)));
        return usuario;
    }

    private TipoUsuario cursorToTipoUsuario(Cursor cursor) {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        tipoUsuario.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DESCRICAO)));
        return tipoUsuario;
    }

}
