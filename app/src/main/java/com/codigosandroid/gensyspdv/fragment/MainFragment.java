package com.codigosandroid.gensyspdv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.activity.ConfigActivity;
import com.codigosandroid.gensyspdv.activity.SobreActivity;
import com.codigosandroid.gensyspdv.cliente.Cliente;
import com.codigosandroid.gensyspdv.cliente.ServiceCliente;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.gensyspdv.empresa.Empresa;
import com.codigosandroid.gensyspdv.empresa.ServiceEmpresa;
import com.codigosandroid.gensyspdv.fragment.dialog.AboutDialog;
import com.codigosandroid.gensyspdv.usuario.ServiceUsuario;
import com.codigosandroid.gensyspdv.usuario.TipoUsuario;
import com.codigosandroid.gensyspdv.usuario.Usuario;
import com.codigosandroid.gensyspdv.utils.AndroidUtils;
import com.codigosandroid.gensyspdv.utils.AsyncListener;
import com.codigosandroid.gensyspdv.utils.Constantes;
import com.codigosandroid.gensyspdv.utils.KeyboardUtil;
import com.codigosandroid.utils.utils.AlertUtil;
import com.codigosandroid.utils.utils.AndroidUtil;
import com.codigosandroid.utils.utils.LogUtil;
import com.codigosandroid.utils.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {

    private AutoCompleteTextView actUsuario;
    private EditText etSenha;

    List<Usuario> usuarios = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();
    List<Empresa> empresas = new ArrayList<>();
    Usuario usuario = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        inicializar(view);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (PermissionUtil.validade(getActivity(), 1, Constantes.PERMISSIONS)) {
            sincronizar(Constantes.RESUME);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sync:
                sincronizar(Constantes.SYNC);
                break;
            case R.id.menu_settings:
                startActivity(new Intent(getAppCompatActivity(), ConfigActivity.class));
                break;
            case R.id.menu_info:
                if (AndroidUtils.isTablet(getActivity())) {
                    startActivity(new Intent(getActivity(), SobreActivity.class));
                } else {
                    AboutDialog.showAbout(getActivity().getSupportFragmentManager());
                }
                break;
            case R.id.menu_exit:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);

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
            actUsuario.requestFocus();
            actUsuario.setError(getActivity().getString(R.string.msg_error_usuario));
            return false;
        } else if (etSenha != null && etSenha.getText().toString().equals("")) {
            etSenha.requestFocus();
            etSenha.setError(getActivity().getString(R.string.msg_error_senha));
            return false;
        } else {
            return true;
        }

    }

    private void login() {

        List<Usuario> usuarioList = ServiceUsuario.getAll(getAppCompatActivity());

        if (AndroidUtil.isNetworkAvaliable(getActivity())) {

            if (ServiceConfiguracoes.isPreferences(getAppCompatActivity()) && !usuarioList.isEmpty()) {
                if (validaCampos()) {

                    new SyncUserTask(new AsyncListener() {
                        @Override
                        public void syncBackground() {
                            for (int i = 0; i < syncList.length; i++) {
                                if (syncList[i].equals("usuario")) {
                                    usuario = syncValidate(actUsuario.getText().toString(), "Validando usuario...");
                                    LogUtil.debug("APELIDO: ", usuario.getApelido());
                                    LogUtil.debug("SENHA: ", usuario.getSenha());
                                }
                            }
                        }

                        @Override
                        public void syncPostExecute() {

                            try {

                                if (usuario.getApelido().toUpperCase().equals(actUsuario.getText().toString().trim().toUpperCase())
                                        && usuario.getSenha().equals(etSenha.getText().toString().trim())) {

                                    AlertUtil.alert(getActivity(), "Informativo", "Login efetuado com sucesso!!");

                                } else {

                                    AlertUtil.alert(getActivity(), "Aviso", "Usuário ou Senha inválidos!", 0, android.R.drawable.ic_dialog_alert);

                                }

                            } catch (Exception e) {
                                AlertUtil.alert(getActivity(), "Erro", "Erro ao validar usuário!", 0, android.R.drawable.ic_dialog_alert);
                            }

                        }

                    }).execute(syncList);

                }

            } else {
                AlertUtil.alert(getActivity(), "Informativo", "É necessário efetuar as configurações para validar o usuário!", 0, new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), ConfigActivity.class));
                    }
                });
            }

        } else {
            snack(getView(), R.string.msg_error_conexao_indisponivel);
        }

    }

    private void sincronizar(String flag) {

        final List<Usuario> usuarioList = ServiceUsuario.getAll(getActivity());

        if (AndroidUtil.isNetworkAvaliable(getActivity())) {

            if (ServiceConfiguracoes.isPreferences(getActivity()) && usuarioList.isEmpty()) {

                new SyncUserTask(new AsyncListener() {
                    @Override
                    public void syncBackground() {
                        for (int i = 0; i < syncList.length; i++) {

                            if (syncList[i].equals("cliente")) {
                                clientes = syncClient("Sincronizando clientes...");
                            } else if (syncList[i].equals("empresa")){
                                empresas = syncEmpresa("Sincronizando empresas...");
                            } else if (syncList[i].equals("usuario")) {
                                usuarios = syncUser("Sincronizando usuarios...");
                            }
                        }
                    }

                    @Override
                    public void syncPostExecute() {

                        cacheClient(clientes);
                        cacheCompany(empresas);
                        cacheUser(usuarios);
                    }

                }).execute(syncList);

            } else if (!usuarioList.isEmpty()) {
                if (flag.equals(Constantes.SYNC)) {
                    if (ServiceConfiguracoes.isPreferences(getActivity())) {

                        new SyncUserTask(new AsyncListener() {
                            @Override
                            public void syncBackground() {
                                for (int i = 0; i < syncList.length; i++) {

                                    if (syncList[i].equals("cliente")) {
                                        clientes = syncClient("Sincronizando clientes...");
                                    } else if (syncList[i].equals("usuario")) {
                                        usuarios = syncUser("Sincronizando usuarios...");
                                    }
                                }
                            }

                            @Override
                            public void syncPostExecute() {

                                cacheClient(clientes);
                                cacheUser(usuarios);
                            }

                        }).execute(syncList);

                    }
                }
                actUp(usuarioList);
            } else {
                AlertUtil.alert(getActivity(), "Informativo", "É necessário efetuar as configurações para sincronizar os dados!", 0, new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), ConfigActivity.class));
                    }
                });
            }

        } else {

            snack(getView(), R.string.msg_error_conexao_indisponivel);

        }

    }

    /* Inicializa a lista no combo */
    private void actUp(List<Usuario> usuarios) {

        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, usuarios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actUsuario.setAdapter(adapter);

    }

    private void cacheUser(List<Usuario> usuarios) {

        boolean flag = true;
        long id = 0;
        if (usuarios.isEmpty()) {

            snack(getView(), "Usuários não encontrados!");

        } else {

            actUp(usuarios);
            ServiceUsuario.deleteTabTipoUsuario(getActivity());
            ServiceUsuario.deleteTab(getActivity());

            for (Usuario usuario : usuarios) {

                if (flag) {
                    id = ServiceUsuario.insert(getContext(), new TipoUsuario(usuario.getTipo()));
                    flag = false;
                }
                usuario.setTipoUsuario(new TipoUsuario(id));
                ServiceUsuario.insert(getActivity(), usuario);

            }

        }

    }

    private void cacheClient(List<Cliente> clientes) {

        if (clientes.isEmpty()) {

            snack(getView(), "Clientes não encontrados!");

        } else {

            ServiceCliente.deleteTab(getActivity());

            for (Cliente cliente : clientes) {

                ServiceCliente.insert(getActivity(), cliente);

            }

        }

    }

    private void cacheCompany(List<Empresa> empresas) {

        if (empresas.isEmpty()) {

            snack(getView(), "Empresas não encontradas!");

        } else {

            ServiceEmpresa.deleteTab(getActivity());

            for (Empresa empresa : empresas) {

                ServiceEmpresa.insert(getActivity(), empresa);

            }

        }

    }

}
