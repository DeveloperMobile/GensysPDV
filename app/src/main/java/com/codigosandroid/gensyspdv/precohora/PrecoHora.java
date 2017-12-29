package com.codigosandroid.gensyspdv.precohora;

import com.codigosandroid.gensyspdv.estoque.Estoque;

/**
 * Created by Tiago on 29/12/2017.
 */

public class PrecoHora {

    private long id;
    private Estoque estoque;
    private String diaSemana;
    private String horaInicio;
    private String horaFim;
    private double valor;
    // Auxiliar
    private int idEstoque;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
    }

}
