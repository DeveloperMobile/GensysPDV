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
import com.codigosandroid.gensyspdv.estoque.Estoque;
import com.codigosandroid.gensyspdv.estoque.EstoqueAdapter;
import com.codigosandroid.gensyspdv.estoque.ServiceEstoque;
import com.codigosandroid.gensyspdv.utils.DialogCallback;

import java.util.List;

/**
 * Created by Tiago on 06/10/2017.
 */

public class EstoqueDialog extends DialogFragment {

    private RecyclerView recyclerProduto;
    private SearchView searchView;
    private EstoqueAdapter adapter, adapterFilter;
    private DialogCallback<Estoque> dialogCallback;

    /* Abre o dialog com a lista de usuários */
    public static void showDialog(FragmentManager fm, DialogCallback<Estoque> dialogCallback) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = fm.findFragmentByTag("estoque_dialog");

        if (frag != null) {

            ft.remove(frag);

        }

        ft.addToBackStack(null);
        EstoqueDialog estoqueDialog = new EstoqueDialog();
        estoqueDialog.dialogCallback = dialogCallback;
        estoqueDialog.show(ft, "estoque_dialog");

    }

    /* Fecha o dialog */
    public static void closeDialog(FragmentManager fm) {

        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
        EstoqueDialog ed = (EstoqueDialog) fm.findFragmentByTag("estoque_dialog");

        if (ed != null) {

            ed.dismiss();
            ft.remove(ed);

        }

    }

    private void inicializar(View view) {


        recyclerProduto = (RecyclerView) view.findViewById(R.id.recyclerProduto);
        recyclerProduto.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerProduto.setItemAnimator(new DefaultItemAnimator());
        recyclerProduto.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerUp();
        /* Fecha dialog */
        view.findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                closeDialog(getActivity().getSupportFragmentManager());

            }

        });
        /* SearchView */
        searchView = (SearchView) view.findViewById(R.id.searchProdut);
        searchView.setOnQueryTextListener(onQueryTextListener());

    }

    private void recyclerUp() {

        final List<Estoque> produtos = ServiceEstoque.getAll(getActivity());
        adapterFilter = new EstoqueAdapter();
        adapter = new EstoqueAdapter(getActivity(), produtos, new EstoqueAdapter.OnClickRecyclerItem() {

            @Override
            public void onClickItem(RecyclerView.ViewHolder holder, int id) {

                if (dialogCallback != null) {

                    adapterFilter = adapter;
                    Estoque estoque = adapterFilter.getEstoqueListFilter().get(id);
                    dialogCallback.getObject(estoque);

                }

            }

        });
        recyclerProduto.setAdapter(adapter);

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

        View view = inflater.inflate(R.layout.dialog_produto, container, false);
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
