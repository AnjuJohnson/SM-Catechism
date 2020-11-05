package com.zinedroid.syromalabar.smartcatechism.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.activity.HomeActivity;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ChapterActivity;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ChapterDiscussion;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ImageGallery;
import com.zinedroid.syromalabar.smartcatechism.common.Models.MenuContent;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.common.adapters.ParagraphAdapter;
import com.zinedroid.syromalabar.smartcatechism.webservice.WebserviceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import se.emilsjolander.flipview.FlipView;

import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.isServiceCalled;
import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.mPlayer;


public class ChapterDetailFragment extends BaseFragment implements WebserviceCall.WebServiceCall
//        , SwipeRefreshLayout.OnRefreshListener
{
    ArrayList<MenuContent> mParagraphsList;
    ArrayList<ChapterActivity> mChapterActivityList;
    ArrayList<ChapterDiscussion> mChapterDiscussionList;
    ParagraphAdapter mParagraphAdapter;
    FlipView flipView;
    View mChapterDetailFragment;
    ArrayList<ImageGallery> array_image;
    Utility.menuIconChange OnMenuIconChange;
    //    SwipeRefreshLayout mSwipeRefreshLayout;
    int flag = 0;
    String fontsize;

    public ChapterDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.fragment = this;
        OnMenuIconChange.iconchange(ChapterDetailFragment.this);
        rebuild();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.stop();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mChapterDetailFragment = inflater.inflate(R.layout.fragment_chapter_detail, container, false);
        rebuild();
//        for (int i = 0; i < 10; i++) {
//            MenuContent singleItem = new MenuContent();
////            singleItem.setName("KAvitha" + i);
//            mParagraphsList.add(singleItem);
//        }


//        mParagraphAdapter = new ParagraphAdapter(getActivity(), mParagraphsList);
//        flipView.setAdapter(mParagraphAdapter);
        return mChapterDetailFragment;
    }

    private void rebuild() {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        fontsize = getSharedPreference("FONT");
//        mSwipeRefreshLayout = (SwipeRefreshLayout) mChapterDetailFragment.findViewById(R.id.swiperefresh);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        mParagraphsList = new ArrayList<>();
        flipView = (FlipView) mChapterDetailFragment.findViewById(R.id.flip_view);

        mParagraphsList = (ArrayList<MenuContent>) MenuContent.findWithQuery(MenuContent.class, "Select * from menu_Content where chapter_Id = " + AppConstants.mChapter.getChapterId() + " AND class_Id = " + getSharedPreference(AppConstants.APIKeys.CLASS_ID) + " AND language_Id = " + getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID));
        if (mParagraphsList.size() == 0) {
            if (isNetworkAvailable()) {
                if (!isServiceCalled) {
                    new WebserviceCall(ChapterDetailFragment.this, AppConstants.Methods.getParagraphs).execute(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID), getSharedPreference(AppConstants.APIKeys.CLASS_ID), getSharedPreference(AppConstants.APIKeys.CHAPTER_ID));
                }
            } else {
                Toast.makeText(getActivity(), "Please connect ot internet and try again!", Toast.LENGTH_SHORT).show();
            }
        } else {
            mParagraphAdapter = new ParagraphAdapter(getActivity(), mParagraphsList, fontsize,ChapterDetailFragment.this);
            mParagraphAdapter.mChapterDiscussionList = new ArrayList<>();

            Log.d("66666666","chapterid="+getSharedPreference(AppConstants.APIKeys.CHAPTER_ID)+"classid="+getSharedPreference(AppConstants.APIKeys.CLASS_ID));
            mParagraphAdapter.mChapterDiscussionList = (ArrayList<ChapterDiscussion>) ChapterDiscussion.findWithQuery(ChapterDiscussion.class, "Select * from chapter_Discussion where chapter_Id = " + getSharedPreference(AppConstants.APIKeys.CHAPTER_ID) + " AND class_Id = " + getSharedPreference(AppConstants.APIKeys.CLASS_ID) + " AND language_Id = " + getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID));
            mParagraphAdapter.mChapterActivityList = new ArrayList<>();
            mParagraphAdapter.mChapterActivityList = (ArrayList<ChapterActivity>) ChapterActivity.findWithQuery(ChapterActivity.class, "Select * from chapter_Activity where chapter_Id = " + getSharedPreference(AppConstants.APIKeys.CHAPTER_ID) + " AND class_Id = " + getSharedPreference(AppConstants.APIKeys.CLASS_ID) + " AND language_Id = " + getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID));

            flipView.setAdapter(mParagraphAdapter);
        }


    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        isServiceCalled = false;
        try {
            switch (method) {
                case AppConstants.Methods.getParagraphs:
                    if (flag == 1) {
                        Log.d("flag value", String.valueOf(flag));
                        MenuContent.deleteAll(MenuContent.class);
                    }
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.ResponseCode.PARAGRAPH_SUCCESS)) {
                        JSONArray mContentJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.PARAGRAPH_ARRAY);
                        mParagraphsList = new ArrayList<>();
                        //  array_image = new ArrayList();
                        for (int i = 0; i < mContentJsonArray.length(); i++) {
                            JSONObject mContentJsonObject = mContentJsonArray.getJSONObject(i);
                            MenuContent singleItem = new MenuContent();
                            singleItem.setContent(mContentJsonObject.getString(AppConstants.APIKeys.CONTENT));
                            singleItem.setContentId(mContentJsonObject.getString(AppConstants.APIKeys.PARAGRAPH_ID));
                            singleItem.setChapterId(mContentJsonObject.getString(AppConstants.APIKeys.CHAPTER_ID));
                            singleItem.setClassId(getSharedPreference(AppConstants.APIKeys.CLASS_ID));
                            singleItem.setLanguageId(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID));
                            array_image = new ArrayList<ImageGallery>();
                            try {
                                JSONArray mGalleryimages = mContentJsonObject.getJSONArray(AppConstants.APIKeys.PARAGRAPH_GALLERY_IMAGES);
                                for (int k = 0; k < mGalleryimages.length(); k++) {
                                    JSONObject mGallerycontent = mGalleryimages.getJSONObject(k);
                                    ImageGallery imageGallery = new ImageGallery();
                                    imageGallery.setImageId(String.valueOf(k));
                                    imageGallery.setImagePath(mGallerycontent.getString(AppConstants.APIKeys.PARAGRAPH_GALLERY_CONTENT));
                                    imageGallery.setChapterId(mContentJsonObject.getString(AppConstants.APIKeys.CHAPTER_ID));
                                    imageGallery.setClassId(getSharedPreference(AppConstants.APIKeys.CLASS_ID));
                                    imageGallery.setLangId(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID));
                                    imageGallery.setContentId(mContentJsonObject.getString(AppConstants.APIKeys.PARAGRAPH_ID));
                                    ArrayList<ImageGallery> mGalleryList = (ArrayList<ImageGallery>) ImageGallery.findWithQuery(ImageGallery.class, "Select * from image_Gallery where chapter_Id = " + singleItem.getChapterId() + " and content_Id = " + singleItem.getContentId());
                                    if (mGalleryList.size() == 0) {
                                        imageGallery.save();
                                    }
                                    array_image.add(imageGallery);
                                }

                            } catch (Exception l) {
                                l.printStackTrace();
                                array_image = new ArrayList<>();
                            }
                            singleItem.setImageGallery(array_image);
                            singleItem.save();
                            mParagraphsList.add(singleItem);
                        }
                    }
