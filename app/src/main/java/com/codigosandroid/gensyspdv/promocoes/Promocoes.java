package com.codigosandroid.gensyspdv.promocoes;

import com.codigosandroid.gensyspdv.empresa.Empresa;
import com.codigosandroid.gensyspdv.estoque.Estoque;

/**
 * Created by Tiago on 02/01/2018.
 */

public class Promocoes {

    private long id;
    private Empresa empresa;
    private Estoque estoque;
    private String dataInicio;
    private String dataFim;
    private String nomePromocao;
    private String operador;
    private String dataCriacao;
    private String operadorAlteracao;
    private String dataAlteracao;
    private double valor;
    private String horaInicio;
    private String horaFim;
    // Atributos tabela externa
    private String codigoEstoque;
    private String descricaoEmpresa;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getNomePromocao() {
        return nomePromocao;
    }

    public void setNomePromocao(String nomePromocao) {
        this.nomePromocao = nomePromocao;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getOperadorAlteracao() {
        return operadorAlteracao;
    }

    public void setOperadorAlteracao(String operadorAlteracao) {
        this.operadorAlteracao = operadorAlteracao;
    }

    public String getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(String dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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

    public String getCodigoEstoque() {
        return codigoEstoque;
    }

    public void setCodigoEstoque(String codigoEstoque) {
        this.codigoEstoque = codigoEstoque;
    }

    public String getDescricaoEmpresa() {
        return descricaoEmpresa;
    }

    public void setDescricaoEmpresa(String descricaoEmpresa) {
        this.descricaoEmpresa = descricaoEmpresa;
    }

}
