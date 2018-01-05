package com.codigosandroid.gensyspdv.venda;

import com.codigosandroid.gensyspdv.cliente.Cliente;
import com.codigosandroid.gensyspdv.empresa.Empresa;
import com.codigosandroid.gensyspdv.usuario.Usuario;

import java.util.List;

/**
 * Created by Tiago on 03/01/2018.
 */

public class PyVenda {

    private long id;
    private String identificador;
    private String tipo;
    private String dataEmissao;
    private Cliente cliente;
    private Usuario usuario;
    private long idOperador;
    private Empresa empresa;
    private String numeroServidor;
    private double total;
    private String captura;
    private String notaFiscal;
    private List<PyDetalhe> pyDetalhes;
    private List<PyRecPag> pyRecPags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public long getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(long idOperador) {
        this.idOperador = idOperador;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getNumeroServidor() {
        return numeroServidor;
    }

    public void setNumeroServidor(String numeroServidor) {
        this.numeroServidor = numeroServidor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCaptura() {
        return captura;
    }

    public void setCaptura(String captura) {
        this.captura = captura;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public List<PyDetalhe> getPyDetalhes() {
        return pyDetalhes;
    }

    public void setPyDetalhes(List<PyDetalhe> pyDetalhes) {
        this.pyDetalhes = pyDetalhes;
    }

    public List<PyRecPag> getPyRecPags() {
        return pyRecPags;
    }

    public void setPyRecPags(List<PyRecPag> pyRecPags) {
        this.pyRecPags = pyRecPags;
    }

}
