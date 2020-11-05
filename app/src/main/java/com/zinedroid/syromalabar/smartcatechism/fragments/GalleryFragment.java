package com.zinedroid.syromalabar.smartcatechism.fragments;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.activity.HomeActivity;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ImageGallery;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.common.adapters.ParagraphGalleryAdapter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Anjumol Johnson on 17/1/19.
 */
public class GalleryFragment extends BaseFragment {
    Utility.menuIconChange OnMenuIconChange;
    Utility.HomeTitle homeTitle;
    private RecyclerView mGalleryrecycler;
    ArrayList<ImageGallery> mGalleryimageList;
    LinearLayoutManager layoutManager;
    private ParagraphGalleryAdapter mAdapter;
    private HomeActivity myContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        homeTitle = (Utility.HomeTitle) getActivity();
        View mFragmentView = inflater.inflate(R.layout.fragment_gallery, container, false);
        functions(mFragmentView);
        return mFragmentView;
    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(HomeActivity) activity;
        super.onAttach(activity);
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.fragment = this;
        OnMenuIconChange.iconchange(GalleryFragment.this);
        homeTitle.onTitleChange("Gallery Images");
    }
    private void functions(View mFragmentView) {
        try {
            mGalleryimageList = new ArrayList<>();
            mGalleryrecycler = (RecyclerView) mFragmentView.findViewById(R.id.mGalleryrecycler);
            mGalleryimageList = AppConstants.mMenuContentGallery.getImageGallery();
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
            // Setup and Handover data to recyclerview

            mAdapter = new ParagraphGalleryAdapter(getActivity(), mGalleryimageList);
            mGalleryrecycler.setHasFixedSize(true);
            mGalleryrecycler.setAdapter(mAdapter);
            mGalleryrecycler.setLayoutManager(layoutManager);


            mGalleryrecycler.addOnItemTouchListener(new ParagraphGalleryAdapter.RecyclerTouchListener(getActivity(), mGalleryrecycler, new ParagraphGalleryAdapter.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("images", (Serializable) mGalleryimageList);
                    bundle.putInt("position", position);
                    FragmentTransaction ft =myContext.getSupportFragmentManager().beginTransaction();
                    SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                    newFragment.setArguments(bundle);
                    newFragment.show(ft, "slideshow");

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));


        } catch (Exception e) {
          //  Toast.makeText(GalleryActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
