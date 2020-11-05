package com.zinedroid.syromalabar.smartcatechism.common.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ChapterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.zinedroid.syromalabar.smartcatechism.R.id.mQuestionradioGroup;


/**
 * Created by Cecil Paul on 22/9/17.
 */

public class ChapterActivityAdapter extends ArrayAdapter<ChapterActivity> {
    Context context;
    int resource;

    HashMap<Integer, String> mOptionListMap;

    public ChapterActivityAdapter(Context context, ArrayList<ChapterActivity> mChapterActivityList) {
        super(context, R.layout.item_chapter_activities, mChapterActivityList);
        this.context = context;
        this.resource = R.layout.item_chapter_activities;
        this.mChapterActivityList = mChapterActivityList;
        mOptionListMap = new HashMap<>();
        mOptionListMap.put(R.id.mOptionRadioButton1, "1");
        mOptionListMap.put(R.id.mOptionRadioButton2, "2");
        mOptionListMap.put(R.id.mOptionRadioButton3, "3");
        mOptionListMap.put(R.id.mOptionRadioButton4, "4");

    }

    List<ChapterActivity> mChapterActivityList;


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(resource, null);
            mViewHolder.mQuestionTextView = (TextView) convertView.findViewById(R.id.mQuestionTextView);
            mViewHolder.mQuestionradioGroup = (RadioGroup) convertView.findViewById(mQuestionradioGroup);
            mViewHolder.mSubmitButton = (Button) convertView.findViewById(R.id.mSubmitButton);

            mViewHolder.mOptionRadioButton1 = (RadioButton) convertView.findViewById(R.id.mOptionRadioButton1);
            mViewHolder.mOptionRadioButton2 = (RadioButton) convertView.findViewById(R.id.mOptionRadioButton2);
            mViewHolder.mOptionRadioButton3 = (RadioButton) convertView.findViewById(R.id.mOptionRadioButton3);
            mViewHolder.mOptionRadioButton4 = (RadioButton) convertView.findViewById(R.id.mOptionRadioButton4);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mQuestionTextView.setText((position + 1 )+ ". " + getItem(position).getQueston());
        mViewHolder.mOptionRadioButton1.setText(getItem(position).getOption1());
        mViewHolder.mOptionRadioButton2.setText(getItem(position).getOption2());
        mViewHolder.mOptionRadioButton3.setText(getItem(position).getOption3());
        mViewHolder.mOptionRadioButton4.setText(getItem(position).getOption4());

        final ViewHolder finalMViewHolder = mViewHolder;
        mViewHolder.mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalMViewHolder.mOptionRadioButton1.isChecked()) {
                    if (finalMViewHolder.mOptionRadioButton1.getText().toString().equalsIgnoreCase(getItem(position).getAnswer())) {
                        finalMViewHolder.mOptionRadioButton1.setBackgroundColor(Color.parseColor("#00FF00"));
                    } else {
                        finalMViewHolder.mOptionRadioButton1.setBackgroundColor(Color.parseColor("#FF0000"));
                    }
                } else {
                    if (finalMViewHolder.mOptionRadioButton1.getText().toString().equalsIgnoreCase(getItem(position).getAnswer())) {
                        finalMViewHolder.mOptionRadioButton1.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                }
                if (finalMViewHolder.mOptionRadioButton2.isChecked()) {
                    if (finalMViewHolder.mOptionRadioButton2.getText().toString().equalsIgnoreCase(getItem(position).getAnswer())) {
                        finalMViewHolder.mOptionRadioButton2.setBackgroundColor(Color.parseColor("#00FF00"));
                    } else {
                        finalMViewHolder.mOptionRadioButton2.setBackgroundColor(Color.parseColor("#FF0000"));
                    }
                } else {
                    if (finalMViewHolder.mOptionRadioButton2.getText().toString().equalsIgnoreCase(getItem(position).getAnswer())) {
                        finalMViewHolder.mOptionRadioButton2.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                }
                if (finalMViewHolder.mOptionRadioButton3.isChecked()) {
                    if (finalMViewHolder.mOptionRadioButton3.getText().toString().equalsIgnoreCase(getItem(position).getAnswer())) {
                        finalMViewHolder.mOptionRadioButton3.setBackgroundColor(Color.parseColor("#00FF00"));
                    } else {
                        finalMViewHolder.mOptionRadioButton3.setBackgroundColor(Color.parseColor("#FF0000"));
                    }
                } else {
                    if (finalMViewHolder.mOptionRadioButton3.getText().toString().equalsIgnoreCase(getItem(position).getAnswer())) {
                        finalMViewHolder.mOptionRadioButton3.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                }
                if (finalMViewHolder.mOptionRadioButton4.isChecked()) {
                    if (finalMViewHolder.mOptionRadioButton4.getText().toString().equalsIgnoreCase(getItem(position).getAnswer())) {
                        finalMViewHolder.mOptionRadioButton4.setBackgroundColor(Color.parseColor("#00FF00"));
                    } else {
                        finalMViewHolder.mOptionRadioButton4.setBackgroundColor(Color.parseColor("#FF0000"));
                    }
                } else {
                    if (finalMViewHolder.mOptionRadioButton4.getText().toString().equalsIgnoreCase(getItem(position).getAnswer())) {
                        finalMViewHolder.mOptionRadioButton4.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                }
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView mQuestionTextView;
        RadioGroup mQuestionradioGroup;
        Button mSubmitButton;
        RadioButton mOptionRadioButton1, mOptionRadioButton2, mOptionRadioButton3, mOptionRadioButton4;
//        List<RadioButton> mRadioButtonList;
    }
}
