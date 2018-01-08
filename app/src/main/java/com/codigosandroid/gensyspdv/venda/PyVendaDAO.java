package com.codigosandroid.gensyspdv.venda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codigosandroid.gensyspdv.cliente.Cliente;
import com.codigosandroid.gensyspdv.cliente.ClienteDAO;
import com.codigosandroid.gensyspdv.db.BaseDAO;
import com.codigosandroid.gensyspdv.empresa.Empresa;
import com.codigosandroid.gensyspdv.estoque.Estoque;
import com.codigosandroid.gensyspdv.pagamento.FormaPagamento;
import com.codigosandroid.gensyspdv.pagamento.TipoPagamento;
import com.codigosandroid.gensyspdv.usuario.Usuario;
import com.codigosandroid.utils.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

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
    private static final String ID_FORMA_PAGAMENTO = "ID_FORMA_PAGAMENTO";

    private static final String LAST_INSERT_ID = "SELECT last_insert_rowid() AS _ID;";

    /* Inner pyvenda */
    private static final String INNER_PYVENDA = "SELECT PV.*, U.*, C.*, E.* FROM PYVENDA PV\n" +
            "INNER JOIN USUARIO U ON PV.ID_USUARIO - U._ID " +
            "INNER JOIN CLIENTE C ON PV.ID_CLIENTE = C._ID " +
            "INNER JOIN EMPRESA E ON PV.ID_EMPRESA = E._ID; ";

    /* Inner pydetalhe */
    private static final String INNER_PYDETALHE = "SELECT PD.*, E.* FROM PYDETALHE PD " +
            "INNER JOIN ESTOQUE E ON PD.ID_ESTOQUE = E._ID WHERE PD.ID_PYVENDA=?;";

    /* Inner pyrecpag */
    private static final String INNER_PYRECPAG = "SELECT PR.*, FP.* FROM PYRECPAG PR\n" +
            "INNER JOIN FORMA_PAGAMENTO FP ON PR.ID_FORMA_PAGAMENTO = FP._ID WHERE PR.ID_PYVENDA=?;";

    private static final String GET_PYDETALHE = "SELECT * FROM PYDETALHE WHERE ID_PYVENDA=?";
    private static final String GET_PYRECPAG = "SELECT * FROM PYRECPAG WHERE ID_PYVENDA=?";

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

    public List<PyVenda> getAllInner() {
        List<PyVenda> pyVendas = new ArrayList<>();
        List<PyDetalhe> detalhes = new ArrayList<>();
        List<PyRecPag> recPags = new ArrayList<>();
        try {
            open();
            Cursor cursor = db.rawQuery(INNER_PYVENDA, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                PyVenda pyVenda = cursorToPyvenda(cursor);
                Cursor cursor2 = db.rawQuery(INNER_PYDETALHE, new String[]{ String.valueOf(pyVenda.getId()) });
                cursor2.moveToFirst();
                while (!cursor2.isAfterLast()) {
                    PyDetalhe pyDetalhe = cursorToPyDetalheInner(cursor);
                    detalhes.add(pyDetalhe);
                    cursor2.moveToNext();
                }
                pyVenda.setPyDetalhes(detalhes);
                Cursor cursor3 = db.rawQuery(INNER_PYRECPAG, new String[]{ String.valueOf(pyVenda.getId()) });
                cursor3.moveToFirst();
                while (!cursor3.isAfterLast()) {
                    PyRecPag pyRecPag = cursorToPyRecPagInner(cursor3);
                    recPags.add(pyRecPag);
                    cursor3.moveToNext();
                }

                pyVenda.setPyRecPags(recPags);
                cursor.moveToNext();
            }
            cursor.close();
            return pyVendas;
        } catch (Exception e) {
            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<PyVenda>();
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
        values.put(ID_FORMA_PAGAMENTO, pyRecPag.getFormaPagamento().getId());
        values.put(VALOR, pyRecPag.getValor());
        return values;
    }

    private PyVenda cursorToPyvenda(Cursor cursor) {
        PyVenda pyVenda = new PyVenda();
        pyVenda.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        pyVenda.setIdentificador(cursor.getString(cursor.getColumnIndexOrThrow(IDENTIFICADOR)));
        pyVenda.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(TIPO)));
        pyVenda.setDataEmissao(cursor.getString(cursor.getColumnIndexOrThrow(DATA_EMISSAO)));
        pyVenda.setCliente(cursorToClienteInner(cursor));
        pyVenda.setUsuario(cursorToInnerUsuario(cursor));
        pyVenda.setIdOperador(cursor.getLong(cursor.getColumnIndexOrThrow(ID_OPERADOR)));
        pyVenda.setEmpresa(cursorToEmpresaInner(cursor));
        pyVenda.setNumeroServidor(cursor.getString(cursor.getColumnIndexOrThrow(NUMERO_SERVIDOR)));
        pyVenda.setTotal(cursor.getDouble(cursor.getColumnIndexOrThrow(TOTAL)));
        pyVenda.setCaptura(cursor.getString(cursor.getColumnIndexOrThrow(CAPTURA)));
        pyVenda.setNotaFiscal(cursor.getString(cursor.getColumnIndexOrThrow(NOTA_FISCAL)));
        return pyVenda;
    }

    public Cliente cursorToClienteInner(Cursor cursor) {
        Cliente cliente = new Cliente();
        cliente.setId(cursor.getLong(cursor.getColumnIndexOrThrow("C."+ID)));
        cliente.setNome(cursor.getString(cursor.getColumnIndexOrThrow("C.NOME")));
        cliente.setFantasia(cursor.getString(cursor.getColumnIndexOrThrow("C.FANTASIA")));
        cliente.setCpf(cursor.getString(cursor.getColumnIndexOrThrow("C.CPF")));
        cliente.setCgc(cursor.getString(cursor.getColumnIndexOrThrow("C.CGC")));
        cliente.setPreco(cursor.getString(cursor.getColumnIndexOrThrow("C.PRECO")));
        cliente.setIdCliente(cursor.getInt(cursor.getColumnIndexOrThrow("C.ID_CLIENTE")));
        cliente.setTabelaPreco(cursor.getInt(cursor.getColumnIndexOrThrow("C.TABELA_PRECO")));
        return cliente;
    }

    public Usuario cursorToInnerUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getLong(cursor.getColumnIndexOrThrow("U." + ID)));
        usuario.setNome(cursor.getString(cursor.getColumnIndexOrThrow("U.NOME" )));
        usuario.setApelido(cursor.getString(cursor.getColumnIndexOrThrow("U.APELIDO")));
        usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("U.SENHA")));
        usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("U.EMAIL")));
        usuario.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("U." + TIPO)));
        return usuario;
    }

    private Empresa cursorToEmpresaInner(Cursor cursor) {
        Empresa empresa = new Empresa();
        empresa.setId(cursor.getLong(cursor.getColumnIndexOrThrow("E."+ID)));
        empresa.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("E.DESCRICAO")));
        empresa.setNfceToken(cursor.getString(cursor.getColumnIndexOrThrow("E.NFCE_TOKEN")));
        empresa.setNfceCsc(cursor.getString(cursor.getColumnIndexOrThrow("E.NFCE_CSC")));
        empresa.setIdEmpresa(cursor.getInt(cursor.getColumnIndexOrThrow("E."+ID_EMPRESA)));
        return empresa;
    }

    private PyDetalhe cursorToPyDetalheInner(Cursor cursor) {
        PyDetalhe pyDetalhe = new PyDetalhe();
        pyDetalhe.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        pyDetalhe.setEstoque(cursorToEstoqueInner(cursor));
        pyDetalhe.setQuantidade(cursor.getInt(cursor.getColumnIndexOrThrow(QTDE)));
        pyDetalhe.setVlDesconto(cursor.getDouble(cursor.getColumnIndexOrThrow(VLDESCONTO)));
        pyDetalhe.setDesconto(cursor.getDouble(cursor.getColumnIndexOrThrow(DESCONTO)));
        pyDetalhe.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(VALOR)));
        pyDetalhe.setTotal(cursor.getDouble(cursor.getColumnIndexOrThrow(TOTAL)));
        return pyDetalhe;
    }

    private PyRecPag cursorToPyRecPagInner(Cursor cursor) {
        PyRecPag pyRecPag = new PyRecPag();
        pyRecPag.setId(cursor.getLong(cursor.getColumnIndexOrThrow("PR."+ID)));
        pyRecPag.setFormaPagamento(cursorFormaPagamentoInner(cursor));
        pyRecPag.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(VALOR)));
        return pyRecPag;
    }

    private FormaPagamento cursorFormaPagamentoInner(Cursor cursor) {
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(cursor.getLong(cursor.getColumnIndexOrThrow("FP."+ID)));
        formaPagamento.setTipoPagamento(cursorToTipoPagamento(cursor));
        formaPagamento.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("FP."+VALOR)));
        formaPagamento.setParcela(cursor.getInt(cursor.getColumnIndexOrThrow("FP.PARCELA")));
        formaPagamento.setNumAutorizacao(cursor.getString(cursor.getColumnIndexOrThrow("FP.NUM_AUTORIZACAO")));
        return formaPagamento;
    }

    private TipoPagamento cursorToTipoPagamento(Cursor cursor) {
        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID)));
        tipoPagamento.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("DESCRICAO")));
        tipoPagamento.setTipoPagamento(cursor.getString(cursor.getColumnIndexOrThrow("TIPO_PAGAMENTO")));
        tipoPagamento.setIdFormapag(cursor.getInt(cursor.getColumnIndexOrThrow("ID_FORMAPAG")));
        return tipoPagamento;
    }

    private Estoque cursorToEstoqueInner(Cursor cursor) {

        Estoque estoque = new Estoque();
        estoque.setId(cursor.getLong(cursor.getColumnIndexOrThrow("E."+ID)));
        estoque.setRecno(cursor.getInt(cursor.getColumnIndexOrThrow("E.RECNO")));
        estoque.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow("E.CODIGO")));
        estoque.setCodigoFornecedor(cursor.getString(cursor.getColumnIndexOrThrow("E.CODIGO_FORNECEDOR")));
        estoque.setEmpresa(cursor.getString(cursor.getColumnIndexOrThrow("E.EMPRESA")));
        estoque.setUnidade(cursor.getString(cursor.getColumnIndexOrThrow("E.UNIDADE")));
        estoque.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("E.DESCRICAO")));
        estoque.setSaldo(cursor.getDouble(cursor.getColumnIndexOrThrow("E.SALDO")));
        estoque.setReservado(cursor.getDouble(cursor.getColumnIndexOrThrow("E.RESERVADO")));
        estoque.setPrecoCusto(cursor.getDouble(cursor.getColumnIndexOrThrow("E.PRECO_CUSTO")));
        estoque.setPrecoVenda(cursor.getDouble(cursor.getColumnIndexOrThrow("E.PRECO_VENDA")));
        estoque.setMinimo(cursor.getDouble(cursor.getColumnIndexOrThrow("E.MINIMO")));
        estoque.setPrecoCompra(cursor.getDouble(cursor.getColumnIndexOrThrow("E.PRECO_COMPRA")));
        estoque.setCobrarIcms(cursor.getString(cursor.getColumnIndexOrThrow("E.COBRAR_ICMS")));
        estoque.setTributaria(cursor.getString(cursor.getColumnIndexOrThrow("E.TRIBUTARIA")));
        estoque.setDetalhada(cursor.getString(cursor.getColumnIndexOrThrow("E.DETALHADA")));
        estoque.setAtualizado(cursor.getString(cursor.getColumnIndexOrThrow("E.ATUALIZADO")));
        estoque.setSubGrupo(cursor.getString(cursor.getColumnIndexOrThrow("E.SUBGRUPO")));
        estoque.setGrupo(cursor.getString(cursor.getColumnIndexOrThrow("E.GRUPO")));
        estoque.setLocalizacao(cursor.getString(cursor.getColumnIndexOrThrow("E.LOCALIZACAO")));
        estoque.setComissaoProduto(cursor.getDouble(cursor.getColumnIndexOrThrow("E.COMISSAO_PRODUTO")));
        estoque.setObservacao(cursor.getString(cursor.getColumnIndexOrThrow("E.OBSERVACAO")));
        estoque.setEstoqueExtra1(cursor.getString(cursor.getColumnIndexOrThrow("E.ESTOQUE_EXTRA_1")));
        estoque.setPrecoPromocao(cursor.getDouble(cursor.getColumnIndexOrThrow("E.PRECO_PROMOCAO")));
        estoque.setPrecoPromocaoRevenda(cursor.getDouble(cursor.getColumnIndexOrThrow("E.PRECO_PROMOCAO_REVENDA")));
        estoque.setPrecoRevenda(cursor.getDouble(cursor.getColumnIndexOrThrow("E.PRECO_REVENDA")));
        estoque.setPesavel(cursor.getString(cursor.getColumnIndexOrThrow("E.PESAVEL")));
        estoque.setFornecedor(cursor.getString(cursor.getColumnIndexOrThrow("E.FORNECEDOR")));
        estoque.setCobrarIcmsSubstituicao(cursor.getString(cursor.getColumnIndexOrThrow("E.COBRAR_ICMS_SUBSTITUICAO")));
        estoque.setEmbalagem(cursor.getString(cursor.getColumnIndexOrThrow("E.EMBALAGEM")));
        estoque.setIndicArredondamento(cursor.getString(cursor.getColumnIndexOrThrow("E.INDIC_ARREDONDAMENTO")));
        estoque.setIndicProducao(cursor.getString(cursor.getColumnIndexOrThrow("E.INDIC_PRODUCAO")));
        estoque.setPromocaoInicio(cursor.getString(cursor.getColumnIndexOrThrow("E.PROMOCAO_INICIO")));
        estoque.setPromocaoFim(cursor.getString(cursor.getColumnIndexOrThrow("E.PROMOCAO_FIM")));
        estoque.setPrecoMinimo(cursor.getDouble(cursor.getColumnIndexOrThrow("E.PRECO_MINIMO")));
        return estoque;

    }

}
