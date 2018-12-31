package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;

public class GetCourseByIdRemotoAndStudentDB extends AsyncTask<Long, Void, CourseEntity> {

    CallbackInterface callback;
    AppDatabase db;

    public GetCourseByIdRemotoAndStudentDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected CourseEntity doInBackground(Long... integers) {
        long idCourse = integers[0];
        long idUser = integers[1];
        CourseEntity courseEntity = db.courseDao().getCourseByIdRemotoAndStudent(idCourse, idUser);

        return courseEntity;
    }

    @Override
    protected void onPostExecute(CourseEntity course) {
        callback.doCallback(course);
    }
}
