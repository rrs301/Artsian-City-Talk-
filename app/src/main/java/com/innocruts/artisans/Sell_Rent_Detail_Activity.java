package com.innocruts.artisans;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class Sell_Rent_Detail_Activity extends Activity {

    ImageView AdsImage;
    String adsTitle,adsPhone,adsPrice,adsUserName,adsDesscription,adsAddress,adsCategory,adsImage;
    TextView AdsTitle,AdsPhone,AdsAddress,AdsDescription,AdsUserName,AdsPrice,AdsCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell__rent__detail_);

        Intent intent=getIntent();
        adsTitle=intent.getStringExtra("Title").trim();
        adsAddress=intent.getStringExtra("Address").trim();
        adsCategory=intent.getStringExtra("Category").trim();
        adsDesscription=intent.getStringExtra("Description").trim();
        adsPhone=intent.getStringExtra("Phone").trim();
        adsPrice=intent.getStringExtra("Price").trim();
        adsUserName=intent.getStringExtra("UserName").trim();
        adsImage=intent.getStringExtra("Image").trim();


        AdsImage=(ImageView)findViewById(R.id.AdsImage);
        AdsTitle=(TextView)findViewById(R.id.AdTitle);
        AdsPrice=(TextView)findViewById(R.id.AdsPrice);
        AdsPhone=(TextView)findViewById(R.id.AdsPhone);
        AdsUserName=(TextView)findViewById(R.id.AdsUserName);
        AdsAddress=(TextView)findViewById(R.id.AdsAddress);
        AdsDescription=(TextView)findViewById(R.id.AdsDescription);
        AdsCategory=(TextView)findViewById(R.id.AdsCategory);



        Glide
                .with(this)
                .load(adsImage)
                .centerCrop()
                .placeholder(R.drawable.imageplaceholder)
                .crossFade()
                .into(AdsImage);

        AdsTitle.setText(adsTitle);
        AdsPrice.setText("Rs."+adsPrice);
        AdsPhone.setText(adsPhone);
        AdsUserName.setText(adsUserName);
        AdsAddress.setText(adsAddress);
        AdsDescription.setText(adsDesscription);
        AdsCategory.setText(adsCategory);


    }
    public void Call(View view)
    {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+adsPhone));
            startActivity(intent);
        }
        catch (android.content.ActivityNotFoundException e){

            Toast.makeText(getApplicationContext(), "Network Is Week", Toast.LENGTH_LONG).show();
        }
    }

    public void GetMap(View view)
    {

        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr= (%s)", adsAddress);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.sell__rent__detail_, menu);
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
