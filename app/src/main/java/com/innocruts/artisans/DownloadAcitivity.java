package com.innocruts.artisans;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class DownloadAcitivity extends Activity {

    String Category,Title,address,phone,service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_acitivity);

        Intent i=getIntent();
       // Category=i.getStringExtra("Category").trim();
        Title=i.getStringExtra("title").trim();
        address=i.getStringExtra("addr").trim();
        phone=i.getStringExtra("phone").trim();
        service=i.getStringExtra("service").trim();


        Log.i("Phine Number is:",phone);
        SqlDatabase();
       // Intent intent=new Intent(this,Category_Result.class);
        //intent.putExtra("Category",Category);
        //startActivity(intent);
        finish();


    }

    public  void GoBack(View view)
    {


    }


    public void SqlDatabase() {
        SQLiteDatabase eventsDB = this.openOrCreateDatabase("OfflineMode", MODE_PRIVATE, null);

       eventsDB.execSQL("CREATE TABLE IF NOT EXISTS OfflineAds (id INTEGER PRIMARY KEY, AdId INTEGER(3),title VARCHAR, addr VARCHAR, phone INTEGER(15), service VARCHAR);");
        //eventsDB.execSQL("CREATE TABLE IF NOT EXISTS OfflineAdstryn (id INTEGER PRIMARY KEY,name VARCHAR);");
       //eventsDB.execSQL("DELETE FROM OfflineAdstrynm");
        try {
            eventsDB.execSQL("INSERT INTO OfflineAds (AdId,title,addr,phone,service) VALUES (1,'" + Title + "','" + address + "','" + phone + "','"  + service + "')");
            // eventsDB.execSQL("INSERT INTO OfflineAdstrynm (AdId,title,addr,email,phone,category,time,service) VALUES (2,'New Juliat','BBBhinejwadi','ddd@gmaill.com',123456789,'Hotel','10AM','All')");
        }
        catch (Exception e)
        {
            Log.i("Error At:",e.getMessage());
        }
        // eventsDB.execSQL("INSERT INTO OfflineAdstryn (name) VALUES ('rahul')");
        //eventsDB.execSQL("INSERT INTO OfflineAdstryn (name) VALUES ('sanap')");

        Cursor c = eventsDB.rawQuery("SELECT * FROM OfflineAds", null);
try {
    int nameIndex = c.getColumnIndex("title");
    int addrIndex = c.getColumnIndex("addr");
      int IdIndex = c.getColumnIndex("AdId");
    // int idIndex = c.getColumnIndex("id");

    c.moveToFirst();

    while (c != null) {

        Log.i("UserResults - name", c.getString(nameIndex));
         Log.i("UserResults - age", (c.getString(addrIndex)));
            Log.i("UserResults - id", Integer.toString(c.getInt(IdIndex)));

        c.moveToNext();
    }
}
catch (Exception e) {
    Log.i("Table And Database Created", "Yes");
}
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.download_acitivity, menu);
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
