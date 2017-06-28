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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 1/14/2017.
 */
public class Offer_Event_Adapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;
    SQLiteDatabase eventsDB;
    RecyclerView recyclerView;
    String Ad;

    public Offer_Event_Adapter(Context context, List<FeedItem> feedItemList, RecyclerView rv) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.recyclerView=rv;
    }

    //   int conditionn=HomePage.condition;
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_offer__event, null);
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
                    .load(feedItem.getEventImage())
                    .centerCrop()
                    .placeholder(R.drawable.imageplaceholder)
                    .crossFade()
                    .into(customViewHolder.EventImage);

            //Log.i("Adv:",Ad);

            //Setting text view title
            customViewHolder.EventTitle.setText(Html.fromHtml(feedItem.getEventTitle()));
            customViewHolder.PostDecription.setText(Html.fromHtml(feedItem.getPostDescription()));
            //customViewHolder.PostTotalRating.setText(Html.fromHtml("("+feedItem.getPostTotalRating()+")"));
            customViewHolder.EndDate.setText(Html.fromHtml("End Date: "+feedItem.getEndDat()));
            customViewHolder.StartDate.setText(Html.fromHtml("Start Date: "+feedItem.getStartDate()));

            // customViewHolder.PostAddress.setText(Html.fromHtml(feedItem.getPostAddress()));



        } catch (Exception e) {
            Log.i("Yes", "Error");
        }

        customViewHolder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.e("msg", feedItem.getTitle());
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
              //  String shareBody = feedItem.getPostAddress().replaceAll("<[^>]*>", "");
                String sharePhone=feedItem.getEndDat().replaceAll("<[^>]*>", "");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, feedItem.getEventTitle());
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "End on: "+sharePhone+" Download City Talk App");
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        customViewHolder.ButtonLayer.setOnClickListener(clickListener);

//        customViewHolder.EndDate.setOnClickListener(clickListener);
//         customViewHolder.PostDecription.setOnClickListener(clickListener);
       // customViewHolder.ButtonLayer.setTag(customViewHolder);
       // customViewHolder.PostTitle.setTag(customViewHolder);
        customViewHolder.ButtonLayer.setTag(customViewHolder);

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
//                Intent intent = new Intent(mContext, DetailPostView.class);
//                intent.putExtra("postTitle", feedItem.getPostTitle());
//                intent.putExtra("postImage", feedItem.getPostImage());
//                intent.putExtra("postAddress", feedItem.getPostAddress());
//                intent.putExtra("postPhone", feedItem.getPostPhone());
//                intent.putExtra("postService", feedItem.getPostService());
//                intent.putExtra("postAboutus", feedItem.getPostAboutUs());
//                intent.putExtra("PostView", feedItem.getPostView());
//                intent.putExtra("PostId", feedItem.getPostId());
//
//                Log.i("PostService", feedItem.getPostService());
//                mContext.startActivity(intent);


            } catch (Exception e) {

            }
        }
    };


    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }


    public void filter(String text) {
        feedItemList = GetPostData.feedsList;
        if (text.length() > 0) {
            List<FeedItem> filterd = new ArrayList<FeedItem>();
            for (int i = 0; i < feedItemList.size(); i++) {
                if (feedItemList.get(i).getPostTitle().toLowerCase().startsWith(text.toLowerCase())) {
                    filterd.add(feedItemList.get(i));
                }
            }
            feedItemList = filterd;
            filterd = null;


        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

}
