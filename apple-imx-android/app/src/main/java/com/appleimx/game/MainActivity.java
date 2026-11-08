package com.appleimx.game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(0, 21, 19));
        getWindow().setNavigationBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(0);

        gameView = new WebView(this);
        gameView.setBackgroundColor(Color.rgb(0, 22, 20));
        gameView.setVerticalScrollBarEnabled(false);
        gameView.setHorizontalScrollBarEnabled(false);
        gameView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        WebSettings options = gameView.getSettings();
        options.setJavaScriptEnabled(true);
        options.setDomStorageEnabled(true);
        options.setDefaultTextEncodingName("UTF-8");
        options.setLoadsImagesAutomatically(true);
        options.setAllowFileAccess(true);
        options.setAllowContentAccess(true);
        options.setAllowFileAccessFromFileURLs(true);
        options.setAllowUniversalAccessFromFileURLs(false);
        options.setSupportZoom(false);
        options.setBuiltInZoomControls(false);
        options.setDisplayZoomControls(false);
        options.setUseWideViewPort(true);
        options.setLoadWithOverviewMode(true);
        options.setMediaPlaybackRequiresUserGesture(false);

        gameView.setWebViewClient(new WebViewClient());
        gameView.setWebChromeClient(new WebChromeClient());
        setContentView(gameView);
        gameView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if (gameView == null) {
            super.onBackPressed();
            return;
        }
        // Android Back first closes the existing IMX modal rather than exiting the app.
        gameView.evaluateJavascript(
            "(function(){var modal=document.getElementById('imxOverlay');" +
            "if(modal&&modal.classList.contains('open')){" +
            "modal.classList.remove('open');return true;}return false;})()",
            result -> {
                if (!"true".equals(result)) {
                    if (gameView.canGoBack()) {
                        gameView.goBack();
                    } else {
                        MainActivity.super.onBackPressed();
                    }
                }
            }
        );
    }

    @Override
    protected void onPause() {
        if (gameView != null) gameView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameView != null) gameView.onResume();
    }

    @Override
    protected void onDestroy() {
        if (gameView != null) {
            gameView.destroy();
            gameView = null;
        }
        super.onDestroy();
    }
}
