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

public class GetCourseEntityDB extends AsyncTask<Long, Void, CourseEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public GetCourseEntityDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected CourseEntity doInBackground(Long... integers) {
        long idCourse = integers[0];
        CourseEntity courseEntity = db.courseDao().getCourseById(idCourse);

        return courseEntity;
    }

    @Override
    protected void onPostExecute(CourseEntity courseEntity) {
        callback.doCallback(courseEntity);
    }
}
