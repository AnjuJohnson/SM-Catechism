package com.zinedroid.syromalabar.smartcatechism.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;

import com.zinedroid.syromalabar.smartcatechism.activity.HomeActivity;

import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Models.SingleItem;
import com.zinedroid.syromalabar.smartcatechism.common.NonScrollListView;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.common.adapters.CommonListAdapter;
import com.zinedroid.syromalabar.smartcatechism.webservice.WebserviceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.isServiceCalled;

/**
 * A simple {@link Fragment} subclass.
 */
public class LanguageSelectionFragment extends BaseFragment implements WebserviceCall.WebServiceCall {

    NonScrollListView mLanguageListView;
    ArrayList<SingleItem> mSingleItemList;
    CommonListAdapter commonListAdapter;
    View mFragmentView;
    Utility.HomeTitle homeTitle;
    Utility.menuIconChange OnMenuIconChange;

    public LanguageSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.fragment = this;
        OnMenuIconChange.iconchange(LanguageSelectionFragment.this);
        homeTitle.onTitleChange("Select Language");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeTitle = (Utility.HomeTitle) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mFragmentView = inflater.inflate(R.layout.activity_splash, container, false);
        objectMap();
        utilizeObjects();
        return mFragmentView;
    }

    private void utilizeObjects() {
        mSingleItemList = new ArrayList<>();

        commonListAdapter = new CommonListAdapter(getActivity(), R.layout.item_language_selection_list, mSingleItemList);
        mLanguageListView.setAdapter(commonListAdapter);
        if (isNetworkAvailable()) {
            if (!isServiceCalled) {
                new WebserviceCall(LanguageSelectionFragment.this, AppConstants.Methods.getLanguage).execute(new String[]{""});
            }
        } else {
            Toast.makeText(getActivity(), "Please Connect to the internet and tryagain!", Toast.LENGTH_SHORT).show();
        }
        mLanguageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SingleItem singleItem = mSingleItemList.get(i);
                setSharedPreference(AppConstants.APIKeys.LANGUAGE_ID, singleItem.getId());
                homeTitle.ChangeFragment(new ClassSelectionFragment(), true, true);
            }
        });
    }

    private void objectMap() {
        mLanguageListView = (NonScrollListView) mFragmentView.findViewById(R.id.mlanguageListView);
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        isServiceCalled = false;
        try {
            String statusCode = mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE);
            if (statusCode.equalsIgnoreCase(AppConstants.ResponseCode.LANGUAGE_SUCCESS)) {
                JSONArray mLanguageArray = mJsonObject.getJSONArray(AppConstants.APIKeys.LANG_LIST);
                for (int i = 0; i < mLanguageArray.length(); i++) {
                    JSONObject mLanguageItem = mLanguageArray.getJSONObject(i);
                    SingleItem mSingleItem = new SingleItem();
                    mSingleItem.setId(mLanguageItem.getString(AppConstants.APIKeys.LANGUAGE_ID));
                    mSingleItem.setName(mLanguageItem.getString(AppConstants.APIKeys.LANGUAGE));
                    mSingleItemList.add(mSingleItem);
                }
                commonListAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
