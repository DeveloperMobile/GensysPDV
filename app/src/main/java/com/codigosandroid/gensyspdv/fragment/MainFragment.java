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
import com.codigosandroid.gensyspdv.cfblob.CfBlob;
import com.codigosandroid.gensyspdv.cfblob.ServiceCfBlob;
import com.codigosandroid.gensyspdv.cliente.Cliente;
import com.codigosandroid.gensyspdv.cliente.ServiceCliente;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.gensyspdv.empresa.Empresa;
import com.codigosandroid.gensyspdv.empresa.ServiceEmpresa;
import com.codigosandroid.gensyspdv.estoque.Estoque;
import com.codigosandroid.gensyspdv.estoque.EstoquePreco;
import com.codigosandroid.gensyspdv.estoque.ServiceEstoque;
import com.codigosandroid.gensyspdv.estoque.ServiceEstoquePreco;
import com.codigosandroid.gensyspdv.fragment.dialog.AboutDialog;
import com.codigosandroid.gensyspdv.pagamento.ServiceTipoPagamento;
import com.codigosandroid.gensyspdv.pagamento.TipoPagamento;
import com.codigosandroid.gensyspdv.precohora.PrecoHora;
import com.codigosandroid.gensyspdv.precohora.ServicePrecoHora;
import com.codigosandroid.gensyspdv.precohora.ServicePromocoes;
import com.codigosandroid.gensyspdv.promocoes.Promocoes;
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
    List<Estoque> estoques = new ArrayList<>();
    List<EstoquePreco> estoquePrecos = new ArrayList<>();
    List<TipoPagamento> tipoPagamentos = new ArrayList<>();
    List<PrecoHora> precoHoras = new ArrayList<>();
    List<Promocoes> promocoes = new ArrayList<>();
    List<CfBlob> cfBlobs = new ArrayList<>();
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

        final List<Usuario> usuarioList = ServiceUsuario.getAllInner(getActivity());

        if (AndroidUtil.isNetworkAvaliable(getActivity())) {

            if (ServiceConfiguracoes.isPreferences(getActivity()) && usuarioList.isEmpty()) {

               sync();

            } else if (!usuarioList.isEmpty()) {
                if (flag.equals(Constantes.SYNC)) {
                    if (ServiceConfiguracoes.isPreferences(getActivity())) {

                        sync();
                        actUp(usuarioList);

                    }
                } else {
                    actUp(usuarioList);
                }
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

    // Sincroniza dados
    private void sync() {
        new SyncUserTask(new AsyncListener() {
            @Override
            public void syncBackground() {
                for (int i = 0; i < syncList.length; i++) {

                    if (syncList[i].equals(Constantes.CLIENTE)) {
                        clientes = syncClient("Sincronizando clientes...");
                    } else if (syncList[i].equals(Constantes.EMPRESA)) {
                        empresas = syncEmpresa("Sincronizando empresas...");
                    } else if (syncList[i].equals(Constantes.ESTOQUE)) {
                        estoques = syncEstoque("Sincronizando estoque...");
                    } else if (syncList[i].equals(Constantes.ESTOQUE_PRECO)){
                        estoquePrecos = syncEstoquePreco("Sincronizando EstoquePreco...");
                    } else if (syncList[i].equals(Constantes.FORMAPAG)){
                        tipoPagamentos = syncTypePay("Sincronizando Formas de Pagamento...");
                    } else if(syncList[i].equals(Constantes.PRECO_HORA)){
                        precoHoras = syncPayHour("Sincronizando Preço Hora...");
                    } else if (syncList[i].equals(Constantes.PROMOCOES)){
                        promocoes = syncPromotions("Sincronizando Promoções...");
                    } else if(syncList[i].equals(Constantes.CFBLOB)){
                        cfBlobs = syncCfBlobs("Sincronizando CfBlobo...");
                    } else if (syncList[i].equals(Constantes.USUARIO)) {
                        usuarios = syncUser("Sincronizando usuarios...");
                    }
                }
            }

            @Override
            public void syncPostExecute() {
                cacheClient(clientes);
                cacheCompany(empresas);
                cacheProduts(estoques);
                cacheProdutPrices(estoquePrecos);
                cacheTypePay(tipoPagamentos);
                cachePayHour(precoHoras);
                cachePromotions(promocoes);
                cacheCfBlobs(cfBlobs);
                cacheUser(usuarios);
            }

        }).execute(syncList);
    }


    /* Inicializa a lista no AutoCompletTextView */
    private void actUp(List<Usuario> usuarios) {

        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, usuarios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actUsuario.setAdapter(adapter);

    }

    private void cacheUser(List<Usuario> usuarios) {

        long id = 0;
        long v = 0;
        long f = 0;
        if (usuarios.isEmpty()) {

            snack(getView(), "Usuários não encontrados!");

        } else {

            actUp(usuarios);
            ServiceUsuario.dropTabTipoUsuario(getActivity());
            ServiceUsuario.createTabTipoUsuario(getActivity());
            ServiceUsuario.dropTabUsuario(getActivity());
            ServiceUsuario.createTabUsuario(getActivity());

            for (Usuario usuario : usuarios) {

                id = ServiceUsuario.insert(getContext(), new TipoUsuario(usuario.getTipo()));

                if (usuario.getTipo().equals(Constantes.VENDEDOR)) {
                    if (id > 0) {
                        v = id;
                    }
                    usuario.setTipoUsuario(new TipoUsuario(v));
                } else if (usuario.getTipo().equals(Constantes.FUNCIONARIO)) {
                    if (id > 0) {
                        f = id;
                    }
                    usuario.setTipoUsuario(new TipoUsuario(f));
                }


                ServiceUsuario.insert(getActivity(), usuario);

            }

        }

    }

    private void cacheClient(List<Cliente> clientes) {

        if (clientes.isEmpty()) {

            snack(getView(), "Clientes não encontrados!");

        } else {

            ServiceCliente.dropTab(getActivity());
            ServiceCliente.createTab(getActivity());

            for (Cliente cliente : clientes) {

                ServiceCliente.insert(getActivity(), cliente);

            }

        }

    }

    private void cacheCompany(List<Empresa> empresas) {

        if (empresas.isEmpty()) {

            snack(getView(), "Empresas não encontradas!");

        } else {

            ServiceEmpresa.dropTab(getActivity());
            ServiceEmpresa.createTab(getActivity());

            for (Empresa empresa : empresas) {

                ServiceEmpresa.insert(getActivity(), empresa);

            }

        }

    }

    private void cacheProduts(List<Estoque> estoques) {

        if (estoques.isEmpty()) {

            snack(getView(), "Estoque não encontrado!");

        } else {

            ServiceEstoque.dropTab(getActivity());
            ServiceEstoque.createTab(getActivity());

            for (Estoque estoque : estoques) {

                estoque.setEmp(ServiceEmpresa.getByName(getActivity()));
                ServiceEstoque.insert(getActivity(), estoque);

            }

        }

    }

    private void cacheProdutPrices(List<EstoquePreco> estoquePrecos) {

        if (estoquePrecos.isEmpty()) {

            snack(getView(), "Estoque Preços não encontrados!");

        } else {

            ServiceEstoquePreco.dropTab(getActivity());
            ServiceEstoquePreco.createTab(getActivity());

            for (EstoquePreco estoquePreco : estoquePrecos) {

                estoquePreco.setEmp(ServiceEmpresa.getByName(getActivity()));
                ServiceEstoquePreco.insert(getActivity(), estoquePreco);

            }

        }

    }

    private void cacheTypePay(List<TipoPagamento> tipoPagamentos) {

        if (tipoPagamentos.isEmpty()) {

            snack(getView(), "Formas de pagamento não encontradas!");

        } else {

            ServiceTipoPagamento.dropTab(getActivity());
            ServiceTipoPagamento.createTab(getActivity());

            for (TipoPagamento tipoPagamento : tipoPagamentos) {

                ServiceTipoPagamento.insert(getActivity(), tipoPagamento);

            }

        }

    }

    private void cachePayHour(List<PrecoHora> precoHoras) {

        if (precoHoras.isEmpty()) {

            snack(getView(), "Preço Hora não encontrado!");

        } else {

            ServicePrecoHora.dropTab(getActivity());
            ServicePrecoHora.createTab(getActivity());

            for (PrecoHora precoHora : precoHoras) {

                precoHora.setEstoque(ServiceEstoque.getByRecno(getActivity(), precoHora.getIdEstoque()));
                ServicePrecoHora.insert(getActivity(), precoHora);

            }

        }

    }

    private void cachePromotions(List<Promocoes> promocoes) {

        if (promocoes.isEmpty()) {

            snack(getView(), "Promoções não encontradas!");

        } else {

            ServicePromocoes.dropTab(getActivity());
            ServicePromocoes.createTab(getActivity());

            for (Promocoes promocao : promocoes) {

                promocao.setEstoque(ServiceEstoque.getByCode(getActivity(), promocao.getCodigoEstoque()));
                promocao.setEmpresa(ServiceEmpresa.getByName(getActivity()));
                ServicePromocoes.insert(getActivity(), promocao);

            }

        }

    }

    private void cacheCfBlobs(List<CfBlob> cfBlobs) {

        if (cfBlobs.isEmpty()) {

            snack(getView(), "Promoções não encontradas!");

        } else {

            ServiceCfBlob.dropTab(getActivity());
            ServiceCfBlob.createTab(getActivity());

            for (CfBlob cfBlob : cfBlobs) {

                ServiceCfBlob.insert(getActivity(), cfBlob);

            }

        }

    }

}
