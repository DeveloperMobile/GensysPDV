package com.codigosandroid.gensyspdv.venda.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.usuario.ServiceUsuario;
import com.codigosandroid.gensyspdv.usuario.Usuario;
import com.codigosandroid.gensyspdv.usuario.UsuarioAdapter;
import com.codigosandroid.gensyspdv.utils.DialogCallback;
import com.codigosandroid.gensyspdv.utils.OnClickRecyclerItem;

import java.util.List;

/**
 * Created by Tiago on 05/10/2017.
 */

public class UsuarioDialog extends DialogFragment {

    private RecyclerView recyclerUsuario;
    private SearchView searchView;
    private UsuarioAdapter adapter, adapterFilter;
    private DialogCallback<Usuario> dialogCallback;

    /* Abre o dialog com a lista de usuários */
    public static void showDialog(FragmentManager fm, DialogCallback<Usuario> dialogCallback) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = fm.findFragmentByTag("usuario_dialog");

        if (frag != null) {

            ft.remove(frag);

        }

        ft.addToBackStack(null);
        UsuarioDialog usuarioDialog = new UsuarioDialog();
        usuarioDialog.dialogCallback = dialogCallback;
        usuarioDialog.show(ft, "usuario_dialog");

    }

    /* Fecha o dialog */
    public static void closeDialog(FragmentManager fm) {

        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
        UsuarioDialog ud = (UsuarioDialog) fm.findFragmentByTag("usuario_dialog");

        if (ud != null) {

            ud.dismiss();
            ft.remove(ud);

        }

    }

    private void inicializar(View view) {


        recyclerUsuario = (RecyclerView) view.findViewById(R.id.recyclerVendedor);
        recyclerUsuario.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerUsuario.setItemAnimator(new DefaultItemAnimator());
        recyclerUsuario.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerUp();
        /* Fecha dialog */
        view.findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                closeDialog(getActivity().getSupportFragmentManager());

            }

        });
        /* SearchView */
        searchView = (SearchView) view.findViewById(R.id.searchSalesman);
        searchView.setOnQueryTextListener(onQueryTextListener());
        searchView.setFocusable(true);

    }

    private void recyclerUp() {

        final List<Usuario> usuarios = ServiceUsuario.getAll(getActivity());
        adapterFilter = new UsuarioAdapter();
        adapter = new UsuarioAdapter(getActivity(), usuarios, new OnClickRecyclerItem() {

            @Override
            public void onClickItem(RecyclerView.ViewHolder holder, int id) {

                if (dialogCallback != null) {

                    adapterFilter = adapter;
                    Usuario usuario = adapterFilter.getUsuarioListFilter().get(id);
                    dialogCallback.getObject(usuario);

                }

            }

        });
        recyclerUsuario.setAdapter(adapter);

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

        View view = inflater.inflate(R.layout.dialog_usuario, container, false);
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
