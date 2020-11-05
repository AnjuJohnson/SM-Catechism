package com.zinedroid.syromalabar.smartcatechism.common.Models;

import java.io.Serializable;

import com.orm.SugarRecord;

public class ImageGallery extends SugarRecord<ImageGallery> implements Serializable {
    String imageId, imagePath;
    String contentId;
    String chapterId;
    String classId;
    String langId;

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getImageId() {
        return imageId;
    }
}
