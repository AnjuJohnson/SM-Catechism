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
import com.zinedroid.syromalabar.smartcatechism.common.Models.Resource;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.common.adapters.ResourceRecyclerAdapter;
import com.zinedroid.syromalabar.smartcatechism.webservice.WebserviceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.APIKeys.RESOURCE_DETAILS_LIST;
import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.isServiceCalled;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuDetailFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    ArrayList<Resource> mResourceList;
    Utility.HomeTitle homeTitle;
    TextView mNoDataTextView;
    private RecyclerView mResourceRecyclerView;
    private LinearLayoutManager layoutManager;
    Utility.menuIconChange OnMenuIconChange;
    ResourceRecyclerAdapter mResourceRecyclerAdapter;

    public MenuDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.fragment = this;
        OnMenuIconChange.iconchange(MenuDetailFragment.this);
        homeTitle.onTitleChange(AppConstants.mMenuContent.getContent());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeTitle = (Utility.HomeTitle) getActivity();
        View mFragmentView = inflater.inflate(R.layout.fragment_menu_detail, container, false);
        mainFunction(mFragmentView);
        return mFragmentView;
    }

    private void mainFunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mNoDataTextView = (TextView) mFragmentView.findViewById(R.id.mNoDataTextView);
        mResourceRecyclerView = (RecyclerView) mFragmentView.findViewById(R.id.mResourceRecyclerView);
        mResourceList = new ArrayList<>();
        mResourceRecyclerAdapter = new ResourceRecyclerAdapter(MenuDetailFragment.this, mResourceList);
        applyConfigChange();
        if (!isServiceCalled) {
            new WebserviceCall(this, AppConstants.Methods.getResourceDetails).execute(new String[]{getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID),AppConstants.mMenuContent.getContentId()});
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
        mResourceRecyclerView.setLayoutManager(layoutManager);
        mResourceRecyclerView.setHasFixedSize(true);
        mResourceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mResourceRecyclerView.setAdapter(mResourceRecyclerAdapter);
        mResourceRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        isServiceCalled = false;
        mResourceList = new ArrayList<>();
        if (mJsonObject.has(RESOURCE_DETAILS_LIST)) {
            try {
                JSONArray mResourceListJsonArray = mJsonObject.getJSONArray(RESOURCE_DETAILS_LIST);
                for (int i = 0; i < mResourceListJsonArray.length(); i++) {
                    Resource mResource = new Resource();
                    JSONObject mResourceItem = mResourceListJsonArray.getJSONObject(i);
                    mResource.setId(mResourceItem.getString(AppConstants.APIKeys.ID));
                    mResource.setCategoryId(mResourceItem.getString(AppConstants.APIKeys.CATEGORY_ID));
                    mResource.setName(mResourceItem.getString(AppConstants.APIKeys.NAME));
                    mResource.setAudioUrl(mResourceItem.getString(AppConstants.APIKeys.AUDIO));
                    mResource.setDescription(mResourceItem.getString(AppConstants.APIKeys.DESCRIPTION));
                    mResource.setImageUrl(mResourceItem.getString(AppConstants.APIKeys.IMAGE));
                    mResource.setVideoUrl(mResourceItem.getString(AppConstants.APIKeys.VIDEO));
                    mResourceList.add(mResource);
                }
                mResourceRecyclerAdapter = new ResourceRecyclerAdapter(MenuDetailFragment.this, mResourceList);
                mResourceRecyclerView.setAdapter(mResourceRecyclerAdapter);

                if (mResourceList.size() > 0) {
                    mNoDataTextView.setVisibility(View.GONE);
                    mResourceRecyclerView.setVisibility(View.VISIBLE);
//                    mResourceRecyclerAdapter.notifyDataSetChanged();
                } else {
                    mNoDataTextView.setVisibility(View.VISIBLE);
                    mResourceRecyclerView.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mNoDataTextView.setVisibility(View.VISIBLE);
                mResourceRecyclerView.setVisibility(View.GONE);
            }
        } else {
            mNoDataTextView.setVisibility(View.VISIBLE);
            mResourceRecyclerView.setVisibility(View.GONE);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        applyConfigChange();
    }
}
