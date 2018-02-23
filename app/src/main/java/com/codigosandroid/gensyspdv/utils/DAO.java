package com.codigosandroid.gensyspdv.utils;


import java.util.List;

/**
 * Created by analise on 23/02/18.
 */

public interface DAO<T> {
    long insert(T t);
    long update(T t);
    long delete(T t);
    List<T> getAll();
    List<T> getAll(String ip, String db, String user, String pass);
    List<T> getById(long id);
    List<T> betByName(String name);
    void deleteTab();
    void dropTab();
    void createTab();
}
