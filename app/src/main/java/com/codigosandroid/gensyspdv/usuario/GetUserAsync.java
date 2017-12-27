package com.codigosandroid.gensyspdv.usuario;

import android.content.Context;

import com.codigosandroid.gensyspdv.utils.AsyncCallback;
import com.codigosandroid.utils.task.TaskListener;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 26/12/2017.
 */

public class GetUserAsync implements TaskListener<Usuario> {

    private String apelido;
    private Context context;
    private AsyncCallback<Usuario> asyncCallback;

    public GetUserAsync(Context context, String apelido, AsyncCallback<Usuario> asyncCallback) {
        this.context = context;
        this.apelido = apelido;
        this.asyncCallback = asyncCallback;
    }


    @Override
    public Usuario execute() throws Exception {
        return ServiceUsuario.getByNameExt(context, apelido);
    }

    @Override
    public void updateView(Usuario usuario) {

        try {
            if (asyncCallback != null) {
                asyncCallback.asyncReturn(usuario);
            }
        } catch (Exception e) {
            LogUtil.error("getUsuario(): ", e.getMessage(), e);
            if (asyncCallback != null) {
                asyncCallback.asyncReturn(null);
            }
        }

    }

    @Override
    public void onError(Exception exception) {
        LogUtil.error("onError(): ", exception.getMessage());
    }

    @Override
    public void onCancelled(String cod) {}

}
