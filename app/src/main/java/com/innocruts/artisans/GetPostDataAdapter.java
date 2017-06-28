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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 1/14/2017.
 */
public class GetPostDataAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;
    SQLiteDatabase eventsDB;
    RecyclerView recyclerView;
    String Ad;
   int offline=0;

    public GetPostDataAdapter(Context context, List<FeedItem> feedItemList,RecyclerView rv) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.recyclerView=rv;
    }

    //   int conditionn=HomePage.condition;
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_layout, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final FeedItem feedItem = feedItemList.get(i);


        try {
            //Download image using picasso library
//    Picasso.with(mContext).load(feedItem.getCat_Image())
//            .error(R.drawable.placeholder)
//            .placeholder(R.drawable.placeholder)
//            .into(customViewHolder.Cat_Image);

            try {
                if (feedItem.getPostPremium().compareTo("Yes") == 0) {
                    customViewHolder.PostPremium.setVisibility(View.VISIBLE);
                }
            }
            catch (Exception e)
            {
                Log.i("Exception",e.getMessage());
            }
            Glide
                    .with(mContext)
                    .load(feedItem.getPostImage())
                    .centerCrop()
                    .placeholder(R.drawable.imageplaceholder)
                    .crossFade()
                    .into(customViewHolder.PostImage);

            //Log.i("Adv:",Ad);

            //Setting text view title
            customViewHolder.PostTitle.setText(Html.fromHtml(feedItem.getPostTitle()));
            customViewHolder.PostAddress.setText(Html.fromHtml(feedItem.getPostAddress()));
            //customViewHolder.PostTotalRating.setText(Html.fromHtml("("+feedItem.getPostTotalRating()+")"));
            customViewHolder.PostView.setText(Html.fromHtml("("+feedItem.getPostView()+")"));
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
                String shareBody = feedItem.getPostAddress().replaceAll("<[^>]*>", "");
                String sharePhone=feedItem.getPostPhone().replaceAll("<[^>]*>", "");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, feedItem.getPostTitle());
                sharingIntent.putExtra(Intent.EXTRA_TEXT, " Address: "+shareBody+"Phone No: "+sharePhone+" -Download City Talk App");
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        customViewHolder.ButtonLayer.setOnClickListener(clickListener);
       // customViewHolder.Offline.setOnClickListener(clickListener);
        // customViewHolder.BTN.setOnClickListener(clickListener);
        customViewHolder.ButtonLayer.setTag(customViewHolder);
        //customViewHolder.Offline.setTag(customViewHolder);
        customViewHolder.Offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(offline==0) {
                    customViewHolder.Offline.setImageResource(R.drawable.star_fill);
                    Toast.makeText(mContext, "Bookamrked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, DownloadAcitivity.class);
                    intent.putExtra("Category", "Hotel");
                    intent.putExtra("title", feedItem.getPostTitle());
                    intent.putExtra("addr", feedItem.getPostAddress());
                    intent.putExtra("phone", feedItem.getPostPhone());
                    intent.putExtra("service", feedItem.getPostService());
                    //intent.putExtra("title",feedItem.getTitle());

                    mContext.startActivity(intent);
                    Log.i("Data Inserted", "Yes");
                }
                else
                {
                    Toast.makeText(mContext,"Already BookMarked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //customViewHolder.BTN.setTag(customViewHolder);

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
                Intent intent = new Intent(mContext, DetailPostView.class);
                intent.putExtra("postTitle", feedItem.getPostTitle());
                intent.putExtra("postImage", feedItem.getPostImage());
                intent.putExtra("postAddress", feedItem.getPostAddress());
                intent.putExtra("postPhone", feedItem.getPostPhone());
                intent.putExtra("postService", feedItem.getPostService());
                intent.putExtra("postAboutus", feedItem.getPostAboutUs());
                intent.putExtra("PostView", feedItem.getPostView());
                intent.putExtra("PostId", feedItem.getPostId());

                Log.i("PostService", feedItem.getPostService());
                mContext.startActivity(intent);

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
                if (feedItemList.get(i).getPostAddress().toLowerCase().startsWith(text.toLowerCase())) {
                    filterd.add(feedItemList.get(i));
                }
            }
            feedItemList = filterd;
            filterd = null;


        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

}
