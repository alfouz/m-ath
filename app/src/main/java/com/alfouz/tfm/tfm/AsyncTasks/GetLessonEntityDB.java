package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;

public class GetLessonEntityDB extends AsyncTask<Long, Void, LessonEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public GetLessonEntityDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected LessonEntity doInBackground(Long... integers) {
        long idLesson = integers[0];
        LessonEntity lessonEntity = db.lessonDao().getLessonById(idLesson);

        return lessonEntity;
    }

    @Override
    protected void onPostExecute(LessonEntity lessonEntity) {
        callback.doCallback(lessonEntity);
    }
}
