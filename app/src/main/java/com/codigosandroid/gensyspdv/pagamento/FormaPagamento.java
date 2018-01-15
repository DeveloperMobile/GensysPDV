package com.codigosandroid.gensyspdv.pagamento;

import java.io.Serializable;

/**
 * Created by Tiago on 05/01/2018.
 */

public class FormaPagamento implements Serializable {

    private long id;
    private TipoPagamento tipoPagamento;
    private double valor;
    private int parcela;
    private String numAutorizacao;
    // atributo auxiliar
    private double total;
    private String valorLong;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    public String getNumAutorizacao() {
        return numAutorizacao;
    }

    public void setNumAutorizacao(String numAutorizacao) {
        this.numAutorizacao = numAutorizacao;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getValorLong() {
        return valorLong;
    }

    public void setValorLong(String valorLong) {
        this.valorLong = valorLong;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.tipoPagamento.equals(((FormaPagamento) obj).getTipoPagamento())) {
            return true;
        }
        return false;
    }

}
