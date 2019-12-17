package com.codigosandroid.gensyspdv.venda.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cliente.Cliente;
import com.codigosandroid.gensyspdv.cliente.ClienteAdapter;
import com.codigosandroid.gensyspdv.cliente.ServiceCliente;
import com.codigosandroid.gensyspdv.utils.DialogCallback;

import java.util.List;

/**
 * Created by Tiago on 05/10/2017.
 */

public class ClienteDialog extends DialogFragment {

    private RecyclerView recyclerCliente;
    private SearchView searchView;
    private ClienteAdapter adapter, adapterFilter;
    private DialogCallback<Cliente> dialogCallback;

    /* Abre o dialog com a lista de clientes */
    public static void showDialog(FragmentManager fm, DialogCallback<Cliente> dialogCallback) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = fm.findFragmentByTag("cliente_dialog");

        if (frag != null) {

            ft.remove(frag);

        }

        ft.addToBackStack(null);
        ClienteDialog clienteDialog = new ClienteDialog();
        clienteDialog.dialogCallback = dialogCallback;
        clienteDialog.show(ft, "cliente_dialog");

    }

    /* Fecha o dialog */
    public static void closeDialog(FragmentManager fm) {

        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
        ClienteDialog cd = (ClienteDialog) fm.findFragmentByTag("cliente_dialog");

        if (cd != null) {

            cd.dismiss();
            ft.remove(cd);

        }

    }

    private void inicializar(View view) {


        recyclerCliente = (RecyclerView) view.findViewById(R.id.recyclerCliente);
        recyclerCliente.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerCliente.setItemAnimator(new DefaultItemAnimator());
        recyclerCliente.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerUp();
        /* Fecha dialog */
        view.findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                closeDialog(getActivity().getSupportFragmentManager());

            }

        });
        /* SearchView */
        searchView = (SearchView) view.findViewById(R.id.searchClient);
        searchView.setOnQueryTextListener(onQueryTextListener());

    }

    private void recyclerUp() {

        final List<Cliente> clientes = ServiceCliente.getAll(getActivity());
        adapterFilter = new ClienteAdapter();
        adapter = new ClienteAdapter(getActivity(), clientes, new ClienteAdapter.OnClickRecyclerItem() {

            @Override
            public void onClickItem(RecyclerView.ViewHolder holder, int id) {

                if (dialogCallback != null) {

                    adapterFilter = adapter;
                    Cliente cliente = adapterFilter.getClienteListFilter().get(id);
                    dialogCallback.getObject(cliente);

                }

            }

        });
        recyclerCliente.setAdapter(adapter);

    }

    /* Filtro SearchView */
    private SearchView.OnQueryTextListener onQueryTextListener() {

        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        };
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_cliente, container, false);
        inicializar(view);
        return view;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
