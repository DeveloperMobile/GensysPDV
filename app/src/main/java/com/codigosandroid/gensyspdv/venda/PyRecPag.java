package com.codigosandroid.gensyspdv.venda;

import com.codigosandroid.gensyspdv.pagamento.TipoPagamento;

/**
 * Created by Tiago on 05/01/2018.
 */

public class PyRecPag {

    private long id;
    private PyVenda pyVenda;
    private TipoPagamento tipoPagamento;
    private double valor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PyVenda getPyVenda() {
        return pyVenda;
    }

    public void setPyVenda(PyVenda pyVenda) {
        this.pyVenda = pyVenda;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