//                    mParagraphAdapter.notifyDataSetChanged();
                    mParagraphAdapter = new ParagraphAdapter(getActivity(), mParagraphsList, fontsize,ChapterDetailFragment.this);

                    if (!isServiceCalled) {
                        new WebserviceCall(ChapterDetailFragment.this, AppConstants.Methods.getDiscussion).execute(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID), getSharedPreference(AppConstants.APIKeys.CLASS_ID), getSharedPreference(AppConstants.APIKeys.CHAPTER_ID));
                    }
                    break;
                case AppConstants.Methods.getDiscussion:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.ResponseCode.DISCUSSION_SUCCESS)) {
                        JSONArray mDiscussionJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.DISCUSSION_LIST);
                        mChapterDiscussionList = new ArrayList<>();
                        for (int i = 0; i < mDiscussionJsonArray.length(); i++) {
                            JSONObject mItemJsonObject = mDiscussionJsonArray.getJSONObject(i);
                            ChapterDiscussion mChapterDiscussion = new ChapterDiscussion();
                            mChapterDiscussion.setDiscussionId(mItemJsonObject.getString(AppConstants.APIKeys.DISCUSSION_ID));
                            mChapterDiscussion.setContent(mItemJsonObject.getString(AppConstants.APIKeys.CONTENT));
                            mChapterDiscussion.setChapterId(mItemJsonObject.getString(AppConstants.APIKeys.CHAPTER_ID));
                            mChapterDiscussion.setClassId(getSharedPreference(AppConstants.APIKeys.CLASS_ID));
                            mChapterDiscussion.setLanguageId(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID));

                            Log.e("savedwebservice",mItemJsonObject.getString(AppConstants.APIKeys.DISCUSSION_ID));
                            mChapterDiscussion.save();
                            mChapterDiscussionList.add(mChapterDiscussion);
                        }
                        mParagraphAdapter.mChapterDiscussionList = mChapterDiscussionList;

                    } else {
                        mParagraphAdapter.mChapterDiscussionList = new ArrayList<>();

                    }
