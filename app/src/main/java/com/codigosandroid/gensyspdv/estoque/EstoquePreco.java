package com.codigosandroid.gensyspdv.estoque;

import com.codigosandroid.gensyspdv.empresa.Empresa;

/**
 * Created by Tiago on 29/12/2017.
 */

public class EstoquePreco {

    private long id;
    private int tabelaCodigo;
    private Empresa emp;
    private String codigo;
    private String descricao;
    private double preco;
    private String empresa;
    private double menorPreco;
    private double ajustePercentual;
    private double margemLucro;
    private String atualizado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTabelaCodigo() {
        return tabelaCodigo;
    }

    public void setTabelaCodigo(int tabelaCodigo) {
        this.tabelaCodigo = tabelaCodigo;
    }

    public Empresa getEmp() {
        return emp;
    }

    public void setEmp(Empresa emp) {
        this.emp = emp;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public double getMenorPreco() {
        return menorPreco;
    }

    public void setMenorPreco(double menorPreco) {
        this.menorPreco = menorPreco;
    }

    public double getAjustePercentual() {
        return ajustePercentual;
    }

    public void setAjustePercentual(double ajustePercentual) {
        this.ajustePercentual = ajustePercentual;
    }

    public double getMargemLucro() {
        return margemLucro;
    }

    public void setMargemLucro(double margemLucro) {
        this.margemLucro = margemLucro;
    }

    public String getAtualizado() {
        return atualizado;
    }

    public void setAtualizado(String atualizado) {
        this.atualizado = atualizado;
    }

}
