package com.innocruts.artisans;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
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

public class Offer_Event extends Activity {

    private static final String TAG = "RecyclerViewExample";
    public static int condition = 1;
    AutoCompleteTextView text;
    MultiAutoCompleteTextView text1;
    String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};
    List<String> list = new ArrayList<String>();
    static  List<FeedItem> feedsList;
    final String url = "http://mauryainfrastructure.com/statusme/get_medical_data.php";
    String urlme="http://artisans.host/appBackend/saveEventData.php";
    private RecyclerView mRecyclerView;
    private Offer_Event_Adapter adapter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_post_data);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

//        list.add("1");
//        list.add("2");

//        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
//        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  mRecyclerView.setLayoutManager(layoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        new AsyncHttpTask().execute();

        text=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        // text1=(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView1);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);

        text.setAdapter(adapter);
        text.setThreshold(1);

        text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                Offer_Event.this.adapter.filter(cs.toString());
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
                adapter = new Offer_Event_Adapter(Offer_Event.this, feedsList,mRecyclerView);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(Offer_Event.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
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
                item.setEventImage(post.optString("image"));
                item.setEventTitle(post.optString("title"));
              //  item.setPostAddress(post.optString("address"));
//                item.setPostPhone(post.optString("phone"));
//                item.setPostService(post.optString("services"));
//                item.setPostAboutUs(post.optString("aboutus"));
//                item.setPostRating(post.optString("rating"));
              //  item.setPostId(post.optString("eid"));
                item.setStartDate(post.optString("startdate"));
                item.setEndDat(post.optString("enddate"));
                item.setPostDescription(post.optString("description"));

                feedsList.add(item);
                list.add(post.optString("title"));
                Log.i("Premium;",(post.optString("title")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.offer__event, menu);
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
