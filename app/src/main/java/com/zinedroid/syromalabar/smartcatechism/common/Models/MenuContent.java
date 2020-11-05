package com.zinedroid.syromalabar.smartcatechism.common.Models;


import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by Cecil Paul on 9/9/17.
 */

public class MenuContent extends SugarRecord<MenuContent> {
    String contentId;
    String content;
    String classId;
    String languageId;
    String chapterId;
    ArrayList <ImageGallery> imageGallery;
    public ArrayList<ImageGallery> getImageGallery() {
        return imageGallery;
    }

    public void setImageGallery(ArrayList<ImageGallery> imageGallery) {
        this.imageGallery = imageGallery;
    }


    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getContentId() {
        return contentId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
