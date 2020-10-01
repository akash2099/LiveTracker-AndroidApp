package com.example.locateme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main3Activity extends AppCompatActivity {
    WebView webView;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        webView=(WebView)findViewById(R.id.wv1);
        url="https://github.com/akash2099/Keep-Notes-App";
        webView.setWebViewClient(new mywbview());
//        webView.canGoBackOrForward(10);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://github.com/akash2099/Keep-Notes-App");
    }
    private class mywbview extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            if ("https://github.com/akash2099/Keep-Notes-App".equals(Uri.parse(url).getHost())) {
                view.loadUrl(url);
                // This is my website, so do not override; let my WebView load the page
                return true;
            }
            else {
                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }


        }

    }
}
