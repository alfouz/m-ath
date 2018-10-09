package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;

public class EditLessonDB extends AsyncTask<LessonEntity, Void, LessonEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public EditLessonDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected LessonEntity doInBackground(LessonEntity... lesson) {

        if(lesson[0]!=null) {
            db.lessonDao().updateLesson(lesson[0]);
        }

        return lesson[0];
    }

    @Override
    protected void onPostExecute(LessonEntity lesson) {
        callback.doCallback(lesson);
    }
}
