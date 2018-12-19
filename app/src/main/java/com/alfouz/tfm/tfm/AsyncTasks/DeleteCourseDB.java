package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;

public class DeleteCourseDB extends AsyncTask<CourseEntity, Void, Boolean> {
    CallbackInterface callback;
    AppDatabase db;

    public DeleteCourseDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected Boolean doInBackground(CourseEntity... courseEntities) {

        try {
            db.courseDao().deleteCourse(courseEntities[0]);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean isDone) {
        callback.doCallback(isDone);
    }
}
