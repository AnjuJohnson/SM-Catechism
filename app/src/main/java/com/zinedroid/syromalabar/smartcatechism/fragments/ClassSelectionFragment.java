package com.zinedroid.syromalabar.smartcatechism.fragments;


import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;

import com.zinedroid.syromalabar.smartcatechism.activity.HomeActivity;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Models.SingleItem;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.common.adapters.CommonListAdapter;
import com.zinedroid.syromalabar.smartcatechism.webservice.WebserviceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.isServiceCalled;
import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.mPlayer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassSelectionFragment extends BaseFragment implements WebserviceCall.WebServiceCall {

    GridView mClassGridView;
    ArrayList<SingleItem> mClassArrayList;
    CommonListAdapter commonListAdapter;
    TextView noDataTextView;
    ImageView mArrowL;
    View mView;
    Utility.menuIconChange OnMenuIconChange;

    Utility.HomeTitle homeTitle;
    public ClassSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.activity_class_selection, container, false);
        homeTitle = (Utility.HomeTitle) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        objectMap();
        utilizeObjects();
        return mView;
    }
    private void utilizeObjects() {
        applyConfigChange();

        mClassGridView.setAdapter(commonListAdapter);
        if (!isServiceCalled) {
            new WebserviceCall(ClassSelectionFragment.this, AppConstants.Methods.getClass).execute(new String[]{getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID)});
        }
        mClassGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SingleItem singleItem = mClassArrayList.get(i);
                setSharedPreference(AppConstants.APIKeys.CLASS_ID, singleItem.getId());
                setSharedPreference(AppConstants.APIKeys.CLASS_TEXT, singleItem.getName());
                homeTitle.ChangeFragment(new HomeFragment(), true, true);


               /*
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().finish();
            //    finishAffinity();

*/
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
        applyConfigChange();
    }

    private void applyConfigChange() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (width < height) {
            mClassGridView.setNumColumns(2);
        } else {
            mClassGridView.setNumColumns(3);
        }
        commonListAdapter.notifyDataSetChanged();
    }

    private void objectMap() {

        mClassGridView = (GridView) mView.findViewById(R.id.mClassGridView);
        noDataTextView = (TextView) mView.findViewById(R.id.noDataTextView);
      /*  mArrowL = (ImageView)mView. findViewById(R.id.mArrowL);
        mArrowL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });*/
        mClassArrayList = new ArrayList<>();
        commonListAdapter = new CommonListAdapter(getActivity(), R.layout.item_class_list, mClassArrayList);
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        isServiceCalled = false;
        try {
            String statusCode = mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE);
            if (statusCode.equalsIgnoreCase(AppConstants.ResponseCode.CLASS_SUCCESS)) {
                JSONArray mClassArray = mJsonObject.getJSONArray(AppConstants.APIKeys.CLASS_LIST);
                for (int i = 0; i < mClassArray.length(); i++) {
                    JSONObject mClassItem = mClassArray.getJSONObject(i);
                    SingleItem mSingleItem = new SingleItem();
                    mSingleItem.setId(mClassItem.getString(AppConstants.APIKeys.CLASS_ID));
                    mSingleItem.setName(mClassItem.getString(AppConstants.APIKeys.CLASS_TEXT));
                    mSingleItem.setImageUrl(mClassItem.getString(AppConstants.APIKeys.IMAGE));
                    mClassArrayList.add(mSingleItem);
                }
                commonListAdapter.notifyDataSetChanged();
                if (mClassArrayList.size() < 1) {
                    noDataTextView.setVisibility(View.VISIBLE);
                } else {
                    noDataTextView.setVisibility(View.GONE);
                }
            } else if (statusCode.equalsIgnoreCase(AppConstants.ResponseCode.NO_DATA_AVAILABLE)) {
                noDataTextView.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.fragment = this;
        OnMenuIconChange.iconchange(ClassSelectionFragment.this);
        homeTitle = (Utility.HomeTitle) getActivity();
        homeTitle.onTitleChange("Select Class");
    }


}
