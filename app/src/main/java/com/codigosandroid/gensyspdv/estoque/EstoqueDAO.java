package com.codigosandroid.gensyspdv.estoque;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.gensyspdv.empresa.Empresa;
import com.codigosandroid.utils.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 28/12/2017.
 */

public class EstoqueDAO extends BaseDAO {

    private static final String TAG = EstoqueDAO.class.getSimpleName();

    // ESTOQUE
    private static final String ID = "_ID";
    private static final String RECNO = "RECNO";
    private static final String ID_EMPRESA = "ID_EMPRESA";
    private static final String CODIGO = "CODIGO";
    private static final String CODIGO_FORNECEDOR = "CODIGO_FORNECEDOR";
    private static final String EMPRESA = "EMPRESA";
    private static final String UNIDADE = "UNIDADE";
    private static final String DESCRICAO = "DESCRICAO";
    private static final String SALDO = "SALDO";
    private static final String RESERVADO = "RESERVADO";
    private static final String PRECO_CUSTO = "PRECO_CUSTO";
    private static final String PRECO_VENDA = "PRECO_VENDA";
    private static final String MINIMO = "MINIMO";
    private static final String PRECO_COMPRA = "PRECO_COMPRA";
    private static final String COBRAR_ICMS = "COBRAR_ICMS";
    private static final String TRIBUTARIA = "TRIBUTARIA";
    private static final String DETALHADA = "DETALHADA";
    private static final String ATUALIZADO = "ATUALIZADO";
    private static final String SUBGRUPO = "SUBGRUPO";
    private static final String GRUPO = "GRUPO";
    private static final String LOCALIZACAO = "LOCALIZACAO";
    private static final String COMISSAO_PRODUTO = "COMISSAO_PRODUTO";
    private static final String OBSERVACAO = "OBSERVACAO";
    private static final String ESTOQUE_EXTRA_1 = "ESTOQUE_EXTRA_1";
    private static final String PRECO_PROMOCAO = "PRECO_PROMOCAO";
    private static final String PRECO_PROMOCAO_REVENDA = "PRECO_PROMOCAO_REVENDA";
    private static final String PRECO_REVENDA = "PRECO_REVENDA";
    private static final String PESAVEL = "PESAVEL";
    private static final String FORNECEDOR = "FORNECEDOR";
    private static final String COBRAR_ICMS_SUBSTITUICAO = "COBRAR_ICMS_SUBSTITUICAO";
    private static final String EMBALAGEM = "EMBALAGEM";
    private static final String INDIC_ARREDONDAMENTO = "INDIC_ARREDONDAMENTO";
    private static final String INDIC_PRODUCAO = "INDIC_PRODUCAO";
    private static final String PROMOCAO_INICIO = "PROMOCAO_INICIO";
    private static final String PROMOCAO_FIM = "PROMOCAO_FIM";
    private static final String PRECO_MINIMO = "PRECO_MINIMO";

    private static final String CREATE =  "CREATE TABLE IF NOT EXISTS " + TABLE_ESTOQUE + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "RECNO INTEGER, "
            + "ID_EMPRESA INTEGER, "
            + "CODIGO TEXT, "
            + "CODIGO_FORNECEDOR TEXT, "
            + "EMPRESA TEXT, "
            + "UNIDADE TEXT, "
            + "DESCRICAO TEXT, "
            + "SALDO REAL, "
            + "RESERVADO REAL, "
            + "PRECO_CUSTO REAL, "
            + "PRECO_VENDA REAL, "
            + "MINIMO REAL, "
            + "PRECO_COMPRA REAL, "
            + "COBRAR_ICMS TEXT, "
            + "TRIBUTARIA TEXT, "
            + "DETALHADA TEXT, "
            + "ATUALIZADO TEXT, "
            + "SUBGRUPO TEXT, "
            + "GRUPO TEXT, "
            + "LOCALIZACAO TEXT, "
            + "COMISSAO_PRODUTO REAL, "
            + "OBSERVACAO TEXT, "
            + "ESTOQUE_EXTRA_1 TEXT, "
            + "PRECO_PROMOCAO REAL, "
            + "PRECO_PROMOCAO_REVENDA REAL, "
            + "PRECO_REVENDA REAL, "
            + "PESAVEL TEXT, "
            + "FORNECEDOR TEXT, "
            + "COBRAR_ICMS_SUBSTITUICAO TEXT, "
            + "EMBALAGEM TEXT, "
            + "INDIC_ARREDONDAMENTO TEXT, "
            + "INDIC_PRODUCAO TEXT, "
            + "PROMOCAO_INICIO TEXT, "
            + "PROMOCAO_FIM TEXT, "
            + "PRECO_MINIMO REAL, "
            + "FOREIGN KEY (ID_EMPRESA) REFERENCES EMPRESA (_ID));";