//                    mParagraphAdapter.notifyDataSetChanged();
                    if (!isServiceCalled) {
                        new WebserviceCall(ChapterDetailFragment.this, AppConstants.Methods.getActivity).execute(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID), getSharedPreference(AppConstants.APIKeys.CLASS_ID), getSharedPreference(AppConstants.APIKeys.CHAPTER_ID));
                    }
                    break;
                case AppConstants.Methods.getActivity:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.ResponseCode.ACTIVITY_SUCCESS)) {
                        JSONArray mActivityJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.ACTIVITY_LIST);
                        mChapterActivityList = new ArrayList<>();
                        for (int i = 0; i < mActivityJsonArray.length(); i++) {
                            JSONObject mItemJsonObject = mActivityJsonArray.getJSONObject(i);
                            ChapterActivity mChapterActivity = new ChapterActivity();
                            mChapterActivity.setActivuty_id(mItemJsonObject.getString(AppConstants.APIKeys.ACTIVITY_ID));
                            mChapterActivity.setQueston(mItemJsonObject.getString(AppConstants.APIKeys.ACTIVITY_QUESTION));
                            mChapterActivity.setAnswer(mItemJsonObject.getString(AppConstants.APIKeys.ACTIVITY_ANSWER));
                            mChapterActivity.setOption1(mItemJsonObject.getString(AppConstants.APIKeys.ACTIVITY_OPTION1));
                            mChapterActivity.setOption2(mItemJsonObject.getString(AppConstants.APIKeys.ACTIVITY_OPTION2));
                            mChapterActivity.setOption3(mItemJsonObject.getString(AppConstants.APIKeys.ACTIVITY_OPTION3));
                            mChapterActivity.setOption4(mItemJsonObject.getString(AppConstants.APIKeys.ACTIVITY_OPTION4));
                            mChapterActivity.setChapterId(mItemJsonObject.getString(AppConstants.APIKeys.CHAPTER_ID));
                            mChapterActivity.setClassId(getSharedPreference(AppConstants.APIKeys.CLASS_ID));
                            mChapterActivity.setLanguageId(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID));
                            mChapterActivity.save();
                            mChapterActivityList.add(mChapterActivity);
                        }
                        mParagraphAdapter.mChapterActivityList = mChapterActivityList;

                    } else {
                        mParagraphAdapter.mChapterActivityList = new ArrayList<>();
                    }
                    flipView.setAdapter(mParagraphAdapter);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

//    @Override
//    public void onRefresh() {
//        if (isNetworkAvailable()) {
//            if (!isServiceCalled) {
//                flag = 1;
//                new WebserviceCall(ChapterDetailFragment.this, AppConstants.Methods.getParagraphs).execute(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID), getSharedPreference(AppConstants.APIKeys.CLASS_ID), getSharedPreference(AppConstants.APIKeys.CHAPTER_ID));
//                flipView.setAdapter(mParagraphAdapter);
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        } else {
//            Toast.makeText(getActivity(), "Please connect ot internet and try again!", Toast.LENGTH_SHORT).show();
//            mSwipeRefreshLayout.setRefreshing(false);
//        }
//    }
}
