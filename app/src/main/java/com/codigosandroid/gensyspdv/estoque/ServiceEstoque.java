package com.codigosandroid.gensyspdv.estoque;

import android.content.Context;

import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;

import java.util.List;

/**
 * Created by Tiago on 29/12/2017.
 */

public class ServiceEstoque {

    private static EstoqueDAO estoqueDAO;
    private static EstoqueExtDAO estoqueExtDAO;

    /**
     * Insere um registro na tabela estoque
     * @param context contexto da classe que utiliza o método
     * @param estoque objeto estoque a ser inserido no db */
    public static long insert(Context context, Estoque estoque) {
        estoqueDAO = new EstoqueDAO(context);
        return estoqueDAO.insert(estoque);
    }

    /**
     * Deleta todos os registros na tabela estoque
     * @param context contexto da classe que utiliza o método */
    public static void deleteTab(Context context) {
        estoqueDAO = new EstoqueDAO(context);
        estoqueDAO.deleteTab();
    }

    /**
     * Deleta a tabela estoque
     * @param context contexto da classe que utiliza o método */
    public static void dropTab(Context context) {
        estoqueDAO = new EstoqueDAO(context);
        estoqueDAO.dropTab();
    }

    /**
     * Cria a tabela estoque
     * @param context contexto da classe que utiliza o método */
    public static void createTab(Context context) {
        estoqueDAO = new EstoqueDAO(context);
        estoqueDAO.createTab();
    }

    /**
     * Busca registro na tabela estoque pelo recno
     * @param context contexto da classe que utiliza o método */
    public static Estoque getByRecno(Context context, int recno) {
        estoqueDAO = new EstoqueDAO(context);
        return estoqueDAO.getByRecno(recno);
    }

    /**
     * Busca registro na tabela estoque pelo codigo do produto
     * @param context contexto da classe que utiliza o método */
    public static Estoque getByCode(Context context, String codigo) {
        estoqueDAO = new EstoqueDAO(context);
        return estoqueDAO.getByCode(codigo);
    }

    /**
     * Busca todos os registros na tabela estoque externa
     * @param context contexto da classe que utiliza o método */
    public static List<Estoque> getAllExt(Context context) {
        estoqueExtDAO = new EstoqueExtDAO();
        Configuracoes configuracoes = ServiceConfiguracoes.getConfiguracoes(context);
        return estoqueExtDAO.getByEmpresa(configuracoes.getHost(), configuracoes.getDb(), configuracoes.getUserDb(),
                configuracoes.getPassDb(), configuracoes.getCompany());

    }

}
