package com.codigosandroid.gensyspdv.estoque;

import com.codigosandroid.gensyspdv.db.AcessoDB;
import com.codigosandroid.utils.utils.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 28/12/2017.
 */

public class EstoqueExtDAO {

    private static final String TAG = EstoqueExtDAO.class.getSimpleName();

    public static final String SELECT_ALL_BY_EMPRESA = "SELECT recno, codigo, coalesce(codigofornecedor, '') codigofornecedor, empresa, unidade, descricao, " +
            "coalesce(saldo, '') saldo, reservado, coalesce(precocusto, '') precocusto, coalesce(precovenda, '') precovenda, minimo, coalesce(precocompra, '') precocompra, " +
            "cobraricms, tributaria, detalhada, coalesce(atualizado, '') atualizado, coalesce(subgrupo, '') subgrupo, coalesce(grupo, '') grupo, coalesce(localizacao, '') localizacao, " +
            "coalesce(comissaoproduto, '') comissaoproduto, coalesce(observacao, '') observacao, coalesce(estoqueextra1, '') estoqueextra1, coalesce(precopromocao, '') precopromocao, " +
            "coalesce(precopromocaorevenda, '') precopromocaorevenda, coalesce(precorevenda, '') precorevenda, coalesce(pesavel, '') pesavel, coalesce(fornecedor, '') fornecedor, " +
            "cobraricmssubstituicao, coalesce(embalagem, '') embalagem, indic_arredondamento, indic_producao, coalesce(promocaoinicio, '') promocaoinicio, " +
            "coalesce(promocaofim, '') promocaofim, coalesce(precominimo, '') precominimo, id_empresa FROM estoque WHERE UPPER(empresa)=? ORDER BY descricao;";

    public static final String SELECT_DATA_PROMOCAO_BY_EMPRESA = "SELECT codigo, coalesce(codigofornecedor, '') codigofornecedor, empresa, unidade, descricao, " +
            "coalesce(saldo, '') saldo, reservado, coalesce(precocusto, '') precocusto, coalesce(precovenda, '') precovenda, minimo, coalesce(precocompra, '') precocompra, " +
            "cobraricms, tributaria, detalhada, coalesce(atualizado, '') atualizado, coalesce(subgrupo, '') subgrupo, coalesce(grupo, '') grupo, coalesce(localizacao, '') localizacao, " +
            "coalesce(comissaoproduto, '') comissaoproduto, coalesce(observacao, '') observacao, coalesce(estoqueextra1, '') estoqueextra1, coalesce(precopromocao, '') precopromocao, " +
            "coalesce(precopromocaorevenda, '') precopromocaorevenda, coalesce(precorevenda, '') precorevenda, coalesce(pesavel, '') pesavel, coalesce(fornecedor, '') fornecedor, " +
            "cobraricmssubstituicao, coalesce(embalagem, '') embalagem, indic_arredondamento, indic_producao, coalesce(precominimo, '') precominimo, id_empresa FROM estoque WHERE UPPER(empresa)=? AND codigo=?" +
            "AND promocaoinicio = CAST(? AS DATE) AND promocaofim = CAST(? AS DATE) ORDER BY descricao;";

    public static final String SELECT_ALL_PROMOCAO_BY_EMPRESA = "SELECT codigo, coalesce(codigofornecedor, '') codigofornecedor, empresa, unidade, descricao, " +
            "coalesce(saldo, '') saldo, reservado, coalesce(precocusto, '') precocusto, coalesce(precovenda, '') precovenda, minimo, coalesce(precocompra, '') precocompra, " +
            "cobraricms, tributaria, detalhada, coalesce(atualizado, '') atualizado, coalesce(subgrupo, '') subgrupo, coalesce(grupo, '') grupo, coalesce(localizacao, '') localizacao, " +
            "coalesce(comissaoproduto, '') comissaoproduto, coalesce(observacao, '') observacao, coalesce(estoqueextra1, '') estoqueextra1, coalesce(precopromocao, '') precopromocao, " +
            "coalesce(precopromocaorevenda, '') precopromocaorevenda, coalesce(precorevenda, '') precorevenda, coalesce(pesavel, '') pesavel, coalesce(fornecedor, '') fornecedor, " +
            "cobraricmssubstituicao, coalesce(embalagem, '') embalagem, indic_arredondamento, indic_producao, coalesce(promocaoinicio, '') promocaoinicio, " +
            "coalesce(promocaofim, '') promocaofim, coalesce(precominimo, '') precominimo, id_empresa FROM estoque WHERE UPPER(empresa)=? AND codigo=? ORDER BY descricao;";

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    AcessoDB acessoBD = new AcessoDB();

