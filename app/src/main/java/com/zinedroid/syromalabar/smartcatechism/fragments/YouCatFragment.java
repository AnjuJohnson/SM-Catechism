package com.zinedroid.syromalabar.smartcatechism.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.activity.HomeActivity;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;

/**
 * Created by Anjumol Johnson on 2/1/19.
 */
public class YouCatFragment extends BaseFragment {
    WebView mPocWebView;
    Utility.HomeTitle homeTitle;
    Utility.menuIconChange OnMenuIconChange;
    public YouCatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.fragment = this;
        OnMenuIconChange.iconchange(YouCatFragment.this);
        homeTitle.onTitleChange("YouCat");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mFragmentView = inflater.inflate(R.layout.fragment_poc, container, false);
        mainFunction(mFragmentView);
        return mFragmentView;
    }
    public void mainFunction(View mView){
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        homeTitle = (Utility.HomeTitle) getActivity();
        mPocWebView=(WebView) mView.findViewById(R.id.pocWebView);
        mPocWebView.getSettings().setJavaScriptEnabled(true);
        mPocWebView.loadUrl("https://www.youcat.org/products/youcat");
    }
}
