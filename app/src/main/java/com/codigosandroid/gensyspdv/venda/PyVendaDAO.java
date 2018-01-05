package com.codigosandroid.gensyspdv.venda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 05/01/2018.
 */

public class PyVendaDAO extends BaseDAO {

    private static final String TAG = PyVendaDAO.class.getSimpleName();

    // PyVenda
    private static final String ID = "_ID";
    private static final String IDENTIFICADOR = "IDENTIFICADOR";
    private static final String TIPO = "TIPO";
    private static final String DATA_EMISSAO = "DATA_EMISSAO";
    private static final String ID_CLIENTE = "ID_CLIENTE";
    private static final String ID_USUARIO = "ID_USUARIO";
    private static final String ID_OPERADOR = "ID_OPERADOR";
    private static final String ID_EMPRESA = "ID_EMPRESA";
    private static final String NUMERO_SERVIDOR = "NUMERO_SERVIDOR";
    private static final String TOTAL = "TOTAL";
    private static final String CAPTURA = "CAPTURA";
    private static final String NOTA_FISCAL = "NOTA_FISCAL";
    // PyDetalhe
    private static final String ID_PYVENDA = "ID_PYVENDA";
    private static final String ID_ESTOQUE = "ID_ESTOQUE";
    private static final String QTDE = "QTDE";
    private static final String VLDESCONTO = "VLDESCONTO";
    private static final String DESCONTO = "DESCONTO";
    private static final String VALOR = "VALOR";
    // PyRecpag
    private static final String ID_TIPO_PAGAMENTO = "ID_TIPO_PAGAMENTO";

    private static final String LAST_INSERT_ID = "SELECT last_insert_rowid() AS _ID;";

    public PyVendaDAO(Context context) {
        super(context);
    }

    public boolean insert(PyVenda pyVenda) {
        try {
            open();
            ContentValues values = pyVendaToValues(pyVenda);
            db.insert(TABLE_PYVENDA, null, values);
            // Pega id inserido
            Cursor cursor = db.rawQuery(LAST_INSERT_ID, null);
            cursor.moveToFirst();
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(ID));
            pyVenda.setId(id);
            // Insere pyDetalhe
            for (int i = 0; i < pyVenda.getPyDetalhes().size(); i++) {
                PyDetalhe pyDetalhe = pyVenda.getPyDetalhes().get(i);
                pyDetalhe.setPyVenda(pyVenda);
                ContentValues valuesDetalhe = pyDetalheToValues(pyDetalhe);
                db.insert(TABLE_PYDETALHE, null, valuesDetalhe);
            }
            // Insere pyRecPag
            for (int i = 0; i < pyVenda.getPyRecPags().size(); i++) {
                PyRecPag pyRecPag = pyVenda.getPyRecPags().get(i);
                pyRecPag.setPyVenda(pyVenda);
                ContentValues valuesRecPag = pyRecPagToValues(pyRecPag);
                db.insert(TABLE_PYRECPAG, null, valuesRecPag);
            }
            return true;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return false;
        } finally {
            close();
        }
    }

    private ContentValues pyVendaToValues(PyVenda pyVenda) {
        ContentValues values = new ContentValues();
        values.put(IDENTIFICADOR, pyVenda.getIdentificador());
        values.put(TIPO, pyVenda.getTipo());
        values.put(DATA_EMISSAO, pyVenda.getDataEmissao());
        values.put(ID_CLIENTE, pyVenda.getCliente().getIdCliente());
        values.put(ID_USUARIO, pyVenda.getUsuario().getId());
        values.put(ID_OPERADOR, pyVenda.getIdOperador());
        values.put(ID_EMPRESA, pyVenda.getEmpresa().getId());
        values.put(NUMERO_SERVIDOR, pyVenda.getNumeroServidor());
        values.put(TOTAL, pyVenda.getTotal());
        values.put(CAPTURA, pyVenda.getCaptura());
        values.put(NOTA_FISCAL, pyVenda.getNotaFiscal());
        return values;
    }

    private ContentValues pyDetalheToValues(PyDetalhe pyDetalhe) {
        ContentValues values = new ContentValues();
        values.put(ID_PYVENDA, pyDetalhe.getPyVenda().getId());
        values.put(ID_ESTOQUE, pyDetalhe.getEstoque().getId());
        values.put(QTDE, pyDetalhe.getQuantidade());
        values.put(VLDESCONTO, pyDetalhe.getVlDesconto());
        values.put(DESCONTO, pyDetalhe.getDesconto());
        values.put(VALOR, pyDetalhe.getValor());
        values.put(TOTAL, pyDetalhe.getTotal());
        return values;
    }

    private ContentValues pyRecPagToValues(PyRecPag pyRecPag) {
        ContentValues values = new ContentValues();
        values.put(ID_PYVENDA, pyRecPag.getPyVenda().getId());
        values.put(ID_TIPO_PAGAMENTO, pyRecPag.getTipoPagamento().getId());
        values.put(VALOR, pyRecPag.getValor());
        return values;
    }

}
