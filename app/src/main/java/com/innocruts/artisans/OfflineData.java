package com.innocruts.artisans;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OfflineData extends Activity {
    private static final String TAG = "RecyclerViewExample";
    public static int condition = 1;
    private List<FeedItem> feedsList;

    String urlme;
    private RecyclerView mRecyclerView;
   // private OfflineDataMyRecyclerAdapter adapter;
    private ProgressBar progressBar;
    String[] mobileArray;
    List<String> list;
    SQLiteDatabase eventsDB;
    private ListView listView;
    TextView title,address,phone;
    ArrayList<String> names=new ArrayList<String>();
    ArrayList<String> addr=new ArrayList<String>();
    ArrayList<String> cont=new ArrayList<String>();



    private String desc[] = {
            "The Powerful Hypter Text Markup Language 5",
            "Cascading Style Sheets",
            "Code with Java Script",
            "Manage your content with Wordpress"
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobilelist);

       // String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
     list = new ArrayList<String>();

        Offline();


                title=(TextView)findViewById(R.id.textViewName);
              //  address=(TextView)findViewById(R.id.textViewDesc);
                  phone=(TextView)findViewById(R.id.phone);


            CustomList customList = new CustomList(this,names,addr,cont);

            listView = (ListView) findViewById(R.id.mobile_list);
          //  ImageView button=(ImageView)findViewById(R.id.delete);
            listView.setAdapter(customList);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                String nameIs,PhoneIs;
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    Toast.makeText(getApplicationContext(), " " + names.get(i), Toast.LENGTH_SHORT).show();
                    nameIs=names.get(i);
                    PhoneIs=cont.get(i);
//                    final Dialog dialog = new Dialog(OfflineData.this);
//                    dialog.setContentView(R.layout.custom_view_data_dialogbox);
//                    try {
//                        Log.i("Hhhhhhh",nameIs);
//                        title.setText(nameIs);
//                        address.setText(addr.get(i));
//                        phone.setText(cont.get(i));
//                        dialog.setTitle(nameIs);
//
//                    }
//                    catch (Exception e)
//                    {
//
//                    }
//
//
//                    // image.setImageResource(R.drawable.newsplaceholder);
//                    Button button = (Button) dialog.findViewById(R.id.dialogButtonOK);
//                    // if button is clicked, close the custom dialog
//                    button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    dialog.show();


                    new SweetAlertDialog(OfflineData.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText("Are you Want To View Or Delete?")
                            .setCustomImage(R.drawable.question)
                                    // .setContentText("Won't be able to recover this file!")
                            .setCancelText("delete it!")
                            .setConfirmText("Call")
                            .showCancelButton(true)

                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    eventsDB.execSQL("DELETE FROM OfflineAds where title='" + nameIs + "'");
                                    finish();
                                    startActivity(getIntent());
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+PhoneIs));
                                        startActivity(intent);
                                    } catch (android.content.ActivityNotFoundException e) {

                                        Toast.makeText(getApplicationContext(), "Network Is Week", Toast.LENGTH_LONG).show();
                                    }


//                                    Intent intent=new Intent(OfflineData.this,DetailPost.class);
//                                    intent.putExtra("title",names.get(i));
//                                    intent.putExtra("address",addr.get(i));
//
//                                    startActivity(intent);


                                }
                            })
                            .show();

                }


            });

    }


    public  void Offline()
    {
        try {
            eventsDB = this.openOrCreateDatabase("OfflineMode", MODE_PRIVATE, null);

            Cursor c = eventsDB.rawQuery("SELECT DISTINCT title,addr,phone FROM OfflineAds", null);
            try {
                int nameIndex = c.getColumnIndex("title");
                int addrIndex = c.getColumnIndex("addr");
                int phoneIndex = c.getColumnIndex("phone");
                //int idIndex = c.getColumnIndex("id");

                c.moveToFirst();
                // FeedItem item = new FeedItem();
                while (c != null) {

                    Log.i("UserResults - Phone", c.getString(phoneIndex));
                    //item.setTitle(c.getString(nameIndex));
                    names.add(c.getString(nameIndex));
                    addr.add(c.getString(addrIndex));
                    cont.add(c.getString(phoneIndex));
                    c.moveToNext();


                }
            } catch (Exception e) {
                Log.i("Table And Database Created", "Yes");
            }
        }
        catch (Exception e)
        {
            Log.i("Exception Is:",e.getMessage());
        }

//        adapter = new OfflineDataMyRecyclerAdapter(OfflineData.this, feedsList);
//        mRecyclerView.setAdapter(adapter);
//        progressBar.setVisibility(View.GONE);

    }






    //









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.category__result, menu);
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
