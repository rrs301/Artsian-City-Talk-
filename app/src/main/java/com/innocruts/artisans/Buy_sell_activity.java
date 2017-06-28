package com.innocruts.artisans;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Buy_sell_activity extends Activity {

    private static final String TAG = "RecyclerViewExample";
    public static int condition = 1;
    AutoCompleteTextView text;
    static  List<FeedItem> feedsList;
    List<String> list = new ArrayList<String>();
    final String url = "http://artisans.host/appBackend/get_medical_data.php";
    String urlme;
    private RecyclerView mRecyclerView;
    private buy_sell_adapter adapter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell_activity);

//        Intent intent=getIntent();
//        String SubCatTitle=intent.getStringExtra("SubCatTitle").trim();
//        Log.i("SubCat:", SubCatTitle);
        urlme="http://artisans.host/appBackend/getSellRent_ads.php";
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
        mRecyclerView.setLayoutManager(mLayoutManager);


      //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  mRecyclerView.setLayoutManager(layoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Downloading data from below url

//        EditText srchname = (EditText) findViewById(R.id.srchname);
//        srchname.setInputType(InputType.TYPE_NULL);
//
//        srchname.setInputType(InputType.TYPE_CLASS_TEXT);
//        srchname.requestFocus();
//        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        mgr.showSoftInput(srchname, InputMethodManager.SHOW_FORCED);
        // Downloading data from below url
//        srchname.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                // When user changed the Text
//                Buy_sell_activity.this.adapter.filter(cs.toString());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//                                          int arg3) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//            }
//        });

        text=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        // text1=(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView1);
        text.setDropDownBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.dropdown)));


        new AsyncHttpTask().execute();

          ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);

          text.setAdapter(adapter);
        text.setThreshold(1);

        text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                Buy_sell_activity.this.adapter.filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }



    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            setProgressBarIndeterminateVisibility(true);
            Log.i("In Pre:","Yes");
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            Log.i("In DOIN:","Yes");
            try {


                URL url = new URL(urlme);


                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new buy_sell_adapter(Buy_sell_activity.this, feedsList,mRecyclerView);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(Buy_sell_activity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //
    private void parseResult(String result) {
        Log.i("InParse","Yes");
        try {
            Log.i("InParseIn","Yes");

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");

            feedsList = new ArrayList();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setAdsImage(post.optString("image"));
                item.setAdsTitle(post.optString("title"));
                item.setAdsCatgeory(post.optString("category"));
                item.setAdsPrice(post.optString("price"));
                item.setAdsSellRent(post.optString("sellrent"));
                item.setAdsid(post.optString("id"));
                item.setAdsPersonName(post.optString("personName"));
                item.setAdsContact(post.optString("contact"));
                item.setAdsDecription(post.optString("description"));
                item.setAdsAddress(post.optString("address"));

                feedsList.add(item);
                list.add(post.optString("title"));
                //Log.i("SubCat;",(post.optString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

public void AddNewAd(View view)
{
    Intent intent=new Intent(this,StoreSellBanner.class);
    startActivity(intent);

}





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.buy_sell_activity, menu);
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
