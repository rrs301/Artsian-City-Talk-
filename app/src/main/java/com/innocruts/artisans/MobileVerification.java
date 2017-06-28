package com.innocruts.artisans;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MobileVerification extends AppCompatActivity {

    EditText UserName,MobileNumber;
    Context context;
   static int rand;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Spinner spinner;
    String Location;
    String MobileNo,Msg,User_Name;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        pref=getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        editor=pref.edit();

       // spinner=(Spinner)findViewById(R.id.spinner1);

        try {
            if (pref.getString("Login", null).compareTo("True") == 0) {
                Intent intent = new Intent(this, MyActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception e)
        {

        }


            UserName=(EditText)findViewById(R.id.UserNameEditText);
            MobileNumber=(EditText)findViewById(R.id.MobileNumberEditText);

            Random r = new Random();
            rand = r.nextInt(9999 - 1000) + 1000;

            findViewById(R.id.verify).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(getApplicationContext(), "Welcome To Sell Market", Toast.LENGTH_SHORT).show();
                    MobileVerify();
                }
            });



    }

    public void MobileVerify()
    {

        MobileNo=MobileNumber.getText().toString();
        User_Name=UserName.getText().toString();
       // Location=spinner.getSelectedItem().toString();
    Location="Nigeria";

        Msg="This Is Your OTP:"+rand +" for Smart City Login";
        if(MobileNo.length()==10 && UserName.getText().toString()!=null) {
            new AsyncHttpTask().execute();
        }
        else
        {
            Toast.makeText(this,"Please fill all record correctly",Toast.LENGTH_SHORT).show();
        }
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MobileVerification.this);
            pd.setMessage("Verification OTP Sending...");
            pd.show();

            Toast.makeText(getApplicationContext(),"OTP iS: "+rand,Toast.LENGTH_SHORT).show();
            setProgressBarIndeterminateVisibility(true);
            Log.i("In Pre:", "Yes");
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            Log.i("In DOIN:","Yes");
            try {


                URL url = new URL("http://artisans.host/appBackend/SMS/index.php?to="+MobileNo+"&msg="+Msg.replace(" ","%20"));


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
                Log.i("This Random", String.valueOf(rand));
                Intent intent=new Intent(getApplicationContext(),OTP_Check.class);
                intent.putExtra("OTPCODE",String.valueOf(rand));
                intent.putExtra("MobileNumber",String.valueOf(MobileNo));
                intent.putExtra("UserName",String.valueOf(User_Name));
                intent.putExtra("Location",String.valueOf(Location));

                startActivity(intent);
            } else {
                Toast.makeText(MobileVerification.this, "Try Again...", Toast.LENGTH_SHORT).show();
            }
        }
    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.mobile_verification, menu);
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
