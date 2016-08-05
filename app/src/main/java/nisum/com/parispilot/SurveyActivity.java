package nisum.com.parispilot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SurveyActivity extends AppCompatActivity {

    private WebView webView;
    private int pageCounter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);
        final String url_survey = getResources().getString(R.string.url_survey);
        webView = (WebView) findViewById(R.id.webView2);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                paginaCargada();
            }
        });

        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.loadUrl(url_survey);


    }

    private void paginaCargada(){
        if(!(pageCounter < 5)){
            try {
                SurveyHandler.setSurveyAnswered(true, this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        pageCounter++;
    }
}

