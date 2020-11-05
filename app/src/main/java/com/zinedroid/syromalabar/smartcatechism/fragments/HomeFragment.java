package com.zinedroid.syromalabar.smartcatechism.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;

import com.zinedroid.syromalabar.smartcatechism.activity.HomeActivity;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Models.Chapter;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.common.adapters.ChapterRecyclerAdapter;
import com.zinedroid.syromalabar.smartcatechism.serviceandreceiver.OfflineService;
import com.zinedroid.syromalabar.smartcatechism.webservice.WebserviceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.isServiceCalled;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements WebserviceCall.WebServiceCall {

    private RecyclerView mChapterRecyclerView;
    private LinearLayoutManager layoutManager;
    ArrayList<Chapter> mChapterList;
    ChapterRecyclerAdapter mChapterRecyclerAdapter;
    TextView mClassTitleTextView;
    Utility.HomeTitle homeTitle;
    Utility.menuIconChange OnMenuIconChange;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.fragment = this;
        OnMenuIconChange.iconchange(HomeFragment.this);
        homeTitle = (Utility.HomeTitle) getActivity();
        homeTitle.onTitleChange("HOME");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        mainFunction(mFragmentView);
        getActivity().startService(new Intent(getActivity(), OfflineService.class));
        return mFragmentView;
    }

    public void mainFunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mClassTitleTextView = (TextView) mFragmentView.findViewById(R.id.mClassTitleTextView);
        mClassTitleTextView.setText(getSharedPreference(AppConstants.APIKeys.CLASS_TEXT));
        mChapterRecyclerView = (RecyclerView) mFragmentView.findViewById(R.id.mChapterRecyclerView);
        mChapterList = new ArrayList<>();
        mChapterRecyclerAdapter = new ChapterRecyclerAdapter(HomeFragment.this, mChapterList);

        mChapterRecyclerView.setHasFixedSize(true);
        mChapterRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        mChapterList = (ArrayList<Chapter>) Chapter.findWithQuery(Chapter.class,"Select * from Chapter where class_Id = "+getSharedPreference(AppConstants.APIKeys.CLASS_ID)+ " and lang_Id = " +getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID));
        Log.i("classId",getSharedPreference(AppConstants.APIKeys.CLASS_ID));
        applyConfigChange();
        Log.i("sugar count", "" + mChapterList.size());
        if (mChapterList.size() == 0) {
            if (!isServiceCalled) {
                new WebserviceCall(this, AppConstants.Methods.getChapter).execute(new String[]{getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID), getSharedPreference(AppConstants.APIKeys.CLASS_ID)});
            }
        } else {
            mChapterRecyclerAdapter = new ChapterRecyclerAdapter(HomeFragment.this, mChapterList);
            mChapterRecyclerView.setAdapter(mChapterRecyclerAdapter);
//            mChapterRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private void applyConfigChange() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (width < height) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 3);
        }
        mChapterRecyclerView.setLayoutManager(layoutManager);
        mChapterRecyclerView.setHasFixedSize(true);
        mChapterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mChapterRecyclerView.setAdapter(mChapterRecyclerAdapter);
        mChapterRecyclerAdapter.notifyDataSetChanged();
    }
    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        isServiceCalled = false;
        try {
            if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.ResponseCode.CHAPTER_SUCCESS)) {
                JSONArray mChapterJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.CHAPTER_LIST);
                for (int i = 0; i < mChapterJsonArray.length(); i++) {
                    Chapter mChapter = new Chapter();
                    JSONObject mChapterItem = mChapterJsonArray.getJSONObject(i);
                    mChapter.setChapterId(mChapterItem.getString(AppConstants.APIKeys.CHAPTER_ID));
                    mChapter.setChapterTitle(mChapterItem.getString(AppConstants.APIKeys.CHAPTER_TITLE));
                    mChapter.setChapterContext(mChapterItem.getString(AppConstants.APIKeys.CHAPTER_CONTEXT));
                    mChapter.setChapterAudioLink(mChapterItem.getString(AppConstants.APIKeys.CHAPTER_AUDIO));
                    mChapter.setChapterImage(mChapterItem.getString(AppConstants.APIKeys.CHAPTER_IMAGE));
                    mChapter.setChapterVideoLink(mChapterItem.getString(AppConstants.APIKeys.CHAPTER_VIDEO));
                    mChapter.setChapterNo(mChapterItem.getString(AppConstants.APIKeys.CHAPTER_NAME));
                    mChapter.setClassId(mChapterItem.getString(AppConstants.APIKeys.CLASS_ID));
                    Log.i("classId",mChapterItem.getString(AppConstants.APIKeys.CLASS_ID));
                    mChapter.setLang_id(mChapterItem.getString(AppConstants.APIKeys.LANGUAGE_ID));
                    mChapter.save();
                    mChapterList.add(mChapter);
                }
                mChapterRecyclerAdapter = new ChapterRecyclerAdapter(HomeFragment.this, mChapterList);
                mChapterRecyclerView.setAdapter(mChapterRecyclerAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        applyConfigChange();
    }


}