    /* Busca os registros de produto pela empresa */
    public List<Estoque> getByEmpresa(String ip, String db, String user, String pass, String empresa) {

        List<Estoque> estoques = new ArrayList<>();

        try {

            conn = acessoBD.getConnection(ip, db, user, pass);
            stmt = conn.prepareStatement(SELECT_ALL_BY_EMPRESA);
            stmt.setString(1, empresa.toUpperCase());
            rs = stmt.executeQuery();

            while (rs.next()) {

                Estoque estoque = resultToEstoque(rs);
                estoques.add(estoque);

            }

            rs.close();
            return estoques;

        } catch (Exception e){

            LogUtil.error(TAG, e.getMessage(), e);
            return new ArrayList<Estoque>();

        }

    }

    private Estoque resultToEstoque(ResultSet rs) throws SQLException {

        Estoque estoque = new Estoque();
        estoque.setRecno(rs.getInt("recno"));
        estoque.setCodigo(rs.getString("codigo"));
        estoque.setCodigoFornecedor(rs.getString("codigofornecedor"));
        estoque.setEmpresa(rs.getString("empresa"));
        estoque.setUnidade(rs.getString("unidade"));
        estoque.setDescricao(rs.getString("descricao"));
        estoque.setSaldo(rs.getDouble("saldo"));
        estoque.setReservado(rs.getDouble("reservado"));
        estoque.setPrecoCusto(rs.getDouble("precocusto"));
        estoque.setPrecoVenda(rs.getDouble("precovenda"));
        estoque.setMinimo(rs.getDouble("minimo"));
        estoque.setPrecoCompra(rs.getDouble("precocompra"));
        estoque.setCobrarIcms(rs.getString("cobraricms"));
        estoque.setTributaria(rs.getString("tributaria"));
        estoque.setDetalhada(rs.getString("detalhada"));
        estoque.setAtualizado(rs.getString("atualizado"));
        estoque.setSubGrupo(rs.getString("subgrupo"));
        estoque.setGrupo(rs.getString("grupo"));
        estoque.setLocalizacao(rs.getString("localizacao"));
        estoque.setComissaoProduto(rs.getDouble("comissaoproduto"));
        estoque.setObservacao(rs.getString("observacao"));
        estoque.setEstoqueExtra1(rs.getString("estoqueextra1"));
        estoque.setPrecoPromocao(rs.getDouble("precopromocao"));
        estoque.setPrecoPromocaoRevenda(rs.getDouble("precopromocaorevenda"));
        estoque.setPrecoRevenda(rs.getDouble("precorevenda"));
        estoque.setPesavel(rs.getString("pesavel"));
        estoque.setFornecedor(rs.getString("fornecedor"));
        estoque.setCobrarIcmsSubstituicao(rs.getString("cobraricmssubstituicao"));
        estoque.setEmbalagem(rs.getString("embalagem"));
        estoque.setIndicArredondamento(rs.getString("indic_arredondamento"));
        estoque.setIndicProducao(rs.getString("indic_producao"));
        estoque.setPromocaoInicio(rs.getString("promocaoinicio"));
        estoque.setPromocaoFim(rs.getString("promocaofim"));
        estoque.setPrecoMinimo(rs.getDouble("precominimo"));
        return estoque;

    }

}
