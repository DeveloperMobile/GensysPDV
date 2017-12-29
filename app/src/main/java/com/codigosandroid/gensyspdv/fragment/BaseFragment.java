package com.codigosandroid.gensyspdv.fragment;


import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cliente.Cliente;
import com.codigosandroid.gensyspdv.cliente.ServiceCliente;
import com.codigosandroid.gensyspdv.empresa.Empresa;
import com.codigosandroid.gensyspdv.empresa.ServiceEmpresa;
import com.codigosandroid.gensyspdv.estoque.Estoque;
import com.codigosandroid.gensyspdv.estoque.EstoquePreco;
import com.codigosandroid.gensyspdv.estoque.ServiceEstoque;
import com.codigosandroid.gensyspdv.estoque.ServiceEstoquePreco;
import com.codigosandroid.gensyspdv.fragment.dialog.DialogSyncFragment;
import com.codigosandroid.gensyspdv.pagamento.ServiceTipoPagamento;
import com.codigosandroid.gensyspdv.pagamento.TipoPagamento;
import com.codigosandroid.gensyspdv.precohora.PrecoHora;
import com.codigosandroid.gensyspdv.precohora.ServicePrecoHora;
import com.codigosandroid.gensyspdv.usuario.ServiceUsuario;
import com.codigosandroid.gensyspdv.usuario.Usuario;
import com.codigosandroid.gensyspdv.utils.AsyncListener;
import com.codigosandroid.utils.utils.AlertUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Tiago on 27/12/2017.
 */

public class BaseFragment extends com.codigosandroid.utils.fragment.BaseFragment {

    protected Handler handler = new Handler();
    protected String[] syncList = new String[] {
            "cliente",
            "empresa",
            "estoque",
            "estoque_preco",
            "formapag",
            "preco_hora",
            "usuario"
    };

    public class SyncUserTask extends AsyncTask<String, Void, Void> {

        private AsyncListener asyncListener;

        public SyncUserTask(AsyncListener asyncListener) {

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

    /* Busca clientes */
    protected List<Cliente> syncClient(String msg) {
        status(msg);
        return ServiceCliente.getAllExt(getActivity());
    }

    /* Busca empresa */
    protected List<Empresa> syncEmpresa(String msg) {
        status(msg);
        return ServiceEmpresa.getAllExt(getActivity());
    }

    /* Busca estoque*/
    protected List<Estoque> syncEstoque(String msg) {
        status(msg);
        return ServiceEstoque.getAllExt(getActivity());
    }

    /* Busca estoque_preco */
    protected List<EstoquePreco> syncEstoquePreco(String msg) {
        status(msg);
        return ServiceEstoquePreco.getAllExt(getActivity());
    }

    /* Busca formas de pagamento */
    protected List<TipoPagamento> syncTypePay(String msg) {
        status(msg);
        return ServiceTipoPagamento.getAllExt(getActivity());
    }

    /* Busca preco hora */
    protected List<PrecoHora> syncPayHour(String msg) {
        status(msg);
        return ServicePrecoHora.getAllExt(getActivity());
    }

    /* Valida usuário */
    protected Usuario syncValidate(String apelido, String msg) {

        status(msg);
        return ServiceUsuario.getByNameExt(getActivity(), apelido);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {

            if (result == PackageManager.PERMISSION_DENIED) {

                /* Negou a permissão. Mostra alerta e fecha */
                AlertUtil.alert(getActivity(), getString(R.string.app_name), getString(R.string.msg_alerta_permissao), 0, new Runnable() {

                    @Override
                    public void run() {

                        /* Negou permissão. Sai do app */
                        getActivity().finish();

                    }

                });

            }

        }
    }

}
