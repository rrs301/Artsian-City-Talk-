package com.innocruts.artisans;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ContactUs extends Activity {
    EditText Msg,BusinessName,BusinessPhone,BusinessService;
    String MsgBody,UserName,UserNo;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        pref=getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        editor=pref.edit();

        Msg=(EditText)findViewById(R.id.textMsg);
        BusinessName=(EditText)findViewById(R.id.businessName);
        BusinessPhone=(EditText)findViewById(R.id.businessPhone);
        BusinessService=(EditText)findViewById(R.id.businessService);
        UserName=pref.getString("UserName",null);
        UserNo=pref.getString("MobileNumber",null);

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
        MsgBody=UserNo;
        String BName=BusinessName.getText().toString().trim();
        String BPhone=BusinessPhone.getText().toString().trim();
        String BService=BusinessService.getText().toString().trim();

        MsgBody=MsgBody+"-"+BName+"-"+BPhone+"-"+BService+"-"+Msg.getText().toString().trim();
        new AsyncHttpTask().execute();


    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(ContactUs.this);
            pd.setMessage("Your msg is sending");
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


                URL url = new URL("http://artisans.host/appBackend/contactUsMail.php?"+UserName+"&msg="+MsgBody.replace(" ","%20"));


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
//                Log.i("This Random", String.valueOf(rand));
//
//                intent.putExtra("OTPCODE",String.valueOf(rand));
//                intent.putExtra("MobileNumber",String.valueOf(MobileNo));
//                intent.putExtra("UserName",String.valueOf(User_Name));
//                intent.putExtra("Location",String.valueOf(Location));
                Toast.makeText(ContactUs.this, "Thank You!, We contact you within 24 hrs", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),MyActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(ContactUs.this, "Try Again...", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
