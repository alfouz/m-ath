package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;

import java.util.ArrayList;
import java.util.List;

public class GetCourseLessonsDB extends AsyncTask<Long, Void, List<LessonEntity>> {
    CallbackInterface callback;
    AppDatabase db;

    public GetCourseLessonsDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected List<LessonEntity> doInBackground(Long... integers) {
        long idCourse = integers[0];
        List<LessonEntity> list = db.lessonDao().getLessonsByCourse(idCourse);
        return list;
    }

    @Override
    protected void onPostExecute(List<LessonEntity> lessons) {
        callback.doCallback(lessons);
    }
}
