package com.codigosandroid.gensyspdv.pagamento;

import java.io.Serializable;

/**
 * Created by Tiago on 29/12/2017.
 */

public class TipoPagamento implements Serializable {

    private long id;
    private String descricao;
    private String tipoPagamento;
    private int idFormapag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public int getIdFormapag() {
        return idFormapag;
    }

    public void setIdFormapag(int idFormapag) {
        this.idFormapag = idFormapag;
    }

}
