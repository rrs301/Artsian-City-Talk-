package com.innocruts.artisans;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OTP_Check extends Activity {
    EditText OTPCode;
    String OTP,User_Name,MobileNumber,Location;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__check);
        pref=getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        Intent intent=getIntent();
        OTP=intent.getStringExtra("OTPCODE");
        User_Name=intent.getStringExtra("UserName");
        Location=intent.getStringExtra("Location");
        MobileNumber=intent.getStringExtra("MobileNumber");
        Log.i("GENOTP:",OTP);
         OTPCode=(EditText)findViewById(R.id.OTPCode);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Welcome To Sell Market", Toast.LENGTH_SHORT).show();
                Sumbit();
            }
        });
    }

    public void Sumbit()
    {
        String OTPEdit=OTPCode.getText().toString();
        Log.i("OTP:",OTPEdit);
        if(OTPEdit.compareTo(OTP)==0)
        {
            editor.putString("UserName",User_Name);
            editor.putString("MobileNumber",MobileNumber);
            editor.commit();
           new AsyncHttpTask().execute();
        }
        else
        {
            Toast.makeText(this,"OTP is Incorrect",Toast.LENGTH_LONG).show();
        }
    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(OTP_Check.this);
            pd.setMessage("Creating Your App...");
            pd.show();
            setProgressBarIndeterminateVisibility(true);
            Log.i("In Pre:", "Yes");
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            Log.i("In DOIN:","Yes");
            try {


                URL url = new URL("http://artisans.host/appBackend/SaveUserData.php?username="+User_Name.replace(" ","%20")+"&mobileno="+MobileNumber+"&location="+Location);


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
            pd.dismiss();
            if (result == 1) {
                editor.putString("Login","True");
                editor.commit();
                Intent intent1=new Intent(getApplicationContext(),MyActivity.class);
                startActivity(intent1);
            } else {
                Toast.makeText(OTP_Check.this, "Try Again...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void backTo(View view)
    {
        this.finish();
       Intent intent=new Intent(this,MobileVerification.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.otp__check, menu);
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
