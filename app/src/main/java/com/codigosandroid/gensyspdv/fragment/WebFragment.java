package com.codigosandroid.gensyspdv.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.codigosandroid.gensyspdv.R;

/**
 * Created by Tiago on 28/12/2017.
 */

public class WebFragment extends BaseFragment {

    private WebView webView;
    private ProgressBar progress;

    String site, privacidade;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        inicializar(view);
        site = getArguments().getString("site");
        privacidade = getArguments().getString("privacidade");
        if (site != null) {
            urlUp(site);
        } else {
            urlUp(privacidade);
        }
        return view;
    }

    private void inicializar(View view) {

        webView = (WebView) view.findViewById(R.id.webView);
        progress = (ProgressBar) view.findViewById(R.id.progress);

    }

    private void urlUp(String url) {

        if (url != null) {
            if (site != null) {

                webView.loadUrl(url);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {

                        // Página começou a ser carregada
                        progress.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {

                        // Página foi carregada
                        progress.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                        toast("Erro ao carregar a página");

                    }

                });

            } else if (privacidade != null) {

                webView.loadUrl(url);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {

                        // Página começou a ser carregada
                        progress.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {

                        // Página foi carregada
                        progress.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                        toast("Erro ao carregar a página");

                    }

                });

            }
        }

    }

}
