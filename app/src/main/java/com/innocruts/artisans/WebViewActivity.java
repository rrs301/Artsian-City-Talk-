package com.innocruts.artisans;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class WebViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ImageView imageView=(ImageView)findViewById(R.id.headimage);
        ImageView imageView1=(ImageView)findViewById(R.id.headimage1);
        ImageView imageView2=(ImageView)findViewById(R.id.headimage2);


        Intent intent=getIntent();
        String NewsUrl=intent.getStringExtra("UrlToLoad").trim();

        if(NewsUrl.compareTo("http://music.uodoo.com/")==0)
        {
            imageView.setVisibility(View.GONE);
        }
        else if(NewsUrl.compareTo("http://uccricket.ucweb.com/")==0)
        {
            imageView.setVisibility(View.VISIBLE);
        }
        else if(NewsUrl.compareTo("https://www.railyatri.in")==0)
        {
            imageView1.setVisibility(View.VISIBLE);
        }
        else if(NewsUrl.compareTo("http://www.ucnews.in/channel/101")==0)
        {

            imageView2.setVisibility(View.VISIBLE);

        }

            Toast.makeText(this, "Please Wait...", Toast.LENGTH_LONG).show();
        WebView webView=(WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(NewsUrl);

        webView.setOnKeyListener(new View.OnKeyListener() {
                                     @Override
                                     public boolean onKey(View v, int keyCode, KeyEvent event) {
                                         if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                             WebView webView = (WebView) v;

                                             switch (keyCode) {
                                                 case KeyEvent.KEYCODE_BACK:
                                                     if (webView.canGoBack()) {
                                                         webView.goBack();
                                                         return true;
                                                     }
                                                     break;
                                             }
                                         }

                                         return false;
                                     }
                                 }
        );

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
