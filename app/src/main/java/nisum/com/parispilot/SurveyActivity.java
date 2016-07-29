package nisum.com.parispilot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey);
        final String url_survey = getResources().getString(R.string.url_survey);
        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setWebChromeClient(new WebChromeClient());
        String string = "Se ha registrado tu respuesta.";
        myWebView.findAllAsync(string); //works for API levels 16 and up
        myWebView.clearCache(true);
        myWebView.clearHistory();
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.loadUrl(url_survey);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton:
                Intent mainIntent = new Intent(SurveyActivity.this, MainActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                SurveyActivity.this.startActivity(mainIntent);
                break;


        }
    }


}

