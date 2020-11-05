package com.zinedroid.syromalabar.smartcatechism.serviceandreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.zinedroid.syromalabar.smartcatechism.Base.BaseService;
import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Models.Chapter;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ChapterActivity;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ChapterDiscussion;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ImageGallery;
import com.zinedroid.syromalabar.smartcatechism.common.Models.MenuContent;
import com.zinedroid.syromalabar.smartcatechism.webservice.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.mChapter;

/**
 * Created by Cecil Paul on 15/11/17.
 */

public class OfflineService extends BaseService {
    int log = 1;
    API api;
    JSONObject mChapterJsonObject, mParagraphJsonObject, mDiscussionJsonObject, mActivityJsonObject = null;

    ArrayList<ImageGallery> array_image = new ArrayList();

    private String[] images = {
            "http://neptune.geekstorage.com/~smsmartc/files/media/chapters/1505824433_7.jpg",
            "http://neptune.geekstorage.com/~smsmartc/files/media/chapters/1506084670_healingthesick.jpg",
            "http://neptune.geekstorage.com/~smsmartc/files/media/chapters/1506084670_healingthesick.jpg",
    };

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public OfflineService() {
        super("OfflineService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("serviceStatus", log + " onStartCommand");
        log++;
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                final OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
                okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint(AppConstants.END_POINT)
                        .setClient(new OkClient(okHttpClient)).setLogLevel(RestAdapter.LogLevel.FULL).build();
                api = restAdapter.create(API.class);

                try {
                    if (networkInfo != null && networkInfo.isConnected()) {

                        ////////dummy images


                        mChapterJsonObject = new JSONObject(String.valueOf(api.getChapterList(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID), getSharedPreference(AppConstants.APIKeys.CLASS_ID))));
                        JSONArray mChapterJsonArray = mChapterJsonObject.getJSONArray(AppConstants.APIKeys.CHAPTER_LIST);
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
                            Log.i("classId", mChapterItem.getString(AppConstants.APIKeys.CLASS_ID));
                            mChapter.setLang_id(mChapterItem.getString(AppConstants.APIKeys.LANGUAGE_ID));
                            ArrayList<Chapter> mChapterList = (ArrayList<Chapter>) Chapter.findWithQuery(Chapter.class, "Select * from Chapter where class_Id = " + mChapter.getClassId() + " and lang_Id = " + mChapter.getLang_id() + " and chapter_Id = " + mChapter.getChapterId());

                            if (mChapterList.size() == 0) {
                                mChapter.save();
                            }


                            mParagraphJsonObject = new JSONObject(String.valueOf(api.getChapterParagraph(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID), getSharedPreference(AppConstants.APIKeys.CLASS_ID), mChapter.getChapterId())));
                            JSONArray mContentJsonArray = mParagraphJsonObject.getJSONArray(AppConstants.APIKeys.PARAGRAPH_ARRAY);
                            for (int j = 0; j < mContentJsonArray.length(); j++) {
                                JSONObject mContentJsonObject = mContentJsonArray.getJSONObject(j);
                                MenuContent singleItem = new MenuContent();
                                singleItem.setContent(mContentJsonObject.getString(AppConstants.APIKeys.CONTENT));
                                singleItem.setContentId(mContentJsonObject.getString(AppConstants.APIKeys.PARAGRAPH_ID));
                                singleItem.setChapterId(mContentJsonObject.getString(AppConstants.APIKeys.CHAPTER_ID));
                                singleItem.setClassId(getSharedPreference(AppConstants.APIKeys.CLASS_ID));
                                singleItem.setLanguageId(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID));
                                array_image = new ArrayList<>();
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
                                        ArrayList<ImageGallery> mGalleryList = (ArrayList<ImageGallery>) ImageGallery.findWithQuery(ImageGallery.class, "Select * from image_Gallery where chapter_Id = " + singleItem.getChapterId() + " and content_Id = " + singleItem.getContentId() + " and image_Id = " + String.valueOf(k));
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
                                ArrayList<MenuContent> mParagraphsList = (ArrayList<MenuContent>) MenuContent.findWithQuery(MenuContent.class, "Select * from menu_Content where chapter_Id = " + singleItem.getChapterId() + " and content_Id = " + singleItem.getContentId());

                                if (mParagraphsList.size() == 0) {
                                    singleItem.save();
                                }
                            }


                            mDiscussionJsonObject = new JSONObject(String.valueOf(api.getChapterDiscussion(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID), getSharedPreference(AppConstants.APIKeys.CLASS_ID), mChapter.getChapterId())));
                            if (mDiscussionJsonObject.has(AppConstants.APIKeys.DISCUSSION_LIST)) {
                                JSONArray mDiscussionJsonArray = mDiscussionJsonObject.getJSONArray(AppConstants.APIKeys.DISCUSSION_LIST);

                                for (int k = 0; k < mDiscussionJsonArray.length(); k++) {
                                    JSONObject mItemJsonObject = mDiscussionJsonArray.getJSONObject(k);
                                    ChapterDiscussion mChapterDiscussion = new ChapterDiscussion();
                                    mChapterDiscussion.setDiscussionId(mItemJsonObject.getString(AppConstants.APIKeys.DISCUSSION_ID));
                                    mChapterDiscussion.setLanguageId(mItemJsonObject.getString(AppConstants.APIKeys.LANGUAGE_ID));
                                    mChapterDiscussion.setClassId(mItemJsonObject.getString(AppConstants.APIKeys.CLASS_ID));
                                    mChapterDiscussion.setContent(mItemJsonObject.getString(AppConstants.APIKeys.CONTENT));
                                    mChapterDiscussion.setChapterId(mItemJsonObject.getString(AppConstants.APIKeys.CHAPTER_ID));
                                    ArrayList<ChapterDiscussion> mPDiscussionList = (ArrayList<ChapterDiscussion>) ChapterDiscussion.findWithQuery(ChapterDiscussion.class, "Select * from chapter_Discussion where chapter_Id = " + mChapterDiscussion.getChapterId() + " and discussion_id = " + mChapterDiscussion.getDiscussionId());
                                    if (mPDiscussionList.size() == 0) {
                                        Log.e("saved",mItemJsonObject.getString(AppConstants.APIKeys.DISCUSSION_ID));
                                        mChapterDiscussion.save();
                                    }

                                }
                            }


