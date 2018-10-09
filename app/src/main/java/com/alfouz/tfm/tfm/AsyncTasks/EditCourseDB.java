package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;

public class EditCourseDB extends AsyncTask<CourseEntity, Void, CourseEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public EditCourseDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected CourseEntity doInBackground(CourseEntity... course) {

        if(course[0]!=null) {
            db.courseDao().updateCourse(course[0]);
        }

        return course[0];
    }

    @Override
    protected void onPostExecute(CourseEntity course) {
        callback.doCallback(course);
    }
}
