package com.zinedroid.syromalabar.smartcatechism.common.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Models.Chapter;
import com.zinedroid.syromalabar.smartcatechism.common.Models.Resource;
import com.zinedroid.syromalabar.smartcatechism.common.Models.Saints;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.fragments.SaintDetailFragment;

import java.util.ArrayList;

/**
 * Created by Cecil Paul on 9/9/17.
 */

public class ResourceRecyclerAdapter extends RecyclerView.Adapter<ResourceRecyclerAdapter.ViewHolder> {
    private ArrayList<Resource> mResourceList;
    Context context;
    Utility.HomeTitle homeTitle;
    BaseFragment baseFragment;

    public ResourceRecyclerAdapter(Context context, ArrayList<Resource> mResourceList) {
        this.mResourceList = mResourceList;
        this.context = context;

    }

    public ResourceRecyclerAdapter(BaseFragment context, ArrayList<Resource> mResourceList) {
        this.mResourceList = mResourceList;
        this.context = context.getActivity();
        this.baseFragment = context;
        homeTitle = (Utility.HomeTitle) context.getActivity();

    }

    @Override
    public ResourceRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resource_list, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ResourceRecyclerAdapter.ViewHolder holder, int position) {
        final Resource mResourceItem = mResourceList.get(position);
        holder.mResourceTitleTextView.setText(mResourceItem.getName());

        holder.mResourceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstants.mChapter = new Chapter();
                AppConstants.mResource = mResourceItem;
//                homeTitle.ChangeFragment(new ChapterDetailFragment(), true, true);
//                homeTitle.onTitleChange(mResourceItem.getChapterNo());
                if (!mResourceItem.getVideoUrl().equalsIgnoreCase("")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + mResourceItem.getVideoUrl()));
//                intent.putExtra("VIDEO_ID", mResourceItem.getVideoUrl());
                    intent.putExtra("force_fullscreen", true);
                    context.startActivity(intent);
                } else if (!mResourceItem.getDescription().equalsIgnoreCase("")) {
                    AppConstants.mSaints = new Saints();
                    AppConstants.mSaints.setName(mResourceItem.getName());
                    AppConstants.mSaints.setDetails(mResourceItem.getDescription());
                    AppConstants.mSaints.setImage(mResourceItem.getImageUrl());
                    homeTitle.ChangeFragment(new SaintDetailFragment(), true, true);
                    homeTitle.onTitleChange(mResourceItem.getName());
                }
            }
        });
        if (mResourceItem.getVideoUrl().equalsIgnoreCase("")) {
            holder.mPlayIconImageView.setVisibility(View.INVISIBLE);
        } else {
            holder.mPlayIconImageView.setVisibility(View.VISIBLE);
            String imageUrl = "http://img.youtube.com/vi/" + mResourceItem.getVideoUrl() + "/0.jpg";
            Picasso.with(context)
                    .load(imageUrl)
//                    .centerCrop()
                    .into(holder.mResourceImageView);
        }
        try {
            Picasso.with(context)
                    .load(mResourceItem.getImageUrl())
//                    .centerCrop()
                    .into(holder.mResourceImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mResourceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mResourceTitleTextView;
        ImageView mResourceImageView, mPlayIconImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mResourceTitleTextView = (TextView) itemView.findViewById(R.id.mResourceTitleTextView);
            mResourceImageView = (ImageView) itemView.findViewById(R.id.mResourceImageView);
            mPlayIconImageView = (ImageView) itemView.findViewById(R.id.mPlayIconImageView);
        }
    }


}
