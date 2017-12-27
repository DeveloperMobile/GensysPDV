package com.codigosandroid.gensyspdv.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.usuario.GetUserAsync;
import com.codigosandroid.gensyspdv.usuario.Usuario;
import com.codigosandroid.gensyspdv.utils.AsyncCallback;
import com.codigosandroid.gensyspdv.utils.KeyboardUtil;
import com.codigosandroid.utils.fragment.BaseFragment;
import com.codigosandroid.utils.utils.AlertUtil;
import com.codigosandroid.utils.utils.AndroidUtil;
import com.codigosandroid.utils.utils.LogUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {

    private AutoCompleteTextView actUsuario;
    private EditText etSenha;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        inicializar(view);
        return view;

    }

    TextView.OnEditorActionListener actionLogin() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                    KeyboardUtil.hideKeyboard(getActivity(), etSenha);
                    return true;
                }
                return false;
            }
        };
    }

    private void inicializar(View view) {

        actUsuario = (AutoCompleteTextView) view.findViewById(R.id.actUsuario);
        etSenha = (EditText) view.findViewById(R.id.etSenha);
        etSenha.setOnEditorActionListener(actionLogin());
        view.findViewById(R.id.fabLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private boolean validaCampos() {

        if (actUsuario != null && actUsuario.getText().toString().equals("")) {
            actUsuario.setError(getActivity().getString(R.string.msg_error_usuario));
            return false;
        } else if (etSenha != null && etSenha.getText().toString().equals("")) {
            etSenha.setError(getActivity().getString(R.string.msg_error_senha));
            return false;
        } else {
            return true;
        }

    }

    private void login() {
        if (validaCampos()) {
            if (AndroidUtil.isNetworkAvaliable(getActivity())) {
                startTask("usuario", new GetUserAsync(getActivity(), actUsuario.getText().toString(), new AsyncCallback<Usuario>() {
                    @Override
                    public void asyncReturn(Usuario usuario) {
                        try {
                            if (actUsuario.getText().toString().equals(usuario.getApelido()) &&
                                    etSenha.getText().toString().equals(usuario.getSenha())) {
                                AlertUtil.alert(getActivity(), "Informativo", "Login efetuado com sucesso!");
                            } else {
                                AlertUtil.alert(getActivity(), "Erro", "Login ou Senha inválidos!");
                            }
                        } catch (NullPointerException e) {
                            LogUtil.error(TAG, e.getMessage(), e);
                            AlertUtil.alert(getActivity(), "Erro", "Usuário inválido!");
                        }
                    }
                }));
            } else {
                snack(getView(), R.string.msg_error_conexao_indisponivel);
            }
        }
    }

}
