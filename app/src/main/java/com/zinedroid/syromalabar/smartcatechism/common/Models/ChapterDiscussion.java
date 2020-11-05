package com.zinedroid.syromalabar.smartcatechism.common.Models;

import com.orm.SugarRecord;

/**
 * Created by Cecil Paul on 22/9/17.
 */

public class ChapterDiscussion extends SugarRecord<ChapterDiscussion> {
    String discussionId;
    String content;
    String chapterId;
    String classId;
    String languageId;

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

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }


    public ChapterDiscussion() {

    }
    public String getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChapterDiscussion(String discussionId, String content) {

        this.discussionId = discussionId;
        this.content = content;
    }
}
