package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.ResultUserLessonEntity;

import java.util.List;

public class GetResultUserLessonListDB extends AsyncTask<Long, Void, List<ResultUserLessonEntity>> {

    CallbackInterface callback;
    AppDatabase db;

    public GetResultUserLessonListDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected List<ResultUserLessonEntity> doInBackground(Long... integers) {
        long idUser = integers[0];
        long idLesson = integers[1];
        List<ResultUserLessonEntity> list = db.resultUserLessonDao().getResultUserLessonList(idUser, idLesson);
        return list;
    }

    @Override
    protected void onPostExecute(List<ResultUserLessonEntity> options) {
        callback.doCallback(options);
    }

}
