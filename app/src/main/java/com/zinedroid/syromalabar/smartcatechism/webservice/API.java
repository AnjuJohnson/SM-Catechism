package com.zinedroid.syromalabar.smartcatechism.webservice;

import com.google.gson.JsonObject;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Cecil Paul on 31/8/17.
 */

public interface API {
    @GET("/list_languages")
    JsonObject getLanguage();

    @GET("/list_class")
    JsonObject getClassList(@Query(AppConstants.APIKeys.LANGUAGE_ID) String languageId);

    @GET("/chapter_list")
    JsonObject getChapterList(@Query(AppConstants.APIKeys.LANGUAGE_ID) String languageId,
                              @Query(AppConstants.APIKeys.CLASS_ID) String classId);

    @FormUrlEncoded
    @POST("/Chapter_activity")
    JsonObject getChapterActivity(@Field(AppConstants.APIKeys.LANGUAGE_ID) String languageId,
                                  @Field(AppConstants.APIKeys.CLASS_ID) String chapterd,
                                  @Field(AppConstants.APIKeys.CHAPTER_ID) String classId);

    @FormUrlEncoded
    @POST("/chapter_discussion")
    JsonObject getChapterDiscussion(@Field(AppConstants.APIKeys.LANGUAGE_ID) String languageId,
                                    @Field(AppConstants.APIKeys.CLASS_ID) String chapterd,
                                    @Field(AppConstants.APIKeys.CHAPTER_ID) String classId);

    @FormUrlEncoded
    @POST("/chapter_paragraphs")
    JsonObject getChapterParagraph(@Field(AppConstants.APIKeys.LANGUAGE_ID) String languageId,
                                   @Field(AppConstants.APIKeys.CLASS_ID) String chapterd,
                                   @Field(AppConstants.APIKeys.CHAPTER_ID) String classId);

    @FormUrlEncoded
    @POST("/paragraph_gallery")
    JsonObject getParagraphGallery(@Field(AppConstants.APIKeys.PARAGRAPH_ID) String languageId);

    @GET("/resource_cat")
    JsonObject getResourceCategory();

    @FormUrlEncoded
    @POST("/resource_cat_details")
    JsonObject getResourceDetails(@Field(AppConstants.APIKeys.LANGUAGE_ID) String languageId,
            @Field(AppConstants.APIKeys.CATEGORY_ID) String categoryId);

    @FormUrlEncoded
    @POST("/daily_saints")
    JsonObject getDailySaints(@Field(AppConstants.APIKeys.LANGUAGE_ID) String languageId);
}
