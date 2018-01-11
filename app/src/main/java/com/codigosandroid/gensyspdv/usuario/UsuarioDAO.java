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

    // USUARIO
    private static final String ID = "_ID";
    private static final String ID_TIPO_USUARIO = "ID_TIPO_USUARIO";
    private static final String NOME = "NOME";
    private static final String APELIDO = "APELIDO";
    private static final String SENHA = "SENHA";
    private static final String EMAIL = "EMAIL";
    private static final String TIPO = "TIPO";
    private static final String ID_VENDEDOR = "ID_VENDEDOR";

    private static final String INNER_USUARIO = "SELECT U.*, TU.* FROM " + TABLE_USUARIO + " U " +
            "INNER JOIN " + TABLE_TIPO_USUARIO + " TU ON U.ID_TIPO_USUARIO = TU._ID;";

    private static final String CREATE_USUARIO =  "CREATE TABLE IF NOT EXISTS " + TABLE_USUARIO + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "ID_TIPO_USUARIO INTEGER, "
            + "NOME TEXT, "
            + "APELIDO TEXT, "
            + "SENHA TEXT, "
            + "EMAIL TEXT, "
            + "TIPO TEXT, "
            + "ID_VENDEDOR INTEGER, "
            + "FOREIGN KEY (ID_TIPO_USUARIO) REFERENCES TIPO_USUARIO (_ID));";

    public UsuarioDAO(Context context) {
        super(context);
    }

    /** Insere dados na tabela usuario
     * @param usuario objeto a ser inserido na tabela usuario */
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

    public Usuario getByName(String aplido) {
        Usuario usuario = new Usuario();
        try {
            open();
            Cursor cursor = db.query(TABLE_USUARIO, null, APELIDO + "=?", new String[]{ aplido },
                    null, null, null);
            if (cursor.moveToFirst()) {
                usuario = cursorToUsuario(cursor);
            }
            cursor.close();
            return usuario;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new Usuario();
        } finally {
            close();
        }
    }

    /** Busca dados na tabela usuario */
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

    /** Busca dados na tabela usuario */
    public List<Usuario> getAllInner() {

        List<Usuario> usuarioList = new ArrayList<>();

        try {

            open();
            Cursor cursor = db.rawQuery(INNER_USUARIO, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Usuario usuario = cursorToInnerUsuario(cursor);
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

    /** Deleta todos dados na tabela usuario */
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

    /** Deleta a tabela usuario */
    public void dropTabUsuario() {
        try {
            open();
            db.execSQL("DROP TABLE " + TABLE_USUARIO);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    /** Cria a tabela usuario */
    public void createTabUsuario() {
        try {
            open();
            db.execSQL(CREATE_USUARIO);
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

    public Usuario cursorToInnerUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getLong(cursor.getColumnIndexOrThrow("U." + ID)));
        usuario.setTipoUsuario(cursorToTipoUsuario(cursor));
        usuario.setNome(cursor.getString(cursor.getColumnIndexOrThrow("U." + NOME)));
        usuario.setApelido(cursor.getString(cursor.getColumnIndexOrThrow("U." + APELIDO)));
        usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("U." + SENHA)));
        usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("U." + EMAIL)));
        usuario.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("U." + TIPO)));
        return usuario;
    }

    private TipoUsuario cursorToTipoUsuario(Cursor cursor) {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(cursor.getLong(cursor.getColumnIndexOrThrow("TU." + ID)));
        tipoUsuario.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("TU.DESCRICAO")));
        return tipoUsuario;
    }

}
