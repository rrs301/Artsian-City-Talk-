package com.innocruts.artisans;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//
//import com.daimajia.androidanimations.library.Techniques;
//import com.daimajia.androidviewhover.BlurLayout;

public class animation extends Activity {

    private Context mContext;
  //  private BlurLayout mSampleLayout, mSampleLayout2, mSampleLayout3, mSampleLayout4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        mContext = this;

//        BlurLayout.setGlobalDefaultDuration(450);
//        BlurLayout sampleLayout = (BlurLayout)findViewById(R.id.sample);
//        View hover = LayoutInflater.from(mContext).inflate(R.layout.sample_hover, null);
//        sampleLayout.setHoverView(hover);

        //sampleLayout.setBlurDuration(550);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.animation, menu);
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
