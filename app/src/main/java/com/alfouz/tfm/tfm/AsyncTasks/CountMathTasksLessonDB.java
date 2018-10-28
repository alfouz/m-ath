package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;

import java.util.List;

public class CountMathTasksLessonDB extends AsyncTask<Long, Void, Long> {
    CallbackInterface callback;
    AppDatabase db;

    public CountMathTasksLessonDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected Long doInBackground(Long... longs) {
        long idLesson = longs[0];
        LessonEntity lessonEntity = db.lessonDao().getLessonById(idLesson);
        return db.mathTaskDao().countMathTasksByLesson(idLesson);
    }

    @Override
    protected void onPostExecute(Long number) {
        callback.doCallback(number);
    }
}
