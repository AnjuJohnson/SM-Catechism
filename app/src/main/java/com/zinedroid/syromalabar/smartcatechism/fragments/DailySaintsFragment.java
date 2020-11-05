package com.zinedroid.syromalabar.smartcatechism.fragments;


import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.activity.HomeActivity;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Models.Saints;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.common.adapters.SaintsRecyclerAdapter;
import com.zinedroid.syromalabar.smartcatechism.webservice.WebserviceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.isServiceCalled;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailySaintsFragment extends BaseFragment implements WebserviceCall.WebServiceCall {

    private RecyclerView mSaintsRecyclerView;
    private LinearLayoutManager layoutManager;
    ArrayList<Saints> mSaintsList;
    SaintsRecyclerAdapter mSaintsRecyclerAdapter;
    Utility.HomeTitle homeTitle;
    TextView noDataTextView;
    Utility.menuIconChange OnMenuIconChange;
    public DailySaintsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.fragment = this;
        OnMenuIconChange.iconchange(DailySaintsFragment.this);
        homeTitle.onTitleChange("Daily Saints");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeTitle = (Utility.HomeTitle) getActivity();
        View mFragmentView = inflater.inflate(R.layout.fragment_daily_saints, container, false);
        mainFunction(mFragmentView);
        return mFragmentView;
    }

    public void mainFunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mSaintsRecyclerView = (RecyclerView) mFragmentView.findViewById(R.id.mSaintsRecyclerView);
        mSaintsList = new ArrayList<>();
        noDataTextView = (TextView) mFragmentView.findViewById(R.id.noDataTextView);
        mSaintsRecyclerAdapter = new SaintsRecyclerAdapter(DailySaintsFragment.this, mSaintsList);
        applyConfigChange();
        if (!isServiceCalled) {
            new WebserviceCall(this, AppConstants.Methods.getDailySaints).execute(new String[]{getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID)});
        }
    }

    private void applyConfigChange() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (width < height) {
            layoutManager = new GridLayoutManager(getActivity(), 3);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 5);
        }
        mSaintsRecyclerView.setLayoutManager(layoutManager);
        mSaintsRecyclerView.setHasFixedSize(true);
        mSaintsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSaintsRecyclerView.setAdapter(mSaintsRecyclerAdapter);
        mSaintsRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        isServiceCalled = false;
        try {
            mSaintsList = new ArrayList<>();
            if (mJsonObject.has(AppConstants.APIKeys.SAINTS_LIST)) {
                JSONArray mSaintsListArray = mJsonObject.getJSONArray(AppConstants.APIKeys.SAINTS_LIST);
                if (mSaintsListArray.length() == 0) {
                    noDataTextView.setVisibility(View.VISIBLE);
                } else {
                    noDataTextView.setVisibility(View.GONE);
                }
                for (int i = 0; i < mSaintsListArray.length(); i++) {
                    JSONObject mSaintsJsonObject = mSaintsListArray.getJSONObject(i);
                    Saints saints = new Saints();
                    saints.setId(mSaintsJsonObject.getString(AppConstants.APIKeys.ID));
                    saints.setName(mSaintsJsonObject.getString(AppConstants.APIKeys.NAME));
                    saints.setDate(mSaintsJsonObject.getString(AppConstants.APIKeys.DATE));
                    saints.setDetails(mSaintsJsonObject.getString(AppConstants.APIKeys.DETAILS));
                    saints.setImage(mSaintsJsonObject.getString(AppConstants.APIKeys.IMAGE));
                    mSaintsList.add(saints);
                }
                mSaintsRecyclerAdapter = new SaintsRecyclerAdapter(DailySaintsFragment.this, mSaintsList);
                mSaintsRecyclerView.setAdapter(mSaintsRecyclerAdapter);
            } else {
                noDataTextView.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            noDataTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        applyConfigChange();
    }


}
