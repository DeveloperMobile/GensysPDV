package com.codigosandroid.gensyspdv.db;

import android.content.Context;

import com.codigosandroid.gensyspdv.utils.DAO;

/**
 * Created by analise on 23/02/18.
 */

public abstract class ServiceGeneric<T> {

    public abstract DAO<T> getDAO(Context context);

}
