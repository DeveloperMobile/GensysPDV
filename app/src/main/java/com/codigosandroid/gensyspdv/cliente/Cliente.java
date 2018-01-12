package com.codigosandroid.gensyspdv.cliente;

import java.io.Serializable;

/**
 * Created by Tiago on 28/12/2017.
 */

public class Cliente implements Serializable {

    private long id;
    private String nome;
    private String fantasia;
    private String cpf;
    private String cgc;
    private String preco;
    private int idCliente;
    private int tabelaPreco;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCgc() {
        return cgc;
    }

    public void setCgc(String cgc) {
        this.cgc = cgc;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getTabelaPreco() {
        return tabelaPreco;
    }

    public void setTabelaPreco(int tabelaPreco) {
        this.tabelaPreco = tabelaPreco;
    }

}
