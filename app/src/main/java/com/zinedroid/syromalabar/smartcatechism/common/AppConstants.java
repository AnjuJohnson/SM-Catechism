package com.zinedroid.syromalabar.smartcatechism.common;

import android.media.MediaPlayer;

import com.zinedroid.syromalabar.smartcatechism.common.Models.Chapter;
import com.zinedroid.syromalabar.smartcatechism.common.Models.MenuContent;
import com.zinedroid.syromalabar.smartcatechism.common.Models.Resource;
import com.zinedroid.syromalabar.smartcatechism.common.Models.Saints;

/**
 * Created by Cecil Paul on 31/8/17.
 */

public class AppConstants {

    public static final String END_POINT = "http://neptune.geekstorage.com/~smsmartc/api/Smart_api";
    public static final String SHARED_KEY = "Catecism";

    public static Chapter mChapter;
    public static MediaPlayer mPlayer;
    public static Saints mSaints;
    public static MenuContent mMenuContent;
    public static MenuContent mMenuContentGallery;
    public static Resource mResource;
    public static boolean isServiceCalled = false;

    public final class APIKeys {
        public static final String ID = "id";
        public static final String IMAGE = "image";
        public static final String AUDIO = "audio";
        public static final String CONTENT = "content";
        public static final String VIDEO = "video_url";
        public static final String CATEGORY = "category";
        public static final String NAME = "name";
        public static final String DATE = "date";
        public static final String DETAILS = "details";
        public static final String DESCRIPTION = "description";
        public static final String STATUS_CODE = "status_code";

        public static final String LANG_LIST = "language_list";
        public static final String LANGUAGE = "language";
        public static final String LANGUAGE_ID = "lang_id";

        public static final String CLASS_LIST = "class_list";
        public static final String CLASS_ID = "class_id";
        public static final String CLASS_TEXT = "class_text";

        public static final String CHAPTER_LIST = "chapter_list";
        public static final String CHAPTER_ID = "chapter_id";
        public static final String CHAPTER_TITLE = "chapter_title";
        public static final String CHAPTER_CONTEXT = "chapter_context";
        public static final String CHAPTER_AUDIO = "chapter_audio";
        public static final String CHAPTER_IMAGE = "chapter_coverimage";
        public static final String CHAPTER_VIDEO = "chapter_video";

        public static final String PARAGRAPH_ARRAY = "paragraph_list";
        public static final String PARAGRAPH_ID = "paragraph_id";
        public static final String PARAGRAPH_GALLERY_IMAGES = "gallery_data";
        public static final String PARAGRAPH_GALLERY_CONTENT = "img";

        public static final String ACTIVITY_LIST = "activity_list";
        public static final String ACTIVITY_ID = "activity_id";
        public static final String ACTIVITY_QUESTION = "question";
        public static final String ACTIVITY_ANSWER = "answer";
        public static final String ACTIVITY_OPTION1 = "option1";
        public static final String ACTIVITY_OPTION2 = "option2";
        public static final String ACTIVITY_OPTION3 = "option3";
        public static final String ACTIVITY_OPTION4 = "option4";

        public static final String DISCUSSION_LIST = "discussion_list";
        public static final String DISCUSSION_ID = "discussion_id";

        public static final String RESOURCE_DETAILS_LIST = "resource_details";
        public static final String RESOURCE_LIST = "resource";

        public static final String CATEGORY_ID = "cat_id";
        public static final String SAINTS_LIST = "saint_list";

        public static final String CHAPTER_NAME = "lesson";
    }

    public class Methods {
        public static final int getLanguage = 101;
        public static final int getClass = 102;
        public static final int getChapter = 103;
        public static final int getActivity = 104;
        public static final int getDiscussion = 105;
        public static final int getParagraphs = 106;
        public static final int getParagraphGallerry = 107;
        public static final int getResourceCategoryList = 108;
        public static final int getDailySaints = 109;
        public static final int getResourceDetails = 110;
    }

    public class ResponseCode {
        public static final String LANGUAGE_SUCCESS = "200";
        public static final String CLASS_SUCCESS = "201";
        public static final String CHAPTER_SUCCESS = "202";
        public static final String PARAGRAPH_SUCCESS = "205";
        public static final String DISCUSSION_SUCCESS = "204";
        public static final String ACTIVITY_SUCCESS = "203";
        public static final String UNKNOWN_INPUT = "403";
        public static final String NO_DATA_AVAILABLE = "400";
        public static final String CATEGORY_LIST_SUCCESS = "208";
    }
}
