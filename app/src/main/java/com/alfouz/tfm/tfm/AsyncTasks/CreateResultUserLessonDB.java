package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.ResultUserLessonEntity;

import java.util.Date;

public class CreateResultUserLessonDB extends AsyncTask<Long, Void, ResultUserLessonEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public CreateResultUserLessonDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }


    @Override
    protected ResultUserLessonEntity doInBackground(Long... longs) {
        long idUser = longs[0];
        long idLesson = longs[1];
        long percentCorrect = longs[2];
        Date tryNumber = new Date();

        ResultUserLessonEntity result = new ResultUserLessonEntity();

        result.setUser(idUser);
        result.setLesson(idLesson);
        result.setTimestamp(tryNumber);
        result.setPercentCorrect(percentCorrect);

        db.resultUserLessonDao().insertResultUserLesson(result);

        return result;
    }

    @Override
    protected void onPostExecute(ResultUserLessonEntity result) {
        callback.doCallback(result);
    }
}
