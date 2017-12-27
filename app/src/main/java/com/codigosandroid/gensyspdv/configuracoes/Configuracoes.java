package com.codigosandroid.gensyspdv.configuracoes;

/**
 * Created by Tiago on 27/12/2017.
 */

public class Configuracoes {

    private String host;
    private String company;
    private String db;
    private String userDb;
    private String passDb;
    private boolean nfce;
    private boolean nfceResume;
    private boolean nota;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUserDb() {
        return userDb;
    }

    public void setUserDb(String userDb) {
        this.userDb = userDb;
    }

    public String getPassDb() {
        return passDb;
    }

    public void setPassDb(String passDb) {
        this.passDb = passDb;
    }

    public boolean isNfce() {
        return nfce;
    }

    public void setNfce(boolean nfce) {
        this.nfce = nfce;
    }

    public boolean isNfceResume() {
        return nfceResume;
    }

    public void setNfceResume(boolean nfceResume) {
        this.nfceResume = nfceResume;
    }

    public boolean isNota() {
        return nota;
    }

    public void setNota(boolean nota) {
        this.nota = nota;
    }

}
