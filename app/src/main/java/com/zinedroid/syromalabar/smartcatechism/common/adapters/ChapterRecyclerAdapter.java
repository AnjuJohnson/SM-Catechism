package com.zinedroid.syromalabar.smartcatechism.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.fragments.ChapterDetailFragment;

import java.util.ArrayList;

/**
 * Created by Cecil Paul on 9/9/17.
 */

public class ChapterRecyclerAdapter extends RecyclerView.Adapter<ChapterRecyclerAdapter.ViewHolder> {
    private ArrayList<Chapter> mChapterList;
    Context context;
    Utility.HomeTitle homeTitle;
    BaseFragment baseFragment;

    public ChapterRecyclerAdapter(Context context, ArrayList<Chapter> mChapterList) {
        this.mChapterList = mChapterList;
        this.context = context;

    }

    public ChapterRecyclerAdapter(BaseFragment context, ArrayList<Chapter> mChapterList) {
        this.mChapterList = mChapterList;
        this.context = context.getActivity();
        this.baseFragment = context;
        homeTitle = (Utility.HomeTitle) context.getActivity();

    }

    @Override
    public ChapterRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chapter_list, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ChapterRecyclerAdapter.ViewHolder holder, int position) {
        final Chapter mChapterItem = mChapterList.get(position);
        mChapterItem.setClassId(baseFragment.getSharedPreference(AppConstants.APIKeys.CLASS_ID));
        holder.mTitleTextView.setText(mChapterItem.getChapterTitle());
        holder.mChapterNoTextView.setText(mChapterItem.getChapterNo());
        holder.mChapterCoverImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstants.mChapter = new Chapter();
                AppConstants.mChapter = mChapterItem;
                baseFragment.setSharedPreference(AppConstants.APIKeys.CHAPTER_ID, mChapterItem.getChapterId());
                homeTitle.ChangeFragment(new ChapterDetailFragment(), true, true);
                homeTitle.onTitleChange(mChapterItem.getChapterNo());
            }
        });
        try {
            Picasso.with(context)
                    .load(mChapterItem.getChapterImage())
//                    .centerCrop()
                    .into(holder.mChapterCoverImageView);
            Log.i("IMage++", mChapterItem.getChapterImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mChapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView, mChapterNoTextView;
        ImageView mChapterCoverImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.mTitleTextView);
            mChapterNoTextView = (TextView) itemView.findViewById(R.id.mChapterNoTextView);
            mChapterCoverImageView = (ImageView) itemView.findViewById(R.id.mChapterCoverImageView);
        }
    }


}
