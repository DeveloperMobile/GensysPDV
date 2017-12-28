package com.codigosandroid.gensyspdv.empresa;

/**
 * Created by Tiago on 28/12/2017.
 */

public class Empresa {

    private long id;
    private String descricao;
    private String nfceToken;
    private String nfceCsc;
    private int idEmpresa;

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

    public String getNfceToken() {
        return nfceToken;
    }

    public void setNfceToken(String nfceToken) {
        this.nfceToken = nfceToken;
    }

    public String getNfceCsc() {
        return nfceCsc;
    }

    public void setNfceCsc(String nfceCsc) {
        this.nfceCsc = nfceCsc;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

}
