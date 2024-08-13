package com.trodev.trodevbusiness;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SendNotification extends AppCompatActivity {

    WebView webview;
    String web_link ="http://push.trovatohub.xyz/";

    private WebView webView;
    private SharedPreferences preferences;
    private CookieManager cookieManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        webview = findViewById(R.id.webview);



        // Initialize SharedPreferences and CookieManager
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        // Initialize WebView
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Ensure the website opens within the app
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // Example condition to check if the user is logged in
                if (url.contains("loginSuccess")) {
                    // Save cookies for future sessions
                    String cookies = cookieManager.getCookie(url);
                    preferences.edit().putString("cookies", cookies).apply();
                }
            }
        });

        // Load saved cookies if available
        String savedCookies = preferences.getString("cookies", null);
        if (savedCookies != null) {
            cookieManager.setCookie(web_link, savedCookies);
        }

        // Load the website
        webView.loadUrl(web_link);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}