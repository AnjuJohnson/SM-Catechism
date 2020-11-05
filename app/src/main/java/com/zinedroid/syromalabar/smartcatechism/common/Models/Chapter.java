package com.zinedroid.syromalabar.smartcatechism.common.Models;

import com.orm.SugarRecord;

/**
 * Created by Cecil Paul on 6/9/17.
 */

public class Chapter extends SugarRecord<Chapter> {
    String chapterId;
    String classId;
    String langId;
    String chapterTitle;
    String chapterNo;


    public String getLang_id() {
        return langId;
    }

    public void setLang_id(String lang_id) {
        this.langId = lang_id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(String chapterNo) {
        this.chapterNo = chapterNo;
    }

    String ChapterContext;
    String chapterVideoLink;
    String chapterAudioLink;
    String chapterImage;

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getChapterContext() {
        return ChapterContext;
    }

    public void setChapterContext(String chapterContext) {
        ChapterContext = chapterContext;
    }

    public String getChapterVideoLink() {
        return chapterVideoLink;
    }

    public void setChapterVideoLink(String chapterVideoLink) {
        this.chapterVideoLink = chapterVideoLink;
    }

    public String getChapterAudioLink() {
        return chapterAudioLink;
    }

    public void setChapterAudioLink(String chapterAudioLink) {
        this.chapterAudioLink = chapterAudioLink;
    }

    public String getChapterImage() {
        return chapterImage;
    }

    public void setChapterImage(String chapterImage) {
        this.chapterImage = chapterImage;
    }
}
