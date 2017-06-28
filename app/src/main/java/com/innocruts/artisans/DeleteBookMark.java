package com.innocruts.artisans;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class DeleteBookMark extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book_mark);

        Intent intent=getIntent();
        String Pid=intent.getStringExtra("Pid");

        SQLiteDatabase eventsDB = this.openOrCreateDatabase("Offline", MODE_PRIVATE, null);
        eventsDB.execSQL("DELETE FROM OfflineAdstrynm where AdId="+Pid);
        Log.i("Data Deleted","Yes");


        finish();

//        try {
//            String Test = intent.getStringExtra("test");
//            if(Test.compareTo("true")==0) {
//                Intent intent2 = new Intent(getApplicationContext(), GetPostData.class);
//                intent2.putExtra("MyFav", "true");
//
//                startActivity(intent2);
//            }
//        }
//        catch (Exception e)
//        {
//
//        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.delete_book_mark, menu);
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
