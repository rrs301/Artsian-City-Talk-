package com.innocruts.artisans;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Rahul on 1/14/2017.
 */
public class SubCategoryAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;
    SQLiteDatabase eventsDB;
    String Ad;

    public SubCategoryAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }
 //   int conditionn=HomePage.condition;
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subcategory_layout, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;

    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final FeedItem feedItem = feedItemList.get(i);


        try {
            //Download image using picasso library
//    Picasso.with(mContext).load(feedItem.getCat_Image())
//            .error(R.drawable.placeholder)
//            .placeholder(R.drawable.placeholder)
//            .into(customViewHolder.Cat_Image);
            Glide
                    .with(mContext)
                    .load(feedItem.getSubCatImage())
                    .centerCrop()
                    .placeholder(R.drawable.policeman)
                    .crossFade()
                    .into(customViewHolder.SubCatImage);

            //Log.i("Adv:",Ad);

            //Setting text view title
            customViewHolder.SubCatName.setText(Html.fromHtml(feedItem.getSubCatName()));


        }
        catch (Exception e) {
            Log.i("Yes", "Error");
        }

        customViewHolder.SubCatImage.setOnClickListener(clickListener);
        customViewHolder.SubCatName.setOnClickListener(clickListener);

        customViewHolder.SubCatImage.setTag(customViewHolder);
        customViewHolder.SubCatName.setTag(customViewHolder);

    }




    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                CustomViewHolder holder = (CustomViewHolder) view.getTag();
                int position = holder.getPosition();
                Log.i("Click", "Click");
                FeedItem feedItem = feedItemList.get(position);
              //  Toast.makeText(mContext, feedItem.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, GetPostData.class);
                intent.putExtra("Sid", feedItem.getSid());

                mContext.startActivity(intent);

            }
            catch (Exception e)
            {

            }
        }
    };



    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

}


