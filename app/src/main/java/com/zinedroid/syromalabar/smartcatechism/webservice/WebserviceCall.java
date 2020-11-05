package com.zinedroid.syromalabar.smartcatechism.webservice;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;

import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.zinedroid.syromalabar.smartcatechism.Base.BaseActivity;
import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.isServiceCalled;

/**
 * Created by Cecil Paul on 30/8/17.
 */

public class WebserviceCall extends AsyncTask<String, Void, JSONObject> {
    API api;
    WebServiceCall webServiceCall;
    Activity activity;
    int method;
    Dialog mDialog;

    public WebserviceCall(BaseActivity activity, int method) {
        this.activity = activity;
        this.method = method;
        webServiceCall = (WebServiceCall) activity;
        isServiceCalled = true;
    }

    public WebserviceCall(BaseFragment fragment, int method) {
        this.activity = fragment.getActivity();
        this.method = method;
        webServiceCall = (WebServiceCall) fragment;
        isServiceCalled = true;
    }

    @Override
    protected void onPreExecute() {

        mDialog = Utility.showProgressBar(activity);
        mDialog.show();
        super.onPreExecute();
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(AppConstants.END_POINT)
                .setClient(new OkClient(okHttpClient)).setLogLevel(RestAdapter.LogLevel.FULL).build();

        api = restAdapter.create(API.class);
    }


    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject mJsonObject = null;
        try {
            mJsonObject = new JSONObject(callService(strings).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return mJsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        webServiceCall.onWebServiceCall(jsonObject, method);
        mDialog.dismiss();
    }

    public JsonObject callService(String... strings) {


        switch (method) {
            case AppConstants.Methods.getLanguage:

                return api.getLanguage();

            case AppConstants.Methods.getClass:
                return api.getClassList(strings[0]);

            case AppConstants.Methods.getChapter:
                return api.getChapterList(strings[0], strings[1]);

            case AppConstants.Methods.getActivity:
                return api.getChapterActivity(strings[0], strings[1], strings[2]);

            case AppConstants.Methods.getDiscussion:
                return api.getChapterDiscussion(strings[0], strings[1], strings[2]);

            case AppConstants.Methods.getParagraphs:
                return api.getChapterParagraph(strings[0], strings[1], strings[2]);

            case AppConstants.Methods.getParagraphGallerry:
                return api.getParagraphGallery(strings[0]);

            case AppConstants.Methods.getResourceCategoryList:
                return api.getResourceCategory();

            case AppConstants.Methods.getResourceDetails:
                return api.getResourceDetails(strings[0],strings[1]);

            case AppConstants.Methods.getDailySaints:
                return api.getDailySaints(strings[0]);
        }

        return null;
    }

    public interface WebServiceCall {
        public void onWebServiceCall(JSONObject mJsonObject, int method);
    }

}
