package com.innocruts.artisans;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class News extends Activity {


    private static final String TAG = "RecyclerViewExample";
    public static int condition = 1;
    private List<FeedItem> feedsList;
  //  final String url;
    String urlme;
    private RecyclerView mRecyclerView;
    private News_Adapter adapter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        urlme="http://innocruts.16mb.com/SmartCityApp/NewsData/Get_News_Data.php";


        // attach click listener to folding cell


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_News);

//        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
//        mRecyclerView.setLayoutManager(mLayoutManager);


          mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
          //mRecyclerView.setLayoutManager(layoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new AsyncHttpTask().execute();
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            setProgressBarIndeterminateVisibility(true);
            Log.i("In Pre:", "Yes");
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
                adapter = new News_Adapter(News.this, feedsList,mRecyclerView);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(News.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
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
                item.setNewsImage(post.optString("image"));
                item.setNewsTitle(post.optString("title"));
                item.setNewsLink(post.optString("link"));
                item.setNewsDescription(post.optString("message"));
                item.setNewsSource(post.optString("caption"));
                //item.(post.optString("address"));

                feedsList.add(item);
                Log.i("NewsTitle;",(post.optString("title")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.news, menu);
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
