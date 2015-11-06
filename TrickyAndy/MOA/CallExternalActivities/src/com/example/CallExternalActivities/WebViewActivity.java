package com.example.CallExternalActivities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by Andrej on 06.11.2015.
 */
public class WebViewActivity extends Activity {

    private WebView _webView;
    private Uri _uri;
    private Intent _intent;

    //<editor-fold desc="Getter & Setter">

    public WebView get_webView() {
        return _webView;
    }

    public void set_webView(WebView _webView) {
        this._webView = _webView;
    }

    public Uri get_uri() {
        return _uri;
    }

    public void set_uri(Uri _uri) {
        this._uri = _uri;
    }

    public Intent get_intent() {
        return _intent;
    }

    public void set_intent(Intent _intent) {
        this._intent = _intent;
    }

    //</editor-fold>

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.layout_webview);

        this.set_intent(getIntent());
        this.set_uri(Uri.parse(this.get_intent().getStringExtra("uri")));
        this.set_webView((WebView) findViewById(R.id.webview));
        this.get_webView().loadUrl(this.get_uri().toString());
    }

}