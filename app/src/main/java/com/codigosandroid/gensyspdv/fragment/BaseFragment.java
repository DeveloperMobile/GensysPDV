package com.codigosandroid.gensyspdv.fragment;


import android.os.AsyncTask;
import android.os.Handler;

import com.codigosandroid.gensyspdv.fragment.dialog.DialogSyncFragment;
import com.codigosandroid.gensyspdv.usuario.ServiceUsuario;
import com.codigosandroid.gensyspdv.usuario.Usuario;
import com.codigosandroid.gensyspdv.utils.AsyncListener;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Tiago on 27/12/2017.
 */

public class BaseFragment extends com.codigosandroid.utils.fragment.BaseFragment {

    protected Handler handler = new Handler();
    protected String[] syncList = new String[] {
            "usuario"
    };

    public class SyncUserTask extends AsyncTask<String, Void, Void> {

        private AsyncListener asyncListener;

        public SyncUserTask(AsyncListener asyncListner) {

            this.asyncListener = asyncListener;

        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            DialogSyncFragment.showDialog(getActivity().getSupportFragmentManager());

        }

        @Override
        protected Void doInBackground(String... strings) {

            asyncListener.syncBackground();
            return null;

        }

        @Override
        protected void onPostExecute(Void avoid) {

            asyncListener.syncPostExecute();
            DialogSyncFragment.closeDialog(getActivity().getSupportFragmentManager());

        }

    }

    protected void status(final String status) {

        handler.post(new Runnable() {

            @Override
            public void run() {

                DialogSyncFragment.lbStatus.setText(status);

            }

        });

    }

    /* Busca usuários */
    protected List<Usuario> syncUser(String msg) {

        status(msg);
        return ServiceUsuario.getAllExt(getActivity());

    }

}
