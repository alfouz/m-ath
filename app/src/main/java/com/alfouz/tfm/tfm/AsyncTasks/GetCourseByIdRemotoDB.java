package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Util.CourseType;

import java.util.ArrayList;
import java.util.List;

public class GetCourseByIdRemotoDB extends AsyncTask<Long, Void, CourseEntity> {

    CallbackInterface callback;
    AppDatabase db;

    public GetCourseByIdRemotoDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected CourseEntity doInBackground(Long... integers) {
        long idCourse = integers[0];
        CourseEntity courseEntity = db.courseDao().getCourseByIdRemoto(idCourse);

        return courseEntity;
    }

    @Override
    protected void onPostExecute(CourseEntity course) {
        callback.doCallback(course);
    }
}
