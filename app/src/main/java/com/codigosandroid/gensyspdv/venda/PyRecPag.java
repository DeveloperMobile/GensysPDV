package com.codigosandroid.gensyspdv.venda;

import com.codigosandroid.gensyspdv.pagamento.FormaPagamento;

/**
 * Created by Tiago on 05/01/2018.
 */

public class PyRecPag {

    private long id;
    private PyVenda pyVenda;
    private FormaPagamento formaPagamento;
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

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
