package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;

import java.util.List;

public class GetMathTasksDB extends AsyncTask<Long, Void, List<MathTaskEntity>> {

    CallbackInterface callback;
    AppDatabase db;

    public GetMathTasksDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected List<MathTaskEntity> doInBackground(Long... integers) {
        long idLesson = integers[0];
        List<MathTaskEntity> list = db.mathTaskDao().getMathTasksByLesson(idLesson);
        return list;
    }

    @Override
    protected void onPostExecute(List<MathTaskEntity> tasks) {
        callback.doCallback(tasks);
    }

}