    public EstoqueDAO(Context context) {
        super(context);
    }

    public long insert(Estoque estoque) {
        try {
            open();
            ContentValues values = estoqueToValues(estoque);
            return db.insert(TABLE_ESTOQUE, null, values);
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
            db.execSQL("DELETE FROM " + TABLE_ESTOQUE);
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
        } finally {
            close();
        }
    }

    public void dropTab() {
        try {
            open();
            db.execSQL("DROP TABLE " + TABLE_ESTOQUE);
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

    public Estoque getByRecno(int recno) {
        Estoque estoque = new Estoque();
        try {
            open();
            Cursor cursor = db.query(TABLE_ESTOQUE, null, RECNO + "=?", new String[]{ String.valueOf(recno) },
                    null, null, null);
            if (cursor.moveToFirst()) {
                estoque = cursorToEstoque(cursor);
            }
            cursor.close();
            return estoque;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new Estoque();
        } finally {
            close();
        }
    }

    public Estoque getByCode(String codigo) {
        Estoque estoque = new Estoque();
        try {
            open();
            Cursor cursor = db.query(TABLE_ESTOQUE, null, CODIGO + "=?", new String[]{ codigo },
                    null, null, null);
            if (cursor.moveToFirst()) {
                estoque = cursorToEstoque(cursor);
            }
            cursor.close();
            return estoque;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new Estoque();
        } finally {
            close();
        }
    }

    public List<Estoque> getAll() {
        List<Estoque> estoques = new ArrayList<>();
        try {
            open();
            Cursor cursor = db.query(TABLE_ESTOQUE, null, null, null,
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Estoque estoque = cursorToEstoque(cursor);
                estoques.add(estoque);
                cursor.moveToNext();
            }
            cursor.close();
            return estoques;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<Estoque>();
        } finally {
            close();
        }
    }

    private ContentValues estoqueToValues(Estoque estoque) {

        ContentValues values = new ContentValues();
        values.put(RECNO, estoque.getRecno());
        values.put(ID_EMPRESA, estoque.getEmp().getId());
        values.put(CODIGO, estoque.getCodigo());
        values.put(CODIGO_FORNECEDOR, estoque.getCodigoFornecedor());
        values.put(EMPRESA, estoque.getEmpresa());
        values.put(UNIDADE, estoque.getUnidade());
        values.put(DESCRICAO, estoque.getDescricao());
        values.put(SALDO, estoque.getSaldo());
        values.put(RESERVADO, estoque.getReservado());
        values.put(PRECO_CUSTO, estoque.getPrecoCusto());
        values.put(PRECO_VENDA, estoque.getPrecoVenda());
        values.put(MINIMO, estoque.getMinimo());
        values.put(PRECO_COMPRA, estoque.getPrecoCompra());
        values.put(COBRAR_ICMS, estoque.getCobrarIcms());
        values.put(TRIBUTARIA, estoque.getTributaria());
        values.put(DETALHADA, estoque.getDetalhada());
        values.put(ATUALIZADO, estoque.getAtualizado());
        values.put(SUBGRUPO, estoque.getSubGrupo());
        values.put(GRUPO, estoque.getGrupo());
        values.put(LOCALIZACAO, estoque.getLocalizacao());
        values.put(COMISSAO_PRODUTO, estoque.getComissaoProduto());
        values.put(OBSERVACAO, estoque.getObservacao());
        values.put(ESTOQUE_EXTRA_1, estoque.getEstoqueExtra1());
        values.put(PRECO_PROMOCAO, estoque.getPrecoPromocao());
        values.put(PRECO_PROMOCAO_REVENDA, estoque.getPrecoPromocaoRevenda());
        values.put(PRECO_REVENDA, estoque.getPrecoRevenda());
        values.put(PESAVEL, estoque.getPesavel());
        values.put(FORNECEDOR, estoque.getFornecedor());
        values.put(COBRAR_ICMS_SUBSTITUICAO, estoque.getCobrarIcmsSubstituicao());
        values.put(EMBALAGEM, estoque.getEmpresa());
        values.put(INDIC_ARREDONDAMENTO, estoque.getIndicArredondamento());
        values.put(INDIC_PRODUCAO, estoque.getIndicProducao());
        values.put(PROMOCAO_INICIO, estoque.getPromocaoInicio());
        values.put(PROMOCAO_FIM, estoque.getPromocaoFim());
        values.put(PRECO_MINIMO, estoque.getPrecoMinimo());
        return values;

    }
    private Estoque cursorToEstoque(Cursor cursor) {

        Estoque estoque = new Estoque();
        estoque.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        estoque.setRecno(cursor.getInt(cursor.getColumnIndexOrThrow(RECNO)));
        estoque.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(CODIGO)));
        estoque.setCodigoFornecedor(cursor.getString(cursor.getColumnIndexOrThrow(CODIGO_FORNECEDOR)));
        estoque.setEmpresa(cursor.getString(cursor.getColumnIndexOrThrow(EMPRESA)));
        estoque.setUnidade(cursor.getString(cursor.getColumnIndexOrThrow(UNIDADE)));
        estoque.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DESCRICAO)));
        estoque.setSaldo(cursor.getDouble(cursor.getColumnIndexOrThrow(SALDO)));
        estoque.setReservado(cursor.getDouble(cursor.getColumnIndexOrThrow(RESERVADO)));
        estoque.setPrecoCusto(cursor.getDouble(cursor.getColumnIndexOrThrow(PRECO_CUSTO)));
        estoque.setPrecoVenda(cursor.getDouble(cursor.getColumnIndexOrThrow(PRECO_VENDA)));
        estoque.setMinimo(cursor.getDouble(cursor.getColumnIndexOrThrow(MINIMO)));
        estoque.setPrecoCompra(cursor.getDouble(cursor.getColumnIndexOrThrow(PRECO_COMPRA)));
        estoque.setCobrarIcms(cursor.getString(cursor.getColumnIndexOrThrow(COBRAR_ICMS)));
        estoque.setTributaria(cursor.getString(cursor.getColumnIndexOrThrow(TRIBUTARIA)));
        estoque.setDetalhada(cursor.getString(cursor.getColumnIndexOrThrow(DETALHADA)));
        estoque.setAtualizado(cursor.getString(cursor.getColumnIndexOrThrow(ATUALIZADO)));
        estoque.setSubGrupo(cursor.getString(cursor.getColumnIndexOrThrow(SUBGRUPO)));
        estoque.setGrupo(cursor.getString(cursor.getColumnIndexOrThrow(GRUPO)));
        estoque.setLocalizacao(cursor.getString(cursor.getColumnIndexOrThrow(LOCALIZACAO)));
        estoque.setComissaoProduto(cursor.getDouble(cursor.getColumnIndexOrThrow(COMISSAO_PRODUTO)));
        estoque.setObservacao(cursor.getString(cursor.getColumnIndexOrThrow(OBSERVACAO)));
        estoque.setEstoqueExtra1(cursor.getString(cursor.getColumnIndexOrThrow(ESTOQUE_EXTRA_1)));
        estoque.setPrecoPromocao(cursor.getDouble(cursor.getColumnIndexOrThrow(PRECO_PROMOCAO)));
        estoque.setPrecoPromocaoRevenda(cursor.getDouble(cursor.getColumnIndexOrThrow(PRECO_PROMOCAO_REVENDA)));
        estoque.setPrecoRevenda(cursor.getDouble(cursor.getColumnIndexOrThrow(PRECO_REVENDA)));
        estoque.setPesavel(cursor.getString(cursor.getColumnIndexOrThrow(PESAVEL)));
        estoque.setFornecedor(cursor.getString(cursor.getColumnIndexOrThrow(FORNECEDOR)));
        estoque.setCobrarIcmsSubstituicao(cursor.getString(cursor.getColumnIndexOrThrow(COBRAR_ICMS_SUBSTITUICAO)));
        estoque.setEmbalagem(cursor.getString(cursor.getColumnIndexOrThrow(EMBALAGEM)));
        estoque.setIndicArredondamento(cursor.getString(cursor.getColumnIndexOrThrow(INDIC_ARREDONDAMENTO)));
        estoque.setIndicProducao(cursor.getString(cursor.getColumnIndexOrThrow(INDIC_PRODUCAO)));
        estoque.setPromocaoInicio(cursor.getString(cursor.getColumnIndexOrThrow(PROMOCAO_INICIO)));
        estoque.setPromocaoFim(cursor.getString(cursor.getColumnIndexOrThrow(PROMOCAO_FIM)));
        estoque.setPrecoMinimo(cursor.getDouble(cursor.getColumnIndexOrThrow(PRECO_MINIMO)));
        return estoque;

    }

}
