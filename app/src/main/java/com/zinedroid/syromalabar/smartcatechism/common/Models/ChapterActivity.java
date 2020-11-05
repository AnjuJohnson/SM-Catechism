package com.zinedroid.syromalabar.smartcatechism.common.Models;

import com.orm.SugarRecord;

/**
 * Created by Cecil Paul on 22/9/17.
 */

public class ChapterActivity extends SugarRecord<ChapterActivity> {
    String activuty_id;
    String queston;
    String answer;
    String option1;
    String option2;
    String option3, chapterId;
    String classId;

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

    String languageId;

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getActivuty_id() {
        return activuty_id;
    }

    public void setActivuty_id(String activuty_id) {
        this.activuty_id = activuty_id;
    }

    public String getQueston() {
        return queston;
    }

    public void setQueston(String queston) {
        this.queston = queston;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public ChapterActivity() {

    }

    public ChapterActivity(String activuty_id, String queston, String answer, String option1, String option2, String option3, String option4) {

        this.activuty_id = activuty_id;
        this.queston = queston;
        this.answer = answer;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    String option4;

}
