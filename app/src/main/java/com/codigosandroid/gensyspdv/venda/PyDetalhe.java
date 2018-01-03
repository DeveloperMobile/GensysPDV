package com.codigosandroid.gensyspdv.venda;

import com.codigosandroid.gensyspdv.estoque.Estoque;

/**
 * Created by Tiago on 03/01/2018.
 */

public class PyDetalhe {

    private long id;
    private PyVenda pyVenda;
    private Estoque estoque;
    private int quantidade;
    private double vlDesconto;
    private double desconto;
    private double valor;
    private double total;

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

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getVlDesconto() {
        return vlDesconto;
    }

    public void setVlDesconto(double vlDesconto) {
        this.vlDesconto = vlDesconto;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
