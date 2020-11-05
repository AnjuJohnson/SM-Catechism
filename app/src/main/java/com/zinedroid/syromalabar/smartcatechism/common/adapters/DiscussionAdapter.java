package com.zinedroid.syromalabar.smartcatechism.common.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ChapterDiscussion;

import java.util.ArrayList;

/**
 * Created by Cecil Paul on 9/9/17.
 */

public class DiscussionAdapter extends ArrayAdapter<ChapterDiscussion> {
    Context context;

    public DiscussionAdapter(Context context, ArrayList<ChapterDiscussion> paragraphsArrayList) {
        super(context, R.layout.item_discussion_list, paragraphsArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_discussion_list, null);
            mViewHolder.mContentTextView = (TextView) convertView.findViewById(R.id.mContentTextView);
            mViewHolder.mSerialTextView = (TextView) convertView.findViewById(R.id.mSerialTextView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mSerialTextView.setText((position + 1) + ". ");
        mViewHolder.mContentTextView.setText(getItem(position).getContent());
        return convertView;
    }

    public class ViewHolder {
        TextView mSerialTextView, mContentTextView;
    }
}
