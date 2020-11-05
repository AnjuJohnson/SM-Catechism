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
import com.zinedroid.syromalabar.smartcatechism.common.Models.Saints;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.fragments.SaintDetailFragment;

import java.util.ArrayList;

/**
 * Created by Cecil Paul on 9/9/17.
 */

public class SaintsRecyclerAdapter extends RecyclerView.Adapter<SaintsRecyclerAdapter.ViewHolder> {
    private ArrayList<Saints> mSaintsList;
    Context context;
    Utility.HomeTitle homeTitle;
    BaseFragment baseFragment;

    public SaintsRecyclerAdapter(Context context, ArrayList<Saints> mSaintsList) {
        this.mSaintsList = mSaintsList;
        this.context = context;

    }

    public SaintsRecyclerAdapter(BaseFragment context, ArrayList<Saints> mSaintsList) {
        this.mSaintsList = mSaintsList;
        this.context = context.getActivity();
        this.baseFragment = context;
        homeTitle = (Utility.HomeTitle) context.getActivity();

    }

    @Override
    public SaintsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saints_list, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(SaintsRecyclerAdapter.ViewHolder holder, int position) {
        final Saints mSaintsItem = mSaintsList.get(position);
        mSaintsItem.setId("Class" + baseFragment.getSharedPreference(AppConstants.APIKeys.CLASS_ID));
        holder.mSaintNameTextView.setText(mSaintsItem.getName());
        holder.mDateTextView.setText(mSaintsItem.getDate());
        holder.mSaintsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstants.mSaints = new Saints();
                AppConstants.mSaints = mSaintsItem;
                homeTitle.ChangeFragment(new SaintDetailFragment(), true, true);
                homeTitle.onTitleChange("Daily Saints");
            }
        });
        try {
            Picasso.with(context)
                    .load(mSaintsItem.getImage())
//                    .centerCrop()
                    .into(holder.mSaintsImageView);
            Log.i("IMage++", mSaintsItem.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mSaintsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mSaintNameTextView, mDateTextView;
        ImageView mSaintsImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mSaintNameTextView = (TextView) itemView.findViewById(R.id.mSaintNameTextView);
            mDateTextView = (TextView) itemView.findViewById(R.id.mDateTextView);
            mSaintsImageView = (ImageView) itemView.findViewById(R.id.mSaintsImageView);
        }
    }


}
