package com.codigosandroid.gensyspdv.usuario;

import java.io.Serializable;

/**
 * Created by Tiago on 26/12/2017.
 */

public class TipoUsuario implements Serializable {

    private long id;
    private String descricao;

    public TipoUsuario() {}

    public TipoUsuario(long id) {
        this.id = id;
    }

    public TipoUsuario(String descricao) {
        this.descricao = descricao;
    }

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

}
