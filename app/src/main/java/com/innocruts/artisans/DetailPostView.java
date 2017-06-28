package com.innocruts.artisans;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class DetailPostView extends Activity {

    String Post_Title,Post_Address,Post_Image,Post_Phone,Post_Service,Post_Aboutus,Post_View,Post_Id;
    TextView DPostTitle,DPostAddress,DPostPhone,PostService,PostViewstext,PostAboutus;
    ImageView DPostImage;
    String urlme;
    String Msg;
    String UserMobileNumber,UserName;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    static int t=0;
    int PostViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post_view);
        pref=getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        try {
            UserName=pref.getString("UserName",null);
            UserMobileNumber = pref.getString("MobileNumber", null);
        }
        catch (Exception e)
        {

        }
        Intent intent=getIntent();
        Post_Title=intent.getStringExtra("postTitle");
        Post_Address=intent.getStringExtra("postAddress");
        Post_Image=intent.getStringExtra("postImage");
        Post_Phone=intent.getStringExtra("postPhone");
        Post_Service=intent.getStringExtra("postService");
        Post_Aboutus=intent.getStringExtra("postAboutus");
        Post_View=intent.getStringExtra("PostView");
         PostViews= Integer.parseInt(Post_View)+1;
        Post_Id=intent.getStringExtra("PostId");

        Log.i("PostViews", String.valueOf(PostViews)+"====+"+Post_Id);
        DPostTitle=(TextView)findViewById(R.id.DPostTitle);
        DPostImage=(ImageView)findViewById(R.id.DPostImage);
        DPostAddress=(TextView)findViewById(R.id.DPostAddress);
        DPostPhone=(TextView)findViewById(R.id.PostPhone);
        PostService=(TextView)findViewById(R.id.PostService);
        PostAboutus=(TextView)findViewById(R.id.PostAboutus);
         PostViewstext=(TextView)findViewById(R.id.Views);

        try {
            DPostPhone.setText(Post_Phone);

        }
        catch (Exception e)
        {
            //  Log.i("MSG:",e.getMessage());
        }

        Msg="User"+UserName+"("+UserMobileNumber+") Saw your post";
        Glide
                .with(this)
                .load(Post_Image)
                .centerCrop()
                .placeholder(R.drawable.imageplaceholder)
                .crossFade()
                .into(DPostImage);

        DPostTitle.setText(Post_Title);
        DPostAddress.setText(Post_Address);
        PostService.setText(Post_Service);
        PostAboutus.setText(Post_Aboutus);
        PostViewstext.setText("("+Post_View+")");
        new AsyncHttpTask().execute();
       // DPostPhone.setText(Post_Phone);
//        findViewById(R.id.RateUs).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShowDialog();
//            }
//        });

        findViewById(R.id.Call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               call();
            }
        });

        findViewById(R.id.GetMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetMap();
            }
        });


    }


    public void ShowDialog()
    {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
      //  View dialogView = inflater.inflate(R.layout.rating_bar, null);
//        final RatingBar rating = new RatingBar(this);
//        rating.setMax(5);
//        popDialog.setIcon(android.R.drawable.btn_star_big_on);
//        popDialog.setTitle("Rate Us ");
//
//        popDialog.setView(dialogView);
//        final RatingBar ratingBar=(RatingBar)dialogView.findViewById(R.id.RatingBarn);
//       ratingBar.setMax(5);
//        popDialog.setPositiveButton(android.R.string.ok,
//        new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//
//              //  txtView.setText(String.valueOf(rating.getProgress()));
//                Toast.makeText(getApplicationContext(),String.valueOf(ratingBar.getProgress()),Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//
//            }
//
//        })

// Button Cancel
//
//                .setNegativeButton("Cancel",
//
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                dialog.cancel();
//
//                            }
//
//                        }
//                );
//
//        popDialog.create();
//
//        popDialog.show();

    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
//            pd = new ProgressDialog(DetailPostView.this);
//            pd.setMessage("Creating Your App...");
//            pd.show();

            setProgressBarIndeterminateVisibility(true);
            Log.i("In Pre:", "Yes");
            if(t==0)
            {
                urlme="http://artisans.host/appBackend/Update_PostView.php?views="+PostViews+"&postId="+Post_Id;
            }
            if(t==1)
            {
                Log.i("In Msg Getway IRL","Yes");
                urlme="http://artisans.host/appBackend/SMS/index.php?to="+Post_Phone+"&msg="+Msg.replace(" ","%20");

            }
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            Log.i("In DOIN:", "Yes");
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
                    //  parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                // Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            //progressBar.setVisibility(View.GONE);
        //    pd.dismiss();
            if (result == 1) {
//                Intent intent1 = new Intent(getApplicationContext(), MyActivity.class);
//                startActivity(intent1);
                if(t==0) {
                    t=1;
                    new AsyncHttpTask().execute();

                }
                if(t==1)
                {
                    Toast.makeText(DetailPostView.this, "Thank You", Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(DetailPostView.this, "Try Again...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void call()
    {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Post_Phone));
            startActivity(intent);
        }
        catch (android.content.ActivityNotFoundException e){

            Toast.makeText(getApplicationContext(),"Network Is Week",Toast.LENGTH_LONG).show();
        }
    }

    public void GetMap()
    {

            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr= (%s)", Post_Address);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.detail_post_view, menu);
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
