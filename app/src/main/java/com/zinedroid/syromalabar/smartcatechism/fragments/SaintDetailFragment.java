package com.zinedroid.syromalabar.smartcatechism.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.activity.HomeActivity;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaintDetailFragment extends BaseFragment {

    Utility.HomeTitle homeTitle;
    Utility.menuIconChange OnMenuIconChange;
    public SaintDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.fragment = this;
        OnMenuIconChange.iconchange(SaintDetailFragment.this);
        if (AppConstants.mSaints.getDate()!=null) {
            homeTitle.onTitleChange("Daily Saints");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeTitle = (Utility.HomeTitle) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        View mFragmentView = inflater.inflate(R.layout.fragment_saints_details, container, false);
        TextView mContentTextView = (TextView) mFragmentView.findViewById(R.id.mContentTextView);
        TextView mSaintNameTextView = (TextView) mFragmentView.findViewById(R.id.mSaintNameTextView);
        mContentTextView.setText(Html.fromHtml("<p align=\"justify\">" + Html.fromHtml(AppConstants.mSaints.getDetails()) + "</p>"));
        mSaintNameTextView.setText(AppConstants.mSaints.getName());
        return mFragmentView;
    }

}
