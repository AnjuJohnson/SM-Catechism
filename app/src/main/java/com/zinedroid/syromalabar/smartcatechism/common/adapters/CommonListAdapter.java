package com.zinedroid.syromalabar.smartcatechism.common.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.common.Models.SingleItem;

import java.util.ArrayList;

/**
 * Created by Cecil Paul on 31/8/17.
 */

public class CommonListAdapter extends ArrayAdapter<SingleItem> {
    Context context;
    int resource;
    ArrayList<SingleItem> objects;

    public CommonListAdapter(Context context, int resource, ArrayList<SingleItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(resource, null);
            mViewHolder.mtitleTextView = (TextView) convertView.findViewById(R.id.mContentTextView);
            mViewHolder.mIconImageView = (ImageView) convertView.findViewById(R.id.mChapterCoverImageView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        Log.i("Image_Url++", getItem(position).getImageUrl() + " " + position);
        try {
            Picasso.with(context)
//                    .load(R.drawable.placeholder)
                    .load(getItem(position).getImageUrl())
//                    .placeholder(R.drawable.placeholder)
//                    .centerCrop()
                    .fit()
                    .into(mViewHolder.mIconImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getItem(position).getName().equalsIgnoreCase("English")) {
            convertView.setBackground(context.getResources().getDrawable(R.drawable.bg_button_cyan));
        }
        if (getItem(position).getName().equalsIgnoreCase("\u0d2e\u0d32\u0d2f\u0d3e\u0d33\u0d02")) {
            convertView.setBackground(context.getResources().getDrawable(R.drawable.bg_button_red));
        }
        if (getItem(position).getName().equalsIgnoreCase("\u0939\u093f\u0928\u094d\u0926\u0940")) {
            convertView.setBackground(context.getResources().getDrawable(R.drawable.bg_button_blue));
        }
        mViewHolder.mtitleTextView.setText(getItem(position).getName());
        return convertView;
    }

    private class ViewHolder {
        TextView mtitleTextView;
        ImageView mIconImageView;
    }
}