                            mActivityJsonObject = new JSONObject(String.valueOf(api.getChapterActivity(getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID), getSharedPreference(AppConstants.APIKeys.CLASS_ID), mChapter.getChapterId())));
                            JSONArray mActivityJsonArray = null;
                            if (mActivityJsonObject.has(AppConstants.APIKeys.ACTIVITY_LIST)) {
                                mActivityJsonArray = mActivityJsonObject.getJSONArray(AppConstants.APIKeys.ACTIVITY_LIST);


                                for (int l = 0; l < mActivityJsonArray.length(); l++) {
                                    JSONObject mItemJsonObject = mActivityJsonArray.getJSONObject(l);
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
                                    ArrayList<ChapterActivity> mActivityList = (ArrayList<ChapterActivity>) ChapterActivity.findWithQuery(ChapterActivity.class, "Select * from chapter_Activity where chapter_Id = " + mChapterActivity.getChapterId() + " and activuty_id = " + mChapterActivity.getActivuty_id());
                                    if (mActivityList.size() == 0) {
                                        mChapterActivity.save();
                                    }
                                }
                            }

                        }
                        Notification n = new Notification.Builder(getApplicationContext())
                                .setContentTitle("Content Offline")
                                .setContentText("Your Chapters are now offline. Avail the features")
                                .setSmallIcon(R.drawable.bible)
                                .setAutoCancel(true)
                                .build();
                        NotificationManager notificationManager =
                                (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                        ArrayList<Chapter> mChapterList1 = null;
                        try {
                            mChapterList1 = (ArrayList<Chapter>) Chapter.findWithQuery(Chapter.class, "Select * from Chapter where class_Id = " + mChapter.getClassId() + " and lang_Id = " + mChapter.getLang_id() + " and chapter_Id = " + mChapter.getChapterId());
                            if (mChapterList1.size() > 0) {
                                notificationManager.notify(0, n);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("serviceStatus", log + " onBind");
        log++;
        return null;
    }
}
//    Notification n = new Notification.Builder(getApplicationContext())
//            .setContentTitle("New mail from " + "test@gmail.com")
//            .setContentText("Network connected")
//            .setSmallIcon(R.drawable.bible)
//            .setAutoCancel(true)
//            .build();
//    NotificationManager notificationManager =
//            (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(0, n);