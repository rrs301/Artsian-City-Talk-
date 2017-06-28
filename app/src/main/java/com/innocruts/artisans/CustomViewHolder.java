package com.innocruts.artisans;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;


/**
 * Created by Rahul on 2/4/2016.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {
    protected ImageView SubCatImage,PostImage,SliderImage,AdsImage,AdsRentSell,NewsImage,PostPremium,EventImage,Share;
    protected TextView SubCatName,PostTitle,PostAddress,PostView,PostRating,PostTotalRating,AdsTitle,AdsPrice,AdsCategory,PostDecription;
    protected Button BTN,ButtonLayer;
    ImageView popup,Offline;
    protected TextView NewsTitle,NewsDescription,NewsSource,NewsLink,StartDate,EndDate,EventTitle;
    protected RatingBar ratingBar;
    protected FoldingCell fc;
    public CustomViewHolder(View view) {
        super(view);

        this.SubCatImage=(ImageView)view.findViewById(R.id.SubCatImage);
        this.SubCatName=(TextView)view.findViewById(R.id.SubCategoryName);
        this.PostImage=(ImageView)view.findViewById(R.id.PostImage);
        this.PostTitle=(TextView)view.findViewById(R.id.PostTitle);
        this.PostAddress=(TextView)view.findViewById(R.id.PostAddress);
        this.SliderImage=(ImageView)view.findViewById(R.id.SliderImage);
        this.AdsTitle=(TextView)view.findViewById(R.id.AdTitle);
        this.AdsCategory=(TextView)view.findViewById(R.id.AdsCategory);
        this.AdsPrice=(TextView)view.findViewById(R.id.AdsPrice);
        this.AdsImage=(ImageView)view.findViewById(R.id.AdsImage);
        this.AdsRentSell=(ImageView)view.findViewById(R.id.rent_sell_tag);
        this.PostPremium=(ImageView)view.findViewById(R.id.premiumtag);
        this.EventTitle=(TextView)view.findViewById(R.id.PostTitle);
        this.ButtonLayer=(Button)view.findViewById(R.id.ButtonLayer);
        this.EventImage=(ImageView)view.findViewById(R.id.PostImage);
        this.popup=(ImageView)view.findViewById(R.id.popupimage);
        this.Offline=(ImageView)view.findViewById(R.id.offline);
      this.fc = (FoldingCell)view.findViewById(R.id.folding_cell);
//       // this.PostRating=(TextView)view.findViewById(R.id.TotalRating);
//       // this.PostTotalRating=(TextView)view.findViewById(R.id.PostTitle);
//        //this.ratingBar=(RatingBar)view.findViewById(R.id.RatingBar);
        this.PostView=(TextView)view.findViewById(R.id.Views);
        this.NewsTitle=(TextView)view.findViewById(R.id.NewsTitle);
       // this.NewsLink=(TextView)view.findViewById(R.id.New);
        this.NewsSource=(TextView)view.findViewById(R.id.source);
        this.NewsDescription=(TextView)view.findViewById(R.id.NewsDescripton);
        this.NewsImage=(ImageView)view.findViewById(R.id.NewsImage);
        this.Share=(ImageView)view.findViewById(R.id.share);
//        // this.AdsRentSell=(Ima)view.findViewById(R.id.rent_sell_tag);
        this.PostDecription=(TextView)view.findViewById(R.id.postDescription);
        this.StartDate=(TextView)view.findViewById(R.id.StartDate);
//       // this.BTN=(Button)view.findViewById(R.id.BTN);
        this.EndDate=(TextView)view.findViewById(R.id.EndDate);

    }
}